package com.loyalty.cardplanet.membershipcard;

/**
 * Created by muoki on 9/17/2015.
 */
public class Purchase {
    int amount, pin, account;
     public Purchase(int amount, int pin, int account)
     {
         this.account = account;
         this.pin = pin;
         this.amount = amount;
     }
}
