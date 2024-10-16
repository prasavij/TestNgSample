import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ApiUtils {
    private static RequestSpecification requestSpecification;

    public static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com"; // Example API

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(RestAssured.baseURI);
        builder.addHeader("Content-Type", "application/json");
        // Add more default settings if needed

        requestSpecification = builder.build();
    }

    public static RequestSpecification getRequestSpecification() {
        if (requestSpecification == null) {
            setup();
        }
        return requestSpecification;
    }
}
