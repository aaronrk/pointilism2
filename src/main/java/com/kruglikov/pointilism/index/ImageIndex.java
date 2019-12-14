package com.kruglikov.pointilism.index;

public interface ImageIndex{

    enum IndexType {
        RGB,
        LUMINANCE
    }
    //Returns a float indicating the degree of similarity between the provided index and this one, if the index is of
    //a different underlying type an InvalidArgumentException should be thrown, otherwise 0 represents the same image,
    //the larger the value the less similar the two images
    float Compare(ImageIndex otherIndex) throws IllegalArgumentException;


    //Returns the components of the index, these have no universal meaning at this point and this method should not be
    //called externally
    float[] IndexComponents();

    boolean deepEquals(ImageIndex otherIndex);

    IndexType type();
}