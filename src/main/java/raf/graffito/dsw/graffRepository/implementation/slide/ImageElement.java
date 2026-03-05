package raf.graffito.dsw.graffRepository.implementation.slide;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.awt.image.BufferedImage;

@Getter
@Setter
public class ImageElement extends SlideElement {
    @JsonIgnore
    private BufferedImage image;
    private String imagePath;

    public ImageElement(int x, int y, int width, int height, BufferedImage image, String imagePath) {
        super(x, y, width, height);
        this.image = image;
        this.imagePath = imagePath;
    }

    @Override
    public SlideElement copy() {
        ImageElement copy = new ImageElement(x, y, width, height, image, imagePath);
        copy.setRotation(rotation);
        return copy;
    }
}

