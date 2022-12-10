/* Advent of Code 2022
* Day 9 Part 1
*/

import java.io.File;
import java.util.*;

public class Rope {

public Rope() throws Exception
    {
        File inputFile = new File("Day 9\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;
        RopeSegment head=new RopeSegment();
        RopeSegment tail=new RopeSegment();

        Set<String> locationCodes=new TreeSet<>();

        //locationCodes.add(0);
        while (input.hasNextLine())
        {
            line=input.nextLine();
            int amount=Character.getNumericValue(line.charAt(2));
            for (int i=0;i<amount;i++)
            {
                head.move(line);
                tail.move(head);
                locationCodes.add(tail.getLocationID());
            }
        }
        input.close();

        int uniqueTailLocations=locationCodes.size();

        System.out.println("The tail was in "+uniqueTailLocations+" different locations");

    }

}
