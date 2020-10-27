import java.io.*;
import java.net.*;

public class UDPEchoClient {

    private static InetAddress host;
    private static final int PORT = 1234;
    private static DatagramSocket dgramSocket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println("Host ID not found!");
            System.exit(1);
        }

        run();
    }

    private static void run() {
        try {
            dgramSocket = new DatagramSocket(); //Step 1.
            //Set up stream for keyboard entry...
            BufferedReader userEntry = new BufferedReader(new InputStreamReader(System.in));
            String message = null;
            String response = null;
            do {
               
                System.out.println("Please enter in the following format (e.g 10 Euro USD)");
                System.out.print("Enter : ");
                message = userEntry.readLine();

                if (!message.equals("exit")) {
                    outPacket = new DatagramPacket(message.getBytes(),message.length(),host,PORT); //Step 2.  sends of users input to be recieved by an inpacket
                    dgramSocket.send(outPacket); //Step 3.
                    buffer = new byte[256]; //Step 4.
                    inPacket = new DatagramPacket(buffer,buffer.length); //Step 5.
                    dgramSocket.receive(inPacket); //Step 6.
                    response = new String(inPacket.getData(), 0, inPacket.getLength()); //Step 7.
                    System.out.println("\nSERVER> " + response); 
                }
            } while (!message.equals("exit"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("\n* Closing connection... *");
            dgramSocket.close(); //Step 8.
        }
    }
}