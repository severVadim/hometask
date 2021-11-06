package tests.api;

import home.task.model.ConsolidatedWeather;
import home.task.util.CommonUtils;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Feature("API Tests for Location Day")
public class LocationDay {


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
                build();
    }

    @AfterGroups("positive")
    public void afterGroupPositive() {
        RestAssured.responseSpecification = null;
    }


    @DataProvider
    public Object[][] locationDayPositive() {
        return new Object[][]{
                {"44418/2020/12/21/", "/testData/locationDay.json"}};
    }

    @DataProvider
    public static Object[][] locationDayNegative() {
        return new Object[][]{
                {"0/2020/12/21/", 404},
                {"44418/2020/12/", 404},
                {"44418/2020/", 404},
                {"44418/12/12/2012", 404},
        };
    }


    @Test(groups = {"positive"}, description = "Positive - Get historical weather by location and date", dataProvider = "locationDayPositive")
    public void getHistoricalWeatherByLocationAndDate(String pathParam, String filePath) {
        List<ConsolidatedWeather> consolidatedWeathers = CommonUtils.parseJsonIntoObjectsList(given().get(pathParam).asString(), ConsolidatedWeather.class);
        Set<String> applicableDate = consolidatedWeathers.stream().map(ConsolidatedWeather::getApplicableDate).collect(Collectors.toSet());
        assertThat(applicableDate.size()).isEqualTo(1);
        assertThat(applicableDate.iterator().next()).isEqualTo(getDateFromPathParam(pathParam));
        assertThat(consolidatedWeathers).containsOnlyOnce(CommonUtils.parseJsonIntoObject(CommonUtils.getJsonStringFromFile(filePath), ConsolidatedWeather.class));
    }

    @Test(groups = {"negative"}, description = "Negative - Get weather by location", dataProvider = "locationDayNegative")
    public void getHistoricalWeatherByLocationAndDateNegative(String pathParam, int statusCode) {
        given()
                .get(pathParam)
                .then()
                .statusCode(statusCode);
    }

    private String getDateFromPathParam(String pathParam) {
        return String.join("-", Arrays.asList(pathParam.split("/")).subList(1, 4));
    }
}
