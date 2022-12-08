/* Advent of Code 2022
* Day 8 Part 1
*/

import java.nio.file.*;
import java.util.*;

public class TreeHouse {
    private static final int TOP=0;
    private static final int BOTTOM=1;
    private static final int LEFT=2;
    private static final int RIGHT=3;

    private int gridWidth;
    private int gridHeight;
    private Tree[][] treeGrid;

    public TreeHouse() throws Exception
    {
        Path path = Paths.get("Day 8\\input.txt");
        List<String> treeGridString=Files.readAllLines(path);

        gridWidth= treeGridString.get(0).length();
        gridHeight=treeGridString.size();
        treeGrid=new Tree[gridWidth][gridHeight];

        for (int y=0;y<gridHeight;y++)
        {
            char[] treeLineChars= treeGridString.get(y).toCharArray();
            for (int x=0;x<gridWidth;x++)
            {
                treeGrid[y][x]=new Tree(Character.getNumericValue(treeLineChars[x]));
            }
        }

        for (int i=0;i<4;i++)
        {
            searchSection(i);
        }

        int numberOfVisableTrees=0;
        for (Tree[] trees : treeGrid) 
        {
            for (Tree tree : trees) 
            {
                if(tree.isVisable())
                {
                    numberOfVisableTrees++;
                }
            }
        }
        System.out.println("The number of visable trees is "+ numberOfVisableTrees);
    }

    private void searchSection(int direction)
    {
        for (int a=0;a<gridHeight;a++)
        {
            int currentMax=-1;
            for (int b=0;b<gridWidth;b++)
            {
                int x,y;
                switch (direction) {
                    case TOP:
                        y=b;
                        x=a;
                        break;
                    case BOTTOM:
                        y=(gridHeight-1)-b;
                        x=a;
                        break;
                    case LEFT:
                        y=a;
                        x=b;
                        break;
                    case RIGHT:
                        y=a;
                        x=(gridWidth-1)-b;
                        break;
                    default:
                        System.out.println("Unrecognised input paramater "+direction);
                        return;
                }
                currentMax=treeGrid[y][x].compapareHeight(currentMax);
            }
        }
    }

    private void treesSeen(int x,int y)
    {
        
    }
    
}
