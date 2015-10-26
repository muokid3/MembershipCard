package com.loyalty.cardplanet.membershipcard;

/**
 * Created by muoki on 9/21/2015.
 */
public class Pin {
    String oldPin, newPin, confPin, status;
    int account;
    public Pin(String oldPin, String newPin, String confPin, int account)
    {
        this.oldPin = oldPin;
        this.newPin = newPin;
        this.confPin = confPin;
        this.account = account;
    }

}
