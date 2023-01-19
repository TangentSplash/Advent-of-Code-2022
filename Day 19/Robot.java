import java.util.*;

public class Robot
{
    private String type;
    private Map<String,Integer> costs;

    public Robot(String type,String[] costsString) 
    {
        this.type=type;
        this.costs=new HashMap<String,Integer>();
        for (String cost : costsString) 
        {
            String[] nameAndAmount=cost.split(" ");
            costs.put(nameAndAmount[1], Integer.parseInt(nameAndAmount[0]));
        }
    }

    public List<String> getRequiredElementTypes()
    {
        return new ArrayList<String>(costs.keySet());
    }

    public Map<String,Integer> getCosts()
    {
        return costs;
    }

    public String getType()
    {
        return type;
    }
    
}
