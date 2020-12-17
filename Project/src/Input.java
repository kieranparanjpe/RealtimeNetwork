public class Input
{
    public boolean w, a, s, d, f;

    public void keyPressed(char key)
    {
        if(key == 'w' || key == 'W')
        {
            w = true;
        }
        if(key == 'a' || key == 'A')
        {
            a = true;
        }
        if(key == 's' || key == 'S')
        {
            s = true;
        }
        if(key == 'd' || key == 'D')
        {
            d = true;
        }
        if(key == ' ' || key == ' ')
        {
            f = true;
        }
    }

    public void keyReleased(char key)
    {
        if(key == 'w' || key == 'W')
        {
            w = false;
        }
        if(key == 'a' || key == 'A')
        {
            a = false;
        }
        if(key == 's' || key == 'S')
        {
            s = false;
        }
        if(key == 'd' || key == 'D')
        {
            d = false;
        }
        if(key == ' ' || key == ' ')
        {
            f = false;
        }
    }

}
