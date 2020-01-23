package org.volgatech.patchvisualizer.app.service;

import org.volgatech.patchvisualizer.app.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommitLineGenerator {
    public static List<GeneralCommitLine> getGeneralCommitLines(List<String> fileLines, List<DifferenceBlock> differenceBlocks) throws IOException {
        List<GeneralCommitLine> generalCommitLines = new ArrayList<>();

        int mainFileIndex = 0;
        int prevIndex = 0;
        int currIndex = 0;

        for (DifferenceBlock differenceBlock : differenceBlocks) {
            int offset = differenceBlock.getCurrentCommit().getOffset() - 1;
            copyDefaultLines(mainFileIndex, offset, generalCommitLines, fileLines, prevIndex, currIndex);

            prevIndex++;
            currIndex++;

            List<CommitLine> commitLines = differenceBlock.getCommitLines();
            for (CommitLine rawLine: commitLines) {
                handleRawLine(rawLine, generalCommitLines, prevIndex, currIndex);
            }

            CommitChunkPosition position = differenceBlock.getCurrentCommit();
            if (position.getHeight() == 0 || position.getOffset() == 0) {
                mainFileIndex = position.getHeight() + position.getOffset() + 1;
            } else {
                mainFileIndex = position.getHeight() + position.getOffset();
            }
        }

        int offset = fileLines.size();
        copyDefaultLines(mainFileIndex, offset, generalCommitLines, fileLines, prevIndex, currIndex);

        return generalCommitLines;
    }

    private static void handleRawLine(CommitLine rawLine, List<GeneralCommitLine> generalCommitLines, int prevIndex, int currIndex) throws IOException {
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

    private static void copyDefaultLines(int mainFileIndex, int offset, List<GeneralCommitLine> generalCommitLines, List<String> fileLines, int prevIndex, int currIndex) {
        for (int i = mainFileIndex; i < offset; i++) {
            GeneralCommitLine line = new GeneralCommitLine();
            line.setPreviousIndex(prevIndex);
            line.setIndex(currIndex);
            line.setText(fileLines.get(i));
            line.setStatus(CommitLineStatus.DEFAULT);

            generalCommitLines.add(line);
        }
    }
}
