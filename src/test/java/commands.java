import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class commands {


    public static void containsValuesInMap(Response response, String field, String keyelement, String expectedValue) {
        if (!((response.jsonPath().get(field)) == null)) {
            String containsVal = response.jsonPath().getString(field + "." + keyelement);
            System.out.println("Original Value '" + containsVal + "' and expected '" + expectedValue + "'");
            Assert.assertTrue(containsVal.contains(expectedValue));

        }
    }

    public static void mapIterator(Response response, String field, String keyelement, String expectedValue) {

        //Promotions[0]
        if (!((response.jsonPath().get(field)) == null)) {
            Map<String, Object> restMap = response.jsonPath().getMap(field);
            processMap(restMap, keyelement, expectedValue);
        } else {
            Assert.fail(field + " This field is not available ");
        }

    }

    private static void processMap(Map<String, Object> map, String keyElement, String expectedValue) {

        Object actualValue;
        if (map instanceof Map) {
            actualValue = map.get(keyElement);
            primitiveDataVerification(actualValue, expectedValue, keyElement);

        }

    }

    private static void processList(List<Object> list, String expectedValue, String keyElement) {

        if (list instanceof List) {

            primitiveDataVerification(list, expectedValue, keyElement);

          //  System.out.println("Expected value is " + expectedValue + " from " + list);
           // Assert.assertTrue(expected, "Expected value is " + expectedValue + " from " + list);


        }

    }

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


    private static void assertString(Object actualValue, String expectedValue, String keyElement) {

        System.out.println("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, expectedValue);

    }

    private static void assertInteger(Object actualValue, String expectedValue, String keyElement) {

        System.out.println("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Integer.parseInt(expectedValue));

    }

    private static void assertBoolean(Object actualValue, String expectedValue, String keyElement) {

        System.out.println("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Boolean.parseBoolean(expectedValue));
    }

    private static void assertFloat(Object actualValue, String expectedValue, String keyElement) {

        System.out.println("Element " + keyElement + " Expected: " + actualValue + ", Actual: " + expectedValue);
        Assert.assertEquals(actualValue, Float.valueOf(expectedValue));
    }

    /**
     * Only accepts Strings and fields which can be converted to Strings
     */
    public static void verifySingleValue(JsonPath jsonPathEvaluator, String fieldName, String expectedValue) {

        //Validate a String
        // StringBuilder fieldsOutput = new StringBuilder();
        try {
            String fieldsOutput = jsonPathEvaluator.get(fieldName).toString();
            System.out.println("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
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

    public static void verifyBooleanValue(JsonPath jsonPathEvaluator, String fieldName, Boolean expectedValue) {

        try {
            Boolean fieldsOutput = jsonPathEvaluator.get(fieldName);
            System.out.println("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
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

    public static void verifySingleDoubleValue(JsonPath jsonPathEvaluator, String fieldName, double expectedValue) {


        //Validate a double value

        try {
            Double fieldsOutput = jsonPathEvaluator.get(fieldName);
            System.out.println("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
            Assert.assertEquals(fieldsOutput, expectedValue);

        } catch (NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }
    }


    //This function does not work as the objects are not equals
    public static void listIterator(JsonPath jsonPathEvaluator, String fieldName, String expectedValue) {

        ArrayList<String> objectList;

        //Validate a double value

        try {
            objectList = jsonPathEvaluator.get(fieldName);
            Iterator<String> iterator = objectList.iterator();

            while (iterator.hasNext()) {
                if (expectedValue.equals(iterator)) {
                    System.out.println("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + iterator);
                    Assert.assertEquals(iterator, expectedValue);

                }
                iterator.next();

            }
            Assert.fail(expectedValue + "is not able to find");

        } catch (NullPointerException ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        } catch (Exception ex) {
            Assert.fail(expectedValue + "is not able to find");
            ex.getMessage();
        }
    }


    public static void verifySingleValue(JsonPath jsonPathEvaluator, String fieldName, Object expectedValue) {

        //Validate a String
        try {
            Object fieldsOutput = jsonPathEvaluator.get(fieldName).toString();
            System.out.println("Element " + fieldName + " Expected: " + expectedValue + ", Actual: " + fieldsOutput);
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
