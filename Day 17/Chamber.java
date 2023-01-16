import java.util.*;

import java.io.BufferedReader;
import java.io.FileReader;

/* Advent of Code 2022
* Day 17 Part 2
*/

public class Chamber 
{

    private List<Boolean> rightJets;
    private final char JET_LEFT='<';
    private final char JET_RIGHT='>';
    public static final int WIDTH=7;
    private final int START_HEIGHT=3;
    private final int START_LEFT=2;
    private final int NO_ROCKS_PART_ONE=2022;
    private final long NO_ROCKS_PART_TWO=1000000000000l;
    private long heightCashed;
    private int heightChecked;

    private List<RockState> rockStateList;

    private List<Rock> rockList;
    private List<List<Boolean>> chamber;

    public Chamber() throws Exception
    {
        rightJets=new ArrayList<Boolean>();
        heightCashed=0;
        heightChecked=0;

        rockStateList=new ArrayList<RockState>();

        FileReader file = new FileReader("Day 17/input.txt");
        BufferedReader input = new BufferedReader(file);

        interperetInput(input);
        input.close();

        rockList=Arrays.asList(new HorizontalRock(),new PlusRock(),new CornerRock(),new VerticalRock(), new SquareRock());

        long towerHeightPartOne=dropRocks(NO_ROCKS_PART_ONE);
        //printChamber((int)towerHeightPartOne);
        towerHeightPartOne+=heightCashed;
        System.out.println("The tower height after "+ NO_ROCKS_PART_ONE+ " rocks is "+towerHeightPartOne);

        long towerHeightPartTwo=dropRocks(NO_ROCKS_PART_TWO);
        towerHeightPartTwo+=heightCashed;
        System.out.println("The tower height after "+ NO_ROCKS_PART_TWO+ " rocks is "+towerHeightPartTwo);
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

    private int dropRocks(long numberOfRocks)
    {
        heightCashed=0;
        chamber=new ArrayList<List<Boolean>>();
        rockStateList=new ArrayList<RockState>();
        for (int i = 0; i < WIDTH; i++) 
        {
            chamber.add(new ArrayList<Boolean>());   
        }

        int y=START_HEIGHT;
        int x=START_LEFT;
        int position=0;

        long r=0;
        for (r=0;r<numberOfRocks;r++)
        {
            int rockNum=(int)(r % (long)rockList.size());
            Rock rock=rockList.get(rockNum);
            position=rock.drop(chamber, x, y, rightJets, position);
            heightChecked=rock.getLowestPoint();
            int height=getHeight();
            int repetitionStartHeight;
            if((repetitionStartHeight=checkChamber(height))!=-1)
            {
                long[] repetitions=checkRepetition(r,rockNum,position,numberOfRocks,repetitionStartHeight);
                if (repetitions!=null)
                {
                    r=repetitions[1];
                    heightCashed=repetitions[0];
                }
            }
            height=getHeight();
            y=height+START_HEIGHT;
            //printChamber(height);
            rockNum++;
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

    public void printChamber(int height)
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

    private int checkChamber(int chamberHeight)
    {
        boolean fullLine;
        for(int y=chamberHeight-1;y>heightChecked;y--)   //Check all lines not checked starting at the top
        {
            fullLine=true;
            for(int x=0;x<WIDTH;x++)    //Check for a line that if fully filled
            {
                if (!chamber.get(x).get(y) && !chamber.get(x).get(y-1))
                {
                    fullLine=false;
                    break;  //Not fully filled, check next line down
                }
            }
            if (fullLine)
            {
                heightCashed+=y;  
                for(int x=0;x<WIDTH;x++)    //Reset
                {
                    List<Boolean> newVert=new ArrayList<Boolean>();
                    newVert=new ArrayList<Boolean>(chamber.get(x).subList(y,chamberHeight));   
                    chamber.set(x, new ArrayList<Boolean>(newVert));
                }
                heightChecked=0;
                return y;
            }
        }
        return -1;
    }

    private long[] checkRepetition(long rocksDropped,int rockNum,int position,long totalNumberOfRocks,int height)
    {
        long chamberCode=calculateChamberCode();
        RockState newState=new RockState(position, rockNum, height, chamberCode,rocksDropped);
        long repetitions=0;
        boolean repetitionFound=false;
        long repetitionHeight=0;
        long rocksDroppedBeforeRepetition=0;
        long rocksDroppedRepetition=0;
        long heightBeforeRepetition=0;
        for (RockState rockState : rockStateList) 
        {
            if(!repetitionFound)
            {
                if(newState.equals(rockState)) //The simulation is repeating itself
                {
                    rocksDroppedBeforeRepetition=rockState.getRocksDropped();
                    long rocksDroppedNow=newState.getRocksDropped();
                    rocksDroppedRepetition=rocksDroppedNow-rocksDroppedBeforeRepetition;
                    repetitions=(long)Math.floor((totalNumberOfRocks-rocksDroppedBeforeRepetition)/rocksDroppedRepetition);
                    for(RockState previouRockState:rockStateList)
                    {
                        heightBeforeRepetition+=previouRockState.getHeight();
                        if(previouRockState.equals(rockState))
                        {
                            break;
                        }
                    }
                    repetitionFound=true;
                }
            }
            else
            {
                repetitionHeight+=rockState.getHeight();
            }
        }
        if(repetitionFound)
        {
            repetitionHeight+=newState.getHeight();
            long heightFromRepetitions=(repetitionHeight*repetitions)+heightBeforeRepetition;
            long r=rocksDroppedBeforeRepetition+(rocksDroppedRepetition*repetitions);
            rockStateList=new ArrayList<RockState>();
            return new long[]{heightFromRepetitions,r};
        }
        rockStateList.add(newState);
        return null;
    }

    private long calculateChamberCode()
    {
        long code=0;
        int newHeight=chamber.get(0).size();
        for(int y=0;y<newHeight;y++)
        {
            for (int x=0;x<WIDTH;x++)
            {
                if(chamber.get(x).get(y))
                {
                    code+=Math.pow(2, (y*WIDTH)+x);
                }
            }
        }
        return code;
    }
}
