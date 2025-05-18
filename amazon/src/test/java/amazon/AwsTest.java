package amazon;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AwsTest {

    static Dotenv dotenv = Dotenv.configure()
            .directory("/home/mazinini/Desktop/automation-selenium/amazon/.env")
            .load();

    static String email = dotenv.get("EMAIL");
    static String password = dotenv.get("PASSWORD");
    public static String browser = "Edge";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setup() {
        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testAmazonRouterSearch() {
        try {
            driver.get("https://www.amazon.in/");
            System.out.println(" Searching for TP-Link Router on Amazon...");

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox")));
            searchBox.sendKeys("TP-Link Router");

            WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-search-submit-button")));
            submitButton.click();

        } catch (TimeoutException e) {
            System.err.println("Page took too long to load or element not found.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIncredibleRouterSearch() {
        try {
            driver.get("https://www.incredible.co.za/customer/account/login/");

            WebElement emailField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));
            emailField.sendKeys(email);

            WebElement passwordField = driver.findElement(By.id("pass"));
            passwordField.sendKeys(password);

            WebElement loginButton = driver.findElement(By.id("send2"));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("/customer/account"));
            System.out.println("✅ Login successful!");

            // Visit product page and add item to cart
            driver.get("https://www.incredible.co.za/tp-link-m7350-lte-mobile-wifi-router");
            WebElement addToCartBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@title='Add to Cart']")));

            AddCart.addItemMultipleTimes(driver, wait, addToCartBtn, 3);
            System.out.println("Added router to cart.");

            // Open cart
            try {
                WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='html-body']/div[5]/header/div[1]/div[1]/div[3]/div[2]/a/span[1]")
                ));
                cartIcon.click();
                System.out.println("Opened cart.");
            } catch (TimeoutException e) {
                System.err.println("Cart icon not found or not clickable.");
                e.printStackTrace();
            }

            // Proceed to checkout
            WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("top-cart-btn-checkout")));
            checkoutButton.click();
            System.out.println("✅ Clicked on checkout.");
            Thread.sleep(5000);


            try {
                Thread.sleep(3000);

                if (driver.findElements(By.id("customer-email")).size() > 0) {
                    WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("customer-email")));
                    emailInput.clear();
                    emailInput.sendKeys(email);

                    WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pass")));
                    passwordInput.clear();
                    passwordInput.sendKeys(password);

                    WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("send2")));
                    signInButton.click();
                    System.out.println("Signed in during checkout.");
                } else {
                    System.out.println("No checkout sign-in form displayed (possibly already signed in).");
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Error handling checkout login.");
                e.printStackTrace();
            }


            try {
                WebElement decreaseItem = driver.findElement(By.xpath("//*[@id='mini-cart']/li/div/div/div[1]/div[2]/div/button[1]/span"));
                decreaseItem.click();
                decreaseItem.click();
            } catch (Exception e) {
                e.printStackTrace();
            }

            driver.get("https://www.incredible.co.za/checkout/#shipping");
            WebElement link = driver.findElement(By.xpath("//*[@id='maincontent']/div[2]/div/div[2]/p[2]/a"));
            link.click();

            System.out.println("Link clicked successfully.");


            WebElement secondShippingOption = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id='checkout-shipping-method-load']/table/tbody/tr[2]/td/div[1]/div/div[2]")
            ));
            secondShippingOption.click();

            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                WebElement shippingMethodBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='checkout-shipping-method-load']/table/tbody/tr[2]/td/div[1]/div/div[1]/button[2]/span")
                ));
                shippingMethodBtn.click();
                System.out.println("Selected shipping method.");
            } catch (TimeoutException e) {
                System.err.println("❌ Shipping method selection took too long.");
                e.printStackTrace();
            }


            try {
                WebElement continueToPaymentBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id='opc-sidebar']/div[1]/div[1]/button/span")
                ));
                continueToPaymentBtn.click();
                System.out.println(" Continued to payment.");
            } catch (TimeoutException e) {
                System.err.println("Continue to Payment' button not found.");
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
