package tests.api;

import home.task.model.LocationSearchResult;
import io.qameta.allure.Feature;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;


@Feature("API Tests for Location Search")
public class SearchLocation {

    @BeforeClass
    public void configureRestAssured() {
        RestAssured.baseURI = "https://www.metaweather.com/api/location/search";
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
    public Object[][] searchPositive() {
        return new Object[][]{
                {"query=san",
                        LocationSearchResult.builder()
                                .title("San Francisco")
                                .locationType("City")
                                .woeid(2487956)
                                .lattLong("37.777119, -122.41964")
                                .build(),
                        11},
                {"query=lONdOn", LocationSearchResult.builder()
                        .title("London")
                        .locationType("City")
                        .woeid(44418)
                        .lattLong("51.506321,-0.12714")
                        .build(),
                        1},
                {"lattlong=36.96,-122.02", LocationSearchResult.builder()
                        .title("Santa Cruz")
                        .locationType("City")
                        .woeid(2488853)
                        .lattLong("36.974018,-122.030952")
                        .distance(1836)
                        .build(),
                        10},
                {"lattlong=50.068,-5.316", LocationSearchResult.builder()
                        .title("Penzance")
                        .locationType("City")
                        .woeid(31889)
                        .lattLong("50.11861,-5.53723")
                        .distance(16744)
                        .build(),
                        10},
        };
    }

    @DataProvider
    public static Object[][] searchNegative() {
        return new Object[][]{
                {"query=", 403},
                {"query=1", 200},
                {"lattlong=", 403},
                {"lattlong=50.068", 500},
                {"lattlong=     50.068,       5.316", 200},
        };
    }


    @Test(groups = {"positive"}, description = "Positive - Location Search", dataProvider = "searchPositive")
    public void searchLocationPositive(String query, LocationSearchResult expectedObject, int numberOfObjects) {
        List<LocationSearchResult> actualResults = given()
                .queryParams(getQueryParamWithValue(query))
                .get()
                .jsonPath()
                .getList(".", LocationSearchResult.class);

        assertThat(actualResults).containsOnlyOnce(expectedObject);
        assertThat(actualResults.size()).isEqualTo(numberOfObjects);
    }

    @Test(groups = {"negative"}, description = "Negative - Location Search", dataProvider = "searchNegative")
    public void searchLocationNegative(String query, int statusCode) {
        given()
                .queryParams(getQueryParamWithValue(query))
                .get()
                .then()
                .statusCode(statusCode);
    }

    private Map<String, String> getQueryParamWithValue(String query) {
        ArrayList<String> temp = new ArrayList<>(Arrays.asList(query.split("=")));
        if (temp.size() == 1) {
            temp.add("");
        }
        return Map.of(temp.get(0), temp.get(1));
    }
}
