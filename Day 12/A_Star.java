import java.nio.file.*;
import java.util.*;

public class A_Star {

    public static final char START='S';
    public static final char END='E';

    public static int endX;
    public static int endY;

    public static char[][] topography;
    public static int width;
    public static int length;

    private int [] startPos;

    public A_Star() throws Exception
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

        HillNode node=new HillNode(startPos[0], startPos[1], 'a', 0, 0);
        
        
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
}
