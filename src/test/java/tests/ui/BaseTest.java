package tests.ui;

import home.task.TypeOfEnvironments;
import home.task.driverManager.DriverManager;
import home.task.driverManager.DriverManagerFactory;
import home.task.driverManager.DriverType;
import home.task.pages.HomePage;
import home.task.util.CommonUtils;
import lombok.SneakyThrows;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.util.Properties;


public class BaseTest {

    private static DriverManager driverManager;
    private static Properties properties;
    protected WebDriver driver;
    public HomePage homePage;

    @SneakyThrows
    @Parameters({"env"})
    @BeforeSuite
    public void beforeSuite(@Optional("LOCAL") final String env) {
        properties = CommonUtils.getProperties(TypeOfEnvironments.valueOf(env).getPropertyFilePath());
    }

    @SneakyThrows
    @BeforeMethod(onlyForGroups = {"ui"})
    public void beforeMethodGlobalUi() {
        driverManager = DriverManagerFactory.getManager(DriverType.valueOf(properties.getProperty("browser")));
        driver = driverManager.getDriver(false);
        driver.manage().window().maximize();
        driver.get(properties.getProperty("url"));
        homePage = new HomePage(driver);
    }

    @AfterMethod(onlyForGroups = {"ui"})
    public void afterMethod() {
        driverManager.quitDriver();
    }

}
