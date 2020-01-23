package org.volgatech.patchvisualizer.app.model;

import java.util.List;

public class DifferenceBlock {
    private ChangeChunk previousCommit;
    private ChangeChunk currentCommit;
    private List<CommitLine> commitLines;

    public ChangeChunk getPreviousCommit() {
        return previousCommit;
    }

    public void setPreviousCommit(ChangeChunk previousCommit) {
        this.previousCommit = previousCommit;
    }

    public ChangeChunk getCurrentCommit() {
        return currentCommit;
    }

    public void setCurrentCommit(ChangeChunk currentCommit) {
        this.currentCommit = currentCommit;
    }

    public List<CommitLine> getCommitLines() {
        return commitLines;
    }

    public void setCommitLines(List<CommitLine> commitLines) {
        this.commitLines = commitLines;
    }
}
