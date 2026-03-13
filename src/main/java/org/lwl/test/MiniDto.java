package org.lwl.test;

public class MiniDto {

    private String url;
    private String type;
    private String title;
    private String appid;
    private String sdkAppid;
    private String path;
    private String imageUrl;
    private String imageDescription;
    private String source;
    private String localPath;
    private Integer unknown1;
    private Integer unknown2;
    private Integer unknown3;

    public MiniDto() {
    }

    public MiniDto(String url, String type, String title, String appid, String sdkAppid,
                   String path, String imageUrl, String imageDescription, String source,
                   String localPath, Integer unknown1, Integer unknown2, Integer unknown3) {
        this.url = url;
        this.type = type;
        this.title = title;
        this.appid = appid;
        this.sdkAppid = sdkAppid;
        this.path = path;
        this.imageUrl = imageUrl;
        this.imageDescription = imageDescription;
        this.source = source;
        this.localPath = localPath;
        this.unknown1 = unknown1;
        this.unknown2 = unknown2;
        this.unknown3 = unknown3;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSdkAppid() {
        return sdkAppid;
    }

    public void setSdkAppid(String sdkAppid) {
        this.sdkAppid = sdkAppid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public Integer getUnknown1() {
        return unknown1;
    }

    public void setUnknown1(Integer unknown1) {
        this.unknown1 = unknown1;
    }

    public Integer getUnknown2() {
        return unknown2;
    }

    public void setUnknown2(Integer unknown2) {
        this.unknown2 = unknown2;
    }

    public Integer getUnknown3() {
        return unknown3;
    }

    public void setUnknown3(Integer unknown3) {
        this.unknown3 = unknown3;
    }

    @Override
    public String toString() {
        return "MiniDto{" +
                "url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", appid='" + appid + '\'' +
                ", sdkAppid='" + sdkAppid + '\'' +
                ", path='" + path + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageDescription='" + imageDescription + '\'' +
                ", source='" + source + '\'' +
                ", localPath='" + localPath + '\'' +
                ", unknown1=" + unknown1 +
                ", unknown2=" + unknown2 +
                ", unknown3=" + unknown3 +
                '}';
    }
}
