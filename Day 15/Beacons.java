import java.util.*;
import java.io.File;

/* Advent of Code 2022
* Day 15 Part 2
*/

public class Beacons 
{
    private final int Y_LINE=2000000;
    public static final int BOUNDING_BOX=4000000;

    private Set<Location> sensorLocations;
    private Set<Location> beaconLocations;
    private Set<Location> noBeaconsOnLine;

 
    public Beacons() throws Exception
    {
        File inputFile = new File("Day 15/input.txt");
        Scanner input = new Scanner(inputFile);

        sensorLocations=new TreeSet<Location>();
        beaconLocations=new TreeSet<Location>();
        noBeaconsOnLine=new TreeSet<Location>();

        interpretInput(input);
        input.close();

        findImpossibleLocations();
        int beaconCannot=noBeaconsOnLine.size();
        System.out.println("There are "+beaconCannot+" locations at y="+Y_LINE+", where beacons cannot be");

        Location distressSignal=borderLocations();
        int x=distressSignal.getX();
        int y=distressSignal.getY();
        System.out.println("");
        System.out.println("x="+x+", y="+y);
        int xAns=x*4;
        System.out.println("Answer is "+xAns+y);
        System.out.println("Go to https://www.wolframalpha.com/input?i=%28x*4000000%29%2By to verify the result");
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
            int maxDistance=sensor.getMaxDistance();
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
                                noBeaconsOnLine.add(check);
                            }
                        }
                    }
                }
            }
        }
    }

    private Location borderLocations()
    {
        boolean here;
        List<Location> sensorList=new ArrayList<Location>(sensorLocations);
        sensorList.sort(new RadiusComparator());
        for (Location sensor : sensorList) 
        {
            List<Location> otherSensors=new ArrayList<>(sensorList);
            otherSensors.remove(sensor);
            List<Location> thisSensorBorder=sensor.borderLocations();
            for (Location thisBorder : thisSensorBorder) 
            {
                here=true;
                for (Location otherSensor : otherSensors) 
                {
                    if(otherSensor.distance(thisBorder)<=otherSensor.getMaxDistance())
                    {
                        here=false;
                        break;
                    }
                }
                if(here)
                {
                    return thisBorder;
                }
            }
            System.out.println("Finished with "+sensorList.indexOf(sensor));
        }
        return null;
    }
}