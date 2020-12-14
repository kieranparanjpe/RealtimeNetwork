
public class RTCFunction {

    public String functionName;
    public String objectID;

    public GameObject[] arguments;

    public RTCFunction(){};

    public RTCFunction(String name, String id, GameObject[] args)
    {
        functionName = name;
        objectID = id;
        arguments = args;
    }
}
