package view;

import driver.MobileDriverManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartView {

    private AppiumDriver driver;

    public CartView() {
        this.driver = MobileDriverManager.getDriver();
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),
                this
        );
    }

    public void waitForPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(cartButton));
    }

    // ================================
    // cartButton
    // ================================
    @AndroidFindBy(accessibility = "View cart")
    @iOSXCUITFindBy(accessibility = "View cart")
    private WebElement cartButton;

    // ================================
    // PRODUCT NAME
    // ================================
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    @iOSXCUITFindBy(id = "com.saucelabs.mydemoapp.android:id/titleTV")
    private WebElement productName;

    // ================================
    // PRODUCT QUANTITY
    // ================================
    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    @iOSXCUITFindBy(id = "com.saucelabs.mydemoapp.android:id/noTV")
    private WebElement productQuantity;

    // ================================
    // ACTIONS
    // ================================

    public String getProductName() {
        return productName.getText();
    }

    public int getProductQuantity() {
        return Integer.parseInt(productQuantity.getText());
    }
}
