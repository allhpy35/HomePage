package com.example.user.hwang_10_02;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class phoneCallService extends Service {
      protected PhoneCallReceiver phoneCallreceiver;            //Outgoing
      protected CommStateListener commStateListener;          //Receiving
    protected TelephonyManager telephonyManager;

    public phoneCallService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        phoneCallreceiver = new PhoneCallReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(phoneCallreceiver, intentFilter);
        intentFilter = new IntentFilter(Intent.ACTION_CALL_BUTTON);
        registerReceiver(phoneCallreceiver, intentFilter);

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);             //
        commStateListener = new CommStateListener(telephonyManager,this);           //내가 만들어서 내가 관리하겠다라는것
        telephonyManager.listen(commStateListener, PhoneStateListener.LISTEN_CALL_STATE); //listener 를 달기 위해서 하는 내용이다.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       int nResult = super.onStartCommand(intent,flags,startId);
        return nResult;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(phoneCallreceiver);
        telephonyManager.listen(commStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
