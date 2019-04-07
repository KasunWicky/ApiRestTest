import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class utility {

    static Logger log = Logger.getLogger(test.class.getName());

    public static void invokeApiCalls() {
        baseURI("https://api.tmsandbox.co.nz/v1");
        basePath("/Categories/6327/Details.json?catalogue=false");
        jSonContent();

    }

    //Create common method for setting URI
    public static String baseURI(String uRIAddress) {

        return (RestAssured.baseURI = uRIAddress);

    }

    //Set the basic path
    public static String basePath(String basePath) {

        return (RestAssured.basePath = basePath);

    }

    public static void jSonContent() {
        //Setting the content type as Json
        given().contentType(ContentType.JSON);
    }

    public static void xmlContent() {
        //Setting the content type XML
        given().contentType(ContentType.XML);
    }

    public static Response basicNavigation() {
        Response response = RestAssured.get();
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);
        long responsetime = response.getTime();
        System.out.println("Response is : " + code);
        log.info("Response is : " + code);
        System.out.println("Response time is : " + responsetime + " ms");
        log.info("Response time is : " + responsetime + " ms");
        return response;

    }
}
