package home.task.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


@Slf4j
public class IFramePage extends BasePage {

    public IFramePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='File']/parent::button")
    private WebElement fileButton;

    @FindBy(xpath = "//span[text()='Edit']/parent::button")
    private WebElement editButton;

    @FindBy(xpath = "//span[text()='View']/parent::button")
    private WebElement viewButton;

    @FindBy(xpath = "//span[text()='Format']/parent::button")
    private WebElement formatButton;

    private final String editor = "//body[@id='tinymce']";
    private final String iFrameId = "mce_0_ifr";


    @Step("Wait until IFrame page will be ready")
    public void waitUntilIFramePageIsLoaded() {
        if (waitVisibility(By.xpath("//h3[contains(text(), 'TinyMCE')]")) == null) {
            Assert.fail("IFrame page isn't loaded");
        }
    }

    @Step("Clear editor")
    public void cleatEditor() {
        fileButton.click();
        waitToBeClickable(By.xpath("//div[@title='New document']")).click();
        if (!getTextFromEditor().isEmpty()) {
            Assert.fail("Editor wasn't cleaned");
        }
    }

    @Step("Get text from editor")
    public String getTextFromEditor() {
        driver.switchTo().frame(iFrameId);
        String text = driver.findElement(By.xpath("//body[@id='tinymce']")).getText();
        driver.switchTo().defaultContent();
        return text;
    }

    @Step("Write text inside editor: {0}")
    public void writeText(String text) {
        driver.switchTo().frame(iFrameId);
        driver.findElement(By.xpath("//body[@id='tinymce']")).sendKeys(text);
        driver.switchTo().defaultContent();
    }

}
