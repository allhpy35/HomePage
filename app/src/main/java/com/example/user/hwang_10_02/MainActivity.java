package com.example.user.hwang_10_02;

import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    protected Button btnHomepage, btnDial, btnCall, SMS, Map, Voice, Gps, Record;
    TextView TextView, VoiceRecord;
        private int CODE_RECOG = 1234;
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

        TextView = (TextView)findViewById(R.id.TextView);

        VoiceRecord = (TextView)findViewById(R.id.VoiceRecord);
        Record=(Button)findViewById(R.id.Record);
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    voiceRecog();
            }
        });


        }
    private void voiceRecog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);      //스마트폰에있는걸 이용해서 좀더 정확한 음성인식을 하기 위해 설정하였다
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
        startActivityForResult(intent, CODE_RECOG);

    }
}
