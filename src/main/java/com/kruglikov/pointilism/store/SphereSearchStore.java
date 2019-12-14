package com.kruglikov.pointilism.store;

import com.kruglikov.pointilism.index.AverageIndexer;
import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.index.Indexer;
import com.kruglikov.pointilism.index.RGBIndex;

import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import static java.lang.Math.max;
import static java.lang.Math.min;

// A store using RGB indexes that locates the most similar point as the point that would lie within
// a sphere of radius r centered at the point that is being compared to where r is minimized.
public class SphereSearchStore implements Store {
    private TreeMap<ImageIndex, List<IndexedImage>> rIndex = new TreeMap<ImageIndex, List<IndexedImage>>(new Comparator<ImageIndex>() {
        @Override
        public int compare(ImageIndex o1, ImageIndex o2) {
            return compareNthElement(0, o1, o2);
        }
    });
    private TreeMap<ImageIndex, List<IndexedImage>> gIndex = new TreeMap<ImageIndex, List<IndexedImage>>(new Comparator<ImageIndex>() {
        @Override
        public int compare(ImageIndex o1, ImageIndex o2) {
            return compareNthElement(1, o1, o2);
        }
    });
    private TreeMap<ImageIndex, List<IndexedImage>> bIndex = new TreeMap<ImageIndex, List<IndexedImage>>(new Comparator<ImageIndex>() {
        @Override
        public int compare(ImageIndex o1, ImageIndex o2) {
            return compareNthElement(2, o1, o2);
        }
    });

    private final Indexer indexer;

    public SphereSearchStore() {
        this.indexer = new AverageIndexer();
    }

    private int compareNthElement(int n, ImageIndex index1, ImageIndex index2) {
        if (n >= index1.IndexComponents().length || n >= index2.IndexComponents().length) {
            throw new RuntimeException("Insufficient number of components for comparison");
        }
        return Float.compare(index1.IndexComponents()[n], index2.IndexComponents()[n]);
    }

    @Override
    public IndexedImage returnMostSimilar(ImageIndex otherIndex) {
        float[] indexElems = otherIndex.IndexComponents();
        float rLow = indexElems[0];
        float rHigh = indexElems[0];
        float gLow = indexElems[1];
        float gHigh = indexElems[1];
        float bLow = indexElems[2];
        float bHigh = indexElems[2];
        for (int delta = 0; delta < 255; delta ++) {
            //for each entry check if the elements at the current boundary fall on or inside the sphere
            //TODO Potential alternative, once an element has appeared 3 times (once per index) it is guaranteed to be inside.

            //Get new elements being added at each index
            //Compare them all and if any of them fall within range on all dimensions return that image


            List<IndexedImage> rLowElems = rIndex.get(new RGBIndex(rLow, 0, 0));
            List<IndexedImage> rHighElems = rIndex.get(new RGBIndex(rHigh, 0, 0));
            List<IndexedImage> gLowElems = gIndex.get(new RGBIndex(0, gLow, 0));
            List<IndexedImage> gHighElems = gIndex.get(new RGBIndex(0, gHigh, 0));
            List<IndexedImage> bLowElems = bIndex.get(new RGBIndex(0, 0, bLow));
            List<IndexedImage> bHighElems = bIndex.get(new RGBIndex(0, 0, bHigh));

            IndexedImage returnableImage;
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, rLowElems);
            if (returnableImage != null) {
                return returnableImage;
            }
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, rHighElems);
            if (returnableImage != null) {
                return returnableImage;
            }
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, gLowElems);
            if (returnableImage != null) {
                return returnableImage;
            }
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, gHighElems);
            if (returnableImage != null) {
                return returnableImage;
            }
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, bLowElems);
            if (returnableImage != null) {
                return returnableImage;
            }
            returnableImage = findFittingElem(rLow, rHigh, gLow, gHigh, bLow, bHigh, bHighElems);
            if (returnableImage != null) {
                return returnableImage;
            }

            //strange boundary condition, all elements are at their extreme before update
            if (rLow == 0 && gLow == 0 && bLow == 0 && rHigh == 255 && gHigh == 255 && bHigh == 255) {
                return null;
            }
            //adjust all indecies
            rLow = max(0, rLow - 1);
            rHigh = min(255, rHigh + 1) ;
            gLow = max(0, gLow - 1);
            gHigh = min(255, gHigh + 1);
            bLow = max(0, bLow - 1);
            bHigh = min(255, bHigh + 1);
//            System.out.printf("rlow:%s , rhigh:%s , glow:%s , ghigh:%s , blow:%s, bhigh: %s\n", rLow, rHigh, gLow, gHigh, bLow, bHigh);

        }
        System.out.printf("Found no matchable elements\n");
        return null;
    }

    private IndexedImage findFittingElem(float rLow, float rHigh, float gLow, float gHigh, float bLow, float bHigh, List<IndexedImage> images) {
        //allows for matching an image to itself
        if (images == null) {
            return null;
        }
        for (IndexedImage image : images) {
            float[] indexElems = image.getIndex().IndexComponents();
            float rVal = indexElems[0];
            float gVal = indexElems[1];
            float bVal = indexElems[2];
            if (rLow <= rVal && rHigh >= rVal && gLow <= gVal && gHigh >= gVal && bLow <= bVal && bHigh >= bVal) {
                return image;
            }

        }
        return null;
    }


    @Override
    public void storeImage(BufferedImage image) {
        ImageIndex key = this.indexer.Index(image);
        IndexedImage indexedImg = new RGBIndexedImage(image, key);
        if (rIndex.containsKey(key)) {
            List<IndexedImage> existingElems = rIndex.get(key);
            //This only needs to be done for the first index any perfect duplicates
            // will be found at this point
            for (IndexedImage elem : existingElems) {
                if (elem.getIndex().deepEquals(key)) {
                    System.out.println("Found perfect match");
                    return;
                }
            }
            existingElems.add(indexedImg);
        } else {
            List<IndexedImage> newImageLst = new LinkedList<IndexedImage>();
            newImageLst.add(indexedImg);
            rIndex.put(key, newImageLst);
        }
        if (gIndex.containsKey(key)) {
            List<IndexedImage> existingElems = gIndex.get(key);
            existingElems.add(indexedImg);
        } else {
            List<IndexedImage> newImageLst = new LinkedList<IndexedImage>();
            newImageLst.add(indexedImg);
            gIndex.put(key, newImageLst);
        }
        if (bIndex.containsKey(key)) {
            List<IndexedImage> existingElems = bIndex.get(key);
            existingElems.add(indexedImg);
        } else {
            List<IndexedImage> newImageLst = new LinkedList<IndexedImage>();
            newImageLst.add(indexedImg);
            bIndex.put(key, newImageLst);
        }
    }

    @Override
    public boolean setIndexer(Indexer indexer) {
        return false;
    }

    @Override
    public ImageIndex.IndexType currentIndexer() {
        return this.indexer.type();
    }
}

