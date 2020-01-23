package org.volgatech.patchvisualizer.infrastructure.htmlgenerator;

import org.volgatech.patchvisualizer.app.model.CommitInfo;
import org.volgatech.patchvisualizer.app.model.OutCommitLine;
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
            "body {" +
            "   margin: 50px 200px;" +
            "   padding: 20px;" +
            "   border: 1px solid;" +
            "}" +
            "code {" +
            "   white-space: pre;" +
            "   color: black" +
            "}" +
            "table {" +
            "   margin-top: 30px;" +
            "}" +
            ".table td, .table th {" +
            "   padding: 0;" +
            "}" +
            "</style>" +
            "  </head>" +
            "  <body>";

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
    protected static final String DIV = "<div>";
    protected static final String DIV_CLOSE = "</div>";

    public static String generateHtml(DisplayMode mode, List<OutCommitLine> lines, CommitInfo commitInfo) throws IOException {
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

    protected String getPayloadHtml(OutCommitLine commitLine) throws IOException {
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

        htmlText += CODE;
        htmlText += commitLine.getText();
        htmlText += CODE_CLOSE;
        htmlText += TD_CLOSE;

        return htmlText;
    }

    private String generateHtml(List<OutCommitLine> lines, CommitInfo commitInfo) throws IOException {
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

        htmlText += DIV;
        htmlText += commitInfo.getCommit();
        htmlText += DIV_CLOSE;

        htmlText += DIV;
        htmlText += commitInfo.getAuthor();
        htmlText += DIV_CLOSE;

        htmlText += DIV;
        htmlText += commitInfo.getDate();
        htmlText += DIV_CLOSE;

        htmlText += DIV;
        htmlText += commitInfo.getSubject();
        htmlText += DIV_CLOSE;

        htmlText += DIV;
        htmlText += "File: " + commitInfo.getFileName();
        htmlText += DIV_CLOSE;

        return htmlText;
    }

    abstract protected String getBody(List<OutCommitLine> lines) throws IOException;
}
