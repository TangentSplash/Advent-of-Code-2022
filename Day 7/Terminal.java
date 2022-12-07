/* Advent of Code 2022
 * Day 7 Part 1
 */

import java.io.File;
import java.util.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Terminal {

    private static final int MAX_DIR_SIZE=100000;
    private static final int TOTAL_SIZE_AVAILABLE=70000000;
    private static final int SIZE_NEEDED=30000000;

    private Directory currentDirectory;
    private Directory home=null;
    public Terminal() throws Exception
    {
        File inputFile = new File("Day 7\\input.txt");
        Scanner input = new Scanner(inputFile);

        String line;

        while (input.hasNextLine())
        {
            line=input.nextLine();
            if(line.charAt(0)=='$')
            {
                Command command=new Command(line.substring(2)); //Remove the '$ ' from the input
                int commandType=command.getCommand();
                if (commandType==Command.CD_COMMAND)
                {
                    String argument=command.getArgument();
                    changeDirectory(argument);
                }
                else if (commandType==Command.LS_COMMAND)
                {
                    // Files are about to be listed
                }
                else
                {
                    System.out.println("Error:" + line+ "not a valid command");
                }
            }
            else
            {
                Pattern dir = Pattern.compile("dir");
                Matcher Matcher = dir.matcher(line);
                String[] fileParts;
                String name;

                if (Matcher.find())
                {
                    fileParts=line.split("dir ");
                    name=fileParts[fileParts.length-1];
                    currentDirectory.add(new Directory(name, currentDirectory));
                }
                else
                {
                    fileParts=line.split(" ");
                    int size=Integer.parseInt(fileParts[0]);
                    name=fileParts[1];
                    currentDirectory.add(new Item(name,size));
                }
            }
        }
        input.close();

        List<Integer> totalSizeList=home.getSizeList();

        int sizeToBeRemoved=SIZE_NEEDED-(TOTAL_SIZE_AVAILABLE-totalSizeList.get(0));
        int minSizeBigger=TOTAL_SIZE_AVAILABLE;
        int totalSize=0;
        for (Integer size : totalSizeList) {
            if(size<=MAX_DIR_SIZE)
            {
                totalSize+=size;
            }
            if(size>sizeToBeRemoved)
            {
                minSizeBigger=Math.min(size, minSizeBigger);
            }

        }
        System.out.println("The total size of directories less than max is " + totalSize);
        System.out.println("The smallest single directory that could be removed to frre up the nessicarry space is "+minSizeBigger);
    }

    private void changeDirectory(String argument)
    {
        Directory moveTo=null;
        if (argument.equals("/"))
        {
            if (home==null)
            {
                home=new Directory(argument,null);
            }
            moveTo=home;
        }
        else if (argument.equals(".."))
        {
            moveTo=currentDirectory.getParentDirectory();          
        }
        else
        {
            moveTo=currentDirectory.findChildDirectory(argument);
        }

        if (moveTo==null)
        {
            System.out.println("Error: No directory above this one");
        }
        else
        {
            currentDirectory=moveTo;
        }
    }
    
}
