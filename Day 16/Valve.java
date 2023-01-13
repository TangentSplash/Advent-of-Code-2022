import java.util.*;

public class Valve
{
    private String name;
    private int flowRate;
    private Map<String,Valve> connectedValves;
    private Map<Valve,Integer> otherValves;

    public Valve(String name, int flowRate,Map<String,Valve> connectedValves)    
    {
        this.name=name;
        this.flowRate=flowRate;
        this.connectedValves=connectedValves;
    }

    public Valve(String name)
    {
        this.name=name;
    }

    public void setParamaters(int flowRate,Map<String,Valve> connectedValves)
    {
        this.flowRate=flowRate;
        this.connectedValves=connectedValves;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Valve))
            return false;
        Valve other = (Valve)o;
        return other.name.equals(this.name);
    }

    public String getName()
    {
        return name;
    }

    public Set<String> getConnectedNames()
    {
        return connectedValves.keySet();
    }

    public int getFlowRate()
    {
        return flowRate;
    }

    public void findPaths()
    {
        Map<Valve,Integer> allValves=new HashMap<Valve,Integer>();
        List<Valve> openList=new ArrayList<Valve>(connectedValves.values());
        List<Valve> newFinds=new ArrayList<Valve>();
        for (Valve valve : openList) 
        {
            allValves.put(valve,1);    
        }
        List<Valve> closedList=new ArrayList<Valve>();
        closedList.add(this);
        while (!openList.isEmpty())
        {
            for (Valve valve : openList) 
            {
                List<Valve> connected=new ArrayList<Valve>(valve.connectedValves.values());
                connected.removeAll(closedList);
                for (Valve newValve : connected) 
                {
                    Integer distToHere=allValves.get(valve);
                    Integer previousDist=allValves.get(newValve);
                    if (previousDist==null)
                    {
                        allValves.put(newValve,distToHere+1);
                    }
                    else
                    {
                        int newDist=Math.max(distToHere, previousDist);
                        allValves.replace(newValve, newDist);
                    }
                }
                newFinds.addAll(connected);
                closedList.add(valve);
            }
            openList.clear();
            openList.addAll(newFinds);
            newFinds.clear();
        }

        this.otherValves=new HashMap<Valve,Integer>(allValves);
        for (Valve valve : allValves.keySet())
        {
            if(valve.getFlowRate()==0)
            {
                otherValves.remove(valve);
            }
        }
        return;
    }

    public int bestPath(int timeRemaining,Set<Valve> opened)
    {
        List<Valve> paths=new ArrayList<Valve>(otherValves.keySet());
        List<Integer> pressures=new ArrayList<Integer>();
        
        paths.removeAll(opened);
        int totalPressure=0;
        if (timeRemaining>0)
        {
            if(flowRate>0)
            {
                opened.add(this);
                timeRemaining--;
                totalPressure=flowRate*timeRemaining;
            }

            for (Valve valve : paths) 
            {
                int timeMoving=otherValves.get(valve);
                pressures.add(valve.bestPath(timeRemaining-timeMoving,new HashSet<Valve>(opened)));
            }

            if(!pressures.isEmpty())
            {
                totalPressure+=Collections.max(pressures);
            }
        }
        return totalPressure;
    }

}
