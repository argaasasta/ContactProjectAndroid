package com.isas.contactapps.db.util;


import com.isas.contactapps.db.contract.Contract;
import com.isas.contactapps.db.contract.Contracts;

abstract class BaseReqisterTable {
    private Contracts mContracts;

    final protected void add(Contract contract){
        mContracts.add(contract);
    }

    protected BaseReqisterTable(){
        mContracts= new Contracts();
        addContract();
    }

    protected abstract void addContract();

    final public Contracts getContracts(){
        return mContracts;
    }

}
