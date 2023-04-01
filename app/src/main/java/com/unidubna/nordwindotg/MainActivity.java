package com.unidubna.nordwindotg;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.widget.TextView;
import android.content.res.Resources;

import OTGTest.OTGTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        TextView modelTextView = findViewById(R.id.Model);
        modelTextView.append(" "+android.os.Build.MODEL);
        TextView usbHostTextView = findViewById(R.id.UsbHostText);
        if (OTGTest.usbHostTest(this)){
            usbHostTextView.setText("UsbHost\nподдерживается");
            usbHostTextView.setTextColor(resources.getColor(R.color.good));
        }
        else{
            usbHostTextView.setText("UsbHost\nНЕ поддерживается");
            usbHostTextView.setTextColor(resources.getColor(R.color.bad));
        }
        //TODO Повторная проверка
        searchIntoPhoneDB test2 = new searchIntoPhoneDB();
        test2.execute();
    }
    private class searchIntoPhoneDB extends AsyncTask <Void, Void, Integer> {
        @Override
        protected void onPreExecute(){
            Resources resources = getResources();
            TextView textView = findViewById(R.id.phoneDB);
            textView.setText("Проверка по базе phonedb.com...");
            textView.setTextColor(resources.getColor(R.color.neutral));
        }
        @Override
        protected Integer doInBackground(Void... parameter) {
            try {
                return OTGTest.searchIntoPhoneDB();
            }
            catch (Exception ex)
            {
                return -2;
            }

        }
        @Override
        protected void onPostExecute(Integer result) {
                    Resources resources = getResources();
            TextView textView = findViewById(R.id.phoneDB);
            switch (result){
                case 1:
                    textView.setText("Согласно PhoneDB.сom OTG поддерживается");
                    textView.setTextColor(resources.getColor(R.color.good));
                break;
                case 0:
                    textView.setText("Ваше устройство не найдено в базе phonedb.сom");
                    textView.setTextColor(resources.getColor(R.color.neutral));
                    break;
                case -1:
                    textView.setText("Согласно phonedb.сom OTG НЕ поддерживается");
                    textView.setTextColor(resources.getColor(R.color.bad));
                    break;
                case -2:
                    textView.setText("Ошибка подключения к phonedb.сom");
                    textView.setTextColor(resources.getColor(R.color.neutral));
                    break;
            }
        }
    }

    }