package com.unidubna.nordwindotg;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.widget.TextView;
import android.content.res.Resources;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Resources resources = getResources();
        boolean test = getPackageManager().hasSystemFeature((PackageManager.FEATURE_USB_HOST));
        TextView textView = findViewById(R.id.MainText);
        if (test){
            textView.setText("OTG\nподдерживается");
            textView.setTextColor(resources.getColor(R.color.good));
        }
        else{
            textView.setText("OTG\nНЕ поддерживается");
            textView.setTextColor(resources.getColor(R.color.bad));
        }
    }
}