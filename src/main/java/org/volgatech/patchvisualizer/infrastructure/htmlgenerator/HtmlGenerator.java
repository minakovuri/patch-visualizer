package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.volgatech.patchvisualizer.app.model.CommitInfo;
import org.volgatech.patchvisualizer.app.model.GeneralCommitLine;
import org.volgatech.patchvisualizer.infrastructure.arguments.DisplayMode;

import java.io.IOException;
import java.util.List;

public abstract class HtmlGenerator {
    protected static final String HTML_HEADER = "<!doctype html>" +
            "<html lang=\"en\">" +
            "  <head>" +
            "    <!-- Required meta tags -->" +
            "    <meta charset=\"utf-8\">" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1, shrink-to-fit=no\">" +
            "" +
            "    <!-- Bootstrap CSS -->" +
            "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
            "" +
            "    <title>Patch-visualizer</title>" +
            "<style type=\"text/css\">" +
            "code {" +
            "white-space: pre;" +
            "color: black" +
            "}" +
            "</style>" +
            "  </head>" +
            "    <body>";

    protected static final String TABLE_PREFIX = "      <table class=\"table\">" +
            "        <thead class=\"thead-dark\">" +
            "        </thead>" +
            "        <tbody>";

    protected static final String HTML_FOOTER = "</tbody>" +
            "      </table>" +
            "" +
            "    <!-- Optional JavaScript -->" +
            "    <!-- jQuery first, then Popper.js, then Bootstrap JS -->" +
            "    <script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>" +
            "    <script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js\" integrity=\"sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q\" crossorigin=\"anonymous\"></script>" +
            "    <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" integrity=\"sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl\" crossorigin=\"anonymous\"></script>" +
            "  </body>" +
            "</html>";

    protected static final String TR = "<tr>";
    protected static final String TR_CLOSE = "</tr>";
    protected static final String TD_SUCCESS = "<td class=\"table-success\">";
    protected static final String TD_DANGEROUS = "<td class=\"table-danger\">";
    protected static final String TD = "<td>";
    protected static final String TD_CLOSE = "</td>";
    protected static final String CODE = "<code>";
    protected static final String CODE_CLOSE = "</code>";
    protected static final String H3 = "<h3>";
    protected static final String H3_CLOSE = "</h3>";
    private static final String FILE_NAME = "File name: ";

    public static String generateHtml(DisplayMode mode, List<GeneralCommitLine> lines, CommitInfo commitInfo) throws IOException {
        HtmlGenerator htmlGenerator;
        switch (mode) {
            case SPLIT:
                htmlGenerator = new SplitModeHtmlGenerator();
                break;
            case UNIFIED:
                htmlGenerator = new UnifiedModeHtmlGenerator();
                break;
            default:
                throw new IOException("Invalid display mode");
        }
        return htmlGenerator.generateHtml(lines, commitInfo);
    }

    protected String getPayloadHtml(GeneralCommitLine commitLine) throws IOException {
        String htmlText = "";

        switch (commitLine.getStatus()) {
            case ADDED:
                htmlText += TD_SUCCESS;
                break;
            case REMOVED:
                htmlText += TD_DANGEROUS;
                break;
            case DEFAULT:
                htmlText += TD;
                break;
            default:
                throw new IOException("Invalid commit line status");
        }

        return htmlText;
    }

    private String generateHtml(List<GeneralCommitLine> lines, CommitInfo commitInfo) throws IOException {
        String htmlText = "";

        htmlText += HTML_HEADER;
        htmlText += getCommitInformationHtml(commitInfo);
        htmlText += TABLE_PREFIX;
        htmlText += getBody(lines);
        htmlText += HTML_FOOTER;

        return htmlText;
    }

    private String getCommitInformationHtml(CommitInfo commitInfo) {
        String htmlText = "";

        htmlText += H3;
        htmlText += commitInfo.getAuthor().getName();
        htmlText += H3_CLOSE;

        htmlText += H3;
        htmlText += commitInfo.getDate();
        htmlText += H3_CLOSE;

        htmlText += H3;
        htmlText += commitInfo.getSubject();
        htmlText += H3_CLOSE;

        htmlText += H3;
        htmlText += FILE_NAME + commitInfo.getFileName();
        htmlText += H3_CLOSE;

        return htmlText;
    }

    abstract protected String getBody(List<GeneralCommitLine> lines) throws IOException;
}
