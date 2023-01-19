import java.util.*;

public class RobotFactory implements Cloneable
{
    private Map<String,Integer> resources;
    private final Map<String,Robot> robotBlueprints;
    private Map<String,Integer> collectionRates;
    private Set<String> availableElements;

    private final int BUILD_TIME=1; 
    
    public RobotFactory(Map<String,Robot> robotBlueprints)
    {
        this.robotBlueprints=robotBlueprints;
        collectionRates=new HashMap<String,Integer>();
        resources=new HashMap<String,Integer>();
        availableElements=new HashSet<String>();

        Set<String> types=robotBlueprints.keySet();
        for (String type : types) 
        {
            collectionRates.put(type,0);
            resources.put(type, 0);
        }
    }

    public int getBestResult(String robotToBuild,int timeSpent,int timeRemaining/*,Set<String> lastRobotsBuilt,Set<String> robotsBeforeThatOne*/) throws CloneNotSupportedException
    {  
        int maxGeodes=0;
        if(timeSpent!=0)
        {
            collectElements(timeSpent/*,robotsBeforeThatOne*/); 
        }
        
        newRobot(robotToBuild);

        List<Robot> canMake=new ArrayList<Robot>();
        for (Robot robot : robotBlueprints.values()) 
        {
            List<String> dontHave=robot.getRequiredElementTypes();
            dontHave.removeAll(availableElements);
            if(dontHave.isEmpty())
            {
                canMake.add(robot);
            }
        }
        
        for (Robot robot : canMake) 
        {
            Map<String,Integer> requirements=robot.getCosts();
            List<String> needs=robot.getRequiredElementTypes();
            int timeNeeded=BUILD_TIME;   //Need a minute to build the robot
            for (String element : needs) 
            {
                int required=requirements.get(element);
                int have=resources.get(element);
                int stillNeeded=Math.max(0,required-have);
                int rate=collectionRates.get(element);
                int timeNeededHere=BUILD_TIME+((int) Math.ceil((double)stillNeeded/(double)rate));
                /*if(lastRobotsBuilt.contains(element))
                {
                    timeNeededHere++;
                }*/
                timeNeeded=Math.max(timeNeeded, timeNeededHere);
            }
            //Create factory clone - for a different instance
            int newTimeRemaining=timeRemaining-timeNeeded;
            RobotFactory newBranch=this.clone();
            int geodes;
            if (newTimeRemaining>0)
            {
                newBranch.useElements(needs,requirements);
                /*Set<String> builtInThisTimeBracket=new HashSet<>();
                Set<String> builtLastTimeBracket=new HashSet<>();
                if(timeNeeded==0)
                {
                    builtInThisTimeBracket.addAll(lastRobotsBuilt);
                    builtInThisTimeBracket.add(robotToBuild);
                    builtLastTimeBracket.addAll(robotsBeforeThatOne);
                }
                else
                {
                    builtInThisTimeBracket.add(robotToBuild);
                    builtLastTimeBracket.addAll(lastRobotsBuilt);
                }*/
                geodes=newBranch.getBestResult(robot.getType(),timeNeeded,newTimeRemaining/*,builtInThisTimeBracket,builtLastTimeBracket*/);
            }
            else
            {
                newBranch.collectElements(timeRemaining/*,new HashSet<>(Arrays.asList(robotToBuild))*/);
                geodes=newBranch.countGeodes();
            }
            maxGeodes=Math.max(geodes, maxGeodes);
        }
        return maxGeodes;
    }

    private void newRobot(String type)
    {
        int rate=collectionRates.get(type);
        collectionRates.replace(type,  ++rate);
        availableElements.add(type);
    }

    private void useElements(List<String> elements,Map<String,Integer> requirements)
    {
        for (String element : elements) 
        {
            useElement(element, requirements.get(element));   
        }
    }

    private void useElement(String element, int amount)
    {
        int currentStock=resources.get(element);
        currentStock-=amount;
        resources.replace(element, currentStock);
    }

    private void collectElements(int timeSpent/*,Set<String> lastRobotBuilt*/)
    {
        for (String element : availableElements) //collect resources
        {
            /*boolean removeOne=lastRobotBuilt.contains(element);*/  //The robot that was last built took a minute to build- during which it was not collecting
            collectElement(element, timeSpent/*,removeOne*/);
        }
    }

    private void collectElement(String element,int timeSpent/*,boolean removeOne*/)
    {
        int rate=collectionRates.get(element);
        int collected=rate*timeSpent;
        /*if(removeOne)
        {
            collected--;
        }*/
        int current=resources.get(element);
        int newAmount=current+collected;
        if (newAmount<0)
        {
            System.out.println("Negative number of elements "+newAmount);
        }
        resources.replace(element, newAmount);
    }

    public RobotFactory clone() throws CloneNotSupportedException
    {
        RobotFactory clone=(RobotFactory)super.clone();
        clone.resources=new HashMap<>(resources);
        clone.collectionRates=new HashMap<>(collectionRates);
        clone.availableElements=new HashSet<>(availableElements);
        return clone;
    }

    private int countGeodes()
    {
        return resources.get(GeodeCollecting.WANTED_ELEMENT);
    }
    
}
