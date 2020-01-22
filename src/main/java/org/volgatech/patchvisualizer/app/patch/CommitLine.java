package org.volgatech.patchvisualizer.app.patch;

public class CommitLine {
    private CommitLineStatus status;
    private String text;
    private int index;

    CommitLineStatus getStatus() {
        return status;
    }

    void setStatus(CommitLineStatus status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    int getIndex() {
        return index;
    }

    void setIndex(int index) {
        this.index = index;
    }
}
