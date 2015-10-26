package com.loyalty.cardplanet.membershipcard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by muoki on 8/14/2015.
 */
public class ServerRequests {
    ProgressDialog progressDialog, purchaseDialog, confirmDialog, sendMessageDialog, resetPinMessage;
    public static final int CONNECTION_TIMEOUT = 1000*20;
    public static final String SERVER_ADDRESS = "http://loyalty.cardplanetsolutions.co.ke/";


    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please Wait...");

        confirmDialog = new ProgressDialog(context);
        confirmDialog.setCancelable(false);
        confirmDialog.setTitle("Confirming");
        confirmDialog.setMessage("Please Wait...");

        sendMessageDialog = new ProgressDialog(context);
        sendMessageDialog.setCancelable(false);
        sendMessageDialog.setTitle("Sending Message");
        sendMessageDialog.setMessage("Please Wait...");

        resetPinMessage = new ProgressDialog(context);
        resetPinMessage.setCancelable(false);
        resetPinMessage.setTitle("Resetting PIN");
        resetPinMessage.setMessage("Please Wait...");
    }

    public void storeUserDataInBackground(User user, GetUserCallBack userCallBack)
    {
        progressDialog.show();
        new StoreUserDataAsyncTask(user, userCallBack).execute();
    }



    public void fetchUserDataInBackground(User user, GetUserCallBack callBack)
    {
        progressDialog.show();
        new fetchUserDataAsyncTask(user, callBack).execute();
    }

    public void fetchMerchantDataInBackground(User user, GetUserCallBack callBack)
    {
        progressDialog.show();
        new fetchMerchantDataAsyncTask(user, callBack).execute();
    }

    public void confirmUserPurchaseInBackground(User user, GetUserCallBack callBack)
    {
        confirmDialog.show();
        new confirmUserPurchaseAsyncTask(user, callBack).execute();
    }

    public void confirmUserCashPurchaseInBackground(User user, GetUserCallBack callBack)
    {
        confirmDialog.show();
        new confirmUserCashPurchaseAsyncTask(user, callBack).execute();
    }

    public void confirmUserRedeemInBackground(User user, GetUserCallBack callBack)
    {
        confirmDialog.show();
        new confirmUserRedeemAsyncTask(user, callBack).execute();
    }

    public void changeUserPinInBackground(Pin PinUser, GetPinCallback callBack)
    {
        progressDialog.show();
        new confirmUserUserChangePinAsyncTask(PinUser, callBack).execute();
    }

    public void sendResetCodeInBackground(User user, GetUserCallBack callBack)
    {
        sendMessageDialog.show();
        new confirmSendResetMessageAsyncTask(user, callBack).execute();
    }

    public void resetUserPinInBackground(User user, GetUserCallBack callBack)
    {
        resetPinMessage.show();
        new confirmUserResetPinMessageAsyncTask(user, callBack).execute();
    }

    public void sendPointsBalanceInBackground(User user, GetUserCallBack callBack)
    {
        sendMessageDialog.show();
        new confirmSendPointsBalanceAsyncTask(user, callBack).execute();
    }


    public class StoreUserDataAsyncTask extends AsyncTask<Void,Void,Void>
    {
        User user;
        GetUserCallBack userCallBack;

        public StoreUserDataAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }


        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", user.name));
            dataToSend.add(new BasicNameValuePair("email", user.email));
            dataToSend.add(new BasicNameValuePair("pin", user.stringPin));
            dataToSend.add(new BasicNameValuePair("phone", user.phone));
            dataToSend.add(new BasicNameValuePair("merchName", user.merchName));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "Register.php");

            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse res = client.execute(post);
                String str =  EntityUtils.toString(res.getEntity());
                commons.account = str;

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            userCallBack.done(null);
            super.onPostExecute(aVoid);
        }
    }





    public class fetchUserDataAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public fetchUserDataAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("pin", user.intPin+""));
            dataToSend.add(new BasicNameValuePair("account", user.account+""));
            dataToSend.add(new BasicNameValuePair("amount", user.amount+""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "AuthUserData.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String name = jObject.getString("name");


                /*if (status.equals("success"))
                {*/
                    //succss
                    returnedUser = new User(user.account, user.intPin, user.amount, name, status);
                //}


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }



    }




    public class confirmUserPurchaseAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmUserPurchaseAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("pin", user.intPin+""));
            dataToSend.add(new BasicNameValuePair("account", user.account+""));
            dataToSend.add(new BasicNameValuePair("amount", user.amount+""));
            dataToSend.add(new BasicNameValuePair("merchName", user.merchName));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "CompletePurchase.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String name = jObject.getString("name");


                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(user.account, user.intPin, user.amount, name, status);
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            confirmDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }



    public class confirmUserCashPurchaseAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmUserCashPurchaseAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("pin", user.intPin+""));
            dataToSend.add(new BasicNameValuePair("account", user.account+""));
            dataToSend.add(new BasicNameValuePair("amount", user.amount+""));
            dataToSend.add(new BasicNameValuePair("merchName", user.merchName));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "CompleteCashPurchase.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String name = jObject.getString("name");


                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(user.account, user.intPin, user.amount, name, status);
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            confirmDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }



    public class confirmUserRedeemAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmUserRedeemAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("pin", user.intPin+""));
            dataToSend.add(new BasicNameValuePair("account", user.account+""));
            dataToSend.add(new BasicNameValuePair("amount", user.amount+""));
            dataToSend.add(new BasicNameValuePair("merchName", user.merchName));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "CompleteRedeem.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String name = jObject.getString("name");

                /*if (status == "pinFail")
                {
                    returnedUser = null;
                }*/
                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(user.account, user.intPin, user.amount, name, status);
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            confirmDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }



    public class confirmUserUserChangePinAsyncTask extends AsyncTask<Void,Void,Pin> {
        Pin pinUser;
        GetPinCallback userCallBack;

        public confirmUserUserChangePinAsyncTask(Pin pinUser, GetPinCallback userCallBack) {
            this.pinUser = pinUser;
            this.userCallBack = userCallBack;

        }

        @Override
        protected Pin doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("oldpin", pinUser.oldPin+""));
            dataToSend.add(new BasicNameValuePair("newpin", pinUser.newPin+""));
            dataToSend.add(new BasicNameValuePair("account", pinUser.account+""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "ChangePin.php");

            Pin returnedPinUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String oldpin = jObject.getString("oldpin");
                String newpin = jObject.getString("newpin");
                String account = jObject.getString("account");
                String confpin = jObject.getString("confpin");

                if (status.equals("success"))
                {
                    //succss
                    returnedPinUser = new Pin(oldpin, newpin, confpin,
                            Integer.parseInt(account));
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedPinUser;
        }
        @Override
        protected void onPostExecute(Pin returnedPinUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedPinUser);
            super.onPostExecute(returnedPinUser);
        }



    }



    public class confirmSendResetMessageAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmSendResetMessageAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("account", user.account+""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "ResetPin.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");

                String account = jObject.getString("account");

                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(Integer.parseInt(account));
                }


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            sendMessageDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }

    }



    public class confirmUserResetPinMessageAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmUserResetPinMessageAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("account", user.account+""));
            dataToSend.add(new BasicNameValuePair("pinResetCode", user.pinResetCode+""));
            dataToSend.add(new BasicNameValuePair("newResetPin", user.newResetPin+""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "CompleteResetPin.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");
                String name = jObject.getString("name");

                /*if (status == "pinFail")
                {
                    returnedUser = null;
                }*/
                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(user.account, user.intPin, user.amount, name, status);
                }

               /* if (jObject.length() == 0)
                {
                    returnedUser = null;
                }
                else
                {*/
                   /* String name = jObject.getString("name");
                    String email = jObject.getString("email");*/

                  /*  returnedUser = new User(user.account, user.intPin, user.amount);


                }*/
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            resetPinMessage.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }


    public class confirmSendPointsBalanceAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public confirmSendPointsBalanceAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("account", user.account+""));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "SendPointsBalance.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                String status = jObject.getString("status");

                String account = jObject.getString("account");

                if (status.equals("success"))
                {
                    //succss
                    returnedUser = new User(Integer.parseInt(account));
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            sendMessageDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }

    }



    public class fetchMerchantDataAsyncTask extends AsyncTask<Void,Void,User> {
        User user;
        GetUserCallBack userCallBack;

        public fetchMerchantDataAsyncTask(User user, GetUserCallBack userCallBack) {
            this.user = user;
            this.userCallBack = userCallBack;

        }

        @Override
        protected User doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("username", user.merchUsername));
            dataToSend.add(new BasicNameValuePair("password", user.merchPassword));

            HttpParams httpRequestParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient(httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "FetchMerchantData.php");

            User returnedUser = null;
            try
            {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0)
                {
                    returnedUser = null;
                }
                else
                {
                    String name = jObject.getString("name");
                    String email = jObject.getString("email");
                    String password = jObject.getString("password");

                    returnedUser = new User(name, email, password);


                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return returnedUser;
        }
        @Override
        protected void onPostExecute(User returnedUser) {
            progressDialog.dismiss();
            userCallBack.done(returnedUser);
            super.onPostExecute(returnedUser);
        }
    }





}
