package com.prototype.uoh.sacchaadhaar;

/**
 * Created by kc on 30/3/17.
 */

/**
 * Created by yashu on 30/3/17.
 */

public class Student {


    int id;
    //private variables
    int sid;
    String u_id;

    String name;
    String phone_number;

    String email;


    // Empty constructor
    public Student(){

    }
    // constructor
    public Student(int id,int sid,String u_id, String name, String _phone_number,String email){

        this.id = id;
        this.sid = sid;
        this.u_id = u_id;
        this.name = name;

        this.phone_number = phone_number;
        this.email = email;

    }

    // constructor
    public Student(int sid,String u_id,String name){
        this.sid = sid;
        this.u_id = u_id;
        this.name = name;
    }





    // getting ID
    public int getID(){
        return this.id;
    }

    // setting id
    public void setID(int id){
        this.id = id;
    }






    // getting ID
    public int getSID(){
        return this.sid;
    }

    // setting id
    public void setSID(int id){
        this.sid = id;
    }





    public String getUID(){
        return this.u_id;
    }

    // setting id
    public void setUID(String uid){
        this.u_id = uid;
    }






    // getting name
    public String getName(){
        return this.name;
    }

    // setting name
    public void setName(String name){
        this.name = name;
    }

    // getting phone number
    public String getPhoneNumber(){
        return this.phone_number;
    }

    // setting phone number
    public void setPhoneNumber(String phone_number){
        this.phone_number = phone_number;
    }


    public String getEmail(){
        return this.email;
    }

    // setting name
    public void setEmail(String email){
        this.email = email;
    }

}
