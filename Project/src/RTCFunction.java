
public class RTCFunction {

    public String functionName = "";
    public String objectID = "";

    public GameObject instantiate;
    public Object[] arguments;

    public RTCFunction(){};

    public RTCFunction(String name, String id, GameObject arg)
    {
        functionName = name;
        objectID = id;
        instantiate = arg;
    }

    public RTCFunction(String name, String id, Object[] args)
    {
        functionName = name;
        objectID = id;
        arguments = args;
    }
}
