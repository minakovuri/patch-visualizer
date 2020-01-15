package org.volgatech.patchvisualizer.app;

public class CommitChunkPosition {
    private int offset = 0;
    private int height = 0;

    public void setOffset(int offset)
    {
        this.offset = offset;
    }

    public int getOffset()
    {
        return offset;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public int getHeight()
    {
        return height;
    }
}
