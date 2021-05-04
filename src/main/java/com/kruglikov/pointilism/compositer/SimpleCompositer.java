package com.kruglikov.pointilism.compositer;

import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.store.IndexedImage;
import com.kruglikov.pointilism.store.Store;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SimpleCompositer {
  private Store imageLibrary;

  public SimpleCompositer(Store imageLibrary) {
    this.imageLibrary = imageLibrary;
  }

  public BufferedImage generateCompositeImageForImage(BufferedImage baseImage, int unitSize) {
    // Crop image to a square

    // Calculate the smaller dimension
    int lesserDim = Math.min(baseImage.getWidth(), baseImage.getHeight());
    boolean xLarger = baseImage.getWidth() != lesserDim;

    // Determine the starting index in the larger dimension to select the center
    int xStart = 0;
    int yStart = 0;

    if (xLarger) {
      xStart = (baseImage.getWidth() - lesserDim) / 2;
    } else {
      yStart = (baseImage.getHeight() - lesserDim) / 2;
    }

    int divisibleDimension = lesserDim - (lesserDim % unitSize);
    System.out.printf("LesserDim is %d\nDivisible dimension is %d\n", lesserDim, divisibleDimension);

    BufferedImage croppedImage =
        baseImage.getSubimage(xStart, yStart, divisibleDimension, divisibleDimension);
    BufferedImage outputImage =
        new BufferedImage(divisibleDimension, divisibleDimension, baseImage.getType());

    // Search unitSize squares from top left going across columns one row at a time
    double total = Math.pow(divisibleDimension/unitSize, 2);
    double count = 0;

    for (int row = 0; row < divisibleDimension; row += unitSize) {
      for (int col = 0; col < divisibleDimension; col += unitSize) {
        int percent = (int)Math.round(count/total * 100);
        System.out.printf("%d%%\n", percent);
        count++;
        // Select the area of this image to be matched
        BufferedImage baseSubarea = croppedImage.getSubimage(row, col, unitSize, unitSize);
        ImageIndex subareaIndex = this.imageLibrary.currentIndexer().Index(baseSubarea);

        // Find the closest matching image for the region
        IndexedImage mostSimilarIndexedImage = imageLibrary.returnMostSimilar(subareaIndex);
        BufferedImage mostSimilarImage = mostSimilarIndexedImage.getImage();

        // Compress the matching image to fit the unit size
        Image compressedImage =
            mostSimilarImage.getScaledInstance(unitSize, unitSize, Image.SCALE_AREA_AVERAGING);

        // Copy the image into the original image
        outputImage.getGraphics().drawImage(compressedImage, row, col, null);
      }
    }

    return outputImage;
  }
}
