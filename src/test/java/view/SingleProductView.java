package view;

import driver.MobileDriverManager;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SingleProductView {

    private AppiumDriver driver;

    public SingleProductView() {
        this.driver = MobileDriverManager.getDriver();
        PageFactory.initElements(
                new AppiumFieldDecorator(driver, Duration.ofSeconds(10)),
                this
        );
    }

    public void waitForPage() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(productImage));
    }

    // ================================
    // INCREASE QUANTITY BUTTON
    // ================================

    @AndroidFindBy(accessibility = "Increase item quantity")
    @iOSXCUITFindBy(accessibility = "Increase item quantity")
    private WebElement increaseProduct;

    // ================================
    // ADD TO CART BUTTON
    // ================================

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartBt")
    @iOSXCUITFindBy(iOSClassChain = "**/XCUIElementTypeImage[1]")
    private WebElement addToCart;

    //
    // ================================
    // CART NUMBER
    // ================================

    @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/cartTV")
    @iOSXCUITFindBy(accessibility = "cartTV")
    private WebElement cartNumber;

    //
    // ================================
    // CART BUTTON
    // ================================
    @AndroidFindBy(accessibility = "View cart")
    @iOSXCUITFindBy(accessibility = "View cart")
    private WebElement cartButton;

    // ================================
    // PRODUCT IMAGE
    // ================================

    @AndroidFindBy(accessibility = "Displays selected product")
    @iOSXCUITFindBy(accessibility = "Displays selected product")
    private WebElement productImage;

    // ================================
    // ACTIONS
    // ================================

    public void openCart() {
        cartButton.click();
    }

    public void tapAddToCart() {
        // Hacemos scroll hasta encontrar el botón
        WebElement product = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().description(\"" + "Increase item quantity" + "\"))"
                )
        );
        addToCart.click();
    }

    public void tapIncreaseProduct() {
        // Hacemos scroll hasta encontrar el botón
        WebElement product = driver.findElement(
                AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                                ".scrollIntoView(new UiSelector().description(\"" + "Tap to add product to cart" + "\"))"
                )
        );
        increaseProduct.click();
    }

    public int getProductQuantity() {
        try {
            // Guardamos el tiempo del implicitWait debido a que el elemento al inicio no existe y no queremos esperar 10 segundos
            Duration defaultWait = driver.manage().timeouts().getImplicitWaitTimeout();
            // Desactivamos el implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));

            List<WebElement> elements = driver.findElements(AppiumBy.id("com.saucelabs.mydemoapp.android:id/cartTV"));

            // Restauramos implicit wait original
            driver.manage().timeouts().implicitlyWait(defaultWait);

            if (elements.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(elements.get(0).getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
