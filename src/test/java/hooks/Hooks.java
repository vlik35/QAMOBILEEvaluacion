package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import driver.MobileDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("=== Starting Scenario ===");
        MobileDriverManager.createDriver();
    }

    // ✅ Screenshot after EVERY step
    @AfterStep
    public void takeScreenshot(Scenario scenario) {

        try {
            byte[] screenshot = ((TakesScreenshot) MobileDriverManager
                    .getDriver())
                    .getScreenshotAs(OutputType.BYTES);

            scenario.attach(screenshot, "image/png", "Step Screenshot");

        } catch (Exception e) {
            System.out.println("Could not capture screenshot: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        System.out.println("=== Ending Scenario ===");
        MobileDriverManager.quitDriver();
    }
}
