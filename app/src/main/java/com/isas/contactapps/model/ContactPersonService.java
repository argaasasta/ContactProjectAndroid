package com.isas.contactapps.model;

import javax.inject.Inject;

/**
 * Created by EB-NB21 on 2/17/2017.
 */

public class ContactPersonService {
    private ContactPerson person;

    @Inject
    public ContactPersonService(ContactPerson person) {
        this.person = person;
    }

    public void addNewContactPerson(String id, String firstName, String lastName,String profPic,String url) {
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setProfilePic(profPic);
        person.setUrl(url);
    }

    public ContactPerson getContactPerson() {
        return person;
    }
}
