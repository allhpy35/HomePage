package com.example.user.hwang_10_02;

import android.telephony.PhoneStateListener;

/**
 * Created by 510 on 2018-11-15 0015.
 */

public class CommStateListener extends PhoneStateListener {
    public CommStateListener() {

    }

    @Override
    public void onSignalStrengthChanged(int signalStrength) {
        super.onSignalStrengthChanged(signalStrength);
    }


}
