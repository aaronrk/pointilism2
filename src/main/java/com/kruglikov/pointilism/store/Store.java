package com.kruglikov.pointilism.store;

import com.kruglikov.pointilism.index.ImageIndex;
import com.kruglikov.pointilism.index.Indexer;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.image.BufferedImage;

public interface Store {

    //Return the image that is most similar to the image whose index is provided
    IndexedImage returnMostSimilar(ImageIndex otherIndex);

    //Take an image, index it and generate
    void storeImage(BufferedImage image);

    //Set the indexer for this store, once an image has been set using the set
    // indexer the indexer can no longer be changed
    boolean setIndexer(Indexer indexer);

    // Returns the currently set indexer, if no indexer has been set returns null
    ImageIndex.IndexType currentIndexer();
}
