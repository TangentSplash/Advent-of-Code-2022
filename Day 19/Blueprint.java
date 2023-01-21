import java.util.*;

public class Blueprint 
{
    private int number;
    private Map<String,Robot> robotBlueprints;
    private Map<Robot,Robot> biggestSpenderOf;
    private Map<String,Integer> lastTimeToBuild;

    private final int TIME_NEEDED_AFTER_BUILD_GEODE=1;
    private final int TIME_NEEDED_AFTER_BUILD_CLAY=5;
    private final int TIME_NEEDED_AFTER_BUILD_OTHER=3;

    private final String CLAY="clay";

    public Blueprint(int number, List<String> types,List<String[]> costs) 
    {
        this.number=number;
        this.robotBlueprints=new HashMap<String,Robot>();
        this.biggestSpenderOf=new HashMap<Robot,Robot>();
        this.lastTimeToBuild=new HashMap<String,Integer>();

        interpretRobotBlueprints(types, costs);

        getBiggestSpender();
        getLastTimeToBuild();
    }

    public int getNumber()
    {
        return number;
    }

    private void interpretRobotBlueprints(List<String> types,List<String[]> costs)
    {
        for (int i = 0; i < types.size(); i++) 
        {
            robotBlueprints.put(types.get(i),new Robot(types.get(i), costs.get(i)));
        }
    }

    public RobotFactory buildRobotFactory()
    {
        return new RobotFactory(robotBlueprints,biggestSpenderOf,lastTimeToBuild);
    }

    private void getBiggestSpender()
    {
        for (Robot element : robotBlueprints.values()) 
        {
            int maxCost=0;
            Robot biggestUser=null;
            for (Robot needs : robotBlueprints.values()) 
            {
                Integer cost=needs.getCosts().get(element.getType());
                if(cost!=null && cost>maxCost)
                {
                    maxCost=cost;
                    biggestUser=needs;
                }
            }
            biggestSpenderOf.put(element, biggestUser);
        }
    }
    
    private void getLastTimeToBuild()
    {
        for (Robot element : robotBlueprints.values()) 
        {
            String type=element.getType();
            if(type.equals(GeodeCollecting.WANTED_ELEMENT))
            {
                lastTimeToBuild.put(type, TIME_NEEDED_AFTER_BUILD_GEODE);
            }
            else if(type.equals(CLAY))
            {
                lastTimeToBuild.put(type,TIME_NEEDED_AFTER_BUILD_CLAY);
            }
            else
            {
                lastTimeToBuild.put(type,TIME_NEEDED_AFTER_BUILD_OTHER);
            }
        }
    }
}
