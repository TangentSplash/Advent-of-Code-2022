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
    private final String START_NODE="AA";

    public Volcano () throws Exception
    {
        File inputFile = new File("Day 16/input.txt");
        Scanner input = new Scanner(inputFile);
        valves=new HashMap<String,Valve>();

        interpertInput(input);
        input.close();

        createMarkdownFiles();
        Valve startValve=valves.get(START_NODE);
        findPaths(startValve);
        int bestPressureRelease=startValve.bestPath(TIME_LIMIT,new HashSet<Valve>());
        System.out.println("The best pressure release is "+ bestPressureRelease);
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

    private void findPaths(Valve startValve)
    {
        for (Valve valve : valves.values()) 
        {
            if (valve.getFlowRate()>0 || valve.equals(startValve))
            {
                valve.findPaths();
            }
        }
    }
}
