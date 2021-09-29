package com.example.task1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    EditText edtSearch;
    ListView listView;

    public  static List<StateModel> stateModelList = new ArrayList<>();
    StateModel stateModel;
    CustomAdaptor customAdaptor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        edtSearch=findViewById(R.id.edtSearch);
        listView=findViewById(R.id.listView);

        getSupportActionBar().setTitle("Affected States");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext() ,Details.class).putExtra("position",position));
            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                customAdaptor.getFilter().filter(s);
                customAdaptor.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void fetchData() {
        String url="https://api.rootnet.in/covid19-in/stats/latest";

        StringRequest request= new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.e("RES :",response);
                        try {
                            JSONObject obj= new JSONObject(response);
                            JSONObject data=obj.getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("regional");
                            for(int i=0; i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject=jsonArray.getJSONObject(i);

                                String stateName=  jsonObject.getString("loc");
                                //System.out.println(stateName);
                                 String confirmedCases =jsonObject.getString("confirmedCasesIndian");
                                 String confirmedCasesForeign=jsonObject.getString("confirmedCasesForeign") ;
                                 String discharged =jsonObject.getString("discharged");
                                 String deaths=jsonObject.getString("deaths");
                                 String totalConfirmed=jsonObject.getString("totalConfirmed");

                                 stateModel=new StateModel(stateName,confirmedCases,confirmedCasesForeign, discharged, deaths, totalConfirmed);
                                //stateModel=new StateModel("maharashtra","456907","677","8745","878","8788");
                                 stateModelList.add(stateModel);


                            }

                            customAdaptor = new CustomAdaptor(Home.this,stateModelList);
                            listView.setAdapter(customAdaptor);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this ,error.getMessage() , Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}