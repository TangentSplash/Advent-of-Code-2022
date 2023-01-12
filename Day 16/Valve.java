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

    public void setOpen()
    {
        open=true;
    }

    public boolean isOpen()
    {
        return open;
    }

    public int getTimeRemaining()
    {
        return timeRemaining;
    }

    public Map<Integer,Valve> connections(int timeLeft,List<Valve> alreadyFound)
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
                allConnections.putAll(connected.connections(timeLeft-1,foundHere));
            }
        }
        if(!open)
        {
            timeRemaining=timeLeft-1;
            
            int pressureReleaseAvailable=timeRemaining*flowRate;
            allConnections.put(pressureReleaseAvailable, this);
        }
        return allConnections;
    }

    public int search(int timeToLive,List<Valve> closedValves,int totalPressure,boolean dontOpen,Valve from)
    {
        boolean notWorthContinuing=flowRate==0 && connectedValves.values().size()==1;
        if (!notWorthContinuing && !closedValves.isEmpty() && timeToLive>0)
        {
            if (flowRate>0 && !dontOpen)
            {
                //Don't open this valve
                search(timeToLive, new ArrayList<Valve>(closedValves), totalPressure, true,from);
                
                // Open this valve
                timeToLive--;
                totalPressure=timeToLive*flowRate;
                closedValves.remove(this);
            }
            if (timeToLive>1)
            {
                List<Integer> pressures=new ArrayList<Integer>();
                for (Valve valve : connectedValves.values()) 
                {
                    if (!valve.equals(from) && (valve.flowRate!=0  || !valve.connectedValves.isEmpty()))
                    {
                        pressures.add(valve.search(timeToLive-1,new ArrayList<Valve>(closedValves),totalPressure,false,this));
                    }
                }
                if(!pressures.isEmpty())
                {
                    totalPressure=Collections.max(pressures);
                }
            }
        }
        return totalPressure;
    }
}
