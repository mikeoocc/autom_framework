package com.ecei.proyecto.reporting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.PickleStepTestStep;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.TestCase;
import io.cucumber.plugin.event.TestCaseFinished;
import io.cucumber.plugin.event.TestCaseStarted;
import io.cucumber.plugin.event.TestStepFinished;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DashboardReporter implements ConcurrentEventListener {

    private static final String DEFAULT_URL = "http://localhost:8080/results";

    private final String dashboardUrl = resolveUrl();
    private final ObjectMapper mapper = new ObjectMapper();
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    /** Pasos acumulados por escenario en ejecución. */
    private final Map<TestCase, List<ObjectNode>> stepsByCase = new ConcurrentHashMap<>();

    private static String resolveUrl() {
        String sys = System.getProperty("dashboard.url");
        if (sys != null && !sys.isBlank()) {
            return sys;
        }
        String env = System.getenv("DASHBOARD_URL");
        return (env != null && !env.isBlank()) ? env : DEFAULT_URL;
    }

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestCaseStarted.class, this::onCaseStarted);
        publisher.registerHandlerFor(TestStepFinished.class, this::onStepFinished);
        publisher.registerHandlerFor(TestCaseFinished.class, this::onCaseFinished);
    }

    private void onCaseStarted(TestCaseStarted event) {
        stepsByCase.put(event.getTestCase(), Collections.synchronizedList(new ArrayList<>()));
    }

    private void onStepFinished(TestStepFinished event) {
        if (!(event.getTestStep() instanceof PickleStepTestStep step)) {
            return;
        }
        List<ObjectNode> steps = stepsByCase.get(event.getTestCase());
        if (steps == null) {
            return;
        }
        Result result = event.getResult();
        ObjectNode node = mapper.createObjectNode();
        node.put("keyword", step.getStep().getKeyword());
        node.put("name", step.getStep().getText());
        node.put("status", result.getStatus().name());
        node.put("durationMs", result.getDuration().toMillis());
        if (result.getError() != null) {
            node.put("errorMessage", result.getError().toString());
        }
        steps.add(node);
    }

    private void onCaseFinished(TestCaseFinished event) {
        TestCase testCase = event.getTestCase();
        List<ObjectNode> steps = stepsByCase.remove(testCase);
        Result result = event.getResult();

        ObjectNode payload = mapper.createObjectNode();
        payload.put("featureName", featureName(testCase));
        payload.put("scenarioName", testCase.getName());
        payload.put("status", result.getStatus().name());
        payload.put("durationMs", result.getDuration().toMillis());
        if (result.getError() != null) {
            payload.put("errorMessage", result.getError().toString());
        }
        ArrayNode stepsArray = payload.putArray("steps");
        if (steps != null) {
            steps.forEach(stepsArray::add);
        }

        send(payload);
    }

    private String featureName(TestCase testCase) {
        String path = testCase.getUri().getSchemeSpecificPart();
        int slash = path.lastIndexOf('/');
        String file = slash >= 0 ? path.substring(slash + 1) : path;
        return file.endsWith(".feature")
                ? file.substring(0, file.length() - ".feature".length())
                : file;
    }

    private void send(ObjectNode payload) {
        try {
            String body = mapper.writeValueAsString(payload);
            HttpRequest request = HttpRequest.newBuilder(URI.create(dashboardUrl))
                    .header("Content-Type", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<Void> response = http.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() >= 300) {
                System.err.println("[DashboardReporter] el dashboard respondió HTTP " + response.statusCode());
            }
        } catch (Exception e) {
            System.err.println("[DashboardReporter] no se pudo enviar el resultado: " + e.getMessage());
        }
    }
}
