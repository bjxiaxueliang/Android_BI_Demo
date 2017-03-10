package com.example.scalephoto;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.scalephoto.cache.NetMntDbManager;
import com.example.scalephoto.cache.models.NetMntLogData;

import java.util.List;


public class MainActivity extends Activity {


    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        setContentView(R.layout.activity_main);
        //
        mTextView = (TextView) findViewById(R.id.textView001);

        findViewById(R.id.button01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetMntCollector.server_monitor(MainActivity.this, "000", "222", "333", "444", "555", "666", "777", "888", "999");
                NetMntCollector.server_monitor(MainActivity.this, "111", "222", "333", "444", "555", "666", "777", "888", "999");
                NetMntCollector.server_monitor(MainActivity.this, "333", "222", "333", "444", "555", "666", "777", "888", "999");


            }
        });

        findViewById(R.id.button02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                List<NetMntLogData> mList = NetMntDbManager.getInstance().query(MainActivity.this);
                //
                mTextView.setText(mList.toString());

            }
        });

        findViewById(R.id.button03).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<NetMntLogData> mList = NetMntDbManager.getInstance().query(MainActivity.this);
                if (mList != null && mList.size() > 1) {
                    NetMntDbManager.getInstance().deleteOldData(MainActivity.this, mList.get(1).getCtime());
                }


            }
        });


    }
}