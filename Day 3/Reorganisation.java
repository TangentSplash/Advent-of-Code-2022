/* Advent of Code 2022
 * Day 3 Part 2
 */

import java.io.File;
import java.util.*;

public class Reorganisation {
    public static void main(String[] args) throws Exception
    {
        File file = new File("Day 3\\input.txt");
        Scanner input = new Scanner(file);

        String backpackItems;
        int totalSinglePriority=0;
        int totalGroupPriority=0;
        int i=0;
        Rucksack Group[]=new Rucksack[3];
        while (input.hasNextLine())
        {
            backpackItems=input.nextLine();
            Rucksack rucksack = new Rucksack(backpackItems);
            rucksack.findSingleBackpackOverlap();
            totalSinglePriority+=rucksack.getPriority();

            Group[i]=new Rucksack(backpackItems);
            if (i==2)
            {
                Group[0].findGroupOverlap(Group[1],Group[2]);
                totalGroupPriority+=Group[0].getPriority();
            }
            i=Math.floorMod(++i, 3);

        }
        input.close();

        System.out.println("The total priority individually is "+totalSinglePriority);
        System.out.println("The total group priority is "+totalGroupPriority);
    }
}
