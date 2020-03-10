package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManagerFactory;

public class LoginActivity extends AppCompatActivity {

    Button
            btnMasuk,
            btnDaftar;

    EditText
            inputEmail,
            inputPassword;

    ProgressBar
            progressBar;

    TextView
            privacyPolicy;

    static final String
            DB_URL = "https://fantastix.id/android/login.php",
            PREFS_NAME = "MyPrefsFile";

    static int
            userLogin = 1,
            userCount;

    RequestQueue
            requestQueue;

    StringRequest
            stringRequest;

    SSLContext
            sslContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnMasuk = findViewById(R.id.btn_masuk);
        btnDaftar = findViewById(R.id.btn_daftar);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);

        privacyPolicy = findViewById(R.id.privacyPolicy);

        progressBar = findViewById(R.id.progressBar);

        requestQueue = Volley.newRequestQueue(this);

        btnDaftar.setOnClickListener(daftar);
        btnMasuk.setOnClickListener(masuk);

        SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);

        userLogin = user.getInt("isLogin", userCount);

        if (userLogin == 1) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private View.OnClickListener daftar = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        }
    };

    private View.OnClickListener masuk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            login();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void login() {
        final String email = inputEmail.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern)) {
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
                                Toast.makeText(LoginActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                                userCount = 1;
                                SharedPreferences user = getSharedPreferences(PREFS_NAME, 0);
                                SharedPreferences.Editor editor = user.edit();
                                editor.putInt("isLogin", userCount);
                                editor.commit();

                                startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                btnDaftar.setVisibility(View.VISIBLE);
                                btnMasuk.setVisibility(View.VISIBLE);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(LoginActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                            btnDaftar.setVisibility(View.VISIBLE);
                            btnMasuk.setVisibility(View.VISIBLE);
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("Email", email);
                    params.put("Password", password);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(LoginActivity.this, "Email is not valid", Toast.LENGTH_LONG).show();
        }
    }
}