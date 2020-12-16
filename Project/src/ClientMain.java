
import com.sun.tools.javac.Main;
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.ArrayList;


import com.fasterxml.jackson.databind.*;

public class ClientMain extends MainSuper {

    public ArrayList<RTCFunction> commands = new ArrayList<RTCFunction>();

    private Client myClient;
    public Input myInput = new Input();

    private ObjectMapper objectMapper = new ObjectMapper();


    public static void main(String args[]) {
        PApplet.main(new String[] { ClientMain.class.getName() });
    }

    public void settings()
    {
        size(600, 600);
    }

    public void setup() {
        myClient = new Client(this, "127.0.0.1", 5204);

        CallRTC("Instantiate", "-1", new GameObject[] {new Player(this)});

        try
        {
            String out = objectMapper.writeValueAsString(commands);
            myClient.write(out);
        }
        catch(Exception e){}
    }

    public void keyPressed()
    {
        myInput.keyPressed(key);
    }

    public void keyReleased()
    {
        myInput.keyReleased(key);
    }

    public void draw() {
        commands.clear();

        super.Update();


        String out = "";


        try
        {
            out = objectMapper.writeValueAsString(commands);
        }
        catch(Exception e){}

        text(out, 200, 200);
        textSize(30);
        fill(0, 255, 0);

        if(myInput.f)
        {
            myClient.write(out);
           println(out);
        }
    }

    public void CallRTC(String name, String id, GameObject[] args)
    {
        RTCFunction function = new RTCFunction(name, id, args);

        if(function.objectID.equals("-1"))
        {
            Instantiate((GameObject) function.arguments[0]);
            println("new player");
        }
        else
        {
            try {
                CallByName(function);
            }
            catch (Exception e)
            {
                return;
            }
        }
        commands.add(function);
    }

    public void clientEvent(Client client)
    {
        /*String input = client.readString();
        try
        {
            IncomingCommand(input);
        }
        catch (Exception e){
            println("Outer" + e);
        }*/
    }
}

