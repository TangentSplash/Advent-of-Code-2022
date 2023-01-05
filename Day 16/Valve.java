import java.util.*;

public class Valve
{
    private String name;
    private int flowRate;
    private Map<String,Valve> connectedValves;
    private boolean open;
    private int timeRemaining;

    public Valve(String name, int flowRate,Map<String,Valve> connectedValves)    
    {
        this.name=name;
        this.flowRate=flowRate;
        this.connectedValves=connectedValves;
        open=false;
        timeRemaining=0;
    }

    public Valve(String name)
    {
        this.name=name;
        open=false;
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

    public Map<Integer,Valve> connections(int timeLeft,List<Valve> alreadyFound/*,List<Valve> allValves*/)
    {
        Map<Integer,Valve> allConnections=new TreeMap<>();
        List<Valve> foundHere=new ArrayList<Valve>(connectedValves.values());
        if (alreadyFound!=null)
        {
            foundHere.addAll(alreadyFound);
        }
        for (Valve connected : connectedValves.values()) 
        {
            if(alreadyFound==null || !alreadyFound.contains(connected))   
            {
                allConnections.putAll(connected.connections(timeLeft-1,foundHere/*,allValves*/));
            }
        }
        if(!open)
        {
            timeRemaining=timeLeft-1;
            
            int pressureReleaseAvailable=timeRemaining*flowRate;    //TODO Add time to get back to a centroid
            allConnections.put(pressureReleaseAvailable, this);
        }
        return allConnections;
    }

    public void setOpen()
    {
        open=true;
    }

    public int getTimeRemaining()
    {
        return timeRemaining;
    }

    private int distToCentroid(List<Valve> allValves)
    {
        List<Valve> otherValves=new ArrayList<>(allValves);
            allValves.remove(this);
            for (Valve valve : otherValves) 
            {
                if(valve.flowRate!=0 && !valve.open)
                {

                }
            }
            return 0;
    }

    public void distToEach()
    {
        Map<String,Valve> openMap;
        List<Valve> openList;
        Map<String,Valve> closedMap;

        int i=0;
        do{
            for (Valve connectedValve : connectedValves.values()) 
            {
                if(openMap.containsKey(connectedValve.name))
                {
                    openMap.get(connectedValve.name).setBestDistance(connectedValve);
                    nextNode.updateNode(openMap.get(connectedValve.name),i);
                }
                else if (!closedMap.containsKey(connectedValve.name))
                {
                    openMap.put(connectedValve.name, connectedValve);
                }
                i++;
            }
            openList=new ArrayList<Valve>(openMap.values());
            
            Collections.sort(openList);

            closedMap.put(nextNode.getPos(), nextNode);

            nextNode=openList.get(0);
            openMap.remove(nextNode.getPos());
        } while(!nextNode.isEnd());
    }

    public Map<String,ValveAndDist> findNodes(String from,int distToHere)
    {
        Map<String, ValveAndDist> shortestDistanceValves=new HashMap<String, ValveAndDist>();
        for (Valve valve : connectedValves.values()) 
        {
            shortestDistanceValves.putAll(valve.findNodes(from,distToHere+1));           
        }
        if(!name.equals(from))
        {
            shortestDistanceValves.put(name, new ValveAndDist(distToHere,this));
        }
        return null;
    }
}
