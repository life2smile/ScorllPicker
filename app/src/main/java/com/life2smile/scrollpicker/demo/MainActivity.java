package com.life2smile.scrollpicker.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.life2smile.scrollpicker.R;
import com.life2smile.scrollpicker.demo.sample2.SampleActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        TextView defaultScrollPickerTv = findViewById(R.id.default_scroll_picker_tv);
        TextView customScrollPickerTv = findViewById(R.id.custom_scroll_picker_tv);

        defaultScrollPickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, com.life2smile.scrollpicker.demo.sample1.SampleActivity.class));
            }
        });

        customScrollPickerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SampleActivity.class));
            }
        });
    }
}
