package com.isas.contactapps.db.util;


import com.isas.contactapps.db.contract.ContactContract;

public class RegisterTable extends BaseReqisterTable {


    public static  RegisterTable getInstance() {
        return new RegisterTable();
    }

    private RegisterTable() {
        super();
    }

    @Override
    protected void addContract() {

        ContactContract contactContract = new ContactContract();
        add(contactContract);

    }
}
