import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

public class ClientGUI extends JFrame {
    private JTextField wordField;
    private JButton searchButton;
    private JButton addButton;
    private JButton removeButton;
    private JButton updateButton;
    private JTextArea textArea;
    private JTextField defField;
    private JPanel panelMain;

    public enum CMD  {query,add,remove,update}

    ClientGUI(Socket socket)
    {

//        JFrame frame = new JFrame();
        super.setTitle("My Socket Dictionary");
        this.setContentPane(this.panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();


        try
        {
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            this.addWindowListener(new WindowAdapter()
            {
                public void windowClosing(WindowEvent we)
                {

                    try
                    {
                        outputStream.writeUTF("exit");
                        inputStream.close();
                        socket.close();
                    }
                    catch (IOException e)
                    {
                        System.out.println("error in closing the window");
                    }
                    System.exit(0);
                }
            });
//        try
//        {
//            textArea.setText(inputStream.readUTF());
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }

            searchButton.addActionListener(e ->
            {
                String cmdSent;
                try
                {
                    cmdSent = CMD.query.name() + "_" + wordField.getText();
                    outputStream.writeUTF(cmdSent);
                    textArea.setText(inputStream.readUTF());
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });


            addButton.addActionListener(e ->
            {
                String cmdSent;
                try
                {
                    cmdSent = CMD.add.name() + "_" + wordField.getText() + "_" + defField.getText();
                    outputStream.writeUTF(cmdSent);
                    textArea.setText(inputStream.readUTF());
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });


            removeButton.addActionListener(e ->
            {
                String cmdSent;
                try
                {
                    cmdSent = CMD.remove.name()+ "_" + wordField.getText();
                    outputStream.writeUTF(cmdSent);
                    textArea.setText(inputStream.readUTF());
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });
            updateButton.addActionListener(e ->
            {
                String cmdSent;
                try
                {
                    cmdSent = CMD.update.name() + "_" + wordField.getText() + "_" + defField.getText();
                    outputStream.writeUTF(cmdSent);
                    textArea.setText(inputStream.readUTF());
                }
                catch (IOException ioException)
                {
                    ioException.printStackTrace();
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }




    }

//    public void initialize()
//    {
//
//    }
}
