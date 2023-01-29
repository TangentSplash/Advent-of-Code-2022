import java.util.*;

public class SortByHeuristic implements Comparator<RobotFactory>
{
    public int compare(RobotFactory a, RobotFactory b)
    {
        return (b.getMaxHyp()/b.getTimeRemaining())-(a.getMaxHyp()/a.getTimeRemaining());
    }
}
