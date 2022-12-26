/* Advent of Code 2022
 * Day 12 Part 1
 */

 import java.nio.file.*;
 import java.util.*;


public class Hill {

    public static final char START='S';
    public static final char END='E';

    public static int endX;
    public static int endY;

    public static int startingTimeToLive; 
    
    public static int checkpoint;

    private char[][] topography;
    private int width;
    private int length;

    private int [] startPos;

    private Clone shortestPathClone;

    public Hill() throws Exception
    {
        Path path = Paths.get("Day 12\\input.txt");
        List<String> topographyString=Files.readAllLines(path);
        
        width=topographyString.size();
        length=topographyString.get(0).length();

        topography=new char[width][length];
        for (int i=0;i<width;i++)
        {
            topography[i]=topographyString.get(i).toCharArray();
        }

        startPos=getStartAndEndPosition();
        startingTimeToLive=(int)(((long)length*(long)width*7L)/8L);
        
        Branch branch=new Branch(startPos[0], startPos[1], topography);

        int score=branch.explore();
        System.out.println(score);
        /*checkpoint=8;
        while (!Climb())
        {
            checkpoint*=2;
        }

        int shortestPathLength=shortestPathClone.getSteps();

        System.out.println("The fewest steps to the destination is "+shortestPathLength);*/
        
    }

    private int[] getStartAndEndPosition()
    {
        boolean startFound=false;
        boolean endFound=false;

        int[] positions=new int[2];
        for (int i=0;i<width;i++)
        {
            for (int j=0;j<length;j++)
            {
                if (topography[i][j]==START)
                {
                    positions[0]=j;
                    positions[1]=i;
                    startFound=true;
                }
                else if (topography[i][j]==END)
                {
                    endX=j;
                    endY=i;
                    endFound=true;
                }

                if(startFound && endFound)
                {
                    return positions;
                }
            }
        }
        return null;
    }

    private boolean Climb()
    {
        Clone climber=new Clone(startPos[0], startPos[1], topography, startingTimeToLive, new TreeSet<String>(),0);
        shortestPathClone=climber.getBestClone();

        return shortestPathClone!=null;
        /*if (shortestPathClone==null)
        {
            System.out.println("Well that didn't work");
            return;
        }*/
    }
    
}
