package home.task.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


@Slf4j
public class DynamicallyLoadedElementsPage extends BasePage {

    public DynamicallyLoadedElementsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Wait until Dynamically Loaded Page Elements page will be ready")
    public void waitUntilDLPEPageIsLoaded() {
        if (waitVisibility(By.xpath("//h3[contains(text(), 'Dynamically Loaded Page Elements')]")) == null) {
            Assert.fail("Dynamically Loaded Page Elements page isn't loaded");
        }
    }

    @Step("Select example - {0}")
    public void selectExampleWithName(String frame) {
        clickOnlLinkWithText(frame);
    }

    @Step("Click button 'Start'")
    public void clickStartButton() {
        click(By.xpath("//button[text()='Start']"));
    }

    @Step("Wait until text {0} will be rendered")
    public void waitUntilTextWillBeRendered(String element) {
        if (waitVisibility(By.xpath(String.format("//h4[text()='%s']", element)), 30) == null) {
            Assert.fail(String.format("Text %s wasn't rendered", element));
        }
    }

}
