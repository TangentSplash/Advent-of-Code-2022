import java.util.*;

public class RobotFactory implements Cloneable
{
    private Map<String,Integer> resources;
    private final Map<String,Robot> robotBlueprints;
    private final Map<Robot,Robot> biggestSpenderOf;
    private final Map<String,Integer> lastTimeToBuild;
    private Map<String,Integer> collectionRates;
    private Set<String> availableElements;

    private final int BUILD_TIME=1; 
    
    
    public RobotFactory(Map<String,Robot> robotBlueprints, Map<Robot,Robot> biggestSpenderOf, Map<String,Integer> lastTimeToBuild)
    {
        this.robotBlueprints=robotBlueprints;
        this.biggestSpenderOf=biggestSpenderOf;
        this.lastTimeToBuild=lastTimeToBuild;
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

    public int getBestResult(String robotToBuild,int timeSpent,int timeRemaining,int maxCollectedAnywhereYet) throws CloneNotSupportedException
    {  
        int maxGeodes=0;

        if(timeSpent!=0)
        {
            collectElements(timeSpent); 
        }
        
        newRobot(robotToBuild);

        int maxPossible=getMaxHypothetical(timeRemaining);
        if(maxPossible<=maxCollectedAnywhereYet)
        {
            return countGeodes();
        }
        //TODO one option - buy as many geodes as possible - ...?
        List<Robot> canMake=new ArrayList<Robot>();
        for (Robot robot : robotBlueprints.values()) 
        {
            List<String> elementsItDoesntHave=robot.getRequiredElementTypes();
            elementsItDoesntHave.removeAll(availableElements);
            if(elementsItDoesntHave.isEmpty())
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

                timeNeeded=Math.max(timeNeeded, timeNeededHere);
            }
            //Create factory clone - for a different instance
            int newTimeRemaining=timeRemaining-timeNeeded;

            if (newTimeRemaining>=lastTimeToBuild.get(robot.getType()) && thisRobotMightContribute(robot, timeRemaining,newTimeRemaining)) //Only build this robot if it could be useful
            {
                RobotFactory newBranch=this.clone();
                newBranch.useElements(needs,requirements);

                maxCollectedAnywhereYet=Math.max(maxGeodes, maxCollectedAnywhereYet);
                int geodes=newBranch.getBestResult(robot.getType(),timeNeeded,newTimeRemaining,maxCollectedAnywhereYet);
                maxGeodes=Math.max(geodes, maxGeodes);
            }
        }
        collectElements(timeRemaining);
        maxGeodes=Math.max(countGeodes(), maxGeodes);
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

    private void collectElements(int timeSpent)
    {
        for (String element : availableElements) //collect resources
        {
            collectElement(element, timeSpent);
        }
    }

    private void collectElement(String element,int timeSpent)
    {
        int rate=collectionRates.get(element);
        int collected=rate*timeSpent;
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

    private int getMaxHypothetical(int timeRemaining)
    {
        int currentRate=collectionRates.get(GeodeCollecting.WANTED_ELEMENT);
        int currentNo=resources.get(GeodeCollecting.WANTED_ELEMENT);
        int resultAtCurrentRate=(timeRemaining*currentRate);
        if(!availableElements.contains(GeodeCollecting.WANTED_ELEMENT))
        {
            resultAtCurrentRate+=currentRate;
            timeRemaining--;
        }
        if(!availableElements.contains("obsidian"))
        {
            resultAtCurrentRate+=2*currentRate;
            timeRemaining-=2;
        }
        if(!availableElements.contains("clay")) //TODO If we dont have clay - what is the fastest time we could get a usable amount of clay?
        {
            resultAtCurrentRate+=2*currentRate;
            timeRemaining-=2;
        }
        int withNewGeodeEachTime=(timeRemaining*(timeRemaining+1))/2;
        int maxHypothetical=resultAtCurrentRate+withNewGeodeEachTime;
        /*for (int t=0;t<timeRemaining;t++)
        {
            if (!availableElements.contains(GeodeCollecting.WANTED_ELEMENT) && t==1)    //If cant possibly make geode this time - dont count it
            {
                continue;
            }
            maxHypothetical+=(currentRate+t)*(timeRemaining-t);
        }*/
        ///int maxHypothetical=(currentRate*timeRemaining*timeRemaining)+((timeRemaining-currentRate)*((timeRemaining*(timeRemaining+1))/2))-((timeRemaining*(timeRemaining+1)*((2*timeRemaining)+1))/6);
        return maxHypothetical+currentNo;
    }

    private boolean thisRobotMightContribute(Robot robot,int timeRemaining,int newTimeRemaining)
    { 
        String robotType=robot.getType();
        if (robotType.equals(GeodeCollecting.WANTED_ELEMENT))
        {
            return true;
        }
        Robot biggestSpender=biggestSpenderOf.get(robot);
        String biggestSpenderType=biggestSpender.getType();

        int lastTime=lastTimeToBuild.get(biggestSpenderType)-1;
        int cost=biggestSpender.getCosts().get(robotType);
        int greatestPossibleCost=cost*(newTimeRemaining-(lastTime+1));
        if(robotType.equals("ore"))
        {
            int newlastTime=lastTimeToBuild.get(GeodeCollecting.WANTED_ELEMENT)-1;
            int costForGeode=robotBlueprints.get(GeodeCollecting.WANTED_ELEMENT).getCosts().get(robotType);
            int time=(lastTime+1)-newlastTime;
            greatestPossibleCost+=(costForGeode*time);
            lastTime=newlastTime;
        }
        int willHaveAtEndWithNoNew=((timeRemaining-lastTime)*collectionRates.get(robotType))+resources.get(robotType);
        return greatestPossibleCost>willHaveAtEndWithNoNew;
    }
}
