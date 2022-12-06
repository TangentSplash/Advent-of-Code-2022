/* Advent of Code 2022
 * Day 6 Part 2
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class PacketHeader {
    private final static int START_OF_PACKET_MARKER_LENGTH=4;
    private final static int START_OF_MESSAGE_MARKER_LENGTH=14;

    public static void main(String[] args) throws Exception
    {

        FileReader file = new FileReader("Day 6\\input.txt");
        BufferedReader input = new BufferedReader(file);

        LimitedQueue<Character> checkingPacketStart=new LimitedQueue<Character>(START_OF_PACKET_MARKER_LENGTH);
        LimitedQueue<Character> checkingMessageStart=new LimitedQueue<Character>(START_OF_MESSAGE_MARKER_LENGTH);

        int in;
        char character;
        int packetMarker=0;
        int messageMarker=1;
        boolean lookingForPacket=true;
        while((in=input.read())!=-1)
        {
            character=(char)in;
            checkingMessageStart.add(character);
            
            if(lookingForPacket)
            {
                checkingPacketStart.add(character);
                Set<Character> uniquePacketCharacters=new TreeSet<Character>(checkingPacketStart);
                if (uniquePacketCharacters.size()==START_OF_PACKET_MARKER_LENGTH)
                {
                    lookingForPacket=false;
                }
                packetMarker++;
            }

            Set<Character> uniqueMessageCharacters=new TreeSet<Character>(checkingMessageStart);
            if (uniqueMessageCharacters.size()==START_OF_MESSAGE_MARKER_LENGTH)
            {
                break;
            }
            messageMarker++;
        }
        input.close();

        System.out.println("The first start-of-packet marker is detected after " + packetMarker + " characters");
        System.out.println("The first start-of-message marker is detected after " + messageMarker + " characters");
    }
    
}
