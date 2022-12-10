/* Advent of Code 2022
* Day 9 Part 1
*/

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Rope {

public static final int GRID_SIZE=500;

public Rope() throws Exception
    {   
        File inputFile = new File("Day 9\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;
        RopeSegment head=new RopeSegment();
        RopeSegment tail=new RopeSegment();

        Set<String> locationCodes=new TreeSet<>();

        char[][] map=new char[GRID_SIZE][GRID_SIZE];
        for (int i=0;i<GRID_SIZE;i++) 
        {
            for (int j=0;j<GRID_SIZE;j++)
            {
                map[i][j]='.';
            }
            
        }

        int hits=0;
        //locationCodes.add(0);
        while (input.hasNextLine())
        {
            line=input.nextLine();
            String[] inputInfo= line.split(" ");
            int amount=Integer.parseInt(inputInfo[1]);
            for (int i=0;i<amount;i++)
            {
                head.move(inputInfo[0]);
                if (map[head.getY()+(GRID_SIZE/2)][head.getX()+(GRID_SIZE/2)]=='.')
                {
                    map[head.getY()+(GRID_SIZE/2)][head.getX()+(GRID_SIZE/2)]='*';
                }
                tail.move(head);
                if(locationCodes.add(tail.getLocationID()))
                {
                    hits++;
                }
                map[tail.getY()+(GRID_SIZE/2)][tail.getX()+(GRID_SIZE/2)]='#';
            }
        }
        input.close();
        
        BufferedWriter output = new BufferedWriter(new FileWriter("Day 9\\output2.txt"));

        for (int j=GRID_SIZE-1;j>=0;j--) 
        {
            output.write(map[j]);
            output.write("\n");
            //System.out.println(map[j]);            
        }

        output.close();

        int uniqueTailLocations=locationCodes.size();

        System.out.println("The tail was in "+uniqueTailLocations+" different locations");

    }

}
