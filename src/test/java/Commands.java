import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import java.util.Map;
import java.util.logging.Logger;

public class Commands {
    private static Logger log = Logger.getLogger(Commands.class.getName());

    /**
     * This method is used validate in a partial/full txt
     * availablity of JSON response
     * @param response, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    public static void containsValuesInMap(Response response, String field, String keyElement, String expectedValue) {
        if (!((response.jsonPath().get(field)) == null)) {
            String containsVal = response.jsonPath().getString(field + "." + keyElement);
            log.info("Original Value '" + containsVal + "' and expected '" + expectedValue + "'");
            Assert.assertTrue(containsVal.contains(expectedValue));

        }
    }

    /**
     * This method is used process the Map object receving from the Json
     * @param response, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    public static void mapIterator(Response response, String field, String keyElement, String expectedValue) {

        //Promotions[0]
        if (!((response.jsonPath().get(field)) == null)) {
            Map<String, Object> restMap = response.jsonPath().getMap(field);
            processMap(restMap, keyElement, expectedValue);
        } else {
            Assert.fail(field + " This field is not available ");
        }

    }

    /**
     * This method is used verify values in a Map object
     * @param map, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void processMap(Map<String, Object> map, String keyElement, String expectedValue) {

        Object actualValue;
        if (map instanceof Map) {
            actualValue = map.get(keyElement);
            primitiveDataVerification(actualValue, expectedValue, keyElement);
        }

    }


    /**
     * This method is used identify and filter the data type
     * of the inbound object file
     * @param actualValue, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void primitiveDataVerification(Object actualValue, String expectedValue, String keyElement) {

        if (actualValue instanceof String) {
            assertString(actualValue, expectedValue, keyElement);
        } else if (actualValue instanceof Integer) {
            assertInteger(actualValue, expectedValue, keyElement);
        } else if (actualValue instanceof Map) {
            processMap((Map) actualValue, keyElement, expectedValue);
        } else if (actualValue instanceof Boolean) {
            assertBoolean(actualValue, expectedValue, keyElement);
        } else if (actualValue instanceof Float) {
            assertFloat(actualValue, expectedValue, keyElement);
        } else {
            Assert.fail(expectedValue + "is unable to find data type of actual value");
        }

    }


    /**
     * This method is used verify and print the expected and actual String values are equal
     * @param actualValue, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void assertString(Object actualValue, String expectedValue, String keyElement) {

        log.info("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, expectedValue);

    }

    /**
     * This method is used verify and print the expected and actual integer values are equal
     * @param actualValue, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void assertInteger(Object actualValue, String expectedValue, String keyElement) {
        log.info("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Integer.parseInt(expectedValue));
    }

    /**
     * This method is used verify and print the expected and actual boolean values are equal
     * @param  actualValue, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void assertBoolean(Object actualValue, String expectedValue, String keyElement) {
        log.info("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Boolean.parseBoolean(expectedValue));
    }

    /**
     * This method is used verify and print the expected and actual float values are equal
     * @param actualValue, incoming actual Value
     * @param expectedValue  incoming expected Value
     * @param keyElement reffering field value
     * @return
     */
    private static void assertFloat(Object actualValue, String expectedValue, String keyElement) {
        log.info("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Float.valueOf(expectedValue));
    }

    /**
     * Verifying String value in non-nested response in the Json
     * show the usage of various javadoc Tags.
     * @param jsonPathEvaluator  This provide Json response for validation
     * @param fieldName This provides the reffering field name
     * @param expectedValue This expected value verify against actual input
     * @return
     */
    public static void verifySingleValue(JsonPath jsonPathEvaluator, String fieldName, String expectedValue) {

        //Validate a String
        // StringBuilder fieldsOutput = new StringBuilder();
        try {
            String fieldsOutput = jsonPathEvaluator.get(fieldName).toString();
            log.info("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
            Assert.assertEquals(fieldsOutput, expectedValue);
        } catch (
                NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (
                Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }

    }

    /**
     * Verifying boolean number in non-nested response in the Json
     * show the usage of various javadoc Tags.
     * @param jsonPathEvaluator  This provide Json response for validation
     * @param fieldName This provides the reffering field name
     * @param expectedValue This expected value verify against actual input
     * @return
     */
    public static void verifyBooleanValue(JsonPath jsonPathEvaluator, String fieldName, Boolean expectedValue) {

        try {
            Boolean fieldsOutput = jsonPathEvaluator.get(fieldName);
            log.info("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
            Assert.assertEquals(fieldsOutput, expectedValue);
        } catch (
                NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (
                Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }

    }

    /**
     * Verifying single double number in non-nested response in the Json
     * show the usage of various javadoc Tags.
     * @param jsonPathEvaluator  This provide Json response for validation
     * @param fieldName This provides the reffering field name
     * @param expectedValue This expected value verify against actual input
     * @return
     */
    public static void verifySingleDoubleValue(JsonPath jsonPathEvaluator, String fieldName, double expectedValue) {
        try {
            Double fieldsOutput = jsonPathEvaluator.get(fieldName);
            log.info("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
            Assert.assertEquals(fieldsOutput, expectedValue);

        } catch (NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }
    }
    /**
     * Verifying single non-nested response in the Json
     * show the usage of various javadoc Tags.
     * @param jsonPathEvaluator  This provide Json response for validation
     * @param fieldName This provides the reffering field name
     * @param expectedValue This expected value verify against actual input
     */
    public static void verifySingleValue(JsonPath jsonPathEvaluator, String fieldName, Object expectedValue) {

        //Validate a String
        try {
            Object fieldsOutput = jsonPathEvaluator.get(fieldName).toString();
            log.info("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
            Assert.assertEquals(fieldsOutput, expectedValue);

        } catch (
                NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (
                Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }

    }


}
