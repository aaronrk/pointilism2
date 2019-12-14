package com.kruglikov.pointilism.index;

public class RGBIndex implements ImageIndex {
    private float rIndex = 0;
    private float gIndex = 0;
    private float bIndex = 0;

    @SuppressWarnings("WeakerAccess")
    public RGBIndex(float rIndex, float gIndex, float bIndex) {
        this.rIndex = rIndex;
        this.gIndex = gIndex;
        this.bIndex = bIndex;
    }


    @Override
    public String toString() {
        return String.format("Red value %f, Green value %f, Blue value %f", rIndex, gIndex, bIndex);
    }
    @Override
    public float Compare(ImageIndex otherIndex) throws IllegalArgumentException {
        if (otherIndex.type() != type()) {
            throw new IllegalArgumentException(String.valueOf(new String[]{
                    "Provided image index is of incompatible type " +
                            otherIndex.getClass().getName()}));
        }
        return sumOfSquaresComparison(this, (RGBIndex) otherIndex);
    }

    @Override
    public float[] IndexComponents() {
        return new float[]{rIndex,gIndex,bIndex};
    }

    private float sumOfSquaresComparison(RGBIndex thisIndex, RGBIndex thatIndex) {
        float rDiff = thisIndex.rIndex - thatIndex.rIndex;
        float gDiff = thisIndex.gIndex - thatIndex.gIndex;
        float bDiff = thisIndex.bIndex - thatIndex.bIndex;

        return rDiff * rDiff + gDiff * gDiff + bDiff * bDiff;
    }

    @Override
    public IndexType type() {
        return IndexType.RGB;
    }
}