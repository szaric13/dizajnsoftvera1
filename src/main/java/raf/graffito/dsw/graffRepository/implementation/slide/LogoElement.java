package raf.graffito.dsw.graffRepository.implementation.slide;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class LogoElement extends SlideElement {
    @JsonIgnore
    private Color fillColor;
    @JsonIgnore
    private Color strokeColor;
    @JsonIgnore
    private float strokeWidth;

    public LogoElement(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.fillColor = Color.YELLOW;
        this.strokeColor = Color.BLACK;
        this.strokeWidth = 2.0f;
    }

    public LogoElement(int x, int y, int width, int height, Color fillColor, Color strokeColor, float strokeWidth) {
        super(x, y, width, height);
        this.fillColor = fillColor;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
    }

    @Override
    public SlideElement copy() {
        LogoElement copy = new LogoElement(x, y, width, height, fillColor, strokeColor, strokeWidth);
        copy.setRotation(rotation);
        return copy;
    }
}

