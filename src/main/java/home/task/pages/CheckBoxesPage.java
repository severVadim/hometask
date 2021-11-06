package home.task.pages;

import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;


@Slf4j
public class CheckBoxesPage extends BasePage {

    public CheckBoxesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    private String checkBoxXpath = "//input[@type='checkbox'][contains(./following::text(), '%s')]";

    @Step("Wait until Checkboxes page will be ready")
    public void waitUntilCheckBoxPageIsLoaded() {
        if (waitVisibility(By.xpath("//h3[contains(text(), 'Checkboxes')]")) == null)
            Assert.fail("Checkboxes page isn't loaded");
    }

    @Step("Make {0} is {1}")
    public void selectCheckBoxByCaption(String caption, boolean check) {
        WebElement checkBox = driver.findElement(By.xpath(String.format(checkBoxXpath, caption)));
        if (check != checkBox.isSelected()) {
            checkBox.click();
            log.info(String.format("%s has been %schecked", caption, check ? "" : "un"));
        } else {
            log.info(String.format("Checkbox is already %schecked", check ? "" : "un"));
        }
    }

    @Step("Take current statement of {0}")
    public boolean isChecked(String caption) {
        return driver.findElement(By.xpath(String.format(checkBoxXpath, caption))).isSelected();
    }
}
