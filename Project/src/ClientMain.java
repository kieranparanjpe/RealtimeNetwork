
import com.sun.tools.javac.Main;
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.UUID;


import com.fasterxml.jackson.databind.*;

public class ClientMain extends MainSuper {

    public ArrayList<RTCFunction> commands = new ArrayList<RTCFunction>();

    private Client myClient;
    public Input myInput = new Input();

    public String id;

    public static void main(String args[]) {
        PApplet.main(new String[] { ClientMain.class.getName() });
    }

    public void settings()
    {
        size(600, 600);
    }

    public void setup() {
        id = UUID.randomUUID().toString() + ID_DIVIDER;

        super.Init();

        myClient = new Client(this, "127.0.0.1", 5204);

        InstantiateRTC(new Player(this));

        try
        {
            String out = objectMapper.writeValueAsString(commands);
            myClient.write(id + out + FINAL_MESSAGE_CHARACTER.charAt(1));
        }
        catch(Exception e){}
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
            myClient.write(id + out + FINAL_MESSAGE_CHARACTER.charAt(1));
        }
    }

    public void clientEvent(Client client)
    {
        input = client.readString();

        if(!CompleteInput())
            return;

        String[] in = input.split(ID_DIVIDER, 2);

        in[0] = in[0] + ID_DIVIDER;
        if(in[0].equals(id))
            return;

        try
        {
            input = in[1].substring(0, in[1].length() - 1);
            IncomingCommand();
            input = "";
        }
        catch (Exception e){
            input = "";
            println("Outer" + e);
        }
    }

    public void CallRTC(String name, String id, Object[] args)
    {
        RTCFunction function = new RTCFunction(name, id, args);
        try {
            CallByName(function);
        }
        catch (Exception e)
        {
            return;
        }

        commands.add(function);
    }

    public void InstantiateRTC(GameObject obj)
    {
        RTCFunction function = new RTCFunction("Instantiate", "-1", obj);

        Instantiate(obj);
        println("new player");
        commands.add(function);

    }

    public void keyPressed()
    {
        myInput.keyPressed(key);
    }

    public void keyReleased()
    {
        myInput.keyReleased(key);
    }
}

