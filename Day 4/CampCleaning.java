/* Advent of Code 2022
 * Day 4 Part 2
 */

import java.io.File;
import java.util.*;

public class CampCleaning {

    public static void main(String[] args) throws Exception
    {
        File file = new File("Day 4\\input.txt");
        Scanner input = new Scanner(file);

        String IDPairs;
        int fullyContained=0;
        int someOverlap=0;
        int lines=0;

        while (input.hasNextLine())
        {
            IDPairs=input.nextLine();
            Group section=new Group(IDPairs);
            if(section.fullyContains())
            {
                fullyContained++;
            }
            if (section.someOverlap())
            {
                someOverlap++;
            }
            lines++;
        }
    
        input.close();
        System.out.println("There are "+fullyContained+" fully contained assignments");
        System.out.println("in "+ lines+" lines");
        System.out.println("There is also "+someOverlap+" assignments with some overlap");
    }
}
