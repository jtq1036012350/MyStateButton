package com.marsjiang.mystatebutton;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private MyStateButton myStateButton;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//              myStateButton.setOnInnerUnFinish("关注");
            myStateButton.setOnInnerFinish("已关注");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myStateButton = (MyStateButton) findViewById(R.id.mystatebar);
        myStateButton.setOnInnerClickeListener(new MyStateButton.ButtonClickListener() {
            @Override
            public void innerClick() {
                Message msg = Message.obtain();
                handler.sendMessageDelayed(msg,2000);
            }
        });
    }
}
