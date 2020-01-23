package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.volgatech.patchvisualizer.app.model.CommitLineStatus;
import org.volgatech.patchvisualizer.app.model.OutCommitLine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class SplitModeHtmlGenerator extends HtmlGenerator {
    @Override
    protected String getBody(List<OutCommitLine> lines) throws IOException {
        StringBuilder htmlText = new StringBuilder();

        List<OutCommitLine> tmpCurLines = new ArrayList<>();
        List<OutCommitLine> tmpPrevLines = new ArrayList<>();

        splitLinesByCommits(lines, tmpCurLines, tmpPrevLines);

        for (int i = 0; i < tmpCurLines.size(); ++i) {
            OutCommitLine curLine = tmpCurLines.get(i);
            OutCommitLine prevLine = tmpPrevLines.get(i);

            htmlText.append(TR);

            htmlText.append(TD);
            String prevLineNumber = (prevLine.getPreviousIndex() != -1) ? String.valueOf(prevLine.getPreviousIndex()+1) : " ";
            htmlText.append(prevLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(getPayloadHtml(prevLine));

            htmlText.append(TD);
            String curLineNumber = (curLine.getIndex() != -1) ? String.valueOf(curLine.getIndex()+1) : " ";
            htmlText.append(curLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(getPayloadHtml(curLine));

            htmlText.append(TR_CLOSE);
        }

        return htmlText.toString();
    }

    private void splitLinesByCommits(List<OutCommitLine> lines, List<OutCommitLine> tmpCurLines, List<OutCommitLine> tmpPrevLines) throws IOException {
        for (OutCommitLine line: lines) {
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

    private void balanceCommits(List<OutCommitLine> tmpCurLines, List<OutCommitLine> tmpPrevLines) {
        while (tmpPrevLines.size() != tmpCurLines.size()) {
            OutCommitLine emptyLine = new OutCommitLine();
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
