package driver;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.URL;
import java.time.Duration;

/**
 * Enterprise-ready MobileDriverManager
 *
 * ✅ Supports Android
 * ✅ Supports iOS
 * ✅ ThreadLocal (parallel ready)
 * ✅ Platform configurable
 */
public class MobileDriverManager {

    private static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static void createDriver() {

        String platform = System.getProperty("platform", "ANDROID").toLowerCase();
        String configFile = "config/" + platform + ".properties";

        try {

            java.util.Properties props = new java.util.Properties();
            java.io.InputStream input =
                    MobileDriverManager.class.getClassLoader().getResourceAsStream(configFile);

            if (input == null) {
                throw new RuntimeException("Config file not found: " + configFile);
            }

            props.load(input);

            String hub = props.getProperty("hub");
            int implicitWait = Integer.parseInt(props.getProperty("implicitWait"));

            switch (platform) {

                case "android":

                    UiAutomator2Options androidOptions = new UiAutomator2Options();
                    androidOptions.setPlatformName(props.getProperty("platformName"));
                    androidOptions.setAutomationName(props.getProperty("automationName"));
                    androidOptions.setDeviceName(props.getProperty("deviceName"));
                    androidOptions.setApp(props.getProperty("appPath"));
                    androidOptions.setAppPackage(props.getProperty("appPackage"));
                    androidOptions.setAppActivity(props.getProperty("appActivity"));

                    driver.set(new AndroidDriver(new URL(hub), androidOptions));
                    break;

                case "ios":

                    XCUITestOptions iosOptions = new XCUITestOptions();
                    iosOptions.setPlatformName(props.getProperty("platformName"));
                    iosOptions.setAutomationName(props.getProperty("automationName"));
                    iosOptions.setDeviceName(props.getProperty("deviceName"));
                    iosOptions.setApp(props.getProperty("appPath"));
                    iosOptions.setBundleId(props.getProperty("bundleId"));

                    driver.set(new IOSDriver(new URL(hub), iosOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

            driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));

        } catch (Exception e) {
            throw new RuntimeException("Error creating mobile driver for platform: " + platform, e);
        }
    }

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
