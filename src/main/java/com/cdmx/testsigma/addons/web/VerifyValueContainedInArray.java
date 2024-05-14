
/*Change Log: AUTO-810 | Function to select from list randomly | 14/11/23 */

package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;


@Data
@Action(
    actionText = "Verify that the array contains value",
    description = "Get random values from an array and store in run-time data without selecting the same element again",
    applicationType = ApplicationType.WEB
)
public class VerifyValueContainedInArray extends WebAction {


    @TestData(reference = "array")
    private com.testsigma.sdk.TestData givenArray;

    @TestData(reference = "value")
    private com.testsigma.sdk.TestData value;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        // Try using run-time data
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        logger.info("Initiating execution");

        String commaSeparatedString = givenArray.getValue().toString();
        commaSeparatedString = commaSeparatedString.replace("[", "").replace("]", "");
        // Split the comma-separated string into an array of strings
        String[] stringArray = commaSeparatedString.split(",");
        int arrayLength = stringArray.length;
        String valueToCheck = value.getValue().toString();
        boolean found = false;


        for (int i= 0; i< arrayLength; i++) 
        {
            if (stringArray[i].equals(valueToCheck) ) {
                found = true;
                break;
        }
        }

        if(found == true)
        {
            result = com.testsigma.sdk.Result.SUCCESS;
            setSuccessMessage("Value " + valueToCheck + " found in array " + givenArray.getValue().toString());

        }
        else
        {
            result = com.testsigma.sdk.Result.FAILED;
            setErrorMessage("The value " + valueToCheck + " was not found in the given array " + givenArray.getValue().toString());
        }

        
        return result;

    }
}
