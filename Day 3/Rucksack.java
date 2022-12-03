import java.util.*;
import java.util.stream.Collectors;

public class Rucksack {

    private Set<Character> compartment1=new TreeSet<>();
    private Set<Character> compartment2=new TreeSet<>();
    private int priority;

    public Rucksack (String items)
    {
        priority=0;
        int length=items.length();
        String compartment1String=items.substring(0, length/2);
        String compartment2String=items.substring(length/2,length);
        compartment1=compartment1String.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());
        compartment2=compartment2String.chars().mapToObj(chr -> (char) chr).collect(Collectors.toSet());

        findOverlap();
        calculatePriority();
    }

    private void findOverlap()
    {
        compartment1.retainAll(compartment2);
    }

    private void calculatePriority()
    {
        int thisPriority=0;
        for (Character character : compartment1) {
            thisPriority=character-38;
            if (Character.isLowerCase(character))
            {
                thisPriority=character-96;
            }
        }
        priority+=thisPriority;
    }

    public int getPriority()
    {
        return priority;
    }
    
}
