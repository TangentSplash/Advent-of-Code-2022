public class Tree {

    private int height;

    /*private boolean visableTop;
    private boolean visableBottom;
    private boolean visableLeft;
    private boolean visableRight;*/
    private boolean visable;

    private int[] visableTrees;

    public Tree(int height)
    {
        this.height=height;
        visable=false;
    }

    public int getHeight()
    {
        return height;
    }
    
    public boolean isVisable()
    {
        return visable;
    }

    public int compapareHeight(int maxHeight)
    {
        if (height>maxHeight)
        {
            visable=true;
            return height;
        }
        else
        {
            return maxHeight;
        }
    }

    public void setTreesVisable(int[] treesVisable)
    {
        visableTrees=treesVisable;
    }

    public int getScenicScore()
    {
        int scenicScore=1;;
        for (int trees : visableTrees) 
        {
            scenicScore*=trees;
        }
        return scenicScore;
    }
}
