package com.kruglikov.pointilism.compositer;

import java.awt.image.BufferedImage;

public class SimpleCompositer {
    private BufferedImage baseImage;
    private BufferedImage generatedImage;

    public SimpleCompositer(BufferedImage baseImage) {
        this.baseImage = baseImage;
    }
}
