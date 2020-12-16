import processing.core.*;
import processing.net.*;
import java.lang.reflect.*;
import com.fasterxml.jackson.annotation.*;

@JsonTypeName("Player")
public class Player extends GameObject
{
    public int x;
    public int y;

    Input myInput = new Input();

    public Player(MainSuper sketch)
    {
        super.Init();

        this.sketch = sketch;

        if(sketch.getClass() == ClientMain.class)
        {
            myInput = ((ClientMain)sketch).myInput;
        }
    }

    public Player()
    {
        super.Init();
    }

    @Override
    public void Update()
    {
        sketch.fill(255);
        sketch.rect(x, y, 100, 100);

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
            ((ClientMain)sketch).CallRTC("SetPosition", id, new GameObject[] {this});
        }
    }

    public void SetPosition(Player p)
    {
        this.x = p.x;
        this.y = p.y;
    }
}
