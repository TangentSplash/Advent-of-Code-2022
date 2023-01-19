import java.util.*;

public class Blueprint 
{
    private int number;
    private Map<String,Robot> robotBlueprints;

    public Blueprint(int number, List<String> types,List<String[]> costs) 
    {
        this.number=number;
        this.robotBlueprints=new HashMap<String,Robot>();
        interpretRobotBlueprints(types, costs);
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
        return new RobotFactory(robotBlueprints);
    }
    
}
