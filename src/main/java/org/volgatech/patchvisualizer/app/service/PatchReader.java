package org.volgatech.patchvisualizer.app.service;

import org.volgatech.patchvisualizer.app.model.*;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PatchReader {
    private static final String PATTERN = "[\\@]{2}\\s[\\-][0-9]+["
            + "\\,]?[0-9]*\\s[\\"
            + "+][0-9]+[\\"
            + ",]?[0-9]{0,}\\s[\\@]{2,2}.{0,}";

    private static final String COMMIT = "From ";
    private static final String AUTHOR = "From: ";
    private static final String DATE = "Date: ";
    private static final String SUBJECT = "Subject: ";

    private static final String FILE_NAME_SEPARATOR = "---";

    public static PatchInfo parsePatchInfo(String patchText) throws IOException {
        String[] lines = patchText.split("\\r?\\n");

        PatchInfo patchInfo = new PatchInfo();

        CommitInfo commitInfo = new CommitInfo();
        List<DifferenceBlock> differenceBlocks = new ArrayList<>();

        DifferenceBlock differenceBlock = new DifferenceBlock();
        List<CommitLine> commitLines = new ArrayList<>();

        int lineNumber = 0;

        boolean isNewBlock = false;
        boolean isFileName = false;

        for (String line : lines) {
            if (!line.isEmpty() && line.toCharArray()[0] == '\\') {
                continue;
            }

            lineNumber++;

            if (lineNumber > 0 && lineNumber < 8 && !line.isEmpty()) {
                if (line.substring(0, 3).contains(FILE_NAME_SEPARATOR)) {
                    isFileName = true;
                    continue;
                }
                parseCommitInfo(line, commitInfo, isFileName);

                if (isFileName) {
                    isFileName = false;
                }

                continue;
            }

            if (line.matches(PATTERN)) {
                setCommitLinesToDifferenceBlock(differenceBlock, commitLines, differenceBlocks);

                differenceBlock = new DifferenceBlock();
                commitLines = new ArrayList<>();

                parseFunctionNameInBlockSeparator(line, differenceBlock);
                parseBlockSeparator(line, differenceBlock);

                isNewBlock = true;
                continue;
            }

            if (isNewBlock) {
                CommitLine commitLine = parseCommitLime(line);
                commitLines.add(commitLine);
            }
        }

        setCommitLinesToDifferenceBlock(differenceBlock, commitLines, differenceBlocks);

        patchInfo.setCommitInfo(commitInfo);
        patchInfo.setDifferenceBlocks(differenceBlocks);

        return patchInfo;
    }

    private static void parseBlockSeparator(String line, DifferenceBlock differenceBlock) throws IOException {
        String[] parsedChunkLines = line.split(" ");
        if (parsedChunkLines.length < 4) {
            throw new IOException();
        }
        if (!parsedChunkLines[0].equals("@@") || !parsedChunkLines[3].equals("@@")) {
            throw new IOException(); // TODO
        }

        String minusNumber = parsedChunkLines[1];
        String plusNumber = parsedChunkLines[2];
        if (minusNumber.contains("-")) {
            CommitChunkPosition commitChunkPosition = parseCommitChunkPosition(minusNumber);
            differenceBlock.setPreviousCommit(commitChunkPosition);
        } else {
            throw new IOException(); // TODO
        }
        if (plusNumber.contains("+")) {
            CommitChunkPosition commitChunkPosition = parseCommitChunkPosition(minusNumber);
            differenceBlock.setCurrentCommit(commitChunkPosition);
        } else {
            throw new IOException(); // TODO
        }
    }

    private static CommitChunkPosition parseCommitChunkPosition(String decimalNumberStr) throws IOException {
        decimalNumberStr = decimalNumberStr.substring(1);
        String[] numbers = decimalNumberStr.split(",");

        CommitChunkPosition chunkPosition = new CommitChunkPosition();
        if (numbers.length == 2) {
            chunkPosition.setOffset(Integer.parseInt(numbers[0]));
            chunkPosition.setHeight(Integer.parseInt(numbers[1]));
        } else if (numbers.length == 1) {
            chunkPosition.setOffset(0);
            chunkPosition.setHeight(Integer.parseInt(numbers[0]));
        } else {
            throw new IOException(); // TODO
        }
        return chunkPosition;
    }

    private static void parseFunctionNameInBlockSeparator(String line, DifferenceBlock differenceBlock) {
        int lastDogDog = line.lastIndexOf("@@");
        String functionName = line.substring(lastDogDog + 2, line.length());
        if (!functionName.equals("")) {
            differenceBlock.setFunctionName(functionName);
        }
    }

    private static void setCommitLinesToDifferenceBlock(DifferenceBlock differenceBlock, List<CommitLine> commitLines, List<DifferenceBlock> differenceBlocks) {
        if (differenceBlock != null && !commitLines.isEmpty()) {
            differenceBlock.setCommitLines(commitLines);
            differenceBlocks.add(differenceBlock);
        }
    }

    private static void parseCommitInfo(String line, CommitInfo commitInfo, boolean isFileName) throws IOException {
        if (line.contains(COMMIT)) {
            commitInfo.setCommit(line);
        } else if (line.contains(AUTHOR)) {
            commitInfo.setAuthor(line);
        } else if (line.contains(DATE)) {
            commitInfo.setDate(line);
        } else if (line.contains(SUBJECT)) {
            commitInfo.setSubject(line);
        } else if (isFileName) {
            commitInfo.setFileName(line);
        } else {
            throw new IOException("Invalid commit info line");
        }
    }

    private static CommitLine parseCommitLime(String line) {
        CommitLine commitLine = new CommitLine();
        commitLine.setText(line);

        CommitLineStatus commitLineStatus = parseCommitLineStatus(line);
        commitLine.setStatus(commitLineStatus);

        return commitLine;
    }

    private static CommitLineStatus parseCommitLineStatus(String line) {
        if (line.isEmpty()) {
            return CommitLineStatus.DEFAULT;
        }
        Character ch = line.charAt(0);
        if (ch.equals('-')) {
            return CommitLineStatus.REMOVED;
        }
        if (ch.equals('+')) {
            return CommitLineStatus.ADDED;
        }
        return CommitLineStatus.DEFAULT;
    }
}
