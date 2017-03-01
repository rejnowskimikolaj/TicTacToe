package com.example.rent.tictactoe.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rent.tictactoe.R;
import com.example.rent.tictactoe.User.User;
import com.example.rent.tictactoe.User.UserService;
import com.example.rent.tictactoe.asyncTasks.RegisterAsyncTask;

public class RegisterActivity extends AppCompatActivity implements RegisterAsyncTask.OnRegisterListener {

    EditText userNameEditText;
    EditText password1EditText;
    EditText password2EditText;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userNameEditText = (EditText) findViewById(R.id.activity_register_userName_edit_text);
        password1EditText=(EditText) findViewById(R.id.activity_register_pass1_edit_text);
        password2EditText=(EditText) findViewById(R.id.activity_register_pass2_edit_text);
        registerButton = (Button) findViewById(R.id.activity_register_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(userNameEditText.getText().equals(null)||userNameEditText.getText().equals(""))){
                    if(password2EditText.getText().toString().equals(password1EditText.getText().toString())){
                        register();
                    }
                }
            }
        });
    }


    @Override
    public void onRegistered(Boolean result) {
        if (result==true){
            Toast.makeText(this, UserService.getInstance().getToken(),Toast.LENGTH_LONG).show();
        }

        else  Toast.makeText(this, "Ooops!",Toast.LENGTH_LONG).show();

    }

    public void register(){
        new RegisterAsyncTask(this).execute(new User(userNameEditText.getText().toString(),password1EditText.getText().toString()));
    }
}
