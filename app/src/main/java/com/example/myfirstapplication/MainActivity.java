package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("i4202rgb24");
    }

    private Button btnJAVA;
    private Button btnC;
    private Button btnNEON;
    private Button btnASM;
    private TextView timeCost;
    private byte[] srcImg;
    private int imgWidth, imgHeight, imgDataSize, imgPixelSize;
    private ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnJAVA = findViewById(R.id.button_demo0);
        btnC = findViewById(R.id.button_demo1);
        btnNEON = findViewById(R.id.button_demo2);
        btnASM = findViewById(R.id.button_demo3);
        timeCost = findViewById(R.id.textView_time);
        imgView = findViewById(R.id.resultImage);
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                "0"));
        imgWidth = 1280;
        imgHeight = 960;
        imgPixelSize = imgWidth * imgHeight;
        imgDataSize = (imgPixelSize * 3) >> 1;

        try {
            InputStream is = getResources().openRawResource(R.raw.pic_1280x960_i420);
            srcImg = IOUtils.toByteArray(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StringFormatMatches")
    public void I4202RGB24_JAVA(View view) {
        byte[] demoImg = new byte[imgDataSize];
        System.arraycopy(srcImg, 0, demoImg, 0, imgDataSize);
        long escapedTime = System.currentTimeMillis();
        int[] rgb = I4202RGB24(demoImg, imgWidth, imgHeight);
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
        Render(rgb, imgWidth, imgHeight);
    }

    public native void Native_I4202RGB24_C(byte[] in, int width, int height, int[] out);
    @SuppressLint("StringFormatMatches")
    public void I4202RGB24_C(View view) {
        byte[] demoImg = new byte[imgDataSize];
        System.arraycopy(srcImg, 0, demoImg, 0, imgDataSize);
        long escapedTime = System.currentTimeMillis();
        int[] rgb = new int[imgPixelSize];
        Native_I4202RGB24_C(demoImg, imgWidth, imgHeight, rgb);
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
        Render(rgb, imgWidth, imgHeight);
    }

    public native void Native_I4202RGB24_NEON(byte[] in, int width, int height, int[] out);
    @SuppressLint("StringFormatMatches")
    public void I4202RGB24_NEON(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }

    public native void Native_I4202RGB24_ASM(byte[] in, int width, int height, int[] out);
    @SuppressLint("StringFormatMatches")
    public void I4202RGB24_ASM(View view) {
        long escapedTime = System.currentTimeMillis();
        //TODO
        escapedTime = System.currentTimeMillis() - escapedTime;
        timeCost.setText(String.format(getResources().getString(R.string.escapedTime),
                escapedTime));
    }

    public static int[] I4202RGB24(byte[] raw, int width, int height) {
        int pixelSize = width * height;

        // I420 to NV21
        int vcount = pixelSize >> 2;
        int yStartPos = 0;
        int uStartPos = pixelSize;
        int uEndPos = pixelSize + vcount - 1;
        int vStartPos = pixelSize + vcount;
        int vEndPos = pixelSize + vcount + vcount - 1;

        byte[] tmp = new byte[vcount];
        System.arraycopy(raw, vStartPos, tmp, 0, vcount);
        int tmpEndPos = vcount - 1;

        for (int i = 0; i < vcount; ++i) {
            raw[vEndPos - (i<<1)] = raw[uEndPos - i];
            raw[vEndPos - ((i<<1)+1)] = tmp[tmpEndPos - i];
        }

        // NV21 to RGB24
        int[] rgb = new int[pixelSize];
        int Y, Cb = 0, Cr = 0, index = 0;
        int R, G, B;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Y = raw[y * width + x];
                if (Y < 0) Y += 255;

                if ((x & 1) == 0) {
                    Cr = raw[(y >> 1) * (width) + x + pixelSize];
                    Cb = raw[(y >> 1) * (width) + x + pixelSize + 1];

                    if (Cb < 0) Cb += 127; else Cb -= 128;
                    if (Cr < 0) Cr += 127; else Cr -= 128;
                }

                R = Y + Cr + (Cr >> 2) + (Cr >> 3) + (Cr >> 5);
                G = Y - (Cb >> 2) + (Cb >> 4) + (Cb >> 5) - (Cr >> 1) + (Cr >> 3) + (Cr >> 4) + (Cr >> 5);
                B = Y + Cb + (Cb >> 1) + (Cb >> 2) + (Cb >> 6);

                if (R < 0) R = 0; else if (R > 255) R = 255;
                if (G < 0) G = 0; else if (G > 255) G = 255;
                if (B < 0) B = 0; else if (B > 255) B = 255;

                rgb[index++] = 0xff000000 + (R << 16) + (G << 8) + B;
            }
        }

        return rgb;
    }

    public void Render(int[] raw, int width, int height) {
        Bitmap bm = Bitmap.createBitmap(raw, width, height, Bitmap.Config.ARGB_8888);
        imgView.setImageBitmap(bm);
    }

    // Unused, reserve
    public void Render2(byte[] raw, int width, int height) {
        Bitmap bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bm.copyPixelsFromBuffer(ByteBuffer.wrap(raw));
        imgView.setImageBitmap(bm);
    }
}