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

        borderLocations();
        /*Location distressSignal=distressSignal();
        int tuningFrequency=(distressSignal.getX()*BOUNDING_BOX)+distressSignal.getY();
        System.out.println("The tuning frequency is "+tuningFrequency);*/
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
        
        List<Location> sensorList=new ArrayList<Location>(sensorLocations);
        Set<Location> bordersFound=new HashSet<Location>();
        sensorList.sort(new RadiusComparator());
        Set<Location> borderAtLeast2=new HashSet<Location>();
        for (Location sensor : sensorList) 
        {
            List<Location> thisSensorBorder=sensor.borderLocations();
            List<Location> otherBorders=new ArrayList<>(bordersFound);
            otherBorders.retainAll(thisSensorBorder);
            borderAtLeast2.addAll(otherBorders);
            bordersFound.addAll(thisSensorBorder);
            System.out.println("Found Borders of "+sensorList.indexOf(sensor));
        }

        boolean here=true;
        for (Location thisBorder : borderAtLeast2) 
        {
            for (Location sensor : sensorList) 
            {
                if(sensor.distance(thisBorder)<=sensor.getMaxDistance())
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
        return null;
    }

    private Location distressSignal()
    {
        List<Location> sensorList=new ArrayList<Location>(sensorLocations);
        boolean here;
        for(int x=0;x<=BOUNDING_BOX;x++)
        {
            for (int y=0;y<=BOUNDING_BOX;y++)
            {
                here=true;
                DistanceComparator thisPoint=new DistanceComparator(x, y);
                if(sensorList.get(1).distance(thisPoint)<sensorList.get(0).distance(thisPoint))
                {
                    sensorList.sort(thisPoint);
                }
                for (Location sensor : sensorList) {
                    if(sensor.distance(thisPoint)<=sensor.getMaxDistance())
                    {
                        here=false;
                        break;
                    }
                }
                if(here)
                {
                    return thisPoint;
                }
            }
        } 
        return null;  
    }
}
