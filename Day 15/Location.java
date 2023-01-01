import java.util.ArrayList;
import java.util.List;

public class Location implements Comparable<Location>
{
    private int x;
    private int y;
    private Location closestBeacon;
    private int maxDistance;

    public Location(int x,int y)
    {
        this.x=x;
        this.y=y;
        closestBeacon=null;
    }

    public Location(int x,int y, Location closestBeacon)
    {
        this.x=x;
        this.y=y;
        this.closestBeacon=closestBeacon;
        maxDistance=distance(closestBeacon);
    }

    public boolean equals(Location other)
    {
        return x==other.x && y==other.y;
    }

    public int hashCode()
    {
        return (x*100000) + y;
    }

    public int compareTo(Location other) 
    {
        return hashCode()-other.hashCode();
    }

    public int distance(Location other)
    {
        return Math.abs(x-other.x)+Math.abs(y-other.y);
    }

    public Location getClosestBeacon()
    {
        return closestBeacon;
    }


    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getMaxDistance()
    {
        return maxDistance;
    }

    public List<Location> borderLocations()
    {
        List<Location> bordering=new ArrayList<Location>();
        int radius=maxDistance+1;
        int borderYmin=Math.max(y-radius, 0);
        int borderYmax=Math.min(y+radius, Beacons.BOUNDING_BOX);
        for (int borderY=borderYmin;borderY<=borderYmax;borderY++)
        {
            int deltaY=borderY-y;
            int xRange=radius-Math.abs(deltaY);
            int borderX1=x-xRange;
            int borderX2=x+xRange;
            if (borderX1>=0)
            {
                Location borderPoint=new Location(borderX1, borderY);
                bordering.add(borderPoint);
            }
            if(borderX2<=Beacons.BOUNDING_BOX && borderX2!=borderX1)
            {
                Location borderPoint=new Location(borderX2, borderY);
                bordering.add(borderPoint);
            }
        } 
        return bordering;
    }
}
