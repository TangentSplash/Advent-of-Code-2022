import java.util.*;

public class HillNode
{

    private int heuristic;
    private int x;
    private int y;
    private char height;
    private int score; 
    private int distToHere;

    private List<HillNode> nextNodes;

    private static final int CLIMBABLE_HEIGHT=1;

    public HillNode(int x,int y, char height,int distToHere,int heightDiff)
    {
        this.x=x;
        this.y=y;
        this.height=height;
        this.distToHere=distToHere;
        int distToEndManhatten=Math.abs(x-A_Star.endX)+Math.abs(y-A_Star.endY);
        heuristic=distToEndManhatten+heightDiff;
        score=distToHere+heuristic;
    }

    public int compareTo(HillNode other)    //Int?
    {
        if(score>other.score)
        {
            return 1;
        }
        return 0;
    }

    public HillNode findNextBest()
    {
        newNode(x+1, y);
        newNode(x, y+1);
        newNode(x, y-1);
        newNode(x-1, y);

        nextNodes.sort();
    }


    private void newNode(int newX, int newY)
    {
        if(newX>=0 && newX<A_Star.length && newY>=0 && newY<A_Star.width)
        {
            char nextHeight=A_Star.topography[newY][newX];
            if(nextHeight==A_Star.END) 
            {
                nextHeight='z';
            }

            if(nextHeight<=height+CLIMBABLE_HEIGHT)  
            {
                nextNodes.add(new HillNode(newX, newY, nextHeight, distToHere+1,nextHeight-height));
            }
        }
    }
}