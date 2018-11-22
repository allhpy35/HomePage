package com.example.user.hwang_10_02;

import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import android.content.Context;



/**
 * Created by 510 on 2018-11-15 0015.
 */

public class CommStateListener extends PhoneStateListener {

        protected TelephonyManager telephonyManager;
        protected Context context;
        public int nRssi;

    public CommStateListener(TelephonyManager telephonyManager, Context context) {
        this.telephonyManager = telephonyManager;
        this.context = context;
    }

    @Override
    public void onSignalStrengthsChanged(SignalStrength signalStrength) {
        super.onSignalStrengthsChanged(signalStrength);
        int nPhoneType = telephonyManager.getPhoneType();
        if (nPhoneType == TelephonyManager.PHONE_TYPE_GSM)
            nRssi = signalStrength.getGsmSignalStrength();
        else if (nPhoneType == TelephonyManager.PHONE_TYPE_CDMA)
            nRssi = signalStrength.getCdmaDbm();
        Toast.makeText(context,"RSSI = "+nRssi,Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onCallStateChanged(int state, String phoneNumber) {
        super.onCallStateChanged(state, phoneNumber);
        switch (state){
            case TelephonyManager.CALL_STATE_RINGING:            //전화가 울리는상태
                Toast.makeText(context, "Ringing"+phoneNumber, Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:           //엣날 전화기에서 안테나를 뽑는 행동을 하고 전화를 받아서 이러헤깅름지었다
                Toast.makeText(context, "offhook"+phoneNumber, Toast.LENGTH_SHORT).show();
            break;
            case TelephonyManager.CALL_STATE_IDLE:
                Toast.makeText(context, "Idle", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
