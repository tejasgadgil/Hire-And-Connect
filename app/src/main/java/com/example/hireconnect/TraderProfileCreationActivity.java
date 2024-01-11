package com.example.hireconnect;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.TextUtils;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.hireconnect.params.Params;
        import com.google.android.material.slider.RangeSlider;

public class TraderProfileCreationActivity extends AppCompatActivity {

    //xml attributes used
    DBHelper dbObj2;
    EditText name, phno;
    RangeSlider rangeSlider;
    CheckBox plumberCheckBox;
    CheckBox electricianCheckBox;
    CheckBox beauticianCheckBox;
    CheckBox laundryCheckBox;
    Button createProfile;


    Intent intent;
    String usernameTXT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trader_profile_creation);
        //database object
        dbObj2 = new DBHelper(this);

        //java connected to xml
        name = (EditText) findViewById(R.id.name);
        phno = (EditText) findViewById(R.id.phno);
        rangeSlider= findViewById(R.id.rangeSlider);
        createProfile = (Button) findViewById(R.id.createProfileButton);
        plumberCheckBox = findViewById(R.id.plumberCheckBox);
        electricianCheckBox = findViewById(R.id.electricianCheckBox);
        beauticianCheckBox = findViewById(R.id.beauticianCheckBox);
        laundryCheckBox = findViewById(R.id.laundryCheckBox);

        intent = getIntent();
        usernameTXT = intent.getStringExtra("usernameOfTrader");

        createProfile.setOnClickListener(v -> {
            String nameTXT = name.getText().toString();
            String phnoTXT = phno.getText().toString();
            float costRangeLB = rangeSlider.getValues().get(0);
            float costRangeUB = rangeSlider.getValues().get(1);
            boolean isPlumberSelected = plumberCheckBox.isChecked();
            boolean isElectricianSelected = electricianCheckBox.isChecked();
            boolean isBeauticianSelected = beauticianCheckBox.isChecked();
            boolean isLaundrySelected = plumberCheckBox.isChecked();

            if(TextUtils.isEmpty(nameTXT) || TextUtils.isEmpty(phnoTXT) ) {
                Toast.makeText(TraderProfileCreationActivity.this, "Please fill all details", Toast.LENGTH_SHORT).show();
            }else if(phnoTXT.length() != 10){
                Toast.makeText(TraderProfileCreationActivity.this, "Please enter valid phno", Toast.LENGTH_SHORT).show();
            }else{
                boolean result = dbObj2.insertNullInTraderTable(usernameTXT, nameTXT, phnoTXT, costRangeUB,costRangeLB,isPlumberSelected,isElectricianSelected,isBeauticianSelected,isLaundrySelected);
                //result == true if db was updated successfully
                if (result){
                    Toast.makeText(TraderProfileCreationActivity.this, "Trader Profile Added!", Toast.LENGTH_SHORT).show();
                    openTraderProfileCreation(nameTXT);
                }
            }//else if to check that fields not empty

        } ); //onclicklistner for createProfile btn


    } //end oncreate method


    //opens Profile creation page of Trader, passes username
    private void openTraderProfileCreation(String usernameTXT) {
        Intent traderProfileIntent = new Intent(this, TraderHomeActivity.class);
        traderProfileIntent.putExtra("usernameOfTrader", usernameTXT);
        startActivity(traderProfileIntent);
    }

} //end class