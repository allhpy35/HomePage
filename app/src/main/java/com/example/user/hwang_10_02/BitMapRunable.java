package com.example.user.hwang_10_02;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BitMapRunable implements Runnable {

    protected ImageView IvBitMap;
    protected  String sBitmapUrI;


    public BitMapRunable(ImageView IvBitMap, String sBitmapUrI) {
            this.IvBitMap = IvBitMap;
            this.sBitmapUrI = sBitmapUrI;
        }

    @Override
    public void run() {
        try {
            final Bitmap bitmap = BitmapFactory.decodeStream((InputStream)(new URL(sBitmapUrI).getContent()));
            IvBitMap.post(new Runnable() {
                @Override
                public void run() {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
