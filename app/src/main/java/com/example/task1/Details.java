package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class Details extends AppCompatActivity {
    private  int positionState;
    TextView tvState,tvConfirmCases,tvconfirmedCasesForeign,tvdischarged,tvdeaths,tvtotalConfirmed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent=getIntent();
        positionState=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details of"+Home.stateModelList.get(positionState).getState());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        tvState = findViewById(R.id.tvState);
        tvConfirmCases=findViewById(R.id.tvConfirmCases);
        tvconfirmedCasesForeign=findViewById(R.id.tvconfirmedCasesForeign);
        tvdischarged=findViewById(R.id.tvdischarged);
        tvdeaths=findViewById(R.id.tvdeaths);
        tvtotalConfirmed=findViewById(R.id.tvtotalConfirmed);

        tvState.setText(Home.stateModelList.get(positionState).getState());
        tvConfirmCases.setText(Home.stateModelList.get(positionState).getConfirmedCases());
        tvconfirmedCasesForeign.setText(Home.stateModelList.get(positionState).getConfirmedCasesForeign());
        tvdischarged.setText(Home.stateModelList.get(positionState).getDischarged());
        tvdeaths.setText(Home.stateModelList.get(positionState).getDeaths());
        tvtotalConfirmed.setText(Home.stateModelList.get(positionState).getTotalConfirmed());




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}