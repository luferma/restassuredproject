import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class ApiTestsUsers {

    //Lista completa de usuarios en general
    @Test
    public void test(){
        Response response = RestAssured.get("https://reqres.in/api/users");
        System.out.println("Status code: " + response.getStatusCode());
        System.out.println("Response: " + response.asString());
        System.out.println("Body: " + response.getBody().asString());
        System.out.println("Time taken: " + response.time());
        System.out.println("Header: " + response.getHeader("content-type"));

        int status = response.getStatusCode();
        Assert.assertEquals(status, 200);
    }

    //Ver usuarios de una pagina especifica
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

    //Ver usuarios de una pagina especifica validando response forma corta
    @Test
    public void test2(){
        given().
                get("https://reqres.in/api/users?page=2").
                then().
                statusCode(200);
    }

    //Consultar usuario por ID
    @Test
    public void test3(){
        String getResponse = given().
                get("https://reqres.in/api/users/1").
                then().
                statusCode(200)
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

    //Consultar lista completa de usuarios e imprimir en detalle los campos
    @Test
    public void test4(){
        String getResponse = given().
                get("https://reqres.in/api/users").
                then().
                statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath jsPathGet = new JsonPath(getResponse);
        System.out.println("Response complete: " + getResponse);
        String user = jsPathGet.getString("data");
        //System.out.println("User complete: " + user);
        String[] users = user.split("],");
        for (String userIterate : users) {
            userIterate.replace("]","");
            userIterate.replace("[","");
            System.out.println("User list: " +userIterate);
        }
    }

    //Actualizar usuario 1 y validar que devuelva el campo "updatedAt"
    @Test
    public void test5(){
        RestAssured.baseURI="https://reqres.in/api/users";
        String getResponse = given()
                .log()
                .all()
                .header("accept", "application/json")
                .body(BodyLoad.updateUser())
                .when()
                .patch("/1")
                .then()
                .assertThat()
                .log()
                .all()
                .statusCode(200)
                .extract()
                .response()
                .asString();

        JsonPath jsPathGet = new JsonPath(getResponse);
        System.out.println("Response complete: " + getResponse);
        String updatedAt = jsPathGet.getString("updatedAt");
        System.out.println("UpdatedAt field: " + updatedAt);

    }

    //Consultar usuario por ID despues de actualizarlo (El metodo patch en la API no está funcionando, no actualiza)
    @Test
    public void test6(){
        String getResponse = given().
                get("https://reqres.in/api/users/1").
                then().
                statusCode(200)
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

    //Eliminar un usuario por ID
    @Test
    public void test7(){
        String getResponse = given().
                delete("https://reqres.in/api/users/1").
                then().
                statusCode(204)
                .extract()
                .response()
                .asString();

        System.out.println("Response complete: " + getResponse);
    }
}

