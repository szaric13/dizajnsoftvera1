package raf.graffito.dsw.graffRepository.implementation.slide;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class TextElement extends SlideElement {
    private String text;
    @JsonIgnore
    private Font font;
    @JsonIgnore
    private Color textColor;

    public TextElement(int x, int y, int width, int height, String text) {
        super(x, y, width, height);
        this.text = text;
        this.font = new Font("Arial", Font.PLAIN, 12);
        this.textColor = Color.BLACK;
    }

    public TextElement(int x, int y, int width, int height, String text, Font font, Color textColor) {
        super(x, y, width, height);
        this.text = text;
        this.font = font;
        this.textColor = textColor;
    }

    @Override
    public SlideElement copy() {
        TextElement copy = new TextElement(x, y, width, height, text, font, textColor);
        copy.setRotation(rotation);
        return copy;
    }
}

