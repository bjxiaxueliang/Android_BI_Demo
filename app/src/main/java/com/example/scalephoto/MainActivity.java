package com.example.scalephoto;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {

    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);


        this.mContext = MainActivity.this;


        // 更新Session
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestBiAgent.updateBiSession(mContext);
            }
        });
        // 埋点一次
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 埋点1
                TestBiAgent.mdOneTest(mContext,123);
            }
        });
        // Session数据
        final Button showButton = (Button) findViewById(R.id.button3);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示Session数据
                showButton.setText(BiDbManager.getInstance().queryBiSessionData(mContext).toString());
            }
        });
        // 埋点数据
        final Button biButton = (Button) findViewById(R.id.button4);
        biButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示Bi数据
                biButton.setText(BiDbManager.getInstance().queryBiData(mContext).toString());
            }
        });
        // 上传
        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 暂时无法上传，因为没有定义上传接口
                TestBiAgent.uploadBi(mContext);
            }
        });

    }
}