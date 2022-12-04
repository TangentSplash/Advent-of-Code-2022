public class IDSection 
{
    private int minID;
    private int maxID;

    private String[] IDs;

    public IDSection(String IDString)
    {
        IDs=IDString.split("-");
        minID=Integer.parseInt(IDs[0]);
        maxID=Integer.parseInt(IDs[1]);
        if (maxID<minID)
        {
            System.out.println("AAAAAHHHHHhhhahah!"+minID+","+maxID);
        }
    }

    public boolean fullyContains(IDSection other)
    {
        boolean contained;
        if(Math.min(minID, other.minID)==minID)
        {
            contained=(Math.max(maxID, other.maxID)==maxID);
        }
        else
        {
            contained=(Math.max(maxID, other.maxID)==other.maxID);
        }

        if (contained==false && minID==other.minID)
        {
            contained=(Math.max(maxID, other.maxID)==other.maxID);
        }
        return contained;
    }

    public boolean someOverlap(IDSection other)
    {
        return (maxID>=other.minID && minID<=other.minID) || (other.maxID>=minID&& other.minID<=maxID);
    }
}
