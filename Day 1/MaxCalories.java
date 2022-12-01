/* Advent of Code 2022
 * Day 1 Part
 */

import java.io.File;
import java.util.Scanner;
import java.util.*;

public class MaxCalories {
  
    public static void main(String[] args) throws Exception
    {
        System.out.println("Find the max calories of an Elf");

        File file = new File("Day 1\\input.txt");
        Scanner input = new Scanner(file);

        String line;
        List<Integer> currentList=new ArrayList<Integer>();
        List<Elf> Elves=new ArrayList<Elf>();

        int maxCalories=0;

        while (input.hasNextLine())
        {
            while (input.hasNextLine())
            {
                line=input.nextLine();
                if (!line.isEmpty())
                {
                    currentList.add(Integer.parseInt(line));
                }
                else
                {
                    break;
                }
            }
            Elf currentElf=new Elf(currentList);
            Elves.add(currentElf);
            currentList.clear();

            int currentCalories=currentElf.getCalories();
            if(currentCalories>maxCalories)
            {
                maxCalories=currentCalories;
            }

        }

        input.close();
        System.out.println("The maximum number of calories an Elf has is "+maxCalories);
    }
  }