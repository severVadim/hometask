package tests.ui;

import home.task.pages.FramesPage;
import home.task.pages.IFramePage;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;


@Feature("iFrame manipulation")
public class FramesTest extends BaseTest {

    @Test(groups = {"ui"}, description = "Write text inside iFrame text area")
    public void changeCheckBoxStatementAndVerifyResult() {
        String text = "Vadym Severynenko";

        homePage.waitUntilHomePageIsLoaded();
        homePage.openExample("Frames");

        FramesPage framesPage = new FramesPage(driver);
        framesPage.waitUntilFramePageIsLoaded();
        framesPage.selectFrameWithName("iFrame");

        IFramePage iFramePage = new IFramePage(driver);
        iFramePage.waitUntilIFramePageIsLoaded();
        iFramePage.cleatEditor();
        iFramePage.writeText(text);
        assertThat(iFramePage.getTextFromEditor()).isEqualTo(text);
    }
}
