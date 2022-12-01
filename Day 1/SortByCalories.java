import java.util.*;

public class SortByCalories implements Comparator<Elf>
{
    public int compare(Elf a, Elf b)
    {
        return b.getCalories() - a.getCalories();
    }
}
