import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.File;

/* Advent of Code 2022
* Day 14 Part 2
*/

public class Cave 
{
    public static int lowestPoint;
    public static int furthestPoint;
    private final int FLOOR_DISTANCE=2;
    public static int floorHeight;
    
    private char[][] caveSlice;
    private Coordinate[] coordinates;

    private final Character ROCK='#';
    private final Character AIR=' ';
    private final Character SAND='o';
    private final Character START='+';

    public Cave() throws Exception
    {
        File inputFile = new File("Day 14/input.txt");
        Scanner input = new Scanner(inputFile);
        
        coordinates=new Coordinate[2];
        for (int i=0;i<2;i++)
        {
            coordinates[i]=new Coordinate();
        }

        interpretInput(input);

        lowestPoint=Collections.max(coordinates[1]);    //y
        furthestPoint=Math.max(Collections.max(coordinates[0]),Math.abs(Collections.min(coordinates[0])));  //Greatest x, could be negative due to encoding...

        floorHeight=lowestPoint+FLOOR_DISTANCE;
        
        caveSlice=new char[floorHeight+1][furthestPoint+1000];   //Expand horizontally a bit to allow for sand that falls to the side

        createLines();

        input.close();


        boolean stopped=false;
        int voidSandUnits=-1;
        int sandUnits=0;
        caveSlice[Sand.DROP_Y][Sand.DROP_X]=START;

        while (!stopped)
        {
            Sand sand=new Sand(caveSlice);
            stopped=sand.drop();
            int y=sand.getY();
            int x=sand.getX();
            caveSlice[y][x]=SAND;
            if(!stopped)
            {
                if(voidSandUnits==-1 && !sand.getBounds())  
                {
                    voidSandUnits=sandUnits;    //Answer for part 1
                    stopped=false;
                }
            }
            sandUnits++;
        }

        FileWriter output=createFile();
        printMap(output);

        output.close();

        System.out.println(voidSandUnits + " units of sand were dropped before reaching the void");
        System.out.println(sandUnits + " of sand were dropped before the source was blocked");
    }

    private void interpretInput(Scanner input)
    {
        String line;

        while(input.hasNextLine())
        {
            line=input.nextLine();
            String[] points=line.split(" -> ");

            for (String point : points) 
            {
                String[] coordinateString=point.split(",");
                int length=coordinateString.length;
                if (length!=2)
                {
                    System.out.println("Error! Length is "+length);
                }
                for (int i=0;i<length;i++)
                {
                    coordinates[i].add(Integer.parseInt(coordinateString[i]));
                }
            }
            int lastIndex=coordinates[0].size()-1;
            int lastConnectedX=coordinates[0].get(lastIndex);
            if(lastConnectedX==0)
            {
                System.out.println("Error! X is 0, will not encode");
            }
            coordinates[0].set(lastIndex,-lastConnectedX);  //Last X set as negative to encode info that next point is not connected
        }
    }

    private void createLines()
    {
        while(coordinates[0].size()>0)
        {
            int y1=coordinates[1].remove(0);        
            int y2=coordinates[1].get(0);
            int x1=coordinates[0].remove(0);    //get and remove first element
            int x2=coordinates[0].get(0);       //get new first element
            if(x2<0)    //The encoded symbol that this is the last connected point has been received
            {
                x2=-x2;
                coordinates[0].remove(0);   //So remove this element - will not be used next time 
                coordinates[1].remove(0);
            }

            if(x1!=x2)
            {
                int lowerX=Math.min(x1, x2);
                int higherX=Math.max(x1, x2);
                for (int x=lowerX;x<=higherX;x++)
                {
                    caveSlice[y1][x]= ROCK;
                }
            }
            else if (y1!=y2)
            {
                int lowerY=Math.min(y1, y2);
                int higherY=Math.max(y1, y2);
                for (int y=lowerY;y<=higherY;y++)
                {
                    caveSlice[y][x1]= ROCK;
                }
            } 
            else
            {
                System.out.println("Error!");
            }
        }
    }

    private FileWriter createFile()
    {
        new File("Day 14/output.txt");
        try {
        return new FileWriter("Day 14/output.txt");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    private void printMap(FileWriter output)
    {
        try {
            for (char[] line : caveSlice)
            {
                for (char space: line)
                {
                    if(space=='\0')
                    {
                        space=AIR;
                    }
                    output.append(space);
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
