import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ApiTests {

    @Test
    public void test1(){
        Response response = RestAssured.get("https://reqres.in/api/users?page=2");
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response: " + response.asString());
        System.out.println("Body: " + response.getBody().asString());
        System.out.println("Time taken: " + response.time());
        System.out.println("Header: " + response.getHeader("content-type"));

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
    }

    @Test
    public void test4(){
        Response response = RestAssured.get("https://reqres.in/api/users");
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response: " + response.asString());
        System.out.println("Body: " + response.getBody().asString());
        System.out.println("Time taken: " + response.time());
        System.out.println("Header: " + response.getHeader("content-type"));

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
    }

    @Test
    public void test2(){
        given().
                get("https://reqres.in/api/users?page=2").
                then().
                statusCode(200);
    }

    @Test
    public void test3(){
        String getResponse = given().
                get("https://reqres.in/api/users/1")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath jsPathGet = new JsonPath(getResponse);
        String email = jsPathGet.getString("data.email");
        String first_name = jsPathGet.getString("data.first_name");
        String last_name = jsPathGet.getString("data.last_name");
        System.out.println("Response complete: " + getResponse);
        System.out.println("E-mail: " + email);
        System.out.println("First name: " + first_name);
        System.out.println("Last name: " + last_name);
    }
}

