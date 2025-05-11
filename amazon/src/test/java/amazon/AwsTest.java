package amazon;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;

public class AwsTest {

    public static String browser = "Edge";

    private WebDriver setupBrowser() {
        if (browser.equalsIgnoreCase("Firefox")) {
            WebDriverManager.firefoxdriver().setup();
            return new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("Chrome")) {
            WebDriverManager.chromedriver().setup();
            return new ChromeDriver();
        } else if (browser.equalsIgnoreCase("Edge")) {
            WebDriverManager.edgedriver().setup();
            return new EdgeDriver();
        } else {
            throw new RuntimeException("Unsupported browser: " + browser);
        }
    }

    @Test
    public void testAmazonRouterSearch() {
        WebDriver driver = null;

        try {
            driver = setupBrowser();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().window().maximize();
            driver.get("https://www.amazon.in/");
            driver.manage().deleteAllCookies();

            System.out.println("Searching for TP-Link Router on Amazon...");
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
            searchBox.sendKeys("TP-Link Router");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
            submitButton.click();

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Test
    public void testIncredibleRouterSearch() {
        WebDriver driver = null;

        try {
            driver = setupBrowser();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().window().maximize();
            driver.get("https://www.incredible.co.za/");
            driver.manage().deleteAllCookies();

            System.out.println("Searching for TP-Link Router on Incredible...");
            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search")));
            searchBox.sendKeys("TP-Link M7350 LTE- Mobile WIFI Router");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//form[@id='search_mini_form']//button[@title='Search']")));
            submitButton.click();

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    @Test
    public void testAwsHomepageLoad() {
        WebDriver driver = null;

        try {
            driver = setupBrowser();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
            driver.manage().window().maximize();
            driver.get("https://aws.amazon.com/");
            driver.manage().deleteAllCookies();

            System.out.println("Verifying AWS homepage...");
            WebElement createAccountBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//a[contains(text(),'Create an AWS Account')]")));

            System.out.println("AWS homepage loaded successfully. Found button: " + createAccountBtn.getText());

        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
