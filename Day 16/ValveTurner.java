import java.util.*;

public class ValveTurner 
{
    private Valve currentValve;
    private Set<Valve> opened;
    private int timeRemaining;

    private List<Valve> paths;
    private List<Integer> pressures;

    public ValveTurner(Valve currentValve,int timeRemaining,Set<Valve> opened)
    {
        this.currentValve=currentValve;
        this.opened=new HashSet<Valve>(opened);
        this.timeRemaining=timeRemaining;

        paths=new ArrayList<Valve>(currentValve.getOtherValves());
        pressures=new ArrayList<Integer>();

        paths.removeAll(opened);
    }

    public int bestPath()
    {
        int totalPressure=0;
        if (timeRemaining>0)
        {
            if(currentValve.getFlowRate()>0)
            {
                opened.add(currentValve);
                timeRemaining--;
                totalPressure=currentValve.getFlowRate()*timeRemaining;
            }

            for (Valve valve : paths) 
            {
                int timeMoving=currentValve.getDistTo(valve);
                ValveTurner option=new ValveTurner(valve,timeRemaining-timeMoving,opened);
                pressures.add(option.bestPath());
            }

            if(!pressures.isEmpty())
            {
                totalPressure+=Collections.max(pressures);
            }
        }
        return totalPressure;
    }

    public List<Valve> getValves()
    {
        return paths;
    }
}
