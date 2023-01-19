import java.nio.file.*;
import java.util.*;

/* Advent of Code 2022
* Day 19 Part 1
*/

public class GeodeCollecting 
{
    private List<Blueprint> blueprints;
    private final int TIME_LIMIT=24;
    public static final String START_ROBOT="ore";
    public static final String WANTED_ELEMENT="geode";

    public GeodeCollecting()throws Exception
    {
        Path path = Paths.get("Day 19/inputtest.txt");
        blueprints=new ArrayList<Blueprint>();
        interpretInput(path);

        for (Blueprint blueprint: blueprints) 
        {
            RobotFactory factory=blueprint.buildRobotFactory();
            int bestNumberOfGeodes=factory.getBestResult(START_ROBOT,0,TIME_LIMIT/*,new HashSet<String>(),new HashSet<String>()*/);
            int qualityLevel=blueprint.getNumber()*bestNumberOfGeodes;
            System.out.println();
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
