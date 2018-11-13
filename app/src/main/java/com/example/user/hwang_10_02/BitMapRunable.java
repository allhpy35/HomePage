package com.example.user.hwang_10_02;

import android.widget.ImageView;

public class BitMapRunable implements Runnable {

    protected ImageView ImageBitMap;
    protected  String BitMapPass;


    public Runnable(ImageView BitMap, String BitMapPass) {
            this.ImageBitMap = BitMap;
            this.BitMapPass = BitMapPass;
        }

    @Override
    public void run() {

    }
}
