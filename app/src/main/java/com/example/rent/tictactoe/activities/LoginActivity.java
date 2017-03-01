package com.example.rent.tictactoe.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rent.tictactoe.R;
import com.example.rent.tictactoe.User.User;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.asyncTasks.LoginAsyncTask;

public class LoginActivity extends AppCompatActivity implements LoginAsyncTask.OnLoggedInListener {

    Button registerButton;
    Button loginButton;
    EditText userNameEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = (Button) findViewById(R.id.activity_login_register_Button);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        userNameEditText = (EditText) findViewById(R.id.activity_login_userName);
        passwordEditText = (EditText) findViewById(R.id.activity_login_password);

        loginButton = (Button) findViewById(R.id.activity_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });
    }

    private void logIn() {
        String userName = userNameEditText.getText().toString();
        String password = passwordEditText.getText().toString();


        if (!(userNameEditText.getText().equals(null)||userNameEditText.getText().equals(""))){
            if(!(passwordEditText.getText().equals(null)||passwordEditText.getText().equals(""))){
                new LoginAsyncTask(this).execute(new User(userName,password));
            }
        }

    }


    @Override
    public void onLoggedIn(Boolean result) {
        if (result==true){
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
        }

        else  Toast.makeText(this, "Ooops!",Toast.LENGTH_LONG).show();

    }
}
