import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.InputStream;

import static io.restassured.RestAssured.given;

public class ApiTestsProject {

    String idProject="";
    String idTask="";
    String bearer = "933f4edae2a13a210751df78e36a0d3fff2ab885";
    @Test(priority=0)
    public void createProject(){
        String create =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + bearer,
                                "Content-Type", ContentType.JSON,
                                "Accept",ContentType.JSON)
                        .when()
                        .queryParam("name", "Shopping List")
                        .post("https://api.todoist.com/rest/v2/projects")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .asString();

        JsonPath jsPathGet = new JsonPath(create);
        idProject = jsPathGet.getString("id");
        System.out.println("ID project:"+ idProject);
    }

    @Test(priority=1)
    public void AssignTask(){
        String assignTask =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + bearer,
                                "Content-Type", ContentType.JSON,
                                "Accept",ContentType.JSON)
                        .when()
                        .queryParam("content", "Buy Milk")
                        .queryParam("project_id", idProject)
                        .contentType(ContentType.JSON)
                        .post("https://api.todoist.com/rest/v2/tasks")
                        .then()
                        .statusCode(200)
                        .extract()
                        .response()
                        .asString();

        JsonPath jsPathGet = new JsonPath(assignTask);
        idTask = jsPathGet.getString("id");
        System.out.println("ID task:"+ idTask);

    }

    //Ver usuarios de una pagina especifica validando response forma corta
    @Test(priority=2)
    public void completeTask(){
        Response response =
                given()
                        .headers(
                                "Authorization",
                                "Bearer " + bearer,
                                "Content-Type", ContentType.JSON,
                                "Accept",ContentType.JSON)
                        .when()
                        .queryParam("due_string", "tomorrow")
                        .contentType(ContentType.JSON)
                        .post("https://api.todoist.com/rest/v2/tasks/"+idTask)
                        .then()
                        .statusCode(204)
                        .extract()
                        .response();
    }
}

