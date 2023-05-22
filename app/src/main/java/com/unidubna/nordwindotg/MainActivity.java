package com.unidubna.nordwindotg;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import OTGTest.OTGTest;

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
        modelTextView.append(" " + android.os.Build.MODEL);
        DoTest test = new DoTest();
        test.execute();
    }

    public void update(View view) {
        DoTest test = new DoTest();
        test.execute();
    }

    /**
     * Класс DoTest отвечает за тестирование и вывод его результатов в интерфейс
     */
    private class DoTest extends AsyncTask<Void, Void, Integer> {
        @Override
        protected void onPreExecute() {
            Resources resources = getResources();
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(R.drawable.wait);
            TextView usbHostTextView = findViewById(R.id.UsbHostText);
            usbHostTextView.setText("Идет проверка...");
        }

        @Override
        protected Integer doInBackground(Void... parameter) {
            try {
                if (OTGTest.serchIntoSupportedDevices()) return 1;
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