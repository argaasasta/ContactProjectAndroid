package com.isas.contactapps.model;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import java.io.File;

/**
 * Created by EB-NB21 on 2/14/2017.
 */
public class ContactPerson extends BaseObservable{

    private String id;
    private String first_name;
    private String last_name;
    private String profile_pic;
    private File profile_pic_file;
    private String url;

    public ContactPerson() {
    }

    public ContactPerson(String id, String first_name, String last_name, String profile_pic, String url) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_pic = profile_pic;
        this.url = url;
    }
    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Bindable
    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    @Bindable
    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    @Bindable
    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    @Bindable
    public File getProfile_pic_file() {
        return profile_pic_file;
    }

    public void setProfile_pic_file(File profile_pic_file) {
        this.profile_pic_file = profile_pic_file;
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
