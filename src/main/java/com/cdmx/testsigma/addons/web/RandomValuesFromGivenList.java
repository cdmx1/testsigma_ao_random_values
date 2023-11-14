
/*Change Log: AUTO-810 | Function to select from list randomly | 14/11/23 */

package com.cdmx.testsigma.addons.web;

import com.testsigma.sdk.WebAction;
import com.testsigma.sdk.ApplicationType;
import com.testsigma.sdk.annotation.Action;
import com.testsigma.sdk.annotation.TestData;
import com.testsigma.sdk.annotation.RunTimeData;
import lombok.Data;
import org.openqa.selenium.NoSuchElementException;

import java.util.HashSet;
import java.util.Set;

@Data
@Action(
    actionText = "Select number-of random values from list array and store in variable",
    description = "Get random values from an array and store in run-time data without selecting the same element again",
    applicationType = ApplicationType.WEB
)
public class RandomValuesFromGivenList extends WebAction {

    @TestData(reference = "number-of")
    private com.testsigma.sdk.TestData numberOfValues;

    @TestData(reference = "array")
    private com.testsigma.sdk.TestData givenArray;

    @TestData(reference = "variable")
    private com.testsigma.sdk.TestData variable;

    @RunTimeData
    private com.testsigma.sdk.RunTimeData runTimeData;

    @Override
    public com.testsigma.sdk.Result execute() throws NoSuchElementException {
        // Try using run-time data
        com.testsigma.sdk.Result result = com.testsigma.sdk.Result.SUCCESS;
        logger.info("Initiating execution");

        String commaSeparatedString = givenArray.getValue().toString();

        // Split the comma-separated string into an array of strings
        String[] stringArray = commaSeparatedString.split(",");
        int arrayLength = stringArray.length;
        String randomValues = "";

        // Create a set to keep track of selected indices
        Set<Integer> selectedIndices = new HashSet<>();

        for (int i = 0; i < Integer.parseInt(numberOfValues.getValue().toString()); i++) 
        {
            int index;
            do {
                double tempIndex = Math.random() * arrayLength;
                index = (int) Math.floor(tempIndex);
            } while (selectedIndices.contains(index));

            selectedIndices.add(index);

            String tempRandomValues = stringArray[index];

            if (i > 0) {
                randomValues += ",";
            }
            randomValues += tempRandomValues;
        }

        runTimeData = new com.testsigma.sdk.RunTimeData();
        runTimeData.setValue(randomValues);
        runTimeData.setKey(variable.getValue().toString());
        setSuccessMessage("Successfully stored " + randomValues + " into :: " + variable.getValue());
        return result;
    }
}