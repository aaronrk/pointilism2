package com.kruglikov.pointilism.index;

import java.awt.image.BufferedImage;

public interface Indexer {
    ImageIndex Index(BufferedImage img);

    ImageIndex.IndexType type();
}