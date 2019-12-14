package com.kruglikov.pointilism.store;

import com.kruglikov.pointilism.index.ImageIndex;

import java.awt.image.BufferedImage;

public interface IndexedImage {

    //Returns the image that was indexed.
    BufferedImage getImage();

    //Returns the index of the image
    ImageIndex getIndex();
}