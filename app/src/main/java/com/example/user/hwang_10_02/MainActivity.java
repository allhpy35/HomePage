package com.example.user.hwang_10_02;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ViewUtils;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {              //초기화를 하고 앞으로
    protected Button btnHomepage, btnDial, btnCall, SMS, Map, Record, TTSBtn, EcoBtn, BtnContact, Voice_Calling, BtBitMap, btToastPs, btnService, btnLocation;
    protected TextView TextView, VoiceRecord, TextVoice;
    protected EditText etTTs, EcoEdText;
    protected TextToSpeech tts;
    private static final int CODE_RECOG = 1234, CODE_ECORECO = 4321, CODE_CONTACT = 1243, CODE_Voice_Calling = 5656, CODE_Voice_Calling1 = 1111, CODE_Voice_Calling2 = 2222, CODE_Voice_Calling3 = 333;
    protected boolean bService = false;
    public ImageView IvBitMap;
    protected String sBitmapUrI = "https://sites.google.com/site/yongheuicho/_/rsrc/1313446792839/config/customLogo.gif?revision=1", Name = " ";
    protected TelephonyManager telephonyManager;
    protected CommStateListener commStateListener;
    protected LocationManager locationManager;
    protected MyLocationListener myLocationListener;
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

        etTTs = (EditText) findViewById(R.id.editTTS);
        TTSBtn = (Button) findViewById(R.id.TTS);
        TTSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = etTTs.getText().toString();
                tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);          //Flush 물이 쭉내려가는것 문자를 소리로 변해주는것이 QUEUE_FLUSH , 이오류는 tts를 지원하지않는 api 버전이 다르면 실행을 안해야하는데 Android 에서 이것을 자동생성하라는 오류이다
            }
        });
        tts = new TextToSpeech(this, this);


        EcoBtn = (Button) findViewById(R.id.Eco);

        EcoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                voiceRecog(CODE_ECORECO);


            }
        });

        EcoEdText = (EditText) findViewById(R.id.EcoEdText);


        BtnContact = (Button) findViewById(R.id.BtnContact);

        BtnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, CODE_CONTACT);
            }
        });

        TextVoice = (TextView) findViewById(R.id.TextVoice);

        Voice_Calling = (Button) findViewById(R.id.Voice_Calling);
        Voice_Calling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hello = TextVoice.getText().toString();
                speakStr(hello);

                voiceRecog(CODE_Voice_Calling);

            }
        });


        IvBitMap = (ImageView) findViewById(R.id.IvBitMap);
        BtBitMap = (Button) findViewById(R.id.BtBitMap);
        BtBitMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new BitMapRunable(IvBitMap, sBitmapUrI)).start();

            }
        });

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        commStateListener = new CommStateListener(telephonyManager,this);

        btToastPs = (Button) findViewById(R.id.btToastPs);
        btToastPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastPhoneState();
            }
        });

        btnService = (Button)findViewById(R.id.btnService);
        btnService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateService();
            }
        });



        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        long minTime = 1000;
        float minDistance = 0; //0으로하면 좀더 민감하게 측정하며 거리가 바뀌면 업데이트한다

        myLocationListener = new MyLocationListener();
       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, myLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, myLocationListener);

        btnLocation = (Button)findViewById(R.id.btLocation);

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocation();
            }
        });





    }

    private void showLocation() {
        double latitude, longitude, altitude;
        latitude  = myLocationListener.latitude;
        longitude = myLocationListener.longitude;
        altitude = myLocationListener.altitude;

        Toast.makeText(this, "Latitude: " +latitude+", Longitude" +longitude+ ", Altitude" +altitude , Toast.LENGTH_SHORT).show();

    }

    private void updateService() {
        Intent intent = new Intent(this,phoneCallService.class);
        if(bService){
            stopService(intent);
            bService=false;
            btnService.setText("Start Svc");
        }
        else {
            startService(intent);
            bService = true;
            btnService.setText("Stop Svc");
        }
    }

    private void toastPhoneState() {
        int nPhoneType = telephonyManager.getPhoneType();           //여기에서 폰은 음성이다
        int nNetworkType = telephonyManager.getNetworkType();
        String sPhoneType;
        switch (nPhoneType) {
            case TelephonyManager.PHONE_TYPE_GSM:
                sPhoneType = "Voice: GSM";
                break;
            case TelephonyManager.PHONE_TYPE_CDMA:
                sPhoneType = "Voice: CDMA";
                break;
            case TelephonyManager.PHONE_TYPE_SIP:
                sPhoneType = "Voice: SIP";
                break;
            default:
                sPhoneType = "Voice: 코드 번호 = " + nPhoneType;
        }
        String sNetworkType;
        switch (nNetworkType) {
            case TelephonyManager.NETWORK_TYPE_CDMA:
                sNetworkType = "Data: 2G CDMA";
                break;
            case TelephonyManager.NETWORK_TYPE_UMTS:
                sNetworkType = "Data: 3G UMTS";
                break;
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                sNetworkType = "Data: 3G HSPA+";
                break;
            case TelephonyManager.NETWORK_TYPE_LTE:
                sNetworkType = "Data: 4G LTE";
                break;
            default:
                sNetworkType = "Data: 코드 번호 = " + nNetworkType;
        }
        Toast.makeText(this, sPhoneType, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, sNetworkType, Toast.LENGTH_SHORT).show();
        int nRssi = commStateListener.nRssi;
        Toast.makeText(this, "RSSI = " + nRssi, Toast.LENGTH_SHORT).show();

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

    private void speakStr(String str) {
        tts.speak(str, TextToSpeech.QUEUE_FLUSH, null, null);
        while (tts.isSpeaking()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    private String getPhoneNumFromName(String sName) {
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
        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == CODE_RECOG) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                VoiceRecord.setText(str);
            } else if (requestCode == CODE_ECORECO) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String sRecg = arList.get(0);
                String sDelay = EcoEdText.getText().toString();
                int nDelay = Integer.parseInt(sDelay);          //int sec
                try {
                    Thread.sleep(nDelay * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                speakStr(sRecg);
            } else if (requestCode == CODE_CONTACT) {
                String[] sFilter = new String[]{ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER};
                Cursor cursor = getContentResolver().query(data.getData(), sFilter, null, null, null, null);        //여기에서 연락처의 정보를 받아온다 현재 필요한것은 이름과 번호이다
                if (cursor != null) {
                    cursor.moveToFirst();
                    String sName = cursor.getString(0);
                    String sPhoneNum = cursor.getString(1);
                    cursor.close();

                    Toast.makeText(this, sName + " = " + sPhoneNum, Toast.LENGTH_SHORT).show();
                }

            } else if (requestCode == CODE_Voice_Calling) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                if (str.equals("전화 걸기") == true) {
                    speakStr("누구에게 전화 걸까요");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    voiceRecog(CODE_Voice_Calling1);
                }

            } else if (requestCode == CODE_Voice_Calling1) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                Name = arList.get(0);
                String inwoo = "안녕";
                if (Name.equals(inwoo) == true) {
                    speakStr(Name + "에게 전화 걸까요?");
                    voiceRecog(CODE_Voice_Calling2);
                }
            } else if (requestCode == CODE_Voice_Calling3) {
                ArrayList<String> arList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS); //리스트는 꼬리에꼬리를 물고 데이터를 저장하는것 <> 는 A데이터 타입을 String 으로하겠다  Intent정보를 풀어서 가져옴
                String str = arList.get(0);
                if (str.equals("예") == true) {
                    String callname = getPhoneNumFromName(Name);
                    Toast.makeText(getApplicationContext(), callname, Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(callname));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        telephonyManager.listen(commStateListener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
    }

    @Override
    protected void onPause() {
        telephonyManager.listen(commStateListener, PhoneStateListener.LISTEN_NONE);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this,phoneCallService.class);
        stopService(intent);
        super.onDestroy();
    }
}


