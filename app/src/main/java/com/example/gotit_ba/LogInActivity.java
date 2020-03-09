package com.example.gotit_ba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;


public class LogInActivity extends AppCompatActivity {

    //Variable declarations
    Button btnSignIn;
    EditText etxtUsername, etxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //Set variables based on id from xml files
        btnSignIn = findViewById(R.id.login);
        etxtUsername = findViewById(R.id.user);
        etxtPassword = findViewById(R.id.pw);

        //On click listener for sign in button
        btnSignIn.setOnClickListener (new View.OnClickListener() {
            @Override
            public void onClick (View V) {

                final String username = etxtUsername.getText().toString();
                final String password = etxtPassword.getText().toString();
                validateUser(username, password);
            }
        });
    }



    //Function to validate user
    private void validateUser(String username, String pw) {
        final String password = pw;

        //Query to check user input against object (username)
        ParseQuery<ParseObject> userQuery = ParseQuery.getQuery("InternalUser");
        userQuery.whereEqualTo("inu_username", username);
        userQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (object == null) {
                    Toast.makeText(LogInActivity.this, "Username is incorrect", Toast.LENGTH_LONG).show();
                } else {
                    ParseQuery<ParseObject> pwQuery = ParseQuery.getQuery("InternalUser");
                    pwQuery.whereEqualTo("inu_password", password);
                    pwQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (object == null) {
                                Toast.makeText(LogInActivity.this, "Password is incorrect", Toast.LENGTH_LONG).show();
                            } else {
                                goHomeActivity();
                            }
                        }
                    });
                }
            }
        });
    }

/*
        //Query to check user input against object (password)
        ParseQuery<ParseObject> pwordQuery = ParseQuery.getQuery("InternalUser");
        pwordQuery.whereEqualTo("inu_password", password);

        //Do compound query to validate both username AND password for a specific object
        List<ParseQuery<ParseObject>> queries = new ArrayList<>();
        queries.add(userQuery);
        queries.add(pwordQuery);
        ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        mainQuery.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> results, ParseException e) {
                if (e == null) {
                    if (results.size() == 0) {
                        Toast.makeText(LogInActivity.this, "Invalid user credentials", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d("users", "Retrieved " + results.size() + " users");
                        goHomeActivity();
                    }

                } else {
                    Log.d("users", "Error: " + e.getMessage());

                }
            }
        }); */






    //Function to change screen from login to homepage
    private void goHomeActivity() {
        Intent intToHome = new Intent(LogInActivity.this, HomeActivity.class);
        startActivity(intToHome);
        finish();

    }
}
