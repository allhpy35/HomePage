package com.example.user.hwang_10_02;

import android.widget.ImageView;

public class BitMapRunable implements Runnable {

    protected ImageView ImageBitMap;
    protected  String sBitmapUrI;


    public BitMapRunable(ImageView BitMap, String sBitmapUrI) {
            this.ImageBitMap = BitMap;
            this.sBitmapUrI = sBitmapUrI;
        }

    @Override
    public void run() {

    }
}
