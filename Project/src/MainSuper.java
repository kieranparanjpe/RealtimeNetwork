import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import processing.core.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class MainSuper extends PApplet
{
    public Map<String, GameObject> gameObjects = new HashMap<String, GameObject>();

    ObjectMapper objectMapper = new ObjectMapper();

    public void Update()
    {
        background(0);

        Set<Map.Entry<String, GameObject>> set = gameObjects.entrySet();

        for (Map.Entry<String, GameObject> e : set)
        {
            e.getValue().Update();
        }
    }
    public void IncomingCommand(String input) throws Exception
    {
        List<RTCFunction> commands = objectMapper.readValue(input, new TypeReference<>() {});


        for (RTCFunction function : commands) {
            if(function.objectID.equals("-1"))
            {
                Instantiate((GameObject) function.arguments[0]);
            }
            else {
                try {
                    CallByName(function);
                } catch (Exception e) {

                }
            }
        }
    }

    public void CallByName(RTCFunction function) throws Exception
    {
        String name = function.functionName;
        GameObject obj = gameObjects.get(function.objectID);
        Object[] arguments = function.arguments;

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
        if(object.sketch == null)
            object.sketch = this;
        gameObjects.put(object.id, object);
    }

}
