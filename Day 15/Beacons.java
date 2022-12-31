import java.util.*;
import java.io.File;

/* Advent of Code 2022
* Day 14 Part 2
*/
public class Beacons 
{
    private final int Y_LINE=2000000;

    private Set<Location> sensorLocations;
    private Set<Location> beaconLocations;
    private Set<Location> noBeaconLocations;
    private Set<Location> noBeaconsOnLine;
 
    public Beacons() throws Exception
    {
        File inputFile = new File("Day 15/input.txt");
        Scanner input = new Scanner(inputFile);

        sensorLocations=new TreeSet<Location>();
        beaconLocations=new TreeSet<Location>();
        noBeaconLocations=new TreeSet<Location>();
        noBeaconsOnLine=new TreeSet<Location>();

        interpretInput(input);
        input.close();

        findImpossibleLocations();
        
        int beaconCannot=noBeaconsOnLine.size();
        System.out.println("There are "+beaconCannot+" locations at y="+Y_LINE+", where beacons cannot be");
    }

    private void interpretInput(Scanner input)
    {
        String line;

        while(input.hasNextLine())
        {
            line=input.nextLine();
            String[] elements;
            elements=line.split("Sensor at x=");
            elements=elements[1].split(", y=");
            int xSensor=Integer.parseInt(elements[0]);
            int yBeacon=Integer.parseInt(elements[2]);
            elements=elements[1].split(": closest beacon is at x=");
            int ySensor=Integer.parseInt(elements[0]);
            int xBeacon=Integer.parseInt(elements[1]);

            Location beacon=new Location(xBeacon,yBeacon);
            sensorLocations.add(new Location(xSensor,ySensor,beacon));
            beaconLocations.add(beacon);
        }
    }

    private void findImpossibleLocations()
    {
        int x;
        int y;
        for (Location sensor : sensorLocations) 
        {
            int maxDistance=sensor.distance(sensor.getClosestBeacon());
            int sensorY=sensor.getY();
            if(sensorY-maxDistance<Y_LINE && sensorY+maxDistance>Y_LINE)
            {
                for (int deltaY=-maxDistance;deltaY<=maxDistance;deltaY++)
                {
                    y=sensorY+deltaY;
                    if (y==Y_LINE)
                    {
                        int xRange=maxDistance-Math.abs(deltaY);
                        for (int deltaX=-xRange;deltaX<=xRange;deltaX++)
                        {
                            x=sensor.getX()+deltaX;
                            Location check=new Location(x, y);
                            if (!beaconLocations.contains(check))
                            {
                                //noBeaconLocations.add(check);
                                noBeaconsOnLine.add(check);
                            }
                        }
                    }
                }
            }
        }
    }
    
}
