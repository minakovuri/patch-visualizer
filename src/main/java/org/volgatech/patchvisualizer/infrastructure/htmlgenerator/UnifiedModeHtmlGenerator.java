package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.volgatech.patchvisualizer.app.model.OutCommitLine;

import java.io.IOException;
import java.util.List;

class UnifiedModeHtmlGenerator extends HtmlGenerator {
    @Override
    protected String getBody(List<OutCommitLine> lines) throws IOException {
        StringBuilder htmlText = new StringBuilder();

        for (OutCommitLine commitLine : lines) {
            htmlText.append(TR);

            htmlText.append(TD);
            String prevLineNumber = (commitLine.getPreviousIndex() != -1) ? String.valueOf(commitLine.getPreviousIndex()+1) : " ";
            htmlText.append(prevLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(TD);
            String currLineNumber = (commitLine.getIndex() != -1) ? String.valueOf(commitLine.getIndex()+1) : " ";
            htmlText.append(currLineNumber);
            htmlText.append(TD_CLOSE);

            htmlText.append(getPayloadHtml(commitLine));

            htmlText.append(TR_CLOSE);
        }

        return htmlText.toString();
    }
}
