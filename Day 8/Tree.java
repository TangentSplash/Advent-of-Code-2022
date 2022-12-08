public class Tree {

    private int height;

    /*private boolean visableTop;
    private boolean visableBottom;
    private boolean visableLeft;
    private boolean visableRight;*/
    private boolean visable;

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
}
