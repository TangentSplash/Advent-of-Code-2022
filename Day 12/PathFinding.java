import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.io.File;

public class PathFinding {
    public static final boolean PART_ONE=false;

    public static final char START='S';
    public static final char END='E';


    public static int endX;
    public static int endY;

    public static int startX;
    public static int startY;

    public static char[][] topography;
    private char[][] map;

    public static int width;
    public static int length;

    private Map<List<Integer>,HillNode> openMap;
    private List<HillNode> openList;
    private Map<List<Integer>,HillNode> closedMap;

    public PathFinding() throws Exception
    {
        Path path = Paths.get("input.txt");
        List<String> topographyString=Files.readAllLines(path);

        openMap=new HashMap<List<Integer>,HillNode>();
        closedMap=new HashMap<List<Integer>,HillNode>();
        
        width=topographyString.size();
        length=topographyString.get(0).length();

        topography=new char[width][length];
        for (int i=0;i<width;i++)
        {
            topography[i]=topographyString.get(i).toCharArray();
        }

        map = new char[topography.length][];
        for(int i = 0; i < topography.length; i++)
            map[i] = topography[i].clone();

        getStartAndEndPosition();

        FileWriter output=createFile();

        int initialPosX=startX;
        int initialPosY=startY;
        if (!PART_ONE)
        {
            initialPosX=endX;
            initialPosY=endY;
        }

        HillNode nextNode=new HillNode(initialPosX, initialPosY, topography[initialPosY][initialPosX], 0, 0,null);

        do{

            nextNode.explore();

            List<HillNode> connected=nextNode.getNextNodes();

            int i=0;
            for (HillNode connectedNode : connected) 
            {
                List<Integer> pos=connectedNode.getPos();

                if(openMap.containsKey(pos))
                {
                    openMap.get(pos).setBestDistance(connectedNode);
                    nextNode.updateNode(openMap.get(pos),i);
                    writeMap(pos, '.');
                }
                else if (!closedMap.containsKey(pos))
                {
                    openMap.put(connectedNode.getPos(), connectedNode);
                    writeMap(pos, ',');
                }
                i++;
            }
            openList=new ArrayList<HillNode>(openMap.values());
            
            Collections.sort(openList);

            closedMap.put(nextNode.getPos(), nextNode);
            writeMap(nextNode.getPos(), '\'');

            nextNode=openList.get(0);
            openMap.remove(nextNode.getPos());
            //printMap(output);
        } while(!nextNode.isEnd());

        int pathLength=nextNode.getDistToHere();

        writeMap(nextNode.getPos(),'E');
        nextNode=nextNode.getPreviousNode();
        for(int i=pathLength;i>1;i--)
        {
            
            char character=(char)(Math.floorMod(i, 10)+'0');
            writeMap(nextNode.getPos(),character);
            nextNode=nextNode.getPreviousNode();
        } 
        writeMap(nextNode.getPos(), 'S');

        printTopography(output);
        printMap(output);

        output.close();

        
        System.out.println("The mimimum path length is "+ pathLength);
        
    }

    private void getStartAndEndPosition()
    {
        boolean startFound=false;
        boolean endFound=false;

        for (int i=0;i<width;i++)
        {
            for (int j=0;j<length;j++)
            {
                if (topography[i][j]==START)
                {
                    startX=j;
                    startY=i;
                    startFound=true;
                }
                else if (topography[i][j]==END)
                {
                    endX=j;
                    endY=i;
                    endFound=true;
                }

                if(startFound && endFound)
                {
                    return;
                }
            }
        }
    }

    private void writeMap(List<Integer> pos,char character)
    {
        map[pos.get(1)][pos.get(0)]=character;
    }

    private FileWriter createFile()
    {
        new File("output.txt");
        try {
        return new FileWriter("output.txt");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return null;
        }
    }

    private void printMap(FileWriter output)
    {
        try {
            for (char[] line : map)
            {
                for (char height: line)
                {
                    output.append(height);
                }
                output.append("\n");
            }
            output.append("\n\n");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

    private void printTopography(FileWriter output)
    {
        try {
            for (char[] line : topography)
            {
                for (char height: line)
                {
                    output.append(height);
                }
                output.append("\n");
            }
            output.append("\n\n");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

}
