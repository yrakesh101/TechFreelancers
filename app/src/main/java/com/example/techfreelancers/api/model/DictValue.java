package com.example.techfreelancers.api.model;

public class DictValue {

    /**
     * Dict value id
     */
    private int dictValueId;

    /**
     * Dict value code
     */
    private String dictValueCode;

    /**
     * Dict value name
     */
    private String dictValueName;

    /**
     * Dict key id
     */
    private int dictKeyId;

    /**
     * Dict value name
     */
    private String note;

    public DictValue(String dictValueName) {
        this.dictValueName = dictValueName;
    }

    public int getDictValueId() {
        return dictValueId;
    }

    public void setDictValueId(int dictValueId) {
        this.dictValueId = dictValueId;
    }

    public String getDictValueCode() {
        return dictValueCode;
    }

    public void setDictValueCode(String dictValueCode) {
        this.dictValueCode = dictValueCode;
    }

    public String getDictValueName() {
        return dictValueName;
    }

    public void setDictValueName(String dictValueName) {
        this.dictValueName = dictValueName;
    }

    public int getDictKeyId() {
        return dictKeyId;
    }

    public void setDictKeyId(int dictKeyId) {
        this.dictKeyId = dictKeyId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
