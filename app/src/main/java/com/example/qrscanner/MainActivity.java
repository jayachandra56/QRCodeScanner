package com.example.qrscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextInputEditText nameEdit,addrEdit,cityEdit,stateEdit,pincodeEdit,countryEdit;
    RelativeLayout progress;
    Button generateCode;
    LinearLayout dataEntry,codeLayout;
    ImageView code;
    private IntentIntegrator qrScan;
    TextView name,address;
    Bitmap bitmap ;
    public final static int QRcodeWidth = 500 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdit=findViewById(R.id.e_name);
        addrEdit=findViewById(R.id.e_address);
        cityEdit=findViewById(R.id.e_city);
        stateEdit=findViewById(R.id.e_state);
        pincodeEdit=findViewById(R.id.e_pinCode);
        countryEdit=findViewById(R.id.e_country);
        generateCode=findViewById(R.id.submit);
        dataEntry=findViewById(R.id.dataEntry);
        codeLayout=findViewById(R.id.codeLayout);
        code=findViewById(R.id.QRCode);
        progress=findViewById(R.id.progress_layout);


        generateCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                progress.setVisibility(View.VISIBLE);
                JSONObject dataObj=new JSONObject();
                try {
                    dataObj.put("name",nameEdit.getText().toString());
                    dataObj.put("address",addrEdit.getText().toString());
                    dataObj.put("city",cityEdit.getText().toString());
                    dataObj.put("state",stateEdit.getText().toString());
                    dataObj.put("pincode",pincodeEdit.getText().toString());
                    dataObj.put("country",countryEdit.getText().toString());

                    bitmap = TextToImageEncode(dataObj.toString());
                    dataEntry.setVisibility(View.GONE);
                    codeLayout.setVisibility(View.VISIBLE);
                    code.setImageBitmap(bitmap);

                } catch (WriterException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );


        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
//        progress.setVisibility(View.GONE);
        return bitmap;
    }
}