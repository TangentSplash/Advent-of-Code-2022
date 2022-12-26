import java.util.*;

public class Clone {
    private static final int CLIMBABLE_HEIGHT=1;

    private int timeToLive;
    private Set<String> myLocations;
    private char myHeight;
    private char[][] map;

    private int score;  //FIXME What is this used for?
    private int distance;

    private boolean newLocation;

    private Clone bestClone;

    private List<Clone> children; 

    private int x;
    private int y;

    private int maxX;

    private int mapLength;
    private int mapWidth;

    public Clone(int x,int y,char[][] map,int timeToLive,Set<String> previousLocations,int maxX)
    {
        bestClone=null;
        this.myLocations=new TreeSet<String>(previousLocations);
        newLocation=myLocations.add("x: "+x+", y: "+y);
        distance=100000;    //Ensure not set to 0 incorrectly when checked;
        this.maxX=Math.max(maxX, x);

        if (newLocation)
        {
            this.timeToLive=timeToLive;
            this.map=map;
            this.x=x;
            this.y=y;

            myHeight=map[y][x];
            if (myHeight==Hill.START)
            {
                myHeight='a';
            }
            mapWidth=map.length;
            mapLength=map[0].length;
            calculateScore();

            int startingTimeToLive=Hill.startingTimeToLive;
            int checkpoint=Hill.checkpoint;

            if (distance==0)
            {
                bestClone=this;
            }
            else if((timeToLive<startingTimeToLive-25 && x<10)||(timeToLive<startingTimeToLive-checkpoint && x<25)||(timeToLive<startingTimeToLive-(checkpoint*3) && x<50)|| this.maxX-x>40)
            {
                return;
            }
            else
            {
                explore();
            }
        }
    }

    private void explore()
    {
        if(timeToLive>1)
        {
            children=new ArrayList<>();
            createClones();
            checkOnClones();
        }
    }

    public boolean newLocation()
    {
        return newLocation;
    }

    private void createClones()
    {
        createClone(x+1, y);
        createClone(x, y+1);
        createClone(x, y-1);
        createClone(x-1, y);
    }

    private void createClone(int newX,int newY)
    {
        if(newX>=0 && newX<mapLength && newY>=0 && newY<mapWidth)
        {
            char nextHeight=map[newY][newX];
            if(nextHeight==Hill.END)    //Won't be able to go back to start - but shouldn't be doing that anyway
            {
                nextHeight='z';
            }
            if(nextHeight<=myHeight+CLIMBABLE_HEIGHT)  
            {
                Clone nextChild=new Clone(newX, newY, map, timeToLive-1, myLocations,maxX);
                if(nextChild.newLocation()) //In new location - not retracing steps
                {
                    children.add(nextChild);
                }
            }
        }
    }

    private void calculateScore()
    {
        distance=Math.abs(x-Hill.endX)+Math.abs(y-Hill.endY);
        score=(((int)myHeight)*100)+distance;
    } 

    public int getScore()
    {
        return score;
    }

    private void checkOnClones()    
    {
        Clone leastStepsClone=null;
        int minimumSteps=100000;    //FIXME Floating constant
        for (Clone child : children) 
        {
            Clone childsBest=child.getBestClone();
            if(childsBest!=null)
            {
                int steps=childsBest.getSteps();
                if(steps<minimumSteps)
                {
                    minimumSteps=steps;
                    leastStepsClone=childsBest;
                }          
            }
        }

        bestClone=leastStepsClone;
    }

    public Clone getBestClone()
    {
        return bestClone;
    }

    public int getSteps()
    {
        return myLocations.size()-1;    //Don't count the starting position as a step
    }
    
}
