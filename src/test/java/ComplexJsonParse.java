import groovyjarjarasm.asm.TypeReference;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperDeserializationContext;
import io.restassured.mapper.ObjectMapperSerializationContext;
import io.restassured.path.json.JsonPath;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ComplexJsonParse {
    public static void main(String[] args) {
        JsonPath jsPathGet = new JsonPath(PayLoad.CoursePrice());
        //Print course size
        int count = jsPathGet.getInt("courses.size()");
        System.out.println("Size: " + count);
        //Print purchase amount
        int totalAmount = jsPathGet.getInt("dashboard.purchaseAmount");
        System.out.println("Total amount: " + totalAmount);
        //Print title first course
        String title = jsPathGet.get("courses[0].title");
        System.out.println("Title: " + title);
        //Print all titles with their prices
        List<Courses> courses = new ArrayList<Courses>();
        LinkedHashMap<Integer,String> coursesMap=new LinkedHashMap<Integer,String>();
        List<Object> object = jsPathGet.get("courses");
        object.stream().forEach(System.out::println);
        courses = jsPathGet.get("courses");
        /*try {
            courses.forEach(c -> System.out.println("Courses: " + c));
        }catch(Exception e){
            System.out.println("Exception: " + e.getMessage());
        };*/

        //Print dinamically
        int countIterate = jsPathGet.getInt("courses.size()");
        for(int i=0;i<countIterate;i++){
            String titlePrint = jsPathGet.get("courses[" + i + "].title");
            String pricePrint = jsPathGet.get("courses[" + i + "].price").toString();
            System.out.println("Title: " + titlePrint);
            System.out.println("Price: " + pricePrint);
            String copiesPrint = "";
            if(titlePrint.equalsIgnoreCase("RPA")){
                copiesPrint = jsPathGet.get("courses[" + i + "].copies").toString();
                System.out.println("Copies RPA: " + copiesPrint);
                break;
            }
        }

    }
}
