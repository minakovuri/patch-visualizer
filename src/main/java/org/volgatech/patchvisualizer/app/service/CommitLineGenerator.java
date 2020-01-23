package org.volgatech.patchvisualizer.app.service;

import org.volgatech.patchvisualizer.app.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommitLineGenerator {
    private int mainFileIndex;
    private int prevIndex;
    private int currIndex;

    public List<GeneralCommitLine> getGeneralCommitLines(List<String> fileLines, List<DifferenceBlock> differenceBlocks) throws IOException {
        List<GeneralCommitLine> generalCommitLines = new ArrayList<>();

        this.mainFileIndex = 0;
        this.prevIndex = 0;
        this.currIndex = 0;

        for (DifferenceBlock differenceBlock : differenceBlocks) {
            int offset = differenceBlock.getCurrentCommit().getOffset() - 1;
            copyDefaultLines(offset, generalCommitLines, fileLines);

            List<CommitLine> commitLines = differenceBlock.getCommitLines();
            for (CommitLine rawLine: commitLines) {
                handleRawLine(rawLine, generalCommitLines);
            }

            CommitChunkPosition position = differenceBlock.getCurrentCommit();
            if (position.getHeight() == 0 || position.getOffset() == 0) {
                mainFileIndex = position.getHeight() + position.getOffset() + 1;
            } else {
                mainFileIndex = position.getHeight() + position.getOffset();
            }
        }

        int offset = fileLines.size();
        copyDefaultLines(offset, generalCommitLines, fileLines);

        return generalCommitLines;
    }

    private void handleRawLine(CommitLine rawLine, List<GeneralCommitLine> generalCommitLines) throws IOException {
        GeneralCommitLine newLine = new GeneralCommitLine();
        newLine.setText(rawLine.getText());
        newLine.setPreviousIndex(-1);
        newLine.setIndex(-1);

        switch (rawLine.getStatus()) {
            case DEFAULT:
                newLine.setStatus(CommitLineStatus.DEFAULT);
                newLine.setIndex(currIndex++);
                newLine.setPreviousIndex(prevIndex++);
                break;
            case ADDED:
                newLine.setStatus(CommitLineStatus.ADDED);
                newLine.setIndex(currIndex++);
                break;
            case REMOVED:
                newLine.setStatus(CommitLineStatus.REMOVED);
                newLine.setPreviousIndex(prevIndex++);
                break;
            default:
                throw new IOException("Unhandled CommitLineStatus");

        }

        generalCommitLines.add(newLine);
    }

    private void copyDefaultLines(int offset, List<GeneralCommitLine> generalCommitLines, List<String> fileLines) {
        for (int i = mainFileIndex; i < offset; i++) {
            GeneralCommitLine line = new GeneralCommitLine();
            line.setPreviousIndex(prevIndex++);
            line.setIndex(currIndex++);
            line.setText(fileLines.get(i));
            line.setStatus(CommitLineStatus.DEFAULT);

            generalCommitLines.add(line);
        }
    }
}
