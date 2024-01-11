package com.example.hireconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;

import java.lang.String;

public class MainActivity extends AppCompatActivity {


    //xml attributes used
    EditText username, password;
    Button signup, login;
    RadioButton traderBtn, userBtn;
    DBHelper dbObj;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //java connected to xml
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        signup = (Button) findViewById(R.id.signUpButton);
        login = (Button) findViewById(R.id.loginButton);
        traderBtn = (RadioButton) findViewById(R.id.traderRadioButton);
        userBtn = (RadioButton) findViewById(R.id.userRadioButton);
        radioGroup = (RadioGroup)findViewById(R.id.RadioGroup);

        //database object
        dbObj = new DBHelper(this);

        //radioGroup
        radioGroup.clearCheck();
        radioGroup.setOnCheckedChangeListener(
                (group, checkedId) -> {
                    // Get the selected Radio Button
                    RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                });//onclick for radiogrp


        // onClickListener for signup Button
        signup.setOnClickListener(v -> {

            String usernameTXT = username.getText().toString();
            String passwordTXT = password.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // If no Radio Button is set, -1 will be returned
            //details not filled
            if (selectedId == -1 || TextUtils.isEmpty(usernameTXT) || TextUtils.isEmpty(passwordTXT)) {
                Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }
            //user account already exists
            else if(dbObj.validateTraderPassword(usernameTXT,passwordTXT) != -1 ){
                Toast.makeText(MainActivity.this, "Trader account already exists", Toast.LENGTH_SHORT).show();
            }
            //user account already exists
            else if(dbObj.validateUserPassword(usernameTXT,passwordTXT) != -1){
                Toast.makeText(MainActivity.this, "User account already exists", Toast.LENGTH_SHORT).show();
            }
            //new entry
            else {

                RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                //Toast.makeText(MainActivity.this, "Creating account: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                if (radioButton.getText().equals(traderBtn.getText())){
                    boolean result = dbObj.insertNotNullInTraderTable(usernameTXT, passwordTXT);
                    if (result == true) {
                        Toast.makeText(MainActivity.this, "Created account of trader" + usernameTXT, Toast.LENGTH_SHORT).show();
                        openTraderProfileCreation(usernameTXT);
                    }
                } else if (radioButton.getText().equals(userBtn.getText())) {
                    boolean result = dbObj.insertNotNullInUserTable(usernameTXT, passwordTXT);
                    if (result == true) {
                        Toast.makeText(MainActivity.this, "Created account of user " + usernameTXT, Toast.LENGTH_SHORT).show();
                        openUserProfileCreation(usernameTXT);
                    }
                } //intent for opening profile creation
            }  //action based on radiobutton selection
        });//onclicklistner for signup


        // onClickListener for login Button
        login.setOnClickListener(v -> {

            String usernameTXT = username.getText().toString();
            String passwordTXT = password.getText().toString();
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // If no Radio Button is set, -1 will be returned
            if (selectedId == -1 || TextUtils.isEmpty(usernameTXT) || TextUtils.isEmpty(passwordTXT)) {
                Toast.makeText(MainActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }
            else {
                RadioButton radioButton = (RadioButton)radioGroup.findViewById(selectedId);
                if (radioButton.getText().equals(traderBtn.getText())){
                    //search in trader table
                    int result = dbObj.validateTraderPassword(usernameTXT, passwordTXT);
                    //if details dont exist -> toast
                    if (result == -1){
                        Toast.makeText(MainActivity.this, "Trader doesn't exist yet", Toast.LENGTH_SHORT).show();
                    }
                    //if details are wrong -> toast
                    else if(result == 0){
                        Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                    //if details are right -> intent to home page
                    else{
                        Toast.makeText(MainActivity.this, "Password correct", Toast.LENGTH_SHORT).show();
                        openTraderHomePage(usernameTXT);
                    }
                } else if (radioButton.getText().equals(userBtn.getText())) {
                   //search in user table
                    int result = dbObj.validateUserPassword(usernameTXT, passwordTXT);
                    //if details dont exist -> toast
                    if (result == -1){
                        Toast.makeText(MainActivity.this, "User doesn't exist yet", Toast.LENGTH_SHORT).show();
                    }
                    //if details are wrong exist -> toast
                    else if(result == 0){
                        Toast.makeText(MainActivity.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                    //if details are right -> intent to home page
                    else{
                        Toast.makeText(MainActivity.this, "Password correct", Toast.LENGTH_SHORT).show();
                        openUserHomePage(usernameTXT);
                    }
                } //intent for opening profile creation
            }  //action based on radiobutton selection
        });//onclicklistner for login
//

    } //end main method



    //opens Home page of User, passes username
    private void openUserHomePage(String usernameTXT) {
        Intent userHomeIntent = new Intent(this, UserHomeActivity.class);
        userHomeIntent.putExtra("usernameOfUser", usernameTXT);
        startActivity(userHomeIntent);
    }

    //opens Home page of Trader, passes username
    private void openTraderHomePage(String usernameTXT) {
        Intent traderHomeIntent = new Intent(this, TraderHomeActivity.class);
        traderHomeIntent.putExtra("usernameOfTrader", usernameTXT);
        startActivity(traderHomeIntent);
    }


    //opens Profile creation page of User, passes username
    private void openUserProfileCreation(String usernameTXT) {
        Intent userProfileIntent = new Intent(this, UserProfileCreationActivity.class);
        userProfileIntent.putExtra("usernameOfUser", usernameTXT);
        startActivity(userProfileIntent);

    }

    //opens Profile creation page of Trader, passes username
    private void openTraderProfileCreation(String usernameTXT) {
        Intent traderProfileIntent = new Intent(this, TraderProfileCreationActivity.class);
        traderProfileIntent.putExtra("usernameOfTrader", usernameTXT);
        startActivity(traderProfileIntent);

    }

}//end class


