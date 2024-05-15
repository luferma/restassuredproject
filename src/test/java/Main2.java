import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Main2 {
    public static void main(String[] args) {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response = given()
                .log()
                .all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(PayLoad.addPlace())
                .when()
                .post("maps/api/place/add/json")
                .then()
                .assertThat()
                .statusCode(200)
                .body("scope", equalTo("APP"))
                .body("status", equalTo("OK"))
                .header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println("Response: " + response);
        JsonPath jsPath = new JsonPath(response);
        String placeId = jsPath.getString("place_id");
        System.out.println("Place Id: " + placeId);

        //---------------------Update place
        String newAdress = "Summer walk, Africa";

        given()
                .log()
                .all()
                .queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeId + "\",\n" +
                        "\"address\":\" "+newAdress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when()
                .put("maps/api/place/update/json")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200)
                .body("msg", equalTo("Address successfully updated"));

        //---------------------Get place
        String getResponse = given()
                .log()
                .all()
                .queryParam("key", "qaclick123")
                .queryParam("place_id", placeId)
                .when()
                .get("maps/api/place/get/json")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath jsPathGet = new JsonPath(getResponse);
        String updatedAddress = jsPathGet.getString("address");
        System.out.println("Updated address: " + updatedAddress);
        Assert.assertEquals(newAdress.trim(), updatedAddress.trim());
    }
}
