package com.kruglikov.pointilism.main;

import com.kruglikov.pointilism.index.AverageIndexer;
import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.index.Indexer;
import com.kruglikov.pointilism.store.IndexedImage;
import com.kruglikov.pointilism.store.SphereSearchStore;
import com.kruglikov.pointilism.store.Store;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        Store store = new SphereSearchStore();
        Indexer indexer = new AverageIndexer();
        BufferedImage img = null;
        File dir = new File("/Users/aaronkruglikov/Desktop/testingPicsPointilism");
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                try {
                    img = ImageIO.read(child);
                    if (img != null) {
                        store.storeImage(img);
                    } else {
                        System.out.printf("Null image found: %s\n", child.getAbsoluteFile());
                    }
                } catch (IOException e) {
                    System.out.printf("Failed for %s", child.getName());
                }
            }
        } else {
            throw new RuntimeException("Passed in path is not a directory");

        }
        ImageIndex testIndex;
        try {
            img = ImageIO.read(new File("/Users/aaronkruglikov/Desktop/turts.jpg"));
            testIndex = indexer.Index(img);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not find searchable file");
        }
        long startTime = System.nanoTime();
        IndexedImage mostSimilar = store.returnMostSimilar(testIndex);
        long endTime = System.nanoTime();
        System.out.printf("Elapsed search time of %s microseconds\n", (endTime - startTime) / 1000);
        System.out.printf("Similarity index is %s (lower is better)\n", mostSimilar.getIndex().Compare(testIndex));
//        System.out.println(Arrays.toString(mostSimilar.getIndex().IndexComponents()));
    }
}