
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.*;
import com.fasterxml.jackson.databind.*;

public class ServerMain extends MainSuper {

    Server myServer;
    Client client;

    private ArrayList<RTCFunction> functionHistory = new ArrayList<RTCFunction>();

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
                List<RTCFunction> tempFunctions = IncomingCommand();

                for(int i = 0; i < tempFunctions.size(); i++)
                {
                    functionHistory.add(tempFunctions.get(i));
                }
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

    public void serverEvent(Server server, Client client)
    {
        RTCFunction last = new RTCFunction("", "", new Object[]{});
        int sameCounter = 0;
        ArrayList<RTCFunction> temp = new ArrayList<RTCFunction>();
        for(int i = functionHistory.size() - 1; i >= 0; i--)
        {
            println(functionHistory.get(i).functionName + " : Name i : " + i);
            if(((functionHistory.get(i).objectID.equals(last.objectID) && functionHistory.get(i).functionName.equals(last.functionName) && sameCounter < 10) ||
            functionHistory.get(i) == last) && !functionHistory.get(i).objectID.equals("-1"))
            {
                sameCounter++;
                println("Remove: " + i);
            }
            else
            {
                temp.add(functionHistory.get(i));
                sameCounter = 0;
            }

            last = functionHistory.get(i);
        }

        Collections.reverse(temp);
        functionHistory = temp;

        try
        {
            String out = objectMapper.writeValueAsString(functionHistory);
            println(functionHistory.size() + out);
            myServer.write("SERVER" + ID_DIVIDER + out + FINAL_MESSAGE_CHARACTER.charAt(1));
        }
        catch (Exception e){}
    }
}
