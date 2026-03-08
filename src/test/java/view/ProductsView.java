package view;

import driver.MobileDriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class ProductsView {

    private AppiumDriver driver;

    public ProductsView() {
        this.driver = MobileDriverManager.getDriver();
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),
                this
        );
    }

    // ================================
    // PRODUCT LIST
    // ================================

    @AndroidFindBy(accessibility = "Displays all products of catalog")
    @iOSXCUITFindBy(accessibility = "Displays all products of catalog")
    private List<WebElement> productList;

    // ================================
    // CART ICON
    // ================================

    @AndroidFindBy(accessibility = "Displays number of items in your cart")
    @iOSXCUITFindBy(accessibility = "Cart")
    private WebElement cartIcon;

    // ================================
    // ACTIONS
    // ================================

    public void waitForApp() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(cartIcon));
    }

    public boolean isProductListOnScreen() {
        return !productList.isEmpty();
    }

    public void tapDesiredProduct(String productName) {
        WebElement productText = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().text(\"" + productName + "\"))"
                )
        );

        driver.findElement(AppiumBy.xpath(
                "//android.view.ViewGroup[android.widget.TextView[@text='" + productName + "']]//android.widget.ImageView"
        )).click();
    }
}
