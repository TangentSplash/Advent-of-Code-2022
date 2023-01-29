import java.util.*;

public class SortByHeuristic implements Comparator<RobotFactory>
{
    public int compare(RobotFactory a, RobotFactory b)
    {
        int heuristic=(b.getMaxHyp()/b.getTimeRemaining())-(a.getMaxHyp()/a.getTimeRemaining());
        if (heuristic==0)
        {    
            if(a.hashCode()>b.hashCode())
            {
                heuristic++;
            }
        }
        return heuristic;
    }
}
