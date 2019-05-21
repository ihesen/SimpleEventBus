package com.ihesen.simpleeventbus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ihesen.eventbus.EventBus;

public class TwoActivity extends AppCompatActivity {

    private boolean mSwitch = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSwitch) {
                    EventBus.getDefault().post(new Message("我是主线程post的消息"));
                } else {
                    sendThreadMessage();
                }
                mSwitch = !mSwitch;
            }
        });
    }

    private void sendThreadMessage() {
        new Thread() {
            @Override
            public void run() {
                EventBus.getDefault().post(new Message("我是子线程post的消息"));
            }
        }.start();
    }
}
