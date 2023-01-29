import java.util.*;

public class RobotFactory implements Cloneable,Comparable<RobotFactory>
{
    private Map<String,Integer> resources;
    private final Map<String,Robot> robotBlueprints;
    private final Map<Robot,Robot> biggestSpenderOf;
    private final Map<String,Integer> lastTimeToBuild;
    private Map<String,Integer> collectionRates;
    private Set<String> availableElements;

    private final int BUILD_TIME=1; 
    private final int MIN_ORE_TIME;
    private final int MIN_OBSIDAIN_TIME;
    private final int MIN_CLAY_TIME;

    private int timeRemaining;

    private int maxHypothetical;
    
    
    public RobotFactory(Map<String,Robot> robotBlueprints, Map<Robot,Robot> biggestSpenderOf, Map<String,Integer> lastTimeToBuild,int minTimeToGetNeededClay,int minTimeToGetNeededObsidian,int minTimeToGetNeededOre)
    {
        this.robotBlueprints=robotBlueprints;
        this.biggestSpenderOf=biggestSpenderOf;
        this.lastTimeToBuild=lastTimeToBuild;
        this.MIN_ORE_TIME=minTimeToGetNeededOre;
        this.MIN_CLAY_TIME=minTimeToGetNeededClay;
        this.MIN_OBSIDAIN_TIME=minTimeToGetNeededObsidian;
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

    public void setupClone(String robotToBuild,int timeSpent,int timeRemaining)
    {
        if(timeSpent!=0)
        {
            collectElements(timeSpent); 
        }
        newRobot(robotToBuild);
        this.maxHypothetical= getMaxHypothetical(timeRemaining);
        this.timeRemaining=timeRemaining;
    }

    public Set<RobotFactory> getOptions(int maxCollectedAnywhereYet) throws CloneNotSupportedException
    {  
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
        Set<RobotFactory> options=new TreeSet<RobotFactory>();
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

            if (newTimeRemaining>=lastTimeToBuild.get(robot.getType()) && thisRobotMightContribute(robot, timeRemaining,newTimeRemaining)) //TODO REINSTATE:   Only build this robot if it could be useful
            {
                RobotFactory newBranch=this.clone();
                newBranch.useElements(needs,requirements);
                newBranch.setupClone(robot.getType(), timeNeeded, newTimeRemaining);
                options.add(newBranch);
            }
        }
        return options;
        /*Collections.sort(options);
        for (RobotFactory branch : options) 
        {
            int maxHypothetical=branch.maxHypothetical;
            if (maxHypothetical>maxCollectedAnywhereYet)
            {
                int geodes=branch.getOptions(maxCollectedAnywhereYet);   //Todo list of all maxHypothetical branches
                maxGeodes=Math.max(geodes, maxGeodes);
                maxCollectedAnywhereYet=Math.max(maxGeodes, maxCollectedAnywhereYet);
            }
        }
        collectElements(timeRemaining);
        maxGeodes=Math.max(countGeodes(), maxGeodes);
        return maxGeodes;*/
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

    public int countGeodes()
    {
        collectElements(timeRemaining);
        return resources.get(GeodeCollecting.WANTED_ELEMENT);
    }

    private int getMaxHypothetical(int timeRemaining)
    {
        int currentRate=collectionRates.get(GeodeCollecting.WANTED_ELEMENT);
        int currentNo=resources.get(GeodeCollecting.WANTED_ELEMENT);
        int resultAtCurrentRate=(timeRemaining*currentRate);

        if(!availableElements.contains(GeodeCollecting.WANTED_ELEMENT))
        {
            timeRemaining--;   
        }
        if(!availableElements.contains("obsidian"))
        {
            timeRemaining-=MIN_OBSIDAIN_TIME;   //Was 2
        }
        if(!availableElements.contains("clay")) 
        {
            timeRemaining-=MIN_CLAY_TIME;   //Was 2
        }
        if(!availableElements.contains("ore")) 
        {
            timeRemaining-=MIN_ORE_TIME;
        }
        int withNewGeodeEachTime=(timeRemaining*(timeRemaining+1))/2;
        int maxHypothetical=resultAtCurrentRate+withNewGeodeEachTime;
        
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

    public int compareTo(RobotFactory other) 
    {
        int hypothetical=maxHypothetical-other.maxHypothetical;
        if (hypothetical==0)
        {
            if(hashCode()>other.hashCode() )
            {
                hypothetical++;
            }
        }
        return hypothetical;
    }

    public int getMaxHyp()
    {
        return maxHypothetical;
    }

    public int getTimeRemaining()
    {
        return timeRemaining;
    }
}
