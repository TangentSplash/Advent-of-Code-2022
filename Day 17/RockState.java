public class RockState 
{
    private int jetPosition;
    private int rockNum;
    private int height;
    private long chamberCode;
    private long rocksDropped;

    public RockState(int jetPosition,int rockNum,int height,long chamberCode,long rocksDropped)
    {
        this.jetPosition=jetPosition;
        this.rockNum=rockNum;
        this.height=height;
        this.chamberCode=chamberCode;
        this.rocksDropped=rocksDropped;
    }

    public boolean equals(RockState other)
    {
        return jetPosition==other.jetPosition && rockNum==other.rockNum && chamberCode==other.chamberCode;
    }

    public int getHeight()
    {
        return height;
    }

    public long getRocksDropped()
    {
        return rocksDropped;
    }
    
}
