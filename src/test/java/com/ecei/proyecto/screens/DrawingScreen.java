package com.ecei.proyecto.screens;
import com.ecei.proyecto.pom.BaseScreen;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import java.time.Duration;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DrawingScreen extends BaseScreen {

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/signature_pad")
    private WebElement signaturePad;

    public DrawingScreen(AndroidDriver driver) {
        super(driver);
    }

    public boolean isDisplayed() {
        return signaturePad.isDisplayed();
    }

    public void drawSmileyFace() {
        Rectangle r = signaturePad.getRect();

        int left = r.getX();
        int top = r.getY();
        int w = r.getWidth();
        int h = r.getHeight();

        int cx = left + w / 2;

        int eyeY = top + (int)(h * 0.40);
        int eyeOffsetX = (int)(w * 0.14);
        int eyeSize = Math.max(6, (int)(w * 0.015));

        strokeW3C(List.of(
                new int[]{cx - eyeOffsetX, eyeY},
                new int[]{cx - eyeOffsetX + eyeSize, eyeY + eyeSize}
        ), 180);

        strokeW3C(List.of(
                new int[]{cx + eyeOffsetX, eyeY},
                new int[]{cx + eyeOffsetX + eyeSize, eyeY + eyeSize}
        ), 180);

        int smileCy = top + (int)(h * 0.58);
        int rx = (int)(w * 0.22);
        int ry = (int)(h * 0.16);

        List<int[]> smile = new ArrayList<>();
        int points = 20;
        for (int i = 0; i <= points; i++) {
            double t = Math.toRadians(20 + (140.0 * i / points));
            int x = (int) Math.round(cx + rx * Math.cos(t));
            int y = (int) Math.round(smileCy + ry * Math.sin(t));
            smile.add(new int[]{x, y});
        }

        strokeW3C(smile, 900);
    }

    private void strokeW3C(List<int[]> pts, int durationMs) {
        if (pts == null || pts.size() < 2) return;

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence seq = new Sequence(finger, 1);

        int[] start = pts.get(0);
        seq.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), start[0], start[1]));
        seq.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));

        int steps = pts.size() - 1;
        int perStep = Math.max(20, durationMs / steps);

        for (int i = 1; i < pts.size(); i++) {
            int[] p = pts.get(i);
            seq.addAction(finger.createPointerMove(Duration.ofMillis(perStep), PointerInput.Origin.viewport(), p[0], p[1]));
        }

        seq.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(seq));
    }

}