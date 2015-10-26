package com.loyalty.cardplanet.membershipcard;

/**
 * Created by muoki on 9/21/2015.
 */
public class Redeem {
    int points, pin, account;
    public Redeem(int points, int pin, int account)
    {
        this.account = account;
        this.pin = pin;
        this.points = points;
    }
}
