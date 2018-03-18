import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Objects;

public class MainIT {

    public static void main(String[] args) {
        System.out.println("Hello World from IT");

        System.setProperty("webdriver.chrome.driver", "/Users/aoprisan/Documents/Facultate/EPA/web-driver/chromedriver");

        final WebDriver driver = new ChromeDriver();

        final String baseURL = "http://localhost:8080/login";

        driver.get(baseURL);

        final WebElement usernameInput = driver.findElement(By.id("j_username"));
        final WebElement passwordInput = driver.findElement(By.id("j_password"));

        usernameInput.sendKeys("andreiopr");
        passwordInput.sendKeys("andreiopr");

        final WebElement login = driver.findElement(By.id("j_idt17"));
        login.click();

        final List<WebElement> images = driver.findElements(By.tagName("img"));

        images.stream()
                .map(element -> element.getAttribute("src"))
                .filter(Objects::nonNull)
                .filter(attribute -> attribute.contains("home.jpg"))
                .findFirst()
                .ifPresent(element -> System.out.println("M-am logat cu succes!"));

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


        if (driver.findElement(By.id("j_idt17")) != null) {
            System.out.println("M-am delogat cu succes");
        }

//        driver.close();
    }
}
