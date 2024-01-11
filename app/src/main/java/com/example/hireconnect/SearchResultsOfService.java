package com.example.hireconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchResultsOfService extends AppCompatActivity {

    ArrayList<TraderModel> traderModels = new ArrayList<>();
    Intent intent;
    String usernameTXT;
    float[] data;
    int trade;
    float compareUB;
    Button bookNowBtn;

    DBHelper dbObj;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results_of_service);
        dbObj = new DBHelper(this);


        //get intent info
        intent = getIntent();
        usernameTXT = intent.getStringExtra("usernameOfUser");
        data = intent.getFloatArrayExtra("service_ub_lb");
        if(data[0]==0){
            trade = R.array.electrician_name;
        } else if (data[0]==1) {
            trade = R.array.plumber_name;
        } else if (data[0]==2) {
            trade = R.array.beautician_name;
        }else {
            trade = R.array.laundry_name;
        }
        compareUB = data[1];

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        setUpTraderModels();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, traderModels);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));





    }

    private void setUpTraderModels(){
        String[] traderNames = getResources().getStringArray(trade);
        String[] phNos = getResources().getStringArray(R.array.phone_numbers);
        String[] costUB = getResources().getStringArray(R.array.numbers_ub);
        String[] costLB = getResources().getStringArray(R.array.numbers_lb);

        for (int i =0; i<traderNames.length; i++){
            if (compareUB >= Integer.parseInt(costUB[i])){
                traderModels.add(new TraderModel(traderNames[i],
                        phNos[i],
                        costLB[i],
                        costUB[i]
                ));
            }
        }



    }

    public void bookService(View view) {
        //db entry in seema electricals
        Toast.makeText(SearchResultsOfService.this, "Service booked!", Toast.LENGTH_SHORT).show();
//        boolean result = dbObj.insertNullInUserTable(usernameTXT, nameTXT, phnoTXT, addressTXT);

    }

//    public void bookService(View view) {
//
//        TextView nameTextView = findViewById(R.id.name);
//
//        String traderTXT = nameTextView.getText().toString();
//
//        //set intent, pass username
//        //new intent to date time picker
//        Intent bookServiceIntent = new Intent(this, BookService.class);
//        bookServiceIntent.putExtra("usernameOfTrader", traderTXT);
//        bookServiceIntent.putExtra("usernameOfUser", usernameTXT);
//        startActivity(bookServiceIntent);
//    }
}