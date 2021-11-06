package tests.api;

import home.task.model.ConsolidatedWeather;
import home.task.model.LocationInformation;
import home.task.util.CommonUtils;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.*;

import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Feature("API Tests for Location")
public class Location {


    @BeforeClass
    public void configureRestAssured() {
        RestAssured.baseURI = "https://www.metaweather.com/api/location/";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @BeforeGroups("positive")
    public void beforeGroupPositive() {
        RestAssured.responseSpecification = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                expectBody(JsonSchemaValidator.matchesJsonSchemaInClasspath("testData/location.json")).
                build();
    }

    @AfterGroups("positive")
    public void afterGroupPositive() {
        RestAssured.responseSpecification = null;
    }


    @DataProvider
    public Object[][] locationPositive() {
        return new Object[][]{{"44418", "/testData/location.json"}};
    }

    @DataProvider
    public static Object[][] locationNegative() {
        return new Object[][]{
                {"london", 404},
                {"1", 404},
                {"null", 404}
        };
    }


    @Test(groups = {"positive"}, description = "Positive - Get weather by location for 5 days", dataProvider = "locationPositive")
    public void getLocationWeather(String pathParam, String filePath) {
        int days = 6;
        LocationInformation locationInformationActual = given().get(pathParam).as(LocationInformation.class);
        LocationInformation locationInformationExpected = CommonUtils.parseJsonIntoObject(CommonUtils.getJsonStringFromFile(filePath), LocationInformation.class);

        assertThat(locationInformationActual.getConsolidatedWeather().size()).isEqualTo(days);
        assertThat(locationInformationActual).isEqualTo(locationInformationExpected);
        assertThat(CommonUtils.getDatesFromNowToN(locationInformationExpected.getTimezone(), days)).isEqualTo(locationInformationActual.getConsolidatedWeather()
                .stream()
                .map(ConsolidatedWeather::getApplicableDate)
                .collect(Collectors.toList()));
    }

    @Test(groups = {"negative"}, description = "Negative - Get weather by location", dataProvider = "locationNegative")
    public void getLocationWeatherNegative(String pathParam, int statusCode) {
        given()
                .get(pathParam)
                .then()
                .statusCode(statusCode);
    }
}
