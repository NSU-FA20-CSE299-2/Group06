package com.ece.nsu.fall2020.cse486.sec1.smssender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    EditText phoneEt, messageEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEt = findViewById(R.id.phoneET);
        messageEt = findViewById(R.id.messageET);
        
    }

    public void sendIt(View view)
    {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if(permissionCheck== PackageManager.PERMISSION_GRANTED)
        {
            Log.d("S1", "sendIt: First Condition");
            MyMessage();
        }
        else
        {
            Log.d("S1", "sendIt: 2nd Condition");
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.SEND_SMS}, 0);
        }
    }

    private void MyMessage()
    {
        if(!phoneEt.getText().toString().trim().isEmpty() && !messageEt.getText().toString().trim().isEmpty())
        {
            String phone = "+88".concat(phoneEt.getText().toString());
            String message = messageEt.getText().toString();
            Log.d("dataSent", "MyMessage: ".concat(message).concat("phone: ").concat(phone));
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone,null,message,null,null);
            Toast.makeText(this, "Success: Sent!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==0)
        {
                if(grantResults.length>=0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    MyMessage();
                else{
                    Toast.makeText(this, "Failed permission!", Toast.LENGTH_SHORT).show();
                }
        }
    }
}