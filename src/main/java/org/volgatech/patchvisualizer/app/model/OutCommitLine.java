package org.volgatech.patchvisualizer.app.model;

public class OutCommitLine extends CommitLine {
    private int previousIndex;

    public void setPreviousIndex(int previousIndex) {
        this.previousIndex = previousIndex;
    }

    public int getPreviousIndex() { return previousIndex; }
}
