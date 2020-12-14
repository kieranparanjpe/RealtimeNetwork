
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.type.TypeReference;
import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import java.util.*;

import com.fasterxml.jackson.databind.*;

public class ServerMain extends PApplet {

    public Map<String, GameObject> gameObjects = new HashMap<String, GameObject>();
    Server myServer;

    Client client;

    int positionX = 0;
    int positionY = 0;

    ObjectMapper objectMapper = new ObjectMapper();

    String input = "";

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
        // Starts a myServer on port 5204
        myServer = new Server(this, 5204);
    }

    public void draw()
    {
        background(0);

        client = myServer.available();

        if(client != null)
        {
            input = client.readString();
            try
            {
                List<RTCFunction> commands = objectMapper.readValue(input, new TypeReference<>() {});


                for (RTCFunction function : commands) {
                    if(function.objectID.equals("-1"))
                    {
                        println(input);

                        Instantiate((GameObject) function.arguments[0]);
                    }
                    else {

                        try {
                            CallByName(gameObjects.get(function.objectID), function.functionName, function.arguments);
                        } catch (Exception e) {
                            println(e + "Inner");
                        }
                    }
                }

            }
            catch (Exception e){
                println("Outer" + e);
            }
        }

        textSize(30);
        fill(0, 255, 0);
        text(gameObjects.size(), 200, 200);

        Set<Map.Entry<String, GameObject>> set = gameObjects.entrySet();

        for (Map.Entry<String, GameObject> e : set)
        {
            rect(((Player)e.getValue()).x, ((Player)e.getValue()).y, 50, 50);
        }


    }

    public void CallByName(Object obj, String name, Object[] arguments) throws Exception
    {
        if(arguments.length > 5)
            return;

        Method method;

        switch (arguments.length)
        {
            case (1):
                method = obj.getClass().getMethod(name, arguments[0].getClass());
                break;
            case (2):
                method = obj.getClass().getMethod(name, arguments[0].getClass(), arguments[1].getClass());
                break;
            case (3):
                method = obj.getClass().getMethod(name, arguments[0].getClass(), arguments[1].getClass(), arguments[2].getClass());
                break;
            case (4):
                method = obj.getClass().getMethod(name, arguments[0].getClass(), arguments[1].getClass(), arguments[2].getClass(), arguments[3].getClass());
                break;
            case (5):
                method = obj.getClass().getMethod(name, arguments[0].getClass(), arguments[1].getClass(), arguments[2].getClass(), arguments[3].getClass(), arguments[4].getClass());
                break;
            default:
                method = obj.getClass().getMethod(name);
                break;
        }

        method.invoke(obj, arguments);
    }

    public void Instantiate(GameObject object)
    {
        gameObjects.put(object.id, object);
    }

}
