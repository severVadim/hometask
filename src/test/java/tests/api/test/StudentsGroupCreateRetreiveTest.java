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

public class StudentsGroupCreateRetreiveTest extends BaseTest {

    private String token;
    private final String endPoint = "student_groups";

    @BeforeClass
    public void configureRestAssured() {
        token = getAdminToken();
    }


    @DataProvider
    public Object[][] createPositive() {
        return new Object[][]{
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build()},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(15)
                        .startDate(getCalculateDateByMethod(DateTimeMethods.MINUS_DAYS, 10))
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(11))
                        .mentorIds(List.of(14)).build()}
        };
    }

    @DataProvider
    public Object[][] createNegative() {
        return new Object[][]{
                {StudentGroup.builder()
                        .name(getExistingStudentGroup())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3))
                        .mentorIds(List.of(4, 5)).build(),
                        "Group name already exists", 422},
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
                        "The StudentIds field is required", 400},
                {StudentGroup.builder()
                        .name(getRandomString())
                        .courseId(10)
                        .startDate(getCurrentDateTime())
                        .finishDate(getCurrentDateTime())
                        .studentIds(List.of(1, 2, 3)).build(),
                        "The MentorIds field is required", 400},
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


    @Test(description = "Positive - Check students group creation and retrieving", dataProvider = "createPositive")
    public void createRetrieveStudentGroupPositive(StudentGroup studentGroup) {
        Response response = given(token)
                .spec(requestSpec())
                .body(studentGroup)
                .post(endPoint);

        Assert.assertEquals(response.getStatusCode(), 200, response.getBody().asString());
        Assert.assertEquals(response.as(StudentGroup.class), studentGroup);

        Response responseGetAll = given(token)
                .spec(requestSpec())
                .get(endPoint);

        String id = response.jsonPath().getString("id");

        Response responseGetById = given(token)
                .spec(requestSpec())
                .get(endPoint + "/"  + id);

        Assert.assertEquals(responseGetAll.getStatusCode(), 200, responseGetAll.getBody().asString());
        Assert.assertEquals(responseGetAll.jsonPath().getList(".", StudentGroup.class)
                .stream()
                .filter(group -> group.getId() == Long.parseLong(id))
                .findFirst()
                .orElse(null), studentGroup);

        Assert.assertEquals(responseGetById.getStatusCode(), 200, responseGetById.getBody().asString());
        Assert.assertEquals(responseGetById.as(StudentGroup.class), studentGroup);
    }

    @Test(description = "Negative - Check students group creation with invalid data", dataProvider = "createNegative")
    public void createStudentGroupNegative(StudentGroup studentGroup, String errorMessage, int statusCode) {
        Response response = given(token)
                .spec(requestSpec())
                .body(studentGroup)
                .post(endPoint);
        Assert.assertEquals(response.getStatusCode(), statusCode, response.getBody().asString());
        Assert.assertTrue(response.asString().contains(errorMessage), response.asString());
    }

    private String getExistingStudentGroup() {
        return String.valueOf(given(token)
                .spec(requestSpec())
                .get(endPoint)
                .jsonPath()
                .getList("name")
                .get(0));
    }
}
