package org.volgatech.patchvisualizer.app.model;

import java.util.List;

public class PatchInfo {
    private List<DifferenceBlock> differenceBlocks;
    private CommitInfo commitInfo;

    public void setDifferenceBlocks(List<DifferenceBlock> differenceBlocks) {
        this.differenceBlocks = differenceBlocks;
    }

    public List<DifferenceBlock> getDifferenceBlocks() {
        return differenceBlocks;
    }

    public void setCommitInfo(CommitInfo commitInfo) {
        this.commitInfo = commitInfo;
    }

    public CommitInfo getCommitInfo() {
        return commitInfo;
    }
}
