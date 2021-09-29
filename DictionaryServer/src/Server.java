import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

// Creating Server class
public class Server
{
    public static void main(String[] args) throws IOException
    {

        System.out.println("Dictionary server is running...");
        // server is listening on port 5000
        ServerSocket serverSocket = new ServerSocket(5000);

        // get multiple client request
        while (true)
        {
            Socket socket = null;
            try
            {
                // accept incoming request and get socket object
                socket = serverSocket.accept();

                System.out.println("New client connected " + socket);

                // obtaining input and out streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Creating new thread for this client");
                Thread t = new MultiClientHandler(socket, dataInputStream, dataOutputStream);

                // call the start() method
                t.start();

            }
            catch (Exception e)
            {
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

 