package com.jluo80.amazinggifter;

/**
 * Created by Jiahao on 7/2/2016.
 */
public class Gift {
    private String itemId;
    private String itemTitle;
    private String itemUrl;
    private String galleryUrl;
    private String currentPrice;

    public Gift() {}

    public Gift(String itemId, String itemTitle, String currentPrice, String galleryUrl, String itemUrl) {
        this.itemId = itemId;
        this.itemTitle = itemTitle;
        this.currentPrice = currentPrice;
        this.galleryUrl = galleryUrl;
        this.itemUrl = itemUrl;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemTitle(){
        return itemTitle;
    }

    public void setItemTitle(String itemTitle){
        this.itemTitle = itemTitle;
    }

    public String getCurrentPrice(){
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice){
        this.currentPrice = currentPrice;
    }

    public String getGalleryUrl(){
        return galleryUrl;
    }

    public void setGalleryUrl(String galleryUrl){
        this.galleryUrl = galleryUrl;
    }

    public String getItemUrl(){
        return itemUrl;
    }

    public void setItemUrl(String itemUrl){
        this.itemUrl = itemUrl;
    }
}