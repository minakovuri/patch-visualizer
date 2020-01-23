package org.volgatech.patchvisualizer.app.service;

import org.volgatech.patchvisualizer.app.model.CommitLine;
import org.volgatech.patchvisualizer.app.model.CommitLineStatus;
import org.volgatech.patchvisualizer.app.model.DifferenceBlock;

import java.io.IOException;
import java.util.List;

public class DifferenceBlockValidator {
    public static void check(List<DifferenceBlock> differenceBlocks, List<String> fileLines) throws IOException {
        for (DifferenceBlock differenceBlock : differenceBlocks) {
            int difLineNumber = differenceBlock.getCurrentCommit().getOffset();

            for (CommitLine commitLine : differenceBlock.getCommitLines()) {
                if (commitLine.getStatus() != CommitLineStatus.REMOVED) {
                    checkLine(difLineNumber, commitLine, fileLines);
                    difLineNumber++;
                }
            }
        }
    }

    private static void checkLine(int difLineNumber, CommitLine line, List<String> fileLines) throws IOException {
        if (fileLines.size() < difLineNumber) {
            throw new IOException("Unexpected line");
        }

        String mainLine = fileLines.get(difLineNumber-1);
        String difLine = line.getText();

        if (difLine.length() > 0) {
            difLine = difLine.substring(1);
        }

        if (!mainLine.equals(difLine)) {
            throw new IOException("Unexpected line");
        }
    }
}
