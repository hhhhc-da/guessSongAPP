package com.example.guessriddle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    protected Button conf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myError" , "开始运行MainActivity界面");
        setContentView(R.layout.activity_main);

        InitView();
        InitListener();
        Log.i("myError" , "onCreate任务完毕");
    }

    class myClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("myError" , "开始执行onClick任务");
            Intent it = new Intent();
            // 意图里面就一直装着这些歌名就可以了
            // it.putExtra("查询",10001);

            if(v.getId() == R.id.button){

                Log.i("myError","准备设置歌名");
                it.setClass(MainActivity.this,NameCondition.class);
                Log.i("myError" , "准备开始运行NameCondition界面");
                startActivity(it);
            }
        }
    }

    protected void InitView(){
        conf = findViewById(R.id.button);
        Log.i("myError" , "视图初始化完毕");
    }

    protected void InitListener(){
        myClick mc = new myClick();
        conf.setOnClickListener(mc);
        Log.i("myError" , "监听器初始化完毕");
    }
}