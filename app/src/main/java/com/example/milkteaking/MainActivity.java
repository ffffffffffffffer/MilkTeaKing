package com.example.milkteaking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.milkteaking.core.app.MilkTea;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MilkTea.getApplicationContext(),
                "配置能成功的使用了", Toast.LENGTH_SHORT).show();
    }
}
