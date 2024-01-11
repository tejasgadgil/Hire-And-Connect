package com.example.hireconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hireconnect.params.Params;

public class UserProfileCreationActivity extends AppCompatActivity {

    //xml attributes used
    DBHelper dbObj1;
    EditText name, phno, address;
    Button createProfile;

    Intent intent;
    String usernameTXT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_creation);

        //database object
        dbObj1 = new DBHelper(this);

        //java connected to xml
        name = (EditText) findViewById(R.id.name);
        phno = (EditText) findViewById(R.id.phno);
        address = (EditText) findViewById(R.id.address);
        createProfile = (Button) findViewById(R.id.createProfileButton);
        intent = getIntent();
        usernameTXT = intent.getStringExtra("usernameOfUser");



        createProfile.setOnClickListener(v -> {
            String nameTXT = name.getText().toString();
            String phnoTXT = phno.getText().toString();
            String addressTXT = address.getText().toString();

            if(TextUtils.isEmpty(nameTXT) || TextUtils.isEmpty(phnoTXT) || TextUtils.isEmpty(addressTXT)) {
                Toast.makeText(UserProfileCreationActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }else if(phnoTXT.length() != 10){
                Toast.makeText(UserProfileCreationActivity.this, "Please enter valid phno", Toast.LENGTH_SHORT).show();
            }
            else{
                boolean result = dbObj1.insertNullInUserTable(usernameTXT, nameTXT, phnoTXT, addressTXT);
                //result == true if db was updated successfully
                if (result){
                    Toast.makeText(UserProfileCreationActivity.this, "User Profile Added!", Toast.LENGTH_SHORT).show();
                    openUserProfileCreation(nameTXT);

                }
            }//else if to check that fields not empty

        } ); //onclicklistner for createProfile btn


    } //end oncreate method


    //opens Profile creation page of User, passes username
    private void openUserProfileCreation(String usernameTXT) {
        Intent userProfileIntent = new Intent(this, UserHomeActivity.class);
        userProfileIntent.putExtra("usernameOfUser", usernameTXT);
        startActivity(userProfileIntent);
    }

} //end class