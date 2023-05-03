package com.unidubna.nordwindotg;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
            usbHostTextView.setText("Android поддерживает\nUSB OTG");

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.check_mark);
            //usbHostTextView.setTextColor(resources.getColor(R.color.good));
        } else {
            usbHostTextView.setText("Android не поддерживает\nUSB OTG");
            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.cross);
            usbHostTextView.setTextColor(resources.getColor(R.color.bad));
        }
        //TODO Повторная проверка
        DoTest test2 = new DoTest();
        test2.execute();
    }

    /**
     * Класс DoTest отвечает за тестирование и вывод его результатов в интерфейс
     */
    private class DoTest extends AsyncTask<Void, Void, Integer> {
        //Интерфейс до начала тестирования
        @Override
        protected void onPreExecute() {
            Resources resources = getResources();
        }

        //Тестирование
        @Override
        protected Integer doInBackground(Void... parameter) {
            try {
                if (OTGTest.serchIntoSupportedDevices()) return 1;
                return OTGTest.searchIntoPhoneDB();
            } catch (Exception ex) {
                return -2;
            }

        }

        //Вывод в интерфейс
        @Override
        protected void onPostExecute(Integer result) {
            Resources resources = getResources();
            switch (result) {
                case 1: //OTG поддерживается

                    break;
                case 0://OTG НЕ поддерживается

                    break;
                case -1://Не найден в базах

                    break;
                case -2://Ошибка

                    break;
            }
        }
    }

    }