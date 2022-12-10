public class RopeSegment {
    private int x;
    private int y;

    public RopeSegment()
    {
        x=0;
        y=0;
    }

    public void move(String instruction)
    {
        switch(instruction.charAt(0))
        {
            case 'U':
                y++;
                break;
            case 'D':
                y--;
                break;
            case 'R':
                x++;
                break;
            case 'L':
                x--;
                break;
            default:
                System.out.println("Instruction not understood: "+instruction);
                break;
        }
    }

    public void move(RopeSegment head)
    {
        int headX=head.getX();
        int headY=head.getY();

        int yDiff=headY-y;
        int xDiff=headX-x;

        int yDiffAbs=Math.abs(yDiff);
        int xDiffAbs=Math.abs(xDiff);

        //double euclideanDistSquared= (Math.pow(yDiffAbs, 2)+Math.pow(xDiffAbs, 2));
        
        //int dist=yDiffAbs+xDiffAbs;

        if (xDiffAbs>1 || yDiffAbs>1)
        {
            if (yDiffAbs!=0)
            {
                int yDirection=yDiff/yDiffAbs;
                y+=yDirection;
            }
            if (xDiffAbs!=0)
            {
                int xDirection=xDiff/xDiffAbs;
                x+=xDirection;
            }
        }

        /*if(yDiffAbs>1 || dist>=3)
        {
            int yDirection=yDiff/yDiffAbs;
            y+=yDirection;
        }
        if(xDiffAbs>1 || dist>=3)
        {
            int xDirection=xDiff/xDiffAbs;
            x+=xDirection;
        }*/
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public String getLocationID()
    {
        return "x:"+x+" y:"+y;
    }
    
}
