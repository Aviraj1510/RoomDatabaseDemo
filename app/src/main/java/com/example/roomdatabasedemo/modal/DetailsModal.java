package com.example.roomdatabasedemo.modal;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "form_table")
public class DetailsModal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String Name;

    private String Email;

    private String PhoneNumber;


    public DetailsModal(String Name, String Email, String PhoneNumber){
        this.Name = Name;
        this.Email = Email;
        this.PhoneNumber = PhoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
