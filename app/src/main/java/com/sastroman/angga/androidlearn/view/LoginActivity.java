package com.sastroman.angga.androidlearn.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.app.AppConfig;
import com.sastroman.angga.androidlearn.app.AppController;
import com.sastroman.angga.androidlearn.helper.SQLiteHandler;
import com.sastroman.angga.androidlearn.helper.SessionManager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private SessionManager session;
    private SQLiteHandler db;

    @ViewById
    EditText email, password;

    @ViewById
    Button btnLogin, btnRegister;

    @ViewById
    ImageView iv, iv2;

    @AfterViews
    void afterViews(){

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // SQLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, MainActivity_.class);
            startActivity(intent);
            finish();
        }

    }


    @Click
    void btnLogin(){
        String em = email.getText().toString().trim();
        String pw = password.getText().toString().trim();

        // Check for empty data in the form
        if (!em.isEmpty() && !pw.isEmpty()) {
            // login user
            checkLogin(em, pw);
        } else {
            // Prompt user to enter credentials
            Toast.makeText(getApplicationContext(),
                    "Please enter the credentials!", Toast.LENGTH_LONG)
                    .show();
        }
    }

    @Click
    void btnRegister(){
        Intent intent = new Intent(this, RegisterActivity_.class);
        startActivity(intent);
    }

    @Click
    void iv(){
        password.setTransformationMethod(null);
        iv2.setVisibility(View.VISIBLE);
        iv.setVisibility(View.GONE);
    }

    @Click
    void iv2(){
        password.setTransformationMethod(new PasswordTransformationMethod());
        iv.setVisibility(View.VISIBLE);
        iv2.setVisibility(View.GONE);
    }
    /**
     * function to verify login details in mysql db
     * */
    private void checkLogin(final String em, final String pw) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                if (response.isEmpty() || response.equals("")){
                    Toast.makeText(getApplicationContext(), " Server is currently unavailable. Please try again later!",
                            Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");

                        // Check for error node in json
                        if (!error) {
                            // user successfully logged in
                            // Create login session
                            session.setLogin(true);

                            // Now store the user in SQLite
                            String uid = jObj.getString("uid");

                            JSONObject user = jObj.getJSONObject("user");
                            String name = user.getString("name");
                            String email = user.getString("email");
                            String address = user.getString("address");
                            String phone = user.getString("phone");
                            String created_at = user.getString("created_at");

                            // Inserting row in users table
                            db.addUser(name, email, address, phone, uid, created_at);

                            // Launch main activity
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity_.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("error_msg");
                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", em);
                params.put("password", pw);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
