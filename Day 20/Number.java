public class Number implements Comparable<Number>
{
    private int order;
    private int number;

    public Number(int number,int order)
    {
        this.order=order;
        this.number=number;
    }

    public int compareTo(Number o) 
    {
        return order-o.order;
    }

    public int getNumber()
    {
        return number;
    }

    public int reorder(int list_length)
    {
        order=Math.floorMod(order+number,list_length-1);
        return order;
    }

    public int getOrder()
    {
        return order;
    }

    public void updateIndex(int index)
    {
        order=index;
    }
    
}
