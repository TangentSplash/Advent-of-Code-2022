import java.util.*;

public class Directory extends Item{
    private List<Item> subItems;
    private Directory parentDirectory;
    
    public Directory(String name, Directory parentDirectory)
    {
        super(name,0);
        this.parentDirectory=parentDirectory;
        subItems=new ArrayList<Item>();
    }

    public boolean add(Item item)
    {
        return subItems.add(item);
    }

    public Directory getParentDirectory()
    {
        return parentDirectory;
    }

    public Directory findChildDirectory(String name)
    {
        for (Item item : subItems) {
            if (item instanceof Directory)  //TODO Does this work properly?
            {
                if (item.getName().equals(name))
                {
                    return (Directory)item;
                }
            }
        }
        //Child not found 
        return null;
    }

    public List<Integer> getSizeList()
    {
        List<Integer> sizes=new ArrayList<>();
        super.size=0;
        int newSize;
        for (Item item : subItems) {
            if (item instanceof Directory)
            {
                List<Integer> returnedList=item.getSizeList();
                newSize=returnedList.get(0);
                sizes.addAll(returnedList);
            }
            else
            {
                newSize=item.getSize();
            }
            super.size+=newSize;
        }
        sizes.add(0, super.size);
        return sizes;
    }
    
}
