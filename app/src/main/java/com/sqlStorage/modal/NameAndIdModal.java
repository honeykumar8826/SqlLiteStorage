package com.sqlStorage.modal;

public class NameAndIdModal {
    private String name;

    public NameAndIdModal(String id, String name) {
        this.name = name;
        this.id = id;
    }

    private String id;
    public String getName() {
        return name;
    }

// --Commented out by Inspection START (15/3/19 11:27 PM):
//    public void setName(String name) {
//        this.name = name;
//    }
// --Commented out by Inspection STOP (15/3/19 11:27 PM)

    public String getId() {
        return id;
    }

// --Commented out by Inspection START (15/3/19 11:26 PM):
//    public void setId(String id) {
//        this.id = id;
//    }
// --Commented out by Inspection STOP (15/3/19 11:26 PM)


}
