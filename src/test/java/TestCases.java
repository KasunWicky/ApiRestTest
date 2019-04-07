import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestCases {


    static JsonPath jsonPathEvaluator;


    /**
     * This method is used invoke api call
     * and other test case related pre-requisites
     *
     */
    @BeforeTest
    public static void setup() {
        //Invoke API call
        Utility.invokeApiCalls();
    }


    /**
     * This method is used to perform the test cases
     *
     */
    @Test
    public void test001() {
        //Call Response
        Response response = Utility.basicNavigation();

        //Obtain response to json
        jsonPathEvaluator = response.jsonPath();

        //Verifying CategoryID field value
        Commands.verifySingleValue(jsonPathEvaluator, "CategoryId", "6327");
        //Verify Name field and it's value
        Commands.verifySingleValue(jsonPathEvaluator, "Name", "Carbon credits");
        //Verify CanRelist and it's value
        Commands.verifyBooleanValue(jsonPathEvaluator, "CanRelist", true);
        //Verify  Promotions element with Name = "Gallery" and description
        Commands.mapIterator(response, "Promotions[1]", "Name", "Gallery");
        Commands.containsValuesInMap(response, "Promotions[1]", "Description", "2x larger image");


    }
}




