import java.util.*;

public class Branch {

    private static final int CLIMBABLE_HEIGHT=1;

    private char myHeight;
    private char[][] map;

    private int x;
    private int y;

    private int score;
    private int distance;
    private int distanceX;
    private int distanceY;

    private List<Integer> locations; 

    public Branch(int x,int y,char[][] map)
    {
        this.x=x;
        this.y=y;
        this.map=map.clone();
        this.myHeight=map[y][x];
        if (myHeight==Hill.START)
        {
            myHeight='a';
        }
    }

    public int explore()
    {
        int nextX=x+1;
        char nextHeight=map[y][nextX];
        while(nextHeight<=(myHeight+CLIMBABLE_HEIGHT))
        {
            x=nextX;
            myHeight=nextHeight;
            nextX++;
            nextHeight=map[y][nextX];
        }
        calculateScore();
        return score;
    }

    private void calculateScore()
    {
        distanceX=Math.abs(x-Hill.endX);
        distanceY=Math.abs(y-Hill.endY);
        distance=(distanceX*10)+distanceY;
        score=(((int)myHeight)*100)+distance;
    } 
    
}
