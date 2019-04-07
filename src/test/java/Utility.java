import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;


import java.util.logging.Logger;

import static io.restassured.RestAssured.given;

public class Utility {

    private static Logger log = Logger.getLogger(Utility.class.getName());

    /**
     * Invoking api call to server
     */
    public static void invokeApiCalls() {
        baseURI(staticData.baseURI);
        basePath("/Categories/6327/Details.json?catalogue=false");
        jSonContent();

    }

    /**
     * Setting the URI by getting input from String
     * @param uRIAddress This obtain the URI from User
     * @return String This returns the baseURI given by the User.
     */
    //Create common method for setting URI
    public static String baseURI(String uRIAddress) {

        return (RestAssured.baseURI = uRIAddress);

    }

    /**
     * Setting the Basepath by adding to URI input from String
     * @param basePath This obtain the URI from User
     * @return String This returns the basePath followed by the URI
     */
    //Set the basic path
    public static String basePath(String basePath) {

        return (RestAssured.basePath = basePath);

    }

    /**
     * This methid is for wetting the content type as Json
     * should with a api request
     *
     */
    public static void jSonContent() {

        given().contentType(ContentType.JSON);
    }

    /**
     * Setting the content type XML
     * should use with api request
     */
    public static void xmlContent() {
        //Setting the content type XML
        given().contentType(ContentType.XML);
    }

    /**
     * This method is used to send the api get() request
     * and receive the response verify status code,
     * get response time and return the response for other
     * data validations and commands
     * @return Response
     */
    public static Response basicNavigation() {
        Response response = RestAssured.get();
        int code = response.getStatusCode();
        Assert.assertEquals(code, 200);
        long responseTime = response.getTime();
        log.info("Response is : " + code);
        log.info("Response time is : " + responseTime + " ms");
        return response;

    }
}
