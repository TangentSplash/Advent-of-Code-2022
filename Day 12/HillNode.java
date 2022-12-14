import java.util.*;

public class HillNode implements Comparable<HillNode>
{

    private int heuristic;
    private int x;
    private int y;
    private char height;
    private int score; 
    private int distToHere;
    private HillNode previosNode;

    private boolean atEnd;

    private List<HillNode> nextNodes;

    private static final int CLIMBABLE_HEIGHT=1;

    public HillNode(int x,int y, char height,int distToHere,int heightDiff, HillNode previosNode)
    {
        this.x=x;
        this.y=y;
        this.height=height;
        this.distToHere=distToHere;
        this.previosNode=previosNode;
        if (PathFinding.PART_ONE)
        {
            int distToEndManhatten=Math.abs(x-PathFinding.endX)+Math.abs(y-PathFinding.endY);
            heuristic=distToEndManhatten-heightDiff;
        }
        else
        {
            heuristic=heightDiff;
        }
        score=distToHere+heuristic;
        nextNodes=new ArrayList<HillNode>();
        atEnd=(height=='E' && PathFinding.PART_ONE)||(height=='a'&& !PathFinding.PART_ONE);
        if (height=='S')
        {
            this.height='a';
        }
        if (height=='E')
        {
            this.height='z';
        }
    }

    public void explore()
    {
        newNode(x+1, y);
        newNode(x, y+1);
        newNode(x, y-1);
        newNode(x-1, y);
    }

    private void newNode(int newX, int newY)
    {
        int previousX=0;
        int previousY=0;
        if (previosNode!=null)
        {
            previousX=previosNode.x;
            previousY=previosNode.y;
        }

        if(newX>=0 && newX<PathFinding.length && newY>=0 && newY<PathFinding.width && (previosNode==null || !(previousX==newX && previousY==newY)))
        {
            char nextHeight=PathFinding.topography[newY][newX];
            char nextHeightNormalised=nextHeight;
            if(nextHeightNormalised==PathFinding.END) 
            {
                nextHeightNormalised='z';
            }
            if (nextHeightNormalised=='S')
            {
                nextHeight='a';
            }

            if((PathFinding.PART_ONE && nextHeightNormalised<=height+CLIMBABLE_HEIGHT) || (!PathFinding.PART_ONE && nextHeightNormalised>=height-CLIMBABLE_HEIGHT))  
            {
                nextNodes.add(new HillNode(newX, newY, nextHeight, distToHere+1,nextHeightNormalised-height,this));
            }
        }
    }

    public int compareTo(HillNode other)
    {
        return this.score-other.score;
    }

    public List<Integer> getPos()
    {
        return new ArrayList<Integer>(Arrays.asList(x,y));
    }

    public void setBestDistance(HillNode newPath)
    {
        int newDist=newPath.getDistToHere();
        if (newDist<distToHere)
        {
            distToHere=newDist;
            previosNode=newPath.getPreviousNode();
        }
    }

    public int getDistToHere()
    {
        return distToHere;
    }

    public HillNode getPreviousNode()
    {
        return previosNode;
    }

    public boolean isEnd()
    {
        return atEnd;
    }

    public boolean isStart()
    {
        return previosNode==null;
    }

    public void updateNode(HillNode updated,int i)
    {
        nextNodes.set(i, updated);
    }

    public List<HillNode> getNextNodes()
    {
        return nextNodes;
    }
}