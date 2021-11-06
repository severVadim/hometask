package tests.api.test;

import home.task.model.StudentGroup;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class StudentsGroupUpdateTest extends BaseTest {

    private String token;
    private final String endPoint = "student_groups";

    @BeforeClass
    public void configureRestAssured() {
        token = getAdminToken();
    }

    @DataProvider
    public Object[][] updateNegative() {
        return new Object[][]{
                {StudentGroup.builder()
                        .name(getRandomString())
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build(),
                        "CourseId does not exist", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCalculateDateByMethod(DateTimeMethods.MINUS_DAYS, 10))
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build(),
                        "Start date must be less than finish date", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate("2015-09-35")
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build(),
                        "Could not convert string to DateTime", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .mentorIds(List.of(4, 5)).build(),
                        "Internal error", 500},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3)).build(),
                        "Internal error", 500},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(new ArrayList<>())
                        .mentorIds(List.of(4, 5)).build(),
                        "The StudentIds field is required", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(new ArrayList<>()).build(),
                        "The MentorIds field is required", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3, 3))
                        .mentorIds(List.of(4, 5)).build(),
                        "Such student ids: 3 are not unique", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5, 5)).build(),
                        "Such mentor ids: 5 are not unique", 400}
        };
    }


    @Test(description = "Positive - Check students group updating")
    public void createStudentGroupPositive() {
        StudentGroup studentGroup = StudentGroup.builder()
                .name(getRandomString())
                .courseId(10)
                .startDate(getCurrentDateTime())
                .finishDate(getCurrentDateTime())
                .studentIds(List.of(1, 2, 3))
                .mentorIds(List.of(4, 5)).build();

        String id = given(token)
                .spec(requestSpec())
                .body(studentGroup)
                .post(endPoint)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("id");


        studentGroup.setName(getRandomString() + "_updated");
        studentGroup.setCourseId(12);
        studentGroup.setStartDate(getCalculateDateByMethod(DateTimeMethods.MINUS_DAYS, 20));
        studentGroup.setFinishDate(getCalculateDateByMethod(DateTimeMethods.PLUS_YEARS, 1));
        studentGroup.setStudentIds(List.of(1, 2));
        studentGroup.setStudentIds(List.of(4, 5, 6));

        Response response = given(token)
                .spec(requestSpec())
                .body(studentGroup)
                .put(endPoint + "/" + id);


        Assert.assertEquals(response.getStatusCode(), 200, response.getBody().asString());
        Assert.assertEquals(response.as(StudentGroup.class), studentGroup);

        Response responseGetById = given(token)
                .spec(requestSpec())
                .get(endPoint + "/" + id);

        Assert.assertEquals(responseGetById.getStatusCode(), 200, responseGetById.getBody().asString());
        Assert.assertEquals(responseGetById.as(StudentGroup.class), studentGroup);
    }

    @Test(description = "Negative - Check students group updating with invalid data", dataProvider = "updateNegative")
    public void createStudentGroupNegative(StudentGroup studentGroup, String errorMessage, int statusCode, ITestContext context) {
        String id = given(token)
                .spec(requestSpec())
                .body(StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build())
                .post(endPoint)
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getString("id");

        Response response = given(token)
                .spec(requestSpec())
                .body(studentGroup)
                .put(endPoint + "/" + id);

        Assert.assertEquals(response.getStatusCode(), statusCode, response.getBody().asString());
        Assert.assertTrue(response.asString().contains(errorMessage), response.asString());
    }
}
