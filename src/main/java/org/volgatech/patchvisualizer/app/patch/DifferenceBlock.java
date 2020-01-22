package org.volgatech.patchvisualizer.app.patch;

import java.util.List;

public class DifferenceBlock {
    private CommitChunkPosition previousCommit;
    private CommitChunkPosition currentCommit;
    private String functionName;
    private List<CommitLine> commitLines;

    public CommitChunkPosition getPreviousCommit() {
        return previousCommit;
    }

    public void setPreviousCommit(CommitChunkPosition previousCommit) {
        this.previousCommit = previousCommit;
    }

    public CommitChunkPosition getCurrentCommit() {
        return currentCommit;
    }

    public void setCurrentCommit(CommitChunkPosition currentCommit) {
        this.currentCommit = currentCommit;
    }

    public List<CommitLine> getCommitLines() {
        return commitLines;
    }

    public void setCommitLines(List<CommitLine> commitLines) {
        this.commitLines = commitLines;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }
}
