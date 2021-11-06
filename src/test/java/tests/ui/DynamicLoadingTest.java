package tests.ui;

import home.task.pages.DynamicallyLoadedElementsPage;
import io.qameta.allure.Feature;
import org.testng.annotations.Test;


@Feature("Dynamical loading elements")
public class DynamicLoadingTest extends BaseTest {

    @Test(groups = {"ui"}, description = "Wait until text will be rendered")
    public void changeCheckBoxStatementAndVerifyResult() {
        homePage.waitUntilHomePageIsLoaded();
        homePage.openExample("Dynamic Loading");

        DynamicallyLoadedElementsPage dynamicallyLoadedElementsPage = new DynamicallyLoadedElementsPage(driver);
        dynamicallyLoadedElementsPage.waitUntilDLPEPageIsLoaded();
        dynamicallyLoadedElementsPage.selectExampleWithName("Example 2");
        dynamicallyLoadedElementsPage.clickStartButton();
        dynamicallyLoadedElementsPage.waitUntilTextWillBeRendered("Hello World!");
    }
}
