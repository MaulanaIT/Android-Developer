package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

public class SignupActivity extends AppCompatActivity {

    Button
            btnDaftar;

    TextView
            btnMasuk;

    EditText
            inputUsername,
            inputEmail,
            inputPassword,
            inputConfPassword;

    ProgressBar
            progressBar;

    private static final String
            DB_URL = "https://fantastix.id/register.php";
//              DB_URL = "http://192.168.1.13/register.php";

    RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnDaftar = findViewById(R.id.btn_daftar);
        btnMasuk = findViewById(R.id.btn_masuk);

        inputUsername = findViewById(R.id.inputUsername);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfPassword = findViewById(R.id.inputConfPassword);

        progressBar = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        btnMasuk.setOnClickListener(masuk);
        btnDaftar.setOnClickListener(daftar);
    }

    private View.OnClickListener masuk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            finish();
        }
    };

    private View.OnClickListener daftar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            register();
        }
    };

    private void register() {
        final String username = inputUsername.getText().toString().trim();
        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();
        final String confirmpassword = inputConfPassword.getText().toString().trim();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (username.equals("") && email.equals("") && password.equals("") && confirmpassword.equals("")) {
            Toast.makeText(SignupActivity.this, "Please fill up data", Toast.LENGTH_SHORT).show();
        } else if (username.length() < 6) {
            Toast.makeText(SignupActivity.this, "Username at least 8 or more character", Toast.LENGTH_SHORT).show();
        } else if (!email.matches(emailPattern)) {
            Toast.makeText(SignupActivity.this, "Enter valid email address", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 6) {
            Toast.makeText(SignupActivity.this, "Password at least 6 or more character", Toast.LENGTH_SHORT).show();
        } else if (!confirmpassword.equals(password)) {
            Toast.makeText(SignupActivity.this, "Enter confirmation password correctly", Toast.LENGTH_SHORT).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            btnDaftar.setVisibility(View.GONE);
            btnMasuk.setVisibility(View.GONE);
            stringRequest = new StringRequest(Request.Method.POST, DB_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.contains("Success")) {
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                                Toast.makeText(SignupActivity.this, "Register Succesful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                finish();
                            } else if (response.contains("Failed")) {
                                Toast.makeText(SignupActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                            } else if (response.contains("Username Error")) {
                                Toast.makeText(SignupActivity.this, "Username Already Taken", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                            } else if (response.contains("Email Error")) {
                                Toast.makeText(SignupActivity.this, "Email Already Registered", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                            } else {
                                Toast.makeText(SignupActivity.this, "Check Error...", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(SignupActivity.this, "" + error, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btnDaftar.setVisibility(View.VISIBLE);
                            btnMasuk.setVisibility(View.VISIBLE);
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Username", username);
                    params.put("Email", email);
                    params.put("Password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
    }
}
