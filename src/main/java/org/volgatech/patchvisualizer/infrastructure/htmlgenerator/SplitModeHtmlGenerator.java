package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.volgatech.patchvisualizer.app.model.CommitLineStatus;
import org.volgatech.patchvisualizer.app.model.GeneralCommitLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SplitModeHtmlGenerator extends HtmlGenerator {
    @Override
    protected String getBody(List<GeneralCommitLine> lines) throws IOException {
        StringBuilder htmlText = new StringBuilder();

        List<GeneralCommitLine> tmpCurLines = new ArrayList<>();
        List<GeneralCommitLine> tmpPrevLines = new ArrayList<>();

        splitLinesByCommits(lines, tmpCurLines, tmpPrevLines);

        for (int i = 0; i < tmpCurLines.size(); ++i) {
            GeneralCommitLine curLine = tmpCurLines.get(i);
            GeneralCommitLine prevLine = tmpPrevLines.get(i);

            htmlText.append(TR);

            htmlText.append(TD);
            String prevLineNumber = (prevLine.getPreviousIndex() != -1) ? String.valueOf(prevLine.getPreviousIndex()) : " ";
            htmlText.append(prevLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(getPayloadHtml(prevLine));

            htmlText.append(TD);
            String curLineNumber = (curLine.getIndex() != -1) ? String.valueOf(curLine.getIndex()) : " ";
            htmlText.append(curLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(getPayloadHtml(curLine));

            htmlText.append(TR_CLOSE);
        }

        return htmlText.toString();
    }

    private void splitLinesByCommits(List<GeneralCommitLine> lines, List<GeneralCommitLine> tmpCurLines, List<GeneralCommitLine> tmpPrevLines) throws IOException {
        for (GeneralCommitLine line: lines) {
            switch (line.getStatus()) {
                case DEFAULT:
                    this.balanceCommits(tmpCurLines, tmpPrevLines);
                    tmpPrevLines.add(line);
                    tmpCurLines.add(line);
                    break;
                case REMOVED:
                    tmpPrevLines.add(line);
                    break;
                case ADDED:
                    tmpCurLines.add(line);
                    break;
                default:
                    throw new IOException("Display mode is invalid.");
            }
        }
        this.balanceCommits(tmpCurLines, tmpPrevLines);
    }

    private void balanceCommits(List<GeneralCommitLine> tmpCurLines, List<GeneralCommitLine> tmpPrevLines) {
        while (tmpPrevLines.size() != tmpCurLines.size()) {
            GeneralCommitLine emptyLine = new GeneralCommitLine();
            emptyLine.setStatus(CommitLineStatus.DEFAULT);
            emptyLine.setText("");
            emptyLine.setIndex(-1);
            emptyLine.setPreviousIndex(-1);
            if (tmpPrevLines.size() > tmpCurLines.size()) {
                tmpCurLines.add(emptyLine);
            } else {
                tmpPrevLines.add(emptyLine);
            }
        }
    }
}
