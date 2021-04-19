package com.kruglikov.pointilism.compositer;

import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.store.IndexedImage;
import com.kruglikov.pointilism.store.Store;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.nio.Buffer;

public class SimpleCompositer {
    private Store imageLibrary = null;

    public SimpleCompositer(Store imageLibrary) {
        this.imageLibrary = imageLibrary;
    }

    public BufferedImage generateCompositeImageForImage(BufferedImage baseImage, int unitSize) {
        // Crop image to a square

        // Calculate the smaller dimension
        int lesserDim = Math.min(baseImage.getNumXTiles(), baseImage.getNumYTiles());
        boolean xLarger = baseImage.getNumXTiles() != lesserDim ? true : false;

        // Determine the starting index in the larger dimension to select the center
        int xStart = 0;
        int yStart = 0;

        if (xLarger) {
            xStart = (baseImage.getNumXTiles() - lesserDim) / 2;
        } else {
            yStart = (baseImage.getNumYTiles() - lesserDim) / 2;
        }

        int divisibleDimension = lesserDim - (lesserDim % unitSize);

        BufferedImage croppedImage = baseImage.getSubimage(xStart, yStart, divisibleDimension,
                                                           divisibleDimension);
        BufferedImage outputImage = new BufferedImage(divisibleDimension, divisibleDimension,
                                                      baseImage.getType());

        // Search unitSize squares from top left going across columns one row at a time
        for (int row = 0; row < divisibleDimension; row += unitSize) {
            for (int col = 0; col < divisibleDimension; col += unitSize) {

                // Select the area of this image to be matched
                BufferedImage baseSubarea = croppedImage.getSubimage(row, col, unitSize, unitSize);
                ImageIndex subareaIndex = this.imageLibrary.currentIndexer().Index(baseSubarea);

                // Find the closest matching image for the region
                IndexedImage mostSimilarIndexedImage = imageLibrary.returnMostSimilar(subareaIndex);
                BufferedImage mostSimilarImage = mostSimilarIndexedImage.getImage();

                // Compress the matching image to fit the unit size
                Image compressedImage = mostSimilarImage.getScaledInstance(unitSize, unitSize,
                                                                                   Image.SCALE_AREA_AVERAGING);


                // Copy the image into the original image

            }
        }

        return outputImage;


    }
}
