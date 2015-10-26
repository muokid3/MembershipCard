package com.loyalty.cardplanet.membershipcard;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;


public class RegisterActivity extends ActionBarActivity implements View.OnClickListener{
    Button reg;
    EditText Uname, Uemail, Uphone;
    UserLocalStore userLocalStore;
    //String account = "100100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userLocalStore = new UserLocalStore(this);

        reg = (Button)findViewById(R.id.btnRegister);
        Uname = (EditText)findViewById(R.id.name);
        Uemail = (EditText)findViewById(R.id.email);
        Uphone = (EditText) findViewById(R.id.phone);



        reg.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authenticate()==false)
        {
            startActivity(new Intent(RegisterActivity.this, MerchantLogin.class));
        }

    }

    private boolean authenticate()
    {
        return userLocalStore.getBoolUserLoggedIn();
    }

    @Override
    public void onClick(View v)
    {
        User loggedInUser = userLocalStore.getLoggedInUser();

        switch (v.getId())
        {
            case R.id.btnRegister:
                String name = Uname.getText().toString();
                String email = Uemail.getText().toString();
                String phone = Uphone.getText().toString();

                String merchName = loggedInUser.merchName;


                Random r = new Random();
                int pin = r.nextInt(9999 - 1000) + 1000;
                String pinn = String.valueOf(pin).toString();

                if (validateEditTexts())
                {
                    User user = new User(name, email, phone, pinn, merchName);
                    registerUser(user);
                    break;
                }


        }

    }

    private boolean validateEditTexts()
    {
        boolean valid = true;

        if(Uname.getText().toString().trim().equals(""))
        {
            Uname.setError("Please provide your name");
            valid = false;
        }

        if(Uemail.getText().toString().trim().equals(""))
        {
            Uemail.setError("Please provide your E-mail Address");
            valid = false;
        }

        if(Uphone.getText().toString().trim().equals(""))
        {
            Uphone.setError("Please provide your Phone Number");
            valid = false;
        }

        return valid;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerUser(User user)
    {
        ServerRequests serverRequests =new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                Intent writeCard =new Intent (RegisterActivity.this, WriteCardActivity.class);
                writeCard.putExtra("account", commons.account);
                startActivity(writeCard);
            }
        });
    }


}
