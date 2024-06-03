package com.sparta.testFramework.stepdefs;

import com.sparta.testFramework.lib.pages.*;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.Matchers.containsString;

public class NavigateStepdefs extends abstractStepdef {

    private HomePage homePage;
    private CreateAccountPage createAccount;
    private SignInPage signIn;
    private SalePage salePage;
    private JacketsPage jacketsPage;
    private CheckOutPage checkOutPage;

    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void setUp(){
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @After
    public void afterEach(){
        webDriver.quit();
    }

    @AfterAll
    public static void afterAll(){
        service.stop();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage(){
        webDriver.get("https://magento.softwaretestingboard.com/");
        homePage = new HomePage(webDriver);
    }
    @When("I click on 'Create an Account' link")
    public void iClickOnCreateLink(){
        createAccount = homePage.goToCreateAccountPage();
    }
    @Then("I am taken to an account creation page")
    public void iAmOnTheCreationPage(){
        MatcherAssert.assertThat(createAccount.getUrl(), Is.is("https://magento.softwaretestingboard.com/customer/account/create/"));
    }

    @When("I click on 'Sign In' link")
    public void iClickOnSignInLink(){
        signIn = homePage.goToSignInPage();
    }
    @Then("I am taken to Customer login page")
    public void iAmOnTheSignInPage(){
        MatcherAssert.assertThat(signIn.getUrl(), containsString("https://magento.softwaretestingboard.com/customer/account/login/referer/"));
    }

    @When("I click on 'Sale' link")
    public void iClickOnSaleLink(){
        salePage = homePage.goToSalePage();
    }
    @Then("I am taken to the Sale page")
    public void iAmOnTheSalePage(){
        MatcherAssert.assertThat(salePage.getUrl(), Is.is("https://magento.softwaretestingboard.com/sale.html"));
    }

    @And("I hover over 'Men' link")
    public void iHoverOverMenLink(){
        homePage.hoverOverMensLink();
    }
    @And("I move to hover over 'Tops' link")
    public void iMoveToHoverOverTopsLink(){
        homePage.hoverOverTopsLink();
    }
    @When("I click the 'Jackets' link")
    public void iClickTheJacketsLink(){
       jacketsPage = homePage.goToJacketsPage();
    }
    @Then("I am taken to the Jackets page")
    public void iAmOnTheJacketsPage(){
        MatcherAssert.assertThat(jacketsPage.getUrl(), Is.is("https://magento.softwaretestingboard.com/men/tops-men/jackets-men.html"));
    }

    @And("There are items inside the basket")
    public void iAddItemToBasket() {
        homePage.selectAndAddRadiantTeeToBasket();
    }
    @When("I click on the basket icon link")
    public void iClickOnBasketIcon() throws InterruptedException {
        homePage.waitFor(5);
        homePage.clickBasketIcon();
    }
    @And("I click on the 'View and Edit Cart' link")
    public void iClickViewAndEditCart() {
        checkOutPage = homePage.goToViewAndEditCart();
    }
    @Then("I am taken to the basket page")
    public void iAmOnTheCheckOutPage(){
        MatcherAssert.assertThat(checkOutPage.getUrl(), Is.is("https://magento.softwaretestingboard.com/checkout/cart/"));
    }


}
