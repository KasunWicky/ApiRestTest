import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {


    static JsonPath jsonPathEvaluator;


    /**
     * This method is used to add two integers. This is
     * a the simplest form of a class method, just to
     * show the usage of various javadoc Tags.
     * @param numA This is the first paramter to addNum method
     * @param numB  This is the second parameter to addNum method
     * @return .
     */
    @BeforeTest
    public static void setup() {
        //Invoke API call
        Utility.invokeApiCalls();
    }


    /**
     * This method is used to add two integers. This is
     * a the simplest form of a class method, just to
     * show the usage of various javadoc Tags.
     * @param numA This is the first paramter to addNum method
     * @param numB  This is the second parameter to addNum method
     * @return .
     */
    @Test
    public void test001() {
        //Call Response
        Response response = Utility.basicNavigation();

        //Obtain response to json
        jsonPathEvaluator = response.jsonPath();

        //Verifying CategoryID field value
        Commands.verifySingleValue(jsonPathEvaluator, "CategoryId", "6327");
        Commands.verifySingleValue(jsonPathEvaluator, "Name", "Carbon credits");
        Commands.verifyBooleanValue(jsonPathEvaluator, "CanRelist", true);
        // verifyMapalue(jsonPathEvaluator, "Promotions",String  ,true);
        Commands.mapIterator(response, "Promotions[1]", "Name", "Gallery");
        Commands.containsValuesInMap(response, "Promotions[1]", "Description", "2x larger image");


    }
}




