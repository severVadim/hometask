package home.task.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


@Slf4j
public class FramesPage extends BasePage {

    public FramesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Wait until Frames page will be ready")
    public void waitUntilFramePageIsLoaded() {
        if (waitVisibility(By.xpath("//h3[contains(text(), 'Frames')]")) == null) {
            Assert.fail("Frames page isn't loaded");
        }
    }

    @Step("Select frame - {0}")
    public void selectFrameWithName(String frame) {
        clickOnlLinkWithText(frame);
    }

}
