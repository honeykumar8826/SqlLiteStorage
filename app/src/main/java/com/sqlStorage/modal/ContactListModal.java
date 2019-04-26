package com.sqlStorage.modal;

public class ContactListModal {
    private String name, contact;

    public ContactListModal(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }
}
