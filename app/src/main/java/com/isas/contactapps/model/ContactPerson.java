package com.isas.contactapps.model;


import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import java.io.File;

/**
 * Created by EB-NB21 on 2/14/2017.
 */
public class ContactPerson extends BaseObservable {

    @SerializedName("id")
    private String id;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("email")
    private String email;
    @SerializedName("profile_pic")
    private String profilePic;
    @SerializedName("profile_pic_file")
    private File profilePicFile;
    @SerializedName("url")
    private String url;

    public ContactPerson() {
    }
    @Inject
    public ContactPerson(String id, String firstName, String lastName, String profilePic, String url) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePic = profilePic;
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
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Bindable
    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Bindable
    public File getProfilePicFile() {
        return profilePicFile;
    }

    public void setProfilePicFile(File profilePicFile) {
        this.profilePicFile = profilePicFile;
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bindable
    public String getFullname() {
        return firstName + " " + lastName;
    }

    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Bindable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
