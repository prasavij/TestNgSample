
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class ApiTest {

    @BeforeClass
    public void setup() {
        ApiUtils.setup();
    }

    @Test(priority = 1)
    public void testGetAllPosts() {
        Response response = given()
                .spec(ApiUtils.getRequestSpecification())
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert response is not empty
        Assert.assertTrue(response.jsonPath().getList("$").size() > 0, "Posts list is empty");
    }

    @Test(priority = 2)
    public void testGetSinglePost() {
        int postId = 1;
        Response response = given()
                .spec(ApiUtils.getRequestSpecification())
                .when()
                .get("/posts/" + postId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert the post ID is correct
        Assert.assertEquals(response.jsonPath().getInt("id"), postId, "Post ID does not match");
    }

    @Test(priority = 3)
    public void testCreatePost() {
        String requestBody = "{\n" +
                "  \"title\": \"foo\",\n" +
                "  \"body\": \"bar\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given()
                .spec(ApiUtils.getRequestSpecification())
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .extract()
                .response();

        // Assert the response contains an ID
        Assert.assertNotNull(response.jsonPath().get("id"), "ID is not present in the response");
    }

    @Test(priority = 4)
    public void testUpdatePost() {
        int postId = 1;
        String requestBody = "{\n" +
                "  \"id\": " + postId + ",\n" +
                "  \"title\": \"updated title\",\n" +
                "  \"body\": \"updated body\",\n" +
                "  \"userId\": 1\n" +
                "}";

        Response response = given()
                .spec(ApiUtils.getRequestSpecification())
                .body(requestBody)
                .when()
                .put("/posts/" + postId)
                .then()
                .statusCode(200)
                .extract()
                .response();

        // Assert the title was updated
        Assert.assertEquals(response.jsonPath().getString("title"), "updated title", "Title was not updated");
    }

    @Test(priority = 5)
    public void testDeletePost() {
        int postId = 1;

        given()
                .spec(ApiUtils.getRequestSpecification())
                .when()
                .delete("/posts/" + postId)
                .then()
                .statusCode(200); // JSONPlaceholder returns 200 for DELETE
    }
}
