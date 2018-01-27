package com.cross.pojo;

public class ManageMoneyPassage {
    private int id;
    private String passageTitle;
    private String passageImg;
    private String passageContent;
    public ManageMoneyPassage(){

    }

    public ManageMoneyPassage( String passageTitle, String passageImg, String passageContent) {

        this.passageTitle = passageTitle;
        this.passageImg = passageImg;
        this.passageContent = passageContent;
    }

    public ManageMoneyPassage(int id, String passageTitle, String passageImg, String passageContent) {
        this.id = id;
        this.passageTitle = passageTitle;
        this.passageImg = passageImg;
        this.passageContent = passageContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassageTitle() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle = passageTitle;
    }

    public String getPassageImg() {
        return passageImg;
    }

    public void setPassageImg(String passageImg) {
        this.passageImg = passageImg;
    }

    public String getPassageContent() {
        return passageContent;
    }

    public void setPassageContent(String passageContent) {
        this.passageContent = passageContent;
    }
}
