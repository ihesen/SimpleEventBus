package com.ihesen.simpleeventbus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ihesen.eventbus.EventBus;
import com.ihesen.eventbus.Subscribe;
import com.ihesen.eventbus.ThreadModle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        Intent intent = new Intent(this, TwoActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadModle = ThreadModle.BACKGROUND)
    public void getMessage(Message message) {
        Log.e("ihesen:", "Thead Name: " + Thread.currentThread().getName() + ", reciver message:" + message.toString());
    }
}
