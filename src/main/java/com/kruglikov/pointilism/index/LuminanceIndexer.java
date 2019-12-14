package com.kruglikov.pointilism.index;

import com.kruglikov.pointilism.utils.ColorConverter;
import com.kruglikov.pointilism.utils.RGBUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class LuminanceIndexer implements Indexer {
    @Override
    public ImageIndex Index(BufferedImage img) {
        BufferedImage outputImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        WritableRaster raster = outputImage.getRaster();
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                int rgbValue = img.getRGB(i, j);
//                System.out.println(utils.RGBUtils.rComponent(rgbValue));
                float[] lumChromComponents = ColorConverter.RGBtoYCbCr(RGBUtils.rComponent(rgbValue),
                                                                             RGBUtils.gComponent(rgbValue),
                                                                             RGBUtils.bComponent(rgbValue), ColorConverter.YCbCrColorSpace.ITU_BT_601
                );
                int[] RGBcomponents = ColorConverter.YCbCrtoRGB(lumChromComponents[0], 0, 0, ColorConverter.YCbCrColorSpace.ITU_BT_601);
//              System.out.println(Arrays.toString(RGBcomponents));
//                int[] rgbaComponents = Arrays.copyOf(RGBcomponents, 4);
                raster.setPixel(i, j, RGBcomponents);

            }
        }

        File outputfile = new File("//Users/aaronkruglikov/Desktop/bwdog.jpg");
        try {
            ImageIO.write(outputImage, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ImageIndex.IndexType type() {
        return ImageIndex.IndexType.LUMINANCE;
    }
}