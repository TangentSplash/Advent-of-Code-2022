import java.util.*;

public class LimitedQueue<E> extends LinkedList<E>
{
    private int limit;

    public LimitedQueue(int limit)
    {
        this.limit=limit;
    }

    public boolean add(E element)
    {
        if (super.size()==limit)
        {
            super.remove();
        }
        return super.add(element);
    }


    
}
