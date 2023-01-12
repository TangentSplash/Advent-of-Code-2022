import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.io.File;

/* Advent of Code 2022
* Day 16 Part 1
*/

public class Volcano 
{
    private final int TIME_LIMIT=30;
    private Map<String,Valve> valves;
    private int timeRemaining;
    private final String START_NODE="AA";

    public Volcano () throws Exception
    {
        timeRemaining=TIME_LIMIT;
        File inputFile = new File("Day 16/inputtest.txt");
        Scanner input = new Scanner(inputFile);
        valves=new HashMap<String,Valve>();

        interpertInput(input);
        input.close();

        createMarkdownFiles();
        Valve startValve=valves.get("AA");
        int best=startValve.search(TIME_LIMIT, new ArrayList<Valve>(valves.values()), 0, false,null);
        System.out.println("The most pressure that can be released is "+best);
        return;
    }

    private void interpertInput(Scanner input)
    {
        String line;

        while(input.hasNextLine())
        {
            line=input.nextLine();
            String[] elements;

            elements=line.split("Valve ");
            elements=elements[1].split(" has flow rate=");
            String name=elements[0];

            elements=elements[1].split("; ");
            int flowRate=Integer.parseInt(elements[0]);
            
            if (elements[1].contains("tunnels lead to valves "))
            {
                elements=elements[1].split("tunnels lead to valves ");
            }
            else if (elements[1].contains("tunnel leads to valve "))
            {
                elements=elements[1].split("tunnel leads to valve ");
            }
            else
            {
                System.out.println("Error! Input not recognised: "+elements[1]);
            }
            elements=elements[1].split(", ");
            
            Map<String,Valve>connectedValves=new HashMap<>();
            for (String valveName : elements) 
            {
                Valve newValve;
                if (valves.containsKey(valveName))
                {
                    newValve=valves.get(valveName);
                    
                }
                else
                {
                    newValve=new Valve(valveName);
                }
                connectedValves.put(valveName,newValve);
                valves.put(valveName, newValve);
            }

            if (valves.containsKey(name))
            {
                valves.get(name).setParamaters(flowRate,connectedValves);
            }
            else
            {
                valves.put(name, new Valve(name,flowRate,connectedValves));
            }
        }
    }

    private FileWriter createFile(String name)
    {
        String path="Day 16/Valves/"+name+".md";
        new File(path);
        try {
        return new FileWriter(path);
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    private void printFile(FileWriter file,Valve valve)
    {
        try 
        {
            String text="# "+valve.getName()+" "+valve.getFlowRate()+"\n";
            file.append(text);
            Set<String> connected=valve.getConnectedNames();
            for (String valveName : connected)
            {
                text="["+valveName+"]("+valveName+".md)\n";
                file.append(text);
            }
            file.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

    private void createMarkdownFiles()
    {
        for (Valve valve : valves.values()) 
        {
            FileWriter file=createFile(valve.getName());
            printFile(file, valve);
        }
    }

    private void findBestPath()
    {
        //Time to get from here to node  & flow rate available
        Valve startValve=valves.get("AA");
        Valve currentValve=startValve;
        int totalPressure=0;
        while (timeRemaining>0)
        {
            Map<Integer,Valve> connections=currentValve.connections(timeRemaining, null/*,(List<Valve>) valves.values()*/);
            List<Integer> pressures=new ArrayList<Integer>(connections.keySet());
            int bestPressure=pressures.get(pressures.size()-1);
            currentValve=connections.get(bestPressure);
            currentValve.setOpen();
            timeRemaining=currentValve.getTimeRemaining();
            totalPressure+=bestPressure;
        }
        System.out.println(totalPressure);
    }
}
