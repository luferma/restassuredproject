import io.restassured.RestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class MainUsers {
    public static void main(String[] args) {
        RestAssured.baseURI="https://reqres.in/api/";
        String response = given()
                .log()
                .all()
                .header("accept", "application/json")
                .when()
                .get("{resource}")
                .then()
                .log()
                .all()
                .assertThat()
                .statusCode(200).extract()
                .response()
                .asString();
    }
}
