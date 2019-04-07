import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class test {


    static JsonPath jsonPathEvaluator;


    @BeforeTest
    public static Response setup() {

        //Invoke API call
        utility.invokeApiCalls();

        //Receive response
        Response response = utility.basicNavigation();
        
        return response;
    }

    @Test
    public void test001() {
        //Call Response
        Response response = setup();

        //Obtain response to json
        jsonPathEvaluator = response.jsonPath();

        //Verifying CategoryID field value
        commands.verifySingleValue(jsonPathEvaluator, "CategoryId", "6327");
        commands.verifySingleValue(jsonPathEvaluator, "Name", "Carbon credits");

        commands.verifyBooleanValue(jsonPathEvaluator, "CanRelist", true);

        // verifyMapalue(jsonPathEvaluator, "Promotions",String  ,true);
        commands.mapIterator(response, "Promotions[1]", "Name", "Gallery");

        commands.containsValuesInMap(response, "Promotions[1]", "Description", "2x larger image");




    }
}




