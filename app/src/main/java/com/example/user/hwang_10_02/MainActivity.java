package com.example.user.hwang_10_02;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {              //초기화를 하고 앞으로
    protected Button btnHomepage, btnDial, btnCall, SMS, Map, Record, TTSBtn, EcoBtn, BtnContact, Voice_Calling, BtBitMap;
    protected TextView TextView, VoiceRecord, TextVoice;
    protected EditText etTTs, EcoEdText;
    protected TextToSpeech tts;
    private static final int CODE_RECOG = 1234, CODE_ECORECO = 4321, CODE_CONTACT = 1243, CODE_Voice_Calling = 5656, CODE_Voice_Calling1 = 1111, CODE_Voice_Calling2 = 2222, CODE_Voice_Calling3 = 333;
    public ImageView IvBitMap;
    protected  String sBitmapUrI = "https://sites.google.com/site/yongheuicho/_/rsrc/1313446792839/config/customLogo.gif?revision=1" , Name =" ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnHomepage = (Button) findViewById(R.id.btnHomePage);
        btnHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://ice.mokwon.ac.kr"));
                startActivity(intent);
            }
        });

        btnDial = (Button) findViewById(R.id.btnDial);
        btnDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01026530933"));
                intent.putExtra("sms_body", "Mokwon University");
                startActivity(intent);
            }
        });


        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:01026530933"));
           //     startActivity(intent);
            }
        });

        SMS = (Button) findViewById(R.id.SMS);
        SMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:0428297670"));
                intent.putExtra("sms_body", "Mokwon University");
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
                voiceRecog(CODE_RECOG);
            }
        });

        etTTs = (EditText)findViewById(R.id.editTTS);
        TTSBtn = (Button)findViewById(R.id.TTS);
        TTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etTTs.getText().toString();
                tts.speak(str, TextToSpeech.QUEUE_FLUSH,  null, null);          //Flush 물이 쭉내려가는것 문자를 소리로 변해주는것이 QUEUE_FLUSH , 이오류는 tts를 지원하지않는 api 버전이 다르면 실행을 안해야하는데 Android 에서 이것을 자동생성하라는 오류이다
            }
        });
        tts = new TextToSpeech(this, this);


        EcoBtn = (Button)findViewById(R.id.Eco);

        EcoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voiceRecog(CODE_ECORECO);


            }
        });

        EcoEdText = (EditText) findViewById(R.id.EcoEdText);



        BtnContact = (Button)findViewById(R.id.BtnContact);

        BtnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, CODE_CONTACT);
            }
        });

        TextVoice = (TextView)findViewById(R.id.TextVoice);

        Voice_Calling = (Button)findViewById(R.id.Voice_Calling);
        Voice_Calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hello = TextVoice.getText().toString();
                speakStr(hello);

                voiceRecog(CODE_Voice_Calling);

            }
        });



        IvBitMap = (ImageView)findViewById(R.id.IvBitMap);
        BtBitMap = (Button)findViewById(R.id.BtBitMap);
        BtBitMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new BitMapRunable(IvBitMap, sBitmapUrI)).start();

            }
        });





    }

    private void voiceRecog(int nCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);                                       //웹쪽으로 다양하게 할수도있다 이거랑 두가지 버전이있다
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);      //스마트폰에있는걸 이용해서 좀더 정확한 음성인식을 하기 위해 설정하였다
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);                                            //local은 한국의문화를 의미 한국의 표현방법대로 할것이고 한국어를지원할것이다
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "please tell me about!");
        startActivityForResult(intent, nCode);      // 비밀번호설정                                                            //forResult 를실행하면 음성인식이 뜬다
        //음서인식된 결과를 String에 저장할수없다
        //안드로이드는 게속움직이기때문에 startActivity가 지원한다
    }
    private void speakStr(String str){
        tts.speak(str, TextToSpeech.QUEUE_FLUSH,  null, null);
        while(tts.isSpeaking()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

    }

    private String getPhoneNumFromName(String sName){
        String sPhoneNum = "";
        Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_FILTER_URI, Uri.encode("정통이"));    //withAppendedPath 이것으로 강제로 URi 생성
        String[] arProjection = new String[]{ContactsContract.Contacts._ID};                                        //Filter, ID는 row값이다
        Cursor cursor = getContentResolver().query(uri, arProjection, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String sId = cursor.getString(0);
            String[] arProjNum = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
            String sWhereNum = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
            String[] sWhereNumParam = new String[]{ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE, sId};
            Cursor cursorNum = getContentResolver().query(ContactsContract.Data.CONTENT_URI, arProjNum, sWhereNum, sWhereNumParam, null);
            if (cursorNum != null && cursorNum.moveToFirst()) {
                sPhoneNum = cursorNum.getString(0);
            }
            cursorNum.close();
        }
        cursor.close();
        return sPhoneNum;

    }

    //오버라이드란 framework에서 제공하는 기능을 내가 가져와서 쓰겠다 @Nullable
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                     //여기에서 결과를 받는것이다
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK && data != null){
            if(requestCode == CODE_RECOG){
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                VoiceRecord.setText(str);
            }
            else if(requestCode == CODE_ECORECO){
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String sRecg = arList.get(0);
                String sDelay = EcoEdText.getText().toString();
                int nDelay = Integer.parseInt(sDelay);          //int sec
                try {
                    Thread.sleep(nDelay*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                speakStr(sRecg);
            }
            else if(requestCode == CODE_CONTACT){
                String[] sFilter = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};
               Cursor cursor = getContentResolver().query(data.getData(), sFilter, null, null, null, null);        //여기에서 연락처의 정보를 받아온다 현재 필요한것은 이름과 번호이다
               if(cursor != null){
                   cursor.moveToFirst();
                   String sName = cursor.getString(0);
                   String sPhoneNum = cursor.getString(1);
                   cursor.close();

                   Toast.makeText(this, sName + " = " + sPhoneNum , Toast.LENGTH_SHORT).show();
               }

            }
            else if(requestCode == CODE_Voice_Calling){
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                if(str.equals("전화 걸기") == true){
                    speakStr("누구에게 전화 걸까요");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   voiceRecog(CODE_Voice_Calling1);
                }

            }
            else if(requestCode == CODE_Voice_Calling1){
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                Name = arList.get(0);
                String inwoo = "안녕";
                if(Name.equals(inwoo) == true){
                    speakStr(Name +"에게 전화 걸까요?");
                    voiceRecog(CODE_Voice_Calling2);
                }
            }
            else if(requestCode == CODE_Voice_Calling3){
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                if(str.equals("예")==true){
                      String callname = getPhoneNumFromName(Name);
                    Toast.makeText(getApplicationContext(), callname, Toast.LENGTH_SHORT).show();


                      Intent intent = new  Intent(Intent.ACTION_CALL, Uri.parse(callname) );
                      startActivity(intent);

                }
            }
        }


    }

    @Override
    public void onInit(int state) {
        if(state == TextToSpeech.SUCCESS){
            tts.setLanguage(Locale.KOREAN);
            tts.setPitch(1.0f);         //기본값으로 체크한다
            tts.setSpeechRate(1.0f);    //기본값으로 체크한다
        }
    }






}


