import java.util.*;

public class Item {
    protected int size;
    private String name;

    public Item(String name,int size)
    {
            this.name=name;
            this.size=size;
    }

    public int getSize()
    {
        return size;
    }

    public String getName()
    {
        return name;
    }

    public List<Integer> getSizeList()
    {
        return null;
    }
    
}
