
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.ArrayList;


import com.fasterxml.jackson.databind.*;


public class ClientMain extends PApplet {

    public ArrayList<RTCFunction> commands = new ArrayList<RTCFunction>();

    public Player player;
    Client myClient;
    Input myInput = new Input();

    public String dataOut = "";

    ObjectMapper objectMapper = new ObjectMapper();
    Player p = new Player();


    public static void main(String args[]) {
        PApplet.main(new String[] { ClientMain.class.getName() });
    }

    public void settings()
    {
        size(600, 600);
    }

    public void setup() {
        myClient = new Client(this, "127.0.0.1", 5204);

        //p.id = "0";

        commands.add(new RTCFunction("New", "-1", new GameObject[]{p}));

        try
        {
            dataOut = objectMapper.writeValueAsString(commands);
            myClient.write(dataOut);
           // println(dataOut);
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

        background(0);

        rect(p.x, p.y, 50, 50);

        commands.add(new RTCFunction("SetPosition", p.id, new GameObject[] {p}));
        try
        {
            dataOut = objectMapper.writeValueAsString(commands);
        }
        catch(Exception e){}

        text(dataOut, 200, 200);
        textSize(30);
        fill(0, 255, 0);

        if(myInput.w)
        {
            p.y -= 10;
        }
        if(myInput.a)
        {
            p.x -= 10;
        }
        if(myInput.s)
        {
            p.y += 10;
        }
        if(myInput.d)
        {
            p.x += 10;
        }

        if(myInput.f)
        {
            myClient.write(dataOut);
            println(dataOut);
        }
    }
}

