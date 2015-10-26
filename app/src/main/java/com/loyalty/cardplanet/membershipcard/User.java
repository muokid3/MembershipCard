package com.loyalty.cardplanet.membershipcard;

/**
 * Created by muoki on 8/14/2015.
 */
public class User {
    String name, email, phone, status, username, password, merchName, merchUsername, merchPassword;
    int account;
    String stringPin, pinResetCode;
    int intPin, amount, newResetPin;

    public User(String name, String email, String phone, String pin, String merchName)
    {
        this.name = name;
        this.email = email;
        this.stringPin = pin;
        this.phone = phone;
        this.merchName = merchName;

    }
    public User(int account, int pin, int amount, String merchName)
    {
        this.account = account;
        this.intPin = pin;
        this.amount = amount;
        this.merchName = merchName;

        this.name = "";
        this.email = "";
        this.phone = "";

    }

    public User(int account, int pin, int amount, String name, String status)
    {
        this.account = account;
        this.intPin = pin;
        this.amount = amount;
        this.name = name;
        this.status = status;

        this.phone = "";

    }

    public User (int account)
    {
        this.account = account;
    }

    public User (int account, String pinResetCode, int newResetPin)
    {
        this.account = account;
        this.pinResetCode = pinResetCode;
        this.newResetPin = newResetPin;
    }

    public User(String username, String password)
    {
        this.merchUsername = username;
        this.merchPassword = password;
    }

    public User(String merchName, String merchUsername, String merchPassword)
    {
        this.merchName = merchName;
        this.merchPassword = merchPassword;
        this.merchUsername = merchUsername;
    }


}
