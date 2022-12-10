public class RopeSegment {
    private int x;
    private int y;

    public RopeSegment()
    {
        x=0;
        y=0;
    }

    public void move(String direction)
    {
        switch(direction.charAt(0))
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
                System.out.println("Instruction not understood: "+direction);
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
