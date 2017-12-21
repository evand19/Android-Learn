package com.sastroman.angga.androidlearn.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.app.AppConfig;
import com.sastroman.angga.androidlearn.app.AppController;
import com.sastroman.angga.androidlearn.helper.SQLiteHandler;
import com.sastroman.angga.androidlearn.helper.SessionManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@EActivity (R.layout.activity_register)
public class RegisterActivity extends Activity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;
    boolean  n, e, p2, p, a, h = false;
    private DatabaseReference mDatabase;

    @ViewById
    Button btnRegister, btnLogin;

    @ViewById
    EditText name, email, password, password2, address, phone;

    @ViewById
    TextInputLayout input_layout_name,input_layout_email,input_layout_address,
            input_layout_phone,input_layout_password,input_layout_password2;

    @Click
    void btnRegister(){
        String nm = name.getText().toString().trim();
        String em = email.getText().toString().trim();
        String ad = address.getText().toString().trim();
        String ph = phone.getText().toString().trim();
        String pw = password.getText().toString().trim();
        String pw2 = password2.getText().toString().trim();

        if (e && n && p && a && h && p2) {
                registerUser(nm, em, pw, ad, ph);
                Log.i(TAG, "Register Data: " + nm + " " + em + " " + pw
                        + " " + ad + " " + ph);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please enter or fix your details!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Click
    void btnLogin(){
        Intent i = new Intent(getApplicationContext(),
                LoginActivity_.class);
        startActivity(i);
        finish();
    }

    @AfterViews
    void afterviews() {
        // ...
        mDatabase = FirebaseDatabase.getInstance().getReference();


        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(RegisterActivity.this,
                    MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @FocusChange
    void email(){
        if (hasWindowFocus()){
            if (!email.getText().toString().equals("")){
                if(!email.getText().toString().contains("@")){
                    input_layout_email.setError(getString(R.string.hint_err_email));
                    e = false;
                } else {
                    e = true;
                    input_layout_email.setErrorEnabled(false);
                }
            }

        }

    }

    @FocusChange
    void name(){
        if (hasWindowFocus()){
            if (!name.getText().toString().equals("")) {
                if (name.getText().toString().length() < 6) {
                    n = false;
                    input_layout_name.setError(getString(R.string.hint_err_name));
                } else {
                    n = true;
                    input_layout_name.setErrorEnabled(false);
                }
            }
        }

    }

    @FocusChange
    void address(){
        if (hasWindowFocus()){
            if (!address.getText().toString().equals("")){
                if(address.getText().toString().length() < 5){
                    a = false;
                    input_layout_address.setError(getString(R.string.hint_err_address));
                } else {
                    a = true;
                    input_layout_address.setErrorEnabled(false);
                }
            }

        }

    }

    @FocusChange
    void phone(){
        if (hasWindowFocus()){
            if (!phone.getText().toString().equals("")){
                if(phone.getText().toString().length() < 10){
                    h = false;
                    input_layout_phone.setError(getString(R.string.hint_err_phone));
                } else {
                    h = true;
                    input_layout_phone.setErrorEnabled(false);
                }
            }

        }

    }

    @FocusChange
    void password(){
        if (hasWindowFocus()){
            if (!password.getText().toString().equals("")){
                if(password.getText().toString().length() < 6){
                    p = false;
                    input_layout_password.setError(getString(R.string.hint_err_password));
                }else {
                    p = true;
                    input_layout_password.setErrorEnabled(false);
                }

            }
        }

    }

    @FocusChange
    void password2(){
        if (hasWindowFocus()){
            if (!password2.getText().toString().equals("")){
                if(!password2.getText().toString().trim().equals(password.getText().toString().trim())){
                    p2 = false;
                    input_layout_password2.setError(getString(R.string.hint_err_password2));
                }else {
                    p2 = true;
                    input_layout_password2.setErrorEnabled(false);
                }

            }
        }

    }
    /**
     * Function to store user in MySQL database will post params(tag, name,
     * email, password) to register url
     * */
    private void registerUser(final String nm, final String em,
                              final String pw, final String ad,
                              final String ph) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";

        Log.i(TAG, "Register Data Sent: " + nm + " " + em + " " + pw
                + " " + ad + " " + ph);

        pDialog.setMessage("Registering ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_REGISTER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String address = user.getString("address");
                        String phone = user.getString("phone");
                        String created_at = user.getString("created_at");

                        // Inserting row in users table
                        db.addUser(name, email, address, phone, uid, created_at);

                        Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(
                                RegisterActivity.this,
                                LoginActivity_.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        " An error occured when registering. Please try again later.", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", nm);
                params.put("email", em);
                params.put("password", pw);
                params.put("address", ad);
                params.put("phone", ph);

                return params;
            }

        };

        // Adding request to request queue
        AppController .getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}