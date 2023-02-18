public class Navigator 
{
    private int x,y;
    private Orientations orientation;
    private char[][] map;

    private final char WALL='#';
    public static final char OPEN='.';
    
    public Navigator(int startX,int startY,Orientations startOrientation,char[][] map)
    {
        this.x=startX;
        this.y=startY;
        this.orientation=startOrientation;
        this.map=map;
    }


    public void move(int steps)
    {
        boolean hitWall=false;
        for (int i=0;i<steps;i++)
        {
            if(hitWall)
            {
                break;
            }
            hitWall=!moveOneStep(false,0,0);
            map[y][x]=orientation.getLabel();
        }
    }

    private boolean moveOneStep(boolean setCurrentPos, int currX,int currY)
    {
        int newX=x;
        int newY=y;

        if(setCurrentPos)
        {
            newX=currX;
            newY=currY;
        }
        switch (orientation) 
        {
            case RIGHT:
                newX=Math.floorMod(++newX, Grove.MAP_WIDTH);
                break;
            case DOWN:
                newY=Math.floorMod(++newY, Grove.MAP_LENGTH);
                break;
            case LEFT:
                newX=Math.floorMod(--newX, Grove.MAP_WIDTH);
                break;
            case UP:
                newY=Math.floorMod(--newY, Grove.MAP_LENGTH);
                break;
            default:
                System.out.println("Unknown orientation "+orientation);
                break;
        }

        if(!isValidPos(newX, newY, map))
        {
            return moveOneStep(true,newX,newY);  //Recursivly try to find next valid position
        }

        if(map[newY][newX]!=WALL)
        {
            x=newX;
            y=newY;
            return true;
        }
        return false;
    }

    public void rotate(String direction)
    {
        switch (direction) 
        {
            case "L":
                orientation=orientation.rotate(-1);
                break;
            case "R":
                orientation=orientation.rotate(1);
                break;
        
            default:
            System.out.println("Unknown direction "+direction);
                break;
        }
    }

    private boolean isValidPos(int newX, int newY,char[][] map)
    {
        return (newY>=0 && newY<Grove.MAP_LENGTH && newX>=0 && newX<Grove.MAP_WIDTH && map[newY][newX]!=' ');
    }

    public int[] getPos()
    {
        return new int[]{x,y};
    }

    public Orientations getOrientation()
    {
        return orientation;
    }
    
}
