import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.annotation.*;

@JsonTypeName("Player")
public class Player extends GameObject
{
    public int x;
    public int y;

    public Player()
    {
        super.Init();
        System.out.println(id);
    }

    public void SetPosition(Player obj)
    {
        this.x = obj.x;
        this.y = obj.y;
    }
}
