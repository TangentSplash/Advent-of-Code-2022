import java.util.Comparator;

public class RadiusComparator implements Comparator<Location>
{
    public int compare(Location l1, Location l2)
    {
        int distance1=l1.getMaxDistance();
        int distance2=l2.getMaxDistance();
        return distance1-distance2;
    }
}
