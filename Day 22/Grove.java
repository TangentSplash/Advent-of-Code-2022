import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import java.io.File;

/* Advent of Code 2022
* Day 22 Part 1
*/

public class Grove 
{
    public static int MAP_LENGTH;
    public static int MAP_WIDTH; 
    public Grove () throws Exception
    {
        Path path = Paths.get("Day 22/input.txt");
        List<String> inputLines=Files.readAllLines(path);
        
        int inputLength=inputLines.size();
        MAP_LENGTH=inputLength-2;
        String instructions=inputLines.get(inputLength-1);
        String[] seperateInstructions=instructions.split("(?=R|L)|(?<=R|L)"); //Split on left/right instruction

        MAP_WIDTH=getWidth(inputLines,MAP_LENGTH);

        char[][] map=new char[MAP_LENGTH][MAP_WIDTH];
        for (int i=0;i<MAP_LENGTH;i++)
        {
            char[] mapLine=inputLines.get(i).toCharArray();
            for(int j=0;j<mapLine.length;j++)
            {
                map[i][j]=mapLine[j];
            }
            for(int j=mapLine.length;j<MAP_WIDTH;j++)
            {
                map[i][j]=' ';
            }
        }
        int startX=getStartX(map[0]);

        Navigator navigator=new Navigator(startX,0,Orientations.RIGHT,map); 

        for (int i=0;i<seperateInstructions.length;i++)
        {
            navigator.move(Integer.parseInt(seperateInstructions[i]));
            i++;
            if(i<seperateInstructions.length)
            {
                navigator.rotate(seperateInstructions[i]);
            }
        }

        int [] pos=navigator.getPos();
        int facing=navigator.getOrientation().ordinal();

        int password=(1000*(pos[1]+1))+(4*(pos[0]+1))+facing;
        FileWriter output=createFile();
        printMap(output, map);
        output.close();


        System.out.println("The final password is "+password);
    }

    private int getWidth(List<String> mapString,int length)
    {
        int width=-1;
        for (int i=0; i<length; i++) 
        {
            width=Math.max(width, mapString.get(i).length());
        }
        return width;
    }

    private int getStartX(char[] map)
    {
        for (int i=0;i<MAP_WIDTH;i++)
        {
            if(map[i]==Navigator.OPEN)
            {
                return i;
            }
        }
        System.out.println("Starting position not found");
        return -1;
    }

    private FileWriter createFile()
    {
        new File("output.txt");
        try {
        return new FileWriter("output.txt");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    private void printMap(FileWriter output,char[][] map)
    {
        try {
            for (char[] line : map)
            {
                for (char height: line)
                {
                    output.append(height);
                }
                output.append("\n");
            }
            output.append("\n\n");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
}


