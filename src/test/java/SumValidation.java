import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

public class SumValidation {
    @Test
    public void sumOfCourses(){
        JsonPath jsPathGet = new JsonPath(PayLoad.CoursePrice());
        int count = jsPathGet.getInt("courses.size()");
        for(int i=0;i<count;i++){
            int price = jsPathGet.get("courses[" + i + "].price");
            int copies = jsPathGet.get("courses[" + i + "].copies");
            int amount = price * copies;
            System.out.println("Amount: " + amount);
        }
    }
}
