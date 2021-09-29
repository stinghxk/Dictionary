import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client
{
    protected static String host = "127.0.0.3";
    protected static int port = 5000;

    public static void main(String[] args)
    {

        if (args.length != 0)
        {
            try
            {
                host = args[0];
//                port = Integer.parseInt(args[1]);
            }
            catch (Exception e)
            {
                System.out.println("Correct format: java -jar Client.jar <String server-address>");
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("Using default address: " + host + ":" + port);
        }

        String msgSent;
        String cmdSent;
        String wordSent = null;
        String defSent = null;
        try
        {
            // creating Scanner class object to get the input 
            Scanner scanner = new Scanner(System.in);

            // establish the connection with server port 5000
            Socket socket = new Socket(host, port);



            // obtaining input and out streams
//            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
//            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            try
            {
//                while (socket.isConnected())
//                {
                    ClientGUI clientGUI = new ClientGUI(socket);
                    clientGUI.setVisible(true);
//                }
            }
            catch (Exception e)
            {
                socket.close();
                System.out.println("connection lost");
            }


        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}