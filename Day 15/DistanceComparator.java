import java.util.Comparator;

public class DistanceComparator extends Location implements Comparator<Location>
{
    public DistanceComparator(int rootX,int rootY)
    {
        super(rootX, rootY);
    }

    public int compare(Location l1, Location l2)
    {
        int distance1=l1.distance(this);
        int distance2=l2.distance(this);
        return distance1-distance2;
    }
 }

