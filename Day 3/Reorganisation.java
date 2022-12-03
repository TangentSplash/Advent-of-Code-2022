/* Advent of Code 2022
 * Day 3 Part 1
 */

import java.io.File;
import java.util.*;

public class Reorganisation {
    public static void main(String[] args) throws Exception
    {
        File file = new File("Day 3\\input.txt");
        Scanner input = new Scanner(file);

        String backpackItems;
        int totalPriority=0;
        while (input.hasNextLine())
        {
            backpackItems=input.nextLine();
            Rucksack rucksack = new Rucksack(backpackItems);
            totalPriority+=rucksack.getPriority();
        }
        input.close();

        System.out.println("The total priority is "+totalPriority);
    }
    
}
