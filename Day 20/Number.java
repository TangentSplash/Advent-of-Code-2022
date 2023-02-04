public class Number implements Comparable<Number>
{
    private int order;
    private int number;
    private int moduloNumber;

    public Number(int number,int order)
    {
        this.order=order;
        this.number=number;
        this.moduloNumber=number;
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
        order=Math.floorMod(order+moduloNumber,list_length-1);
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

    public void updateNumber(int newNumber)
    {
        moduloNumber=newNumber;
    }
    
}
