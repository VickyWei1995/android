package com.example.broadcastbestpractice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.prefs.PreferenceChangeEvent;

public class LoginActivity extends BasicActivity {
    private EditText accountText;
    private EditText passwordText;
    private Button login_button;
    private SharedPreferences reader;
    private SharedPreferences.Editor editor;
    private CheckBox remember_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        accountText = (EditText) findViewById(R.id.account);
        passwordText = (EditText) findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.login_button);
        reader = PreferenceManager.getDefaultSharedPreferences(this);
        remember_pass = (CheckBox) findViewById(R.id.remember_pass);
        boolean isRemember = reader.getBoolean("remember_pass", false);
        if(isRemember) {
            String account = reader.getString("account", "");
            String password = reader.getString("password", "");
            accountText.setText(account);
            passwordText.setText(password);
            remember_pass.setChecked(true);
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                String account = accountText.getText().toString();
                String password = passwordText.getText().toString();
                // log in successfully, start main activity
                if (account.equals("weiyu") && password.equals("123456")) {
                    editor = reader.edit();
                    // ass.isChecked() to check if the checkBox is selected to true
                    if (remember_pass.isChecked()) {
                        editor.putBoolean("remember_pass", true);
                        editor.putString("account", account);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
