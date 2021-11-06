package tests.api.test;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class BaseTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    private final String url = "https://whatbackend.azurewebsites.net/api";

    protected RequestSpecification requestSpec() {
        return new RequestSpecBuilder().setBaseUri(url)
                .setContentType(ContentType.JSON)
                .build();
    }

    protected RequestSpecification given(String token) {
        return RestAssured.given().auth().preemptive().oauth2(token);
    }

    protected RequestSpecification given() {
        return RestAssured.given();
    }


    public String getAdminToken() {
        return getToken("admin.@gmail.com", "admiN_12");
    }

    public String getToken(String email, String password) {
        return given()
                .spec(requestSpec())
                .body(Map.of("email", email, "password", password))
                .post("/accounts/auth")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .header("Authorization").split(" ")[1];
    }

    public static String getRandomString() {
        return RandomStringUtils.randomAlphabetic(7);
    }

    public static String getCurrentDateTime() {
        return LocalDate.now().atTime(0, 0).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }


    @SneakyThrows
    public static String getCalculateDateByMethod(DateTimeMethods method, long units) {
        LocalDateTime localDateTime = (LocalDateTime) LocalDateTime.class.getDeclaredMethod(method.getGetMethod(), long.class).invoke(LocalDate.now().atTime(0, 0), units);
        return localDateTime.format(formatter);
    }
}
