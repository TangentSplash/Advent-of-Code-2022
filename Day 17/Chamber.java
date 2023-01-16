import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;

/* Advent of Code 2022
* Day 17 Part 1
*/

public class Chamber 
{

    private List<Boolean> rightJets;
    private final char JET_LEFT='<';
    private final char JET_RIGHT='>';
    public static final int WIDTH=7;
    private final int START_HEIGHT=3;
    private final int START_LEFT=2;
    private final int NO_ROCKS=2022;

    private List<Rock> rockList;
    private List<List<Boolean>> chamber;

    public Chamber() throws Exception
    {
        rightJets=new ArrayList<Boolean>();

        FileReader file = new FileReader("Day 17/input.txt");
        BufferedReader input = new BufferedReader(file);

        interperetInput(input);
        input.close();

        rockList=Arrays.asList(new HorizontalRock(),new PlusRock(),new CornerRock(),new VerticalRock(), new SquareRock());
        int towerHeight=dropRocks();
        printChamber(towerHeight);
        System.out.println("The tower height after "+ NO_ROCKS+ " rocks is "+towerHeight);
    }

    private void interperetInput(BufferedReader input) throws Exception
    {
        int in;
        char direction;
        while((in=input.read())!=-1)
        {
            direction=(char)in;
            if (direction==JET_LEFT || direction==JET_RIGHT)
            {
                rightJets.add(direction==JET_RIGHT);
            }
            if (direction!=JET_LEFT && direction!=JET_RIGHT)
            {
                System.out.println("Note: Unexpected input "+direction);
            }
        }
    }

    private int dropRocks()
    {
        chamber=new ArrayList<List<Boolean>>();
        for (int i = 0; i < WIDTH; i++) 
        {
            chamber.add(new ArrayList<Boolean>());   
        }

        int y=START_HEIGHT;
        int x=START_LEFT;
        int position=0;

        int r=0;
        for (r=0;r<NO_ROCKS;r++)
        {
            Rock rock=rockList.get(Math.floorMod(r, rockList.size()));
            position=rock.drop(chamber, x, y, rightJets, position);
            int height=getHeight();
            y=height+START_HEIGHT;
            //printChamber(height);
        }
        return getHeight();
    }

    private int getHeight()
    {
        int maxHeight=0;
        for(int i=0;i<WIDTH;i++)
        {
            maxHeight=Math.max(chamber.get(i).size(),maxHeight);
        }
        initilise(maxHeight);
        return maxHeight;
    }

    private void initilise(int height)
    {
        for(int i=0;i<WIDTH;i++)
        {
            for(int j=chamber.get(i).size();j<height;j++)
            {
                chamber.get(i).add(false);
            }
        }
    }

    private void printChamber(int height)
    {
        for (int j=height-1;j>=0;j--)
        {
            for (int i=0;i<WIDTH;i++)
            {
                char c =' ';
                if (chamber.get(i).get(j))
                {
                    c='#';
                }
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
}
