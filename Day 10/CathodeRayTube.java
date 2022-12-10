/* Advent of Code 2022
* Day 10 Part 1
*/

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CathodeRayTube {
    public static void main(String[] args) throws Exception
    {
        File inputFile = new File("Day 10\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;

        int addNextTime=0;

        int X=1;

        int cycle=1;

        int signalStrength=0;

        Pattern addX = Pattern.compile("addx");

        boolean justRun=false;
        
        while (input.hasNextLine())
        {
            line=input.nextLine();

            Matcher addXMatcher = addX.matcher(line);
            
            if (line.equals("noop"))
            {
                //Do Nothing
            }
            else if (addXMatcher.find())
            {
                String[] instructions=line.split("addx ");
                addNextTime=Integer.parseInt(instructions[1]);
                cycle++;
            }

            if((cycle==20 || Math.floorMod(cycle, 40)==20) && cycle<=220 && !justRun)
            {
                signalStrength+=(cycle*X);
            }

            justRun=false;
            X+=addNextTime;
            addNextTime=0;
            
            cycle++;
            if((cycle==20 || Math.floorMod(cycle, 40)==20) && cycle<=220)
            {
                signalStrength+=(cycle*X);
                justRun=true;

            }
        }

        input.close();

        System.out.println("The Signal Strength is "+signalStrength);
    }   
}
