import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class MyDictionary
{
    private static HashMap<String, String> words;
    private String filePath;
    private JSONObject jsonWords;

    public MyDictionary()
    {
    }

    public MyDictionary(String filePath)
    {
        this.filePath = filePath;
        words = new HashMap<>();
//        if (filePath == null)
        loadFile();
    }


    public HashMap<String, String> getWords()
    {
        return words;
    }

    public void loadFile()
    {
        words.clear();

        try
        {
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(this.filePath);
            Object object = jsonParser.parse(reader);
            this.jsonWords = (JSONObject) object;
            jsonToMap(this.jsonWords);
        }
        catch (FileNotFoundException e)
        {
            //Create json file for jar
            this.jsonWords = jarJson();
            jsonToMap(this.jsonWords);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private JSONObject jarJson()
    {
        JSONObject jObject = new JSONObject();
        jObject.put("java", "An island in Indonesia south of Borneo.");
        jObject.put("python", "(Greek mythology) Dragon killed by Apollo at Delphi.");
        jObject.put("c", "The 3rd letter of the Roman alphabet.");
        return jObject;
    }

    /**
     *
     */
    public void updateFile()
    {
        try
        {
            mapToJson();
            FileWriter file = new FileWriter(this.filePath);
            file.write(this.jsonWords.toJSONString());
            file.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * Get definition of words
     * @param word
     * @return definition
    * */
    public String getDefinition(String word)
    {
        return words.get(word);
    }

    /**
     *put more word in dictionary
     * @param word
     * @param definition
     */
    public void addWord(String word, String definition)
    {
        words.put(word, definition);
        updateFile();
    }

    public void updateWord(String word, String definition)
    {
        words.replace(word, definition);
        updateFile();
    }

    public void removeWord(String word)
    {
        words.remove(word);
        updateFile();
    }

    public boolean hasWord(String word)
    {
        return this.words.get(word) != null;
    }

    public void jsonToMap(JSONObject jObject) throws RuntimeException
    {

        for (Object o : jObject.keySet())
        {
            String word = (String) o;
            String definition = jObject.get(word).toString();
            words.put(word, definition);

        }
    }

    public void mapToJson()
    {

        this.jsonWords = new JSONObject(words);

    }



}


