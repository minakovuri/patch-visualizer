package org.volgatech.patchvisualizer.app.model;

public class CommitInfo {
    private String commit;
    private String author;
    private String date;
    private String subject;
    private String fileName;

    public String getCommit() { return commit; }

    public void setCommit(String commit) { this.commit = commit; }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
