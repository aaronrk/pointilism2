package com.kruglikov.pointilism.store;

import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.index.Indexer;

import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.TreeSet;

public class InefficientStore implements Store {

    private Set<IndexedImage> indexedImages = new TreeSet<IndexedImage>();
    @Override
    public IndexedImage returnMostSimilar(ImageIndex otherIndex) {
        return null;
    }

    @Override
    public void storeImage(BufferedImage image) {

    }

    @Override
    public boolean setIndexer(Indexer indexer) {
        return false;
    }

    @Override
    public Indexer currentIndexer() {
        return null;
    }
}
