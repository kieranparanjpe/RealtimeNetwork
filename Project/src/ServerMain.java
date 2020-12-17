
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class ServerMain extends MainSuper {

    Server myServer;
    Client client;

    public static void main(String args[]) {
        PApplet.main(new String[] { ServerMain.class.getName() });
        PApplet.main(new String[] { ClientMain.class.getName() });
        PApplet.main(new String[] { ClientMain.class.getName() });
    }

    public void settings() {
        size(600,600);
    }

    public void setup()
    {
        super.Init();

        // Starts a myServer on port 5204
        myServer = new Server(this, 5204);
    }

    public void draw()
    {
        super.Update();

        client = myServer.available();

        if(client != null)
        {
            input += client.readString();

            if(!CompleteInput())
                return;

            String[] in = input.split(ID_DIVIDER, 2);

            try
            {
                myServer.write(input);
                input = in[1].substring(0, in[1].length() - 1);
                println(input);
                IncomingCommand();
                input = "";
            }
            catch (Exception e){
                input = "";
                println("Outer" + e);
            }
        }

        textSize(30);
        fill(0, 255, 0);
        text(gameObjects.size(), 200, 200);
    }
}
