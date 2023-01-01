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
}
