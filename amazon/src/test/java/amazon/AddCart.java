package amazon;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebDriver;

public class AddCart {

    public static void addItemMultipleTimes(WebDriver driver, WebDriverWait wait, WebElement addToCartBtn, int times) {
        for (int i = 0; i < times; i++) {
            addToCartBtn.click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("div.message-success.success.message")));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        }
    }
}
