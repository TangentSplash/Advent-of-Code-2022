/* Advent of Code 2022
* Day 8 Part 2
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
        int y=0;
        for (Tree[] trees : treeGrid) 
        {
            int x=0;
            for (Tree tree : trees) 
            {
                if(tree.isVisable())
                {
                    numberOfVisableTrees++;
                }
                treesSeen(x, y);
                x++;
            }
            y++;
        }

        int bestScenicScore=0;
        for (Tree[] trees : treeGrid) 
        {
            for (Tree tree : trees) 
            {
                bestScenicScore=Math.max(bestScenicScore, tree.getScenicScore());
            }
        }


        System.out.println("The number of visable trees is "+ numberOfVisableTrees);
        System.out.println("The best scenic score is "+ bestScenicScore);
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

    private void treesSeen(int currentTreeX,int currentTreeY)
    {
        int[] visableTrees=new int[4];
        for(int i=0;i<4;i++)
        {
            visableTrees[i]=checkDirection(currentTreeX, currentTreeY, i);
        }
        treeGrid[currentTreeY][currentTreeX].setTreesVisable(visableTrees);
    }

    private int checkDirection(int currentTreeX,int currentTreeY, int direction)
    {
        int currentTreeHeight=treeGrid[currentTreeY][currentTreeX].getHeight();
        int treesVisable=0;

        int x=currentTreeX;
        int y=currentTreeY;
        int i=1;
        while(x<gridWidth && x>=0 && y<gridHeight && y>=0)   
        {
            switch (direction)
            {
                case TOP:
                    x=currentTreeX;
                    y=currentTreeY-i;
                    break;
                case BOTTOM:
                    x=currentTreeX;
                    y=currentTreeY+i;
                    break;
                case LEFT:
                    x=currentTreeX-i;
                    y=currentTreeY;
                    break;
                case RIGHT:
                    x=currentTreeX+i;
                    y=currentTreeY;
            }

            if (x>=gridWidth || x<0 || y>=gridHeight || y<0)
            {
                return treesVisable;
            }
            treesVisable++;
            if(treeGrid[y][x].getHeight()>=currentTreeHeight)
            {
                return treesVisable;
            }
            i++;
        }
        return treesVisable;
    }
    
}
