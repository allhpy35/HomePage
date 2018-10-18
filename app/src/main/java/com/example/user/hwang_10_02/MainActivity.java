package com.example.user.hwang_10_02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {              //초기화를 하고 앞으로
    protected Button btnHomepage, btnDial, btnCall, SMS, Map, Voice, Gps, Record, TTSBtn;
    protected TextView TextView, VoiceRecord;
    protected EditText etTTs;
    protected TextToSpeech tts;
    private static int CODE_RECOG = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnDial = (Button) findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01026530933"));
                intent.putExtra("sms_body", "Mokwon University");
                startActivity(intent);
            }
        });
        btnHomepage = (Button) findViewById(R.id.btnHomePage);
        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ice.mokwon.ac.kr"));
                startActivity(intent);
            }
        });

        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tell:0428297670"));
                startActivity(intent);
            }
        });

        SMS = (Button) findViewById(R.id.SMS);
        SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0428297670"));
                startActivity(intent);
            }
        });


        Map = (Button) findViewById(R.id.Map);
        Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:s36.321609,127.337957?z=20"));
                startActivity(intent);

            }
        });

        TextView = (TextView) findViewById(R.id.TextView);

        VoiceRecord = (TextView) findViewById(R.id.VoiceRecord);
        Record = (Button) findViewById(R.id.Record);
        Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voiceRecog();
            }
        });
        etTTs = (EditText)findViewById(R.id.editTTS);
        TTSBtn = (Button)findViewById(R.id.TTS);
        TTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etTTs.getText().toString();
                tts.speak(str, TextToSpeech.QUEUE_FLUSH,  null, null);          //Flush 물이 쭉내려가는것 문자를 소리로 변해주는것이 QUEUE_FLUSH
            }
        });
        tts = new TextToSpeech(this, this);
    }

    private void voiceRecog() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);                                       //웹쪽으로 다양하게 할수도있다 이거랑 두가지 버전이있다
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);      //스마트폰에있는걸 이용해서 좀더 정확한 음성인식을 하기 위해 설정하였다
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);                                            //local은 한국의문화를 의미 한국의 표현방법대로 할것이고 한국어를지원할것이다
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "please tell me about!");
        startActivityForResult(intent, CODE_RECOG);      // 비밀번호설정                                                            //forResult 를실행하면 음성인식이 뜬다
        //음서인식된 결과를 String에 저장할수없다
        //안드로이드는 게속움직이기때문에 startActivity가 지원한다
    }

    //오버라이드란 framework에서 제공하는 기능을 내가 가져와서 쓰겠다 @Nullable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                     //여기에서 결과를 받는것이다
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_RECOG) {                                                                     //내가 실행한 activity 번호와 코드값을 비교해야한다
            if (resultCode == Activity.RESULT_OK && data != null) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String sRecg = arList.get(0);
                VoiceRecord.setText(sRecg);
            }
        }
    }



    @Override
    public void onInit(int i) {
        if(i == TextToSpeech.SUCCESS){
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(1.0f);         //기본값으로 체크한다
            tts.setSpeechRate(1.0f);    //기본값으로 체크한다
        }
    }
}