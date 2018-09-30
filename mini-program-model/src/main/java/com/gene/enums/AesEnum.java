package com.gene.enums;

public enum AesEnum {

    //
    FIRST("AES/CBC/PKCS5Padding", "fc07d382b5475798", "b5475798f71dc641", "20160926"),
    //
    SECOND("AES/CBC/PKCS5Padding", "fc07d382b5475799", "b5475798f71dc642", "20160929");

    private String cipherMode;
    private String key;
    private String iv;
    private String date;

    AesEnum(String cipherMode, String key, String iv, String date) {
        this.cipherMode = cipherMode;
        this.key = key;
        this.iv = iv;
        this.date = date;
    }

    public static void main(String[] args) {
        AesEnum a = valueOf("RED");
        System.out.println(a.getCipherMode());
        AesEnum b = valueOf("GREEN");
        System.out.println(b.getCipherMode());
    }

    public String getCipherMode() {
        return this.cipherMode;
    }

    public void setCipherMode(String cipherMode) {
        this.cipherMode = cipherMode;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getIv() {
        return this.iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
