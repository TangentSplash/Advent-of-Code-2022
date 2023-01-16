import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import java.io.File;

/* Advent of Code 2022
* Day 16 Part 2
*/

public class Volcano 
{
    private final int TIME_LIMIT=30;
    private Map<String,Valve> valves;
    private final String START_NODE="AA";

    private List<Valve> allValves;

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
        int timeRemaining=TIME_LIMIT;

        ValveTurner me=new ValveTurner(startValve,timeRemaining,new HashSet<Valve>());
        allValves=me.getValves();
        
        int bestSoloPressureRelease=me.bestPath();
        System.out.println("The best pressure release alone is "+ bestSoloPressureRelease);
        
        int bestPressureReleaseHelped=bestPathWithHelper(startValve);
        System.out.println("The best pressure release with help is "+ bestPressureReleaseHelped);
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

    private int bestPathWithHelper(Valve startValve)
    {
        int bestPressure=0;
        int timeRemaining=TIME_LIMIT-4;// Train the elephant
        int size=allValves.size();
        boolean[] flags=new boolean[size];
        for(int i=0; i<Math.pow(2, size-1);i++)
        {
            Set<Valve> myValves=new HashSet<Valve>();
            Set<Valve> helperValves=new HashSet<Valve>();
            for (int j=0;j<size;j++)
            {
                if(flags[j])
                {
                    myValves.add(allValves.get(j));
                }
                else
                {
                    helperValves.add(allValves.get(j));
                }
            }
            System.out.println("Searching node split number "+i);
            flags=binaryCounter(i, flags);
            ValveTurner me=new ValveTurner(startValve, timeRemaining, helperValves);
            int myScore=me.bestPath();
            ValveTurner helper=new ValveTurner(startValve, timeRemaining, myValves);
            int helperScore=helper.bestPath();
            int totalPressure=myScore+helperScore;
            bestPressure=Math.max(totalPressure, bestPressure);
        }
        return bestPressure;
    }

    private boolean[] binaryCounter(int i,boolean[] previousFlags)
    {
        int size=previousFlags.length;
        boolean[] newFlags = new boolean[size];
            newFlags=Arrays.copyOf(previousFlags,size);
            
            for(int j=0;j<size;j++)
            {
                if(i>0 && Math.floorMod(i,(int)Math.pow(2, j))==0)
                {
                    newFlags[j]=!previousFlags[j];
                }
                //System.out.print(newFlags[j]+" ");
            }
            //System.out.println();
            return newFlags;
    }
}
