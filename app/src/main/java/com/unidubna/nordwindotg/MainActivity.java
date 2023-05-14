package com.unidubna.nordwindotg;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.res.Resources;



import android.widget.ImageView;

import OTGTest.OTGTest;

import androidx.core.content.res.ResourcesCompat;


import android.graphics.drawable.Drawable;

public class MainActivity extends AppCompatActivity {
    protected Button button1;
    protected ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button1.setVisibility(View.INVISIBLE);
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);
        Resources resources = getResources();
        TextView modelTextView = findViewById(R.id.Model);
        modelTextView.append(" "+android.os.Build.MODEL);



        //TODO Повторная проверка
        searchIntoPhoneDB test2 = new searchIntoPhoneDB();
        test2.execute();
    }
    public void update(View view) {
        searchIntoPhoneDB test3 = new searchIntoPhoneDB();
        test3.execute();
    }
    private class searchIntoPhoneDB extends AsyncTask <Void, Void, Integer> {
        @Override
        protected void onPreExecute(){
            Resources resources = getResources();
            //TextView textView = findViewById(R.id.phoneDB);
            //textView.setText("Проверка по базе phonedb.com...");
            //textView.setTextColor(resources.getColor(R.color.neutral));
        }
        @Override
        protected Integer doInBackground(Void... parameter) {
            try {
                OTGTest.serchIntoSupportedDevices();
                return OTGTest.searchIntoPhoneDB();
            }
            catch (Exception ex)
            {
                return -2;
            }

        }
        @Override
        protected void onPostExecute(Integer result) {
            TextView usbHostTextView = findViewById(R.id.UsbHostText);
            imageView.setVisibility(View.VISIBLE);
            switch (result){
                case 1:
                    usbHostTextView.setText("Android поддерживает\nUSB OTG");
                    imageView.setImageResource(R.drawable.check_mark);
                    button1.setVisibility(View.INVISIBLE);
                break;
                case 0:
                    usbHostTextView.setText("Ваше устройство не\nнайдено в базе");
                    imageView.setImageResource(R.drawable.cross);
                    button1.setVisibility(View.INVISIBLE);
                    break;
                case -1:
                    usbHostTextView.setText("Android не поддерживает\nUSB OTG");
                    imageView.setImageResource(R.drawable.cross);
                    button1.setVisibility(View.INVISIBLE);
                    break;
                case -2:
                    ImageView imageView = findViewById(R.id.imageView);
                    imageView.setImageResource(R.drawable.no_wifi);
                    usbHostTextView.setText("Нет подключения к\nинтернету");
                    button1.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    }