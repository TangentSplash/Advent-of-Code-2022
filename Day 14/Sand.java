public class Sand {
    public static final int DROP_X=500;
    public static final int DROP_Y=0;

    private int x;
    private int y;
    private char[][] caveSlice;

    public Sand(char[][] caveSlice)
    {
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
                    if (!isInBounds(newX, newY))
                    {
                        return true;    //Fell out of bounds
                    }
                    break;
                }
            }
        }
        return false;   //Came to rest in bounds  
    }

    private boolean isInBounds(int newX,int newY)
    {
        return newX>0 && newX<=Cave.furthestPoint && newY>0 && newY<Cave.lowestPoint;
    }

    private boolean tryDrop(int newX,int newY)
    {
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
}
