import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {
    public static final int CD_COMMAND=0;
    public static final int LS_COMMAND=1;
    private String commandString;
    private int command;
    private String argument;

    public Command(String command)
    {
        commandString=command;
        interpretCommand();
    }

    private void interpretCommand()
    {
        Pattern cd = Pattern.compile("cd");
        Matcher cdMatcher = cd.matcher(commandString);

        Pattern ls = Pattern.compile("ls");
        Matcher lsMatcher = ls.matcher(commandString);

        if (cdMatcher.find())
        {
            command=CD_COMMAND;
            String[] commandParts=commandString.split("cd ");
            argument=commandParts[commandParts.length-1];
        }
        else if (lsMatcher.find())
        {
            command=LS_COMMAND;
        }
        else
        {
            command=-1;
        }

    }

    public int getCommand()
    {
        return command;
    }

    public String getArgument()
    {
        return argument;
    }
}
