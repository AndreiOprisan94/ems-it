package com.ems.it;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public final class EmsIT {

    private static WebDriver driver;
    private static final String baseURL = "http://localhost:8080/login";

    @BeforeClass
    public static void init() {
        System.setProperty("webdriver.chrome.driver", "/Users/aoprisan/Documents/Facultate/EPA/web-driver/chromedriver");
        driver = new ChromeDriver();
        driver.get(baseURL);
    }

    @Test
    public void shouldSuccesfullLogin() {
        final WebElement usernameInput = driver.findElement(By.id("j_username"));
        final WebElement passwordInput = driver.findElement(By.id("j_password"));

        usernameInput.sendKeys("andreiopr");
        passwordInput.sendKeys("andreiopr");

        final WebElement loginButton = driver.findElement(By.id("j_idt17"));
        loginButton.click();

        final List<WebElement> images = driver.findElements(By.tagName("img"));

        Optional<String> loggedIn = images.stream()
                                        .map(element -> element.getAttribute("src"))
                                        .filter(Objects::nonNull)
                                        .filter(attribute -> attribute.contains("home.jpg"))
                                        .findFirst();

        assertTrue("User is not logged in",loggedIn.isPresent());

    }

    @Test
    public void shoudSuccesfullLogout() {
        final List<WebElement> spans = driver.findElements(By.tagName("span"));

        spans.stream()
                .filter(webElement -> webElement.getAttribute("class") != null)
                .filter(webElement -> webElement.getAttribute("class").equals("ui-button-text ui-c"))
                .filter(webElement -> webElement.getText().equals("Options"))
                .findFirst()
                .ifPresent(webElement -> webElement.click());

        final String logoutOnClickIdentifier
                = "PrimeFaces.addSubmitParam('j_idt11',{'j_idt11:j_idt66':'j_idt11:j_idt66'}).submit('j_idt11');return false;";

        final List<WebElement> as = driver.findElements(By.tagName("a"));

        as.stream()
                .filter(webElement -> webElement.getAttribute("onclick") != null)
                .filter(webElement -> webElement.getAttribute("onclick").equals(logoutOnClickIdentifier))
                .findFirst()
                .ifPresent(webElement -> webElement.click());

        try {
            assertNotNull(driver.findElement(By.id("j_idt17")));
        } catch (final NoSuchElementException noElementFound) {
            fail("Could not properly logout");
        }
    }

}
