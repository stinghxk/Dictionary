import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MultiClientHandler extends Thread
{
    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    // Initializing a Dictionary
    MyDictionary myDictionary = new MyDictionary("src/dictionary.json");


    public MultiClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream)
    {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;

    }

    @Override
    public void run()
    {

        String msgReceived;
        String cmdReceived;
        String wordReceived = null;
        String defReceived = null;

        outer:
        while (socket.isConnected())
        {
            try
            {

                // receive the answer from client

                msgReceived = dataInputStream.readUTF();
                String[] msgParse =msgReceived.split("_");
                cmdReceived = msgParse[0];
                if (msgParse.length > 1)
                    wordReceived = msgParse[1];
                if (msgParse.length > 2)
                    defReceived = msgParse[2];

                switch (cmdReceived.toLowerCase())
                {
                    case "query":
                        if(wordReceived != null)
                            dataOutputStream.writeUTF(query(wordReceived.toLowerCase()));
                        else
                            dataOutputStream.writeUTF("Please try again to enter a word.");
                        break;

                    case "add":
                        if(wordReceived != null && defReceived != null)
                            dataOutputStream.writeUTF(add(wordReceived.toLowerCase(),defReceived));
                        else if(wordReceived == null)
                            dataOutputStream.writeUTF("PLease try again to enter a word.");
                        else if(defReceived == null)
                            dataOutputStream.writeUTF("PLease try again to enter the definition.");
                        break;

                    case "update":
                        if(wordReceived != null && defReceived != null)
                            dataOutputStream.writeUTF(update(wordReceived.toLowerCase(),defReceived));
                        else if(wordReceived == null)
                            dataOutputStream.writeUTF("PLease try again to enter a word.");
                        else if(defReceived == null)
                            dataOutputStream.writeUTF("PLease try again to enter the definition.");
                        break;

                    case "remove":
                        if(wordReceived != null)
                            dataOutputStream.writeUTF(remove(wordReceived.toLowerCase()));
                        else
                            dataOutputStream.writeUTF("Please try again to enter a word.");
                        break;

                    case "exit":
                    default:
                        System.out.println("Closing this connection with " + socket);
                        this.socket.close();
                        System.out.println("Connection closed");
                        break outer;
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
                break outer;
            }
        }

        try
        {
            // closing resources
            this.dataOutputStream.close();
            this.dataInputStream.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param word
     * @return
     */
    public String query(String word)
    {
        String result = null;


        if (word != null)
        {
            System.out.println("Word to query is: " + word);
            if (!myDictionary.hasWord(word))
            {
                result = "No result for:" + word + " Try again or add this word.\n";
            }
            else
            result = myDictionary.getDefinition(word) + "\n";
        }
        else
            result = "Please enter a word.";

        return result;
    }

    /**
     *
     * @param word
     * @param definition
     * @return
     */
    public synchronized String add(String word, String definition)
    {
        String result = null;

        if (word != null)
        {
            System.out.println("Word to add is: " + word);
            if (myDictionary.hasWord(word))
            {
                result = word + " has already exists. Try again or update this word.\n";

            }
            else if (definition != null)
            {
                myDictionary.addWord(word, definition);
                result = word + " has been added to the dictionary!";
            }
            else
            {
                result = "Please try again to add word and its definition.";
            }
        }
        return result;
    }

    /**
     *
     * @param word
     * @param definition
     * @return
     */
    public synchronized String update(String word, String definition)
    {
        String result = null;

        if (word != null)
        {
            System.out.println("Word to update is: " + word);
            if (!myDictionary.hasWord(word))
            {
                result = word + " doesn't exist. Try again or add this word.\n";

            }
            else if (definition != null)
            {
                myDictionary.updateWord(word, definition);
                result ="Definition of " + word + " has been updated to the dictionary!\n";
            }
            else
            {
                result = "Please try again to update this word.\n";
            }
        }
        return result;
    }

    /**
     *
     * @param word
     * @return
     */
    public synchronized String remove(String word)
    {
        String result = null;

        if (word != null)
        {
            System.out.println("Word to remove is: " + word);
            if (!myDictionary.hasWord(word))
            {
                result = word + " doesn't exist. \n";

            }
            else
            {
                myDictionary.removeWord(word);
                result ="Definition of " + word + " has been removed from the dictionary!\n";
            }
        }
        return result;
    }


}
