/* Advent of Code 2022
* Day 9 Part 2
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Rope {

public static final int GRID_SIZE=500;
public static final int NUMBER_OF_KNOTS=10;

public Rope() throws Exception
    {   
        File inputFile = new File("Day 9\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;
        RopeSegment[] knots=new RopeSegment[10];

        for (int i=0;i<NUMBER_OF_KNOTS;i++) {
            knots[i]=new RopeSegment();
        }

        Set<String> locationCodesTail1=new TreeSet<>();
        Set<String> locationCodesTail9=new TreeSet<>();

        char[][] map=new char[GRID_SIZE][GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++) 
        {
            for (int j=0;j<GRID_SIZE;j++)
            {
                map[i][j]='.';
            }
            
        }

        while (input.hasNextLine())
        {
            line=input.nextLine();
            String[] inputInfo= line.split(" ");
            int amount=Integer.parseInt(inputInfo[1]);
            for (int i=0;i<amount;i++)
            {
                knots[0].move(inputInfo[0]);
                if (map[knots[0].getY()+(GRID_SIZE/2)][knots[0].getX()+(GRID_SIZE/2)]=='.')
                {
                    map[knots[0].getY()+(GRID_SIZE/2)][knots[0].getX()+(GRID_SIZE/2)]='H';
                }
                for(int j=1;j<NUMBER_OF_KNOTS;j++)
                {
                    knots[j].move(knots[j-1]);
                    map[knots[j].getY()+(GRID_SIZE/2)][knots[j].getX()+(GRID_SIZE/2)]=Integer.toString(j).charAt(0);
                }
                locationCodesTail1.add(knots[1].getLocationID());
                locationCodesTail9.add(knots[NUMBER_OF_KNOTS-1].getLocationID());
            }
        }
        input.close();
        
        BufferedWriter output = new BufferedWriter(new FileWriter("Day 9\\output.txt"));

        for (int j=GRID_SIZE-1;j>=0;j--) 
        {
            output.write(map[j]);
            output.write("\n");         
        }

        output.close();

        int uniqueTail1Locations=locationCodesTail1.size();
        int uniqueTail9Locations=locationCodesTail9.size();

        System.out.println("The 1'st tail was in "+uniqueTail1Locations+" different locations");
        System.out.println("The 9'th tail was in "+uniqueTail9Locations+" different locations");
    }
}
