import java.io.File;
import java.util.*;
import org.json.*;

/* Advent of Code 2022
* Day 13 Part 1
*/

public class Packets {
    public Packets() throws Exception
    {
        File inputFile = new File("Day 13/input.txt");
        Scanner input = new Scanner(inputFile);
        String line;
        int packetIndices=0;
        int result;
        int i=1;
        while(input.hasNextLine())
        {
            line=input.nextLine();
            //System.out.println(line);
            List<Object> signal1=interpret(line);
            line=input.nextLine();
            List<Object> signal2=interpret(line);
            result=compare(signal1, signal2);
            if(result==-1)
            {
                System.out.println("Error!");
            }
            packetIndices+=(result*i);
            if (input.hasNextLine())
            {
                line=input.nextLine();
            }
            i++;
        }
        input.close();

        System.out.println("The sum of the packet indices in the correct order is "+ packetIndices);
    }

    private List<Object> interpret(String line)
    {
        line="{\"array\":"+line+"};";
        JSONObject obj = new JSONObject(line);
        JSONArray arr = obj.getJSONArray("array");
        //System.out.println(line);
        List<Object>signal=arr.toList();
        return signal;
    }

    private int compare (Object signal1, Object signal2)    //Return 1 if in right order, 0 if not and -1 if this test not conclusive
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
}
