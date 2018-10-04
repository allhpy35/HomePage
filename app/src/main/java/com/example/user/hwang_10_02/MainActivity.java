package com.example.user.hwang_10_02;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    protected Button btnHomepage, btnDial, btnCall, SMS, Map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnDial = (Button)findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01026530933"));
                intent.putExtra("sms_body","Mokwon University");
                startActivity(intent);
            }
        });
        btnHomepage = (Button)findViewById(R.id.btnHomePage);
        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ice.mokwon.ac.kr"));
                startActivity(intent);
            }
        });

        btnCall= (Button)findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tell:0428297670"));
                startActivity(intent);
            }
        });

        SMS = (Button)findViewById(R.id.SMS);
        SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0428297670"));
                startActivity(intent);
            }
        });


        Map =(Button)findViewById(R.id.Map);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:s36.321609,127.337957?z=20"));
                startActivity(intent);

            }
        });
    }
}
