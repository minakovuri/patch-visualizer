package org.volgatech.patchvisualizer.app.model;

public class CommitLine {
    private CommitLineStatus status;
    private String text;
    private int index;

    public CommitLineStatus getStatus() {
        return status;
    }

    public void setStatus(CommitLineStatus status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
