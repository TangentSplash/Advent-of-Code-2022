import java.util.*;

public class Rock 
{
    private boolean[][] shape;
    private final char ROCK_CHAR='#';
    private final int WIDTH;
    private final int LENGTH;

    public Rock(String shapeString)
    {
        List<List<Boolean>> shapeList=new ArrayList<List<Boolean>>();
        char[] shapeChars=shapeString.toCharArray();
        int row=0;
        shapeList.add(new ArrayList<Boolean>());
        for (char c : shapeChars) 
        {
            if(c=='\n')
            {
                shapeList.add(new ArrayList<Boolean>());
                row++;
            }
            else
            {
                shapeList.get(row).add(c==ROCK_CHAR);
            }
        }

        LENGTH=shapeList.size();
        WIDTH=shapeList.get(0).size();
        shape=new boolean[LENGTH][WIDTH];
        for (int i=0;i<LENGTH;i++) 
        {
            for (int j=0;j<WIDTH;j++)
            {
                shape[i][j]=shapeList.get(i).get(j);
            }
        }
        return;
    }

    public int drop(List<List<Boolean>> chamber,int x,int y,List<Boolean> rightJets,int position)
    {
        while (true)
        {
            int newX=move(x,rightJets.get(position));   //Push left / right
            
            if(checkOverlap(newX,y,chamber))
            {
                newX=x;
            }
            
            position=Math.floorMod(++position, rightJets.size());

            int newY=move(y, false);         // Drop
            if(checkOverlap(newX,newY,chamber))
            {
                stamp(newX,y,chamber);
                return position;
            }
            x=newX;
            y=newY;
        }
    }

    private int move(int a,boolean direction)
    {
        if (direction)
        {
            a++;
        }
        else
        {
            a--;
        }
        return a;
    }

    private boolean checkOverlap(int x,int y,List<List<Boolean>> chamber)
    {
        for (int i=0;i<LENGTH;i++)
        {
            boolean initilised=(chamber.get(0).size()>y+i);
            for (int j=0;j<WIDTH;j++)
            {
                if(x+j>=Chamber.WIDTH || x+j<0 || y<0)
                {
                    return true;
                }
                if (initilised)
                {
                    boolean chamberElement=chamber.get(x+j).get(y+i);
                    boolean rockElement=shape[(LENGTH-1)-i][j];
                    if(chamberElement&&rockElement)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void stamp(int x,int y,List<List<Boolean>> chamber)
    {
        for (int j=0;j<WIDTH;j++)
        {
            for(int i=chamber.get(x+j).size();i<y+LENGTH;i++)
            {
                chamber.get(x+j).add(false);
            }
            for (int i=0;i<LENGTH;i++)
            {
                boolean rockElement=shape[(LENGTH-1)-i][j];
                boolean current=chamber.get(x+j).get(y+i);
                chamber.get(x+j).set(y+i, current || rockElement);
            }
        }
        return;
    }
}
