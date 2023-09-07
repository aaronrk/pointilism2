package com.kruglikov.pointilism.main;

import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.index.Indexer;
import com.kruglikov.pointilism.index.AverageIndexer;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class alternateMain {

    public static void main(String[] args) {
        ImageIndex turtle1 = calculateImageAverage("/*pathToPhoto*/");
        ImageIndex turtle2 = calculateImageAverage("/*pathToPhoto*/");
        System.out.println(calculateImageAverage("/*pathToPhoto*/"));
        System.out.println(calculateImageAverage("/*pathToPhoto*/"));
        System.out.println(turtle1.Compare(turtle2));
    }

    private static ImageIndex calculateImageAverage(String pathToImage) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(pathToImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Indexer indexer = new AverageIndexer();
        ImageIndex index = indexer.Index(img);
        float diffValue = 0;
        try {
            diffValue = index.Compare(index);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        System.out.println(Arrays.toString(index.IndexComponents()));

        return index;
    }
}
