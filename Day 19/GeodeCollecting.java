import java.nio.file.*;
import java.util.*;

/* Advent of Code 2022
* Day 19 Part 1
*/

public class GeodeCollecting 
{
    private List<Blueprint> blueprints;
    private final int PART_ONE_TIME_LIMIT=24;
    private final int PART_TWO_TIME_LIMIT=32;
    private final boolean PART_ONE=true;
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
                int bestNumberOfGeodes=factory.getBestResult(START_ROBOT,0,PART_ONE_TIME_LIMIT,0);
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
                int bestNumberOfGeodes=factory.getBestResult(START_ROBOT,0,PART_TWO_TIME_LIMIT,0);
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
    
}
