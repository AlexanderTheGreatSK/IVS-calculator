package profiling;

import com.company.*;
import java.util.Scanner;

import java.util.ArrayList;
import java.util.List;

public class Profile {

    public static void main(){
        //CalcLib calc = new CalcLib();
        
        Scanner input = new Scanner(System.in);

        List<String> nums = new ArrayList<String>();
        while(input.hasNext()){
            nums.add(input.nextLine());
        }

        String sumExpression = nums.get(0);
        for(int i = 1; i < nums.size(); i++){
            sumExpression = String.join(sumExpression, "+", nums.get(i));
        }
        String sum = CalcLib.main(sumExpression);

        String meanExpression =
                String.join(sum, "/", Integer.toString(nums.size()));
        String mean = CalcLib.main(meanExpression);

        List<String> distances = new ArrayList<String>();
        for(int i = 0; i < nums.size(); i++){
            String distanceExpression = String.join(mean, "-", nums.get(i));
            String distance = CalcLib.main(distanceExpression);
            //Our calculator does not have an absolute value function, so this
            //we have to compensate for here
            if(distance.charAt(0) == '-'){
                distances.add(distance.substring(1));
            }else {
                distances.add(distance);
            }
        }

        String sumOfSquaredDistancesExpression = String.join
                ("pow(", distances.get(0), ", 2)");
        for(int i = 1; i < nums.size(); i++){
            sumOfSquaredDistancesExpression = String.join
                    (sumOfSquaredDistancesExpression,
                    "+",
                    "pow(", distances.get(i), ", 2)");
        }
        String sumOfSquaredDistances =
                CalcLib.main(sumOfSquaredDistancesExpression);

        String divideByAmountExpression = String.join(
                sumOfSquaredDistances, "/", Integer.toString(distances.size()));
        String divideByAmount = CalcLib.main(divideByAmountExpression);

        String resultExpression = String.join(
                "root(", divideByAmount, ", 2)");
        String result = CalcLib.main(resultExpression);

        System.out.println(result);
    }
}
