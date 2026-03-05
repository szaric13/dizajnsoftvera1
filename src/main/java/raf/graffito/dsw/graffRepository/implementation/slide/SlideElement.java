package raf.graffito.dsw.graffRepository.implementation.slide;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "element_type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImageElement.class, name = "image"),
        @JsonSubTypes.Type(value = TextElement.class, name = "text"),
        @JsonSubTypes.Type(value = LogoElement.class, name = "logo"),
})
@Getter
@Setter
public abstract class SlideElement {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected double rotation;

    public SlideElement(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rotation = 0.0;
    }

    public abstract SlideElement copy();

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}

