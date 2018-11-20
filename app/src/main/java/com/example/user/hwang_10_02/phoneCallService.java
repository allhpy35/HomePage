package com.example.user.hwang_10_02;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

public class phoneCallService extends Service {
     PhoneCallReceiver phoneCallreceiver;

    public phoneCallService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       int nResult = super.onStartCommand(intent,flags,startId);
        phoneCallreceiver = new PhoneCallReceiver();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(phoneCallreceiver, intentFilter);
        return nResult;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(phoneCallreceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
