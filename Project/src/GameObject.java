import processing.core.PApplet;
import com.fasterxml.jackson.annotation.*;
import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Player.class, name = "Player"),
})
public abstract class GameObject {

    public String id;

    protected void Init()
    {
        id = UUID.randomUUID().toString();
    }
}
