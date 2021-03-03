package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanedData extends AppCompatActivity {
    String name,addr,city,state,pincode,country;
    TextView nameText,addrText,cityText,stateText,pincodeText,countryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        nameText=findViewById(R.id.nameText);
        addrText=findViewById(R.id.addrText);
        cityText=findViewById(R.id.cityText);
        stateText=findViewById(R.id.stateText);
        pincodeText=findViewById(R.id.pincodeText);
        countryText=findViewById(R.id.countryText);

        String data=getIntent().getStringExtra("data");
        try {
            JSONObject dataObj=new JSONObject(data);
            name=dataObj.getString("name");
            addr=dataObj.getString("address");
            city=dataObj.getString("city");
            state=dataObj.getString("state");
            pincode=dataObj.getString("pincode");
            country=dataObj.getString("country");

            nameText.setText(name);
            addrText.setText(addr);
            cityText.setText(city);
            stateText.setText(state);
            pincodeText.setText(pincode);
            countryText.setText(country);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}