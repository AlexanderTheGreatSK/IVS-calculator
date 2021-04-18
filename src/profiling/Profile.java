/******************************************************************************
 * Name: Profile.java
 * Package: profiling
 *
 * Constructs expressions to calculate standard deviation of numbers entered by
 * standrad input and sends them to the calculator library.
 * Used to profile the application.
 *
 * Authors: Patrik Skaloš (xskalo01)
 *****************************************************************************/

/**
 * Constructs expressions to calculate standard deviation of numbers entered by
 * standrad input and sends them to the calculator library.
 * Used to profile the application.
 *
 * @author xskalo01
 * @version 1.0
 */

package profiling;

import com.company.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Profile {

    /**
     * Calculating standard deviation using the calculator library.
     * <p>
     * Calculation steps:
     * sum of all the numbers -> mean -> distances of numbers to the
     * mean -> sum of these distances squared -> dividing the result by 'N-1'
     * while N is the amount of numbers -> taking a square root of the result
     * <p>
     * The numbers are to by entered by standard input (stdin).
     * The result is then written to standard output (stdout.
     */
    public static void main(String[] args){

        //Read the input from stdin
        Scanner input = new Scanner(System.in);

        //Save the numbers to an array
        List<String> nums = new ArrayList<String>();
        while(input.hasNext()){
            nums.add(input.nextLine());
        }

        //Calculate the sum of all numbers
        String sumExpression = nums.get(0);
        for(int i = 1; i < nums.size(); i++){
            sumExpression = String.join("", sumExpression, "+", nums.get(i));
        }
        String sum = CalcLib.main(sumExpression);

        //Calculate the mean (average)
        String meanExpression =
                String.join("", sum, "/", Integer.toString(nums.size()));
        String mean = CalcLib.main(meanExpression);

        //Calculate the distances (from a number to the mean) and put them in an array
        List<String> distances = new ArrayList<String>();
        for(int i = 0; i < nums.size(); i++){
            String distanceExpression = String.join("", mean, "-", nums.get(i));
            String distance = CalcLib.main(distanceExpression);
            //Our calculator does not have an absolute value function, so this
            //we have to compensate for here
            if(distance.charAt(0) == '-'){
                distances.add(distance.substring(1));
            }else {
                distances.add(distance);
            }
        }

        //Calculate the sum of these distances^2
        String sumOfSquaredDistancesExpression = String.join
                ("", "pow(", distances.get(0), ", 2)");
        for(int i = 1; i < nums.size(); i++){
            sumOfSquaredDistancesExpression = String.join
                    ("", sumOfSquaredDistancesExpression,
                    "+", "pow(", distances.get(i), ", 2)");
        }
        String sumOfSquaredDistances =
                CalcLib.main(sumOfSquaredDistancesExpression);

        //Calculate N-1 (N stands for amount of numbers)
        String nMinusOneExpression = String.join("",
                Integer.toString(distances.size()), "-1");
        String nMinusOne = CalcLib.main(nMinusOneExpression);

        //Divide the sum of squared distances by N-1
        String divideByAmountExpression = String.join(
                "", sumOfSquaredDistances, "/", nMinusOne);
        String divideByAmount = CalcLib.main(divideByAmountExpression);

        //Take a square root and acquire the result
        String resultExpression = String.join(
                "", "root(", divideByAmount, ", 2)");
        String result = CalcLib.main(resultExpression);

        //Print the result to stdout
        System.out.println(result);
    }
}
