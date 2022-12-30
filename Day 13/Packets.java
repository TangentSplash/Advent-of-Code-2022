import java.io.File;
import java.util.*;
import org.json.*;

/* Advent of Code 2022
* Day 13 Part 2
*/

public class Packets {
    private TreeMap<Signal, String> signals= new TreeMap<Signal, String>();

    private final String DIVIDER_PACKET1="[[2]]";
    private final String DIVIDER_PACKET2="[[6]]";
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
            Signal signal1=interpret(line);
            signals.put(signal1, line);
            line=input.nextLine();
            Signal signal2=interpret(line);
            signals.put(signal2, line);
            result=signal1.compare(signal1.getObject(),signal2.getObject());
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

        signals.put(interpret(DIVIDER_PACKET1),DIVIDER_PACKET1);
        signals.put(interpret(DIVIDER_PACKET2),DIVIDER_PACKET2);

        ArrayList<String> sortedSignalStrings=new ArrayList<String>(signals.values());
        int divider1=sortedSignalStrings.indexOf(DIVIDER_PACKET1)+1;
        int divider2=sortedSignalStrings.indexOf(DIVIDER_PACKET2)+1;

        int decoderKey=divider1*divider2;

        System.out.println("The sum of the packet indices in the correct order is "+ packetIndices);
        System.out.println("The decoder key is "+decoderKey);
    }

    private Signal interpret(String line)
    {
        line="{\"array\":"+line+"};";
        JSONObject obj = new JSONObject(line);
        JSONArray arr = obj.getJSONArray("array");
        //System.out.println(line);
        List<Object>signalList=arr.toList();
        Signal signal=new Signal(signalList);
        return signal;
    }
}
