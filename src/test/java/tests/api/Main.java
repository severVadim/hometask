package tests.api;

import io.restassured.http.ContentType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Main {
    public static void main(String[] args) {
        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));    }
}
