package com.kruglikov.pointilism.index;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class AverageIndexer implements Indexer{
    @Override
    public ImageIndex Index(BufferedImage img) {
        float rIndex = 0;
        float gIndex = 0;
        float bIndex = 0;
        long totalPixels = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb & 0x00ff0000) >> 16;
                int g = (rgb & 0x0000ff00) >> 8;
                int b = rgb & 0x0000ff;
                rIndex += r;
                gIndex += g;
                bIndex += b;
                totalPixels ++;
            }
        }
        return new RGBIndex(Math.round(rIndex/totalPixels), Math.round(gIndex/totalPixels), Math.round(bIndex/totalPixels));
    }

    @Override
    public ImageIndex.IndexType type() {
        return ImageIndex.IndexType.RGB;
    }
}