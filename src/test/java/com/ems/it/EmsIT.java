package com.ems.it;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public final class EmsIT {

    private static WebDriver driver;
    private static final String BASE_URL = "http://localhost:8080/login";
    private static final String BROWSER_DRIVER = "webdriver.chrome.driver";
    private static final String BROWSER_DRIVER_FILE_PATH = "/Users/aoprisan/Documents/Facultate/EPA/web-driver/chromedriver";

    @BeforeClass
    public static void init() {
        System.setProperty(BROWSER_DRIVER, BROWSER_DRIVER_FILE_PATH);
        driver = new ChromeDriver();
        driver.get(BASE_URL);
    }

    @Test
    public void a_shouldDoSuccessfulLogin() {
        final WebElement usernameInput = driver.findElement(By.id("j_username"));
        final WebElement passwordInput = driver.findElement(By.id("j_password"));

        final String emsUsername = "andreiopr";
        final String emsPassword = "andreiopr";

        usernameInput.sendKeys(emsUsername);
        passwordInput.sendKeys(emsPassword);

        final WebElement loginButton = driver.findElement(By.id("j_idt17"));
        loginButton.click();

        final List<WebElement> images = driver.findElements(By.tagName("img"));

        Optional<String> loggedIn = images.stream()
                .map(element -> element.getAttribute("src"))
                .filter(Objects::nonNull)
                .filter(attribute -> attribute.contains("home.jpg"))
                .findFirst();

        assertTrue("User is not logged in", loggedIn.isPresent());

    }

    @Test
    public void b_shouldHaveBonus() {
//        How to extract al childs of an element
//        final List<WebElement> childs = bonusList.findElements(By.xpath(".//*"));

        driver.get("http://localhost:8080/pages/my-bonuses.xhtml");

        final WebElement bonusList = driver.findElement(By.id("form:bonusList_list"));

        final List<WebElement> bonusListContent = bonusList.findElement(By.tagName("tbody"))
                .findElement(By.tagName("tbody"))
                .findElements(By.tagName("tr"));


        final List<Tuple2<String>> contents = bonusListContent.stream()
                .map(this::extractTuple)
                .collect(Collectors.toList());

        final String expectedProposedBy = "Oprisan Andrei";
        final String expectedBonusType = "???Performance???";
        final float expectedBonusValue = 234.56f;

        assertEquals("Not expected proposed by", expectedProposedBy, contents.get(0).getY());
        assertEquals("Not expected bonus type", expectedBonusType, contents.get(1).getY());
        assertEquals("Not expected bonus value", expectedBonusValue, Float.parseFloat(contents.get(2).getY()), 0.0002f);

        try {
            Thread.sleep(5000);
        } catch (final InterruptedException interrupted) {
            fail("Thread Exception encountered");
        }

        driver.navigate().back();

    }

    private Tuple2<String> extractTuple(WebElement webElement) {
        final List<WebElement> tds = webElement.findElements(By.tagName("td"));

        final String first = tds.get(0).getText();
        final String second = tds.get(1).getText();

        return new Tuple2<>(first, second);
    }

    @Test
    public void c_shouldDoSuccessfulLogout() {
        final List<WebElement> spans = driver.findElements(By.tagName("span"));

        spans.stream()
                .filter(webElement -> webElement.getAttribute("class") != null)
                .filter(webElement -> webElement.getAttribute("class").equals("ui-button-text ui-c"))
                .filter(webElement -> webElement.getText().equals("Options"))
                .findFirst()
                .ifPresent(WebElement::click);

        final String logoutOnClickIdentifier
                = "PrimeFaces.addSubmitParam('j_idt11',{'j_idt11:j_idt66':'j_idt11:j_idt66'}).submit('j_idt11');return false;";

        final List<WebElement> as = driver.findElements(By.tagName("a"));

        as.stream()
                .filter(webElement -> webElement.getAttribute("onclick") != null)
                .filter(webElement -> webElement.getAttribute("onclick").equals(logoutOnClickIdentifier))
                .findFirst()
                .ifPresent(WebElement::click);

        try {
            assertNotNull(driver.findElement(By.id("j_idt17")));
        } catch (final NoSuchElementException noElementFound) {
            fail("Could not properly logout");
        }
    }

    public static final class Tuple2<T> {
        private final T x;
        private final T y;

        Tuple2(T x, T y) {
            this.x = x;
            this.y = y;
        }

        T getX() {
            return x;
        }

        T getY() {
            return y;
        }
    }

}
