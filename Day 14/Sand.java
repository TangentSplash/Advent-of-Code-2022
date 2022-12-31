public class Sand {
    public static final int DROP_X=500;
    public static final int DROP_Y=0;

    private int x;
    private int y;
    private char[][] caveSlice;

    private boolean isInBounds;

    public Sand(char[][] caveSlice)
    {
        isInBounds=true;
        this.caveSlice=caveSlice;
        x=DROP_X;
        y=DROP_Y;
    }

    public boolean drop()
    {
        int[] xPositions={0,-1,1};
        int newX;
        int newY;
        boolean dropping=true;
        while(dropping)
        {
            for (int i=0;i<3;i++)
            {
                newX=x+xPositions[i];
                newY=y+1;

                dropping=tryDrop(newX, newY);
                if(dropping)
                {
                    isInBounds(newX, newY);
                    break;
                }
            }
        }
        return (x==DROP_X && y==DROP_Y);    //Came to rest at start? 
    }

    private boolean isInBounds(int newX,int newY)
    {
        isInBounds=newX>0 && newX<=Cave.furthestPoint && newY>0 && newY<Cave.lowestPoint;
        return isInBounds;
    }


    private boolean tryDrop(int newX,int newY)
    {
        if(newY==Cave.floorHeight)
        {
            return false;
        }
        boolean canDropHere=caveSlice[newY][newX]=='\0';
        if(canDropHere)
        {
            x=newX;
            y=newY;
        }
        return canDropHere;  
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public boolean getBounds()
    {
        return isInBounds;
    }
}
