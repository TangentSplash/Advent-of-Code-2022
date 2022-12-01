import java.util.List;

public class Elf {
    private int totalCalories;
    private List<Integer> items;

    public Elf(List<Integer> items)
    {
        totalCalories=0;
        this.items=items;
        countCalories();
    }

    private void countCalories()
    {
        int caolries=0;
        for (int item : items) {
            caolries+=item;
        }
        totalCalories=caolries;
    }

    public int getCalories()
    {
        return totalCalories;
    }
}
