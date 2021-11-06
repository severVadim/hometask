package home.task.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


@Slf4j
public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Wait until Home page will be ready")
    public void waitUntilHomePageIsLoaded() {
        if (waitVisibility(By.xpath("//h2[contains(text(), 'Available Examples')]")) == null) {
            Assert.fail("Home page isn't loaded");
        }
    }

    @Step("Click on example {0}")
    public void openExample(String example) {
        clickOnlLinkWithText(example);
    }
}
