/* Advent of Code 2022
 * Day 2 Part 1
 */

import java.io.File;
import java.util.*;

public class RockPaperScissors {
  
    public static void main(String[] args) throws Exception
    {
        File file = new File("Day 2\\input.txt");
        Scanner input = new Scanner(file);

        Player me=new Player();
        Player opponent=new Player();

        String round;
        while (input.hasNextLine())
        {
            round=input.nextLine();
            opponent.moveChosen(round.charAt(0));
            me.moveChosen(round.charAt(2));
            me.compareMoves(opponent);
        }

        input.close();
        int score=me.getScore();
        System.out.println("Final score is " + score);

  }
}