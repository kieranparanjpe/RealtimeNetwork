import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.annotation.*;

@JsonTypeName("Player")
public class Player extends GameObject
{
    public Integer x = 0;
    public Integer y = 0;

    Input myInput = new Input();

    public Player(MainSuper sketch)
    {
        super();

        this.sketch = sketch;

        isLocal = true;

        if(sketch.getClass() == ClientMain.class)
        {
            myInput = ((ClientMain)sketch).myInput;
        }
    }

    public Player()
    {
        super();
    }

    @Override
    public void Update()
    {
        sketch.fill(255);
        sketch.rect(x, y, 100, 100);

        sketch.text(x + " : " + y, 100, 500);

        if(myInput.w)
        {
            y -= 10;
        }
        if(myInput.a)
        {
            x -= 10;
        }
        if(myInput.s)
        {
            y += 10;
        }
        if(myInput.d)
        {
            x += 10;
        }


        if(sketch.getClass() == ClientMain.class)
        {
            ((ClientMain)sketch).CallRTC("SetPosition", id, new Object[] {x, y});
        }
    }

    public void SetPosition(Integer x, Integer y)
    {
        this.x = x;
        this.y = y;
    }
}
