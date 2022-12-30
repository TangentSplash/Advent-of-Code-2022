import java.util.*;

public class Signal implements Comparable<Signal>
{
    private Object signal;
    public Signal (List<Object> signal)
    {
        this.signal=signal;
    }

    public int compareTo(Signal other)
    {
        int result=compare(signal, other.getObject());
        //Poorly convert result to comparable result
        if (result==0)
        {
            result=1;
        }
        else if (result==1)
        {
            result=-1;
        }
        else if (result==-1)
        {
            result=0;
            //System.out.println("Same");
        }
        return result;
    }

    public int compare(Object signal1,Object signal2)    //Return 1 if in right order, 0 if not and -1 if this test not conclusive
    {
        boolean sig1IsInt=signal1 instanceof Integer;
        boolean sig2IsInt=signal2 instanceof Integer;

        if (sig1IsInt && sig2IsInt) //Both Integers
        {
            int signal1Int=(int)signal1;
            int signal2Int=(int)signal2;
            return compareInts(signal1Int, signal2Int);
        }

        else if(!sig1IsInt && !sig2IsInt)
        {
            List<Object>signal1List=new ArrayList<>((Collection<?>)signal1);
            List<Object>signal2List=new ArrayList<>((Collection<?>)signal2);
            int length1=signal1List.size();
            int length2=signal2List.size();
            int length=Math.min(length1, length2);
            for(int i=0;i<length;i++)
            {
                int result=compare(signal1List.get(i), signal2List.get(i));
                if (result!=-1)
                {
                    return result;
                }
            }
            return compareInts(length1, length2);
        }
        else if(!sig1IsInt ^ !sig2IsInt)
        {
            int result=-1;
            if (sig1IsInt)
            {
                int signal1Int=(int)signal1;
                result=compare(Arrays.asList(signal1Int), signal2);
            }
            else
            {
                int signal2Int=(int)signal2;
                result=compare(signal1,Arrays.asList(signal2Int));
            }
            return result;
        }
        else
        {
            System.out.println("Error!");
        }
        return -1;
    }

    private int compareInts(int one,int two)
    {
        if(one<two)
            {
                return 1;
            }
            else if (one>two)
            {
                return 0;
            }
            else
            {
                return -1;
            }
    }

    public Object getObject()
    {
        return signal;
    }

}
