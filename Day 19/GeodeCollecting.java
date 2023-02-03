import java.nio.file.*;
import java.util.*;

/* Advent of Code 2022
* Day 19 Part 2
*/

public class GeodeCollecting 
{
    private List<Blueprint> blueprints;
    private final int PART_ONE_TIME_LIMIT=24;
    private final int PART_TWO_TIME_LIMIT=32;
    private final boolean PART_ONE=false;
    private final int PART_TWO_BLUEPRINTS=3;
    public static final String START_ROBOT="ore";
    public static final String WANTED_ELEMENT="geode";

    public GeodeCollecting()throws Exception
    {
        Path path = Paths.get("Day 19/input.txt");
        blueprints=new ArrayList<Blueprint>();
        interpretInput(path);

        if(PART_ONE)
        {
            int qualityLevelSum=0;
            for (Blueprint blueprint: blueprints) 
            {
                RobotFactory factory=blueprint.buildRobotFactory();
                int bestNumberOfGeodes=getMaxGeodes(PART_ONE_TIME_LIMIT, factory);
                int number=blueprint.getNumber();
                int qualityLevel=number*bestNumberOfGeodes;
                System.out.println("Blueprint "+number+": Best number of geodes is "+ bestNumberOfGeodes+". Giving a quality level of "+qualityLevel);
                qualityLevelSum+=qualityLevel;
            }
            System.out.println("\nQuality level sum is "+qualityLevelSum);
        }
        else
        {
            int geodes=1;
            for (int i=0;i<PART_TWO_BLUEPRINTS;i++)
            {
                Blueprint blueprint=blueprints.get(i);
                RobotFactory factory=blueprint.buildRobotFactory();
                int number=blueprint.getNumber();
                int bestNumberOfGeodes=getMaxGeodes(PART_TWO_TIME_LIMIT, factory);
                System.out.println("Blueprint "+number+": Best number of geodes is "+ bestNumberOfGeodes);
                geodes*=bestNumberOfGeodes;
            }
            System.out.println("\nThe geodes number is "+geodes);
        }
    }

    private void interpretInput(Path path) throws Exception
    {
        List<String> blueprintStringList=Files.readAllLines(path);
        String allBlueprintsString=String.join("",blueprintStringList);
        String[] blueprintsStrings=allBlueprintsString.split("Blueprint ");
        for (String blueprint : blueprintsStrings) 
        {
            if (!blueprint.isBlank())
            {
                String[] details=blueprint.split(":");  
                int number=Integer.parseInt(details[0]);
                String[] robotsDetails=details[1].split("Each ");
                List<String> types=new ArrayList<String>();
                List<String[]> costsList=new ArrayList<String[]>();
                for(String robotDetails : robotsDetails)
                {
                    if (!robotDetails.isBlank())
                    {
                        String[] typeAndCosts=robotDetails.split(" robot costs ");
                        types.add(typeAndCosts[0]);
                        String[] costs=typeAndCosts[1].split(" and ");
                        int size=costs.length;
                        costs[size-1]=costs[size-1].replace(".", "");
                        costsList.add(costs);
                    }
                }
                blueprints.add(new Blueprint(number, types,costsList));
            }
        }
        return;
    }

    private int getMaxGeodes(int timeLimit,RobotFactory startingFactory) throws CloneNotSupportedException
    {
        startingFactory.setupClone(START_ROBOT,0,timeLimit);
        List<RobotFactory> nodes=new ArrayList<RobotFactory>();
        nodes.add(startingFactory);
        int maxCollected=0;

        while(!nodes.isEmpty())
        {
            int length=nodes.size();
            Collections.sort(nodes);
            for (int i=0;i<length;i++)
            {
                int hypothetical=nodes.get(i).getMaxHyp();
                if(hypothetical<=maxCollected)
                {
                    nodes.remove(i);
                    i--;
                    length--;
                }
                else
                {
                    break;
                }
            }
            if(nodes.isEmpty())
            {
                break;
            }
            Collections.sort(nodes,new SortByHeuristic());  //Todo Double sorting is a disaster
            RobotFactory currentNode=nodes.remove(0);
            List<RobotFactory> newOptions=currentNode.getOptions(maxCollected);
            if(newOptions.isEmpty())
            {
                int geodes=currentNode.countGeodes();
                maxCollected=Math.max(maxCollected, geodes);
            }
            else
            {
                nodes.addAll(newOptions);
            }
        }

        return maxCollected;
    }
    
}
