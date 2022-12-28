import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.io.File;

public class A_Star {

    public static final char START='S';
    public static final char END='E';

    public static int endX;
    public static int endY;

    public static int startX;
    public static int startY;

    public static char[][] topography;
    public char[][] map;
    public static int width;
    public static int length;

    public Map<List<Integer>,HillNode> allNodes;

    public A_Star() throws Exception
    {
        Path path = Paths.get("input.txt");
        List<String> topographyString=Files.readAllLines(path);
        allNodes=new HashMap<List<Integer>,HillNode>();
        
        width=topographyString.size();
        length=topographyString.get(0).length();

        topography=new char[width][length];
        for (int i=0;i<width;i++)
        {
            topography[i]=topographyString.get(i).toCharArray();
        }

        map=topography.clone();

        getStartAndEndPosition();

        HillNode nextNode=new HillNode(startX, startY, 'a', 0, 0,null);
        allNodes.put(new ArrayList<Integer>(Arrays.asList(startX,startY)),nextNode);
        nextNode.explore();

        do{
            if (!nextNode.isExplored())
            {
                nextNode.explore();
            }
            nextNode.sortNextNodes();

            List<HillNode> connected=nextNode.conncetedNodes(); //here what are connected?
            
            int i=0;
            for (HillNode connectedNode : connected) 
            {
                List<Integer> pos=connectedNode.getPos();

                if(allNodes.containsKey(pos))
                {
                    allNodes.get(pos).setBestDistance(connectedNode);
                    nextNode.updateNode(allNodes.get(pos),i);
                }
                else
                {
                    allNodes.put(connectedNode.getPos(), connectedNode);
                }
                i++;
            }
            HillNode nextPlannnedNode=nextNode.findNextBest();
            if(nextPlannnedNode==null)
            {
                nextPlannnedNode=nextNode.getPreviousNode();
                if (nextPlannnedNode==null)
                {
                    System.out.println("Damn!");
                    break;
                }
            }
            nextNode=nextPlannnedNode;
            
            writeMap(nextNode.getPos());
        }while(!nextNode.isEnd());

        printMap();
        int pathLength=nextNode.getDistToHere();
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

    private void writeMap(List<Integer> pos)
    {
        map[pos.get(1)][pos.get(0)]='#';
    }

    private void printMap()
    {
        new File("output.txt");
        try {
        FileWriter output = new FileWriter("output.txt");
        for (char[] line : map)
        {
            for (char height: line)
            {
                output.append(height);
            }
            output.append("\n");
        }
        output.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
