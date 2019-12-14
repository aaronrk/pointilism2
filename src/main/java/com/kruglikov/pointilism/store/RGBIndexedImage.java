package com.kruglikov.pointilism.store;

import com.kruglikov.pointilism.index.ImageIndex;

import java.awt.image.BufferedImage;

public class RGBIndexedImage implements IndexedImage {

    private final BufferedImage image;
    private final ImageIndex index;
    public RGBIndexedImage(BufferedImage image, ImageIndex index) {
        if (image == null) {
            throw new RuntimeException("Null images not allowed.");
        }
        if (index == null) {
            throw new RuntimeException("Null indexes not allowed.");
        }
        this.image = image;
        this.index = index;
    }

    @Override
    public BufferedImage getImage() {
        return this.image;
    }

    @Override
    public ImageIndex getIndex() {
        return this.index;
    }
}
