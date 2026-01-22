package com.ecei.proyecto.appiumgluecode;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

public class ModalSteps extends BaseTest {

    @Given("El usuario esta en Home")
    public void elUsuarioEstaEnHome() {
        Assert.assertTrue(home().isDisplayed());
    }

    @When("Abre el Menu y entra en About")
    public void abreMenuYEntraEnAbout() {
        home().openMenu();
        Assert.assertTrue(home().menu().isOpen());
        home().menu().tapAbout();
    }

    @Then("Llega a About")
    public void llegaAAbout() {
        Assert.assertTrue(about().isDisplayed());
    }

    @When("Abre el Menu y entra en Drawing")
    public void abreMenuYEntraEnDrawing() {
        home().openMenu();
        Assert.assertTrue(home().menu().isOpen());
        home().menu().tapDrawing();
    }

    @And("Llega a Drawing")
    public void llegaADrawing() {
        Assert.assertTrue(drawing().isDisplayed());
    }

    @Then("Dibuja una cara sonriente")
    public void dibujaUnaCaraSonriente() {
        drawing().drawSmileyFace();
    }

}
