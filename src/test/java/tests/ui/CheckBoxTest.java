package tests.ui;

import home.task.pages.CheckBoxesPage;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


@Feature("Checkboxes manipulation")
public class CheckBoxTest extends BaseTest {

    @Test(groups = {"ui"}, description = "Change statement of checkboxes and verify that it is applied")
    public void changeCheckBoxStatementAndVerifyResult() {
        CheckBoxesPage checkBoxesPage = new CheckBoxesPage(driver);
        homePage.waitUntilHomePageIsLoaded();
        homePage.openExample("Checkboxes");
        checkBoxesPage.waitUntilCheckBoxPageIsLoaded();
        checkBoxesPage.selectCheckBoxByCaption("checkbox 1", true);
        checkBoxesPage.selectCheckBoxByCaption("checkbox 2", false);
        assertThat(checkBoxesPage.isChecked("checkbox 1")).isTrue();
        assertThat(checkBoxesPage.isChecked("checkbox 2")).isFalse();
    }
}
