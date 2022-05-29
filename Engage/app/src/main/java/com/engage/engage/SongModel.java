package com.engage.engage;

public class SongModel {
    String image,link,name;

    public SongModel(String image, String link, String name) {
        this.image = image;
        this.link = link;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
