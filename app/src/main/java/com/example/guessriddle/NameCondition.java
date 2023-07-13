package com.example.guessriddle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameCondition extends AppCompatActivity {

    protected Button setOK;
    protected EditText[] names;
    protected Data myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("myError" , "开始运行NameCondition界面");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_condition);

        InitView();
        InitListener();
    }

    class myClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent it = new Intent();

            if(v.getId() == R.id.button3){
                Log.i("myError","准备初始化myData");
                String[] allStrings = getAllString();

                myData = new Data( allStrings );
                Log.i("myError","myData初始化完毕");

                // 使用 Bundle 传输自定义数据类型
                Bundle bundle = new Bundle();
                bundle.putSerializable("Data", myData);

                it.putExtras(bundle);
                Log.i("myError","bundle准备完毕");
                Log.i("myError(Data)",myData.toString());

                it.setClass(NameCondition.this,GameStart.class);
                Log.i("myError","准备开始运行GameStart界面");
                startActivity(it);
            }
        }
    }

    protected void InitView(){
        setOK = findViewById(R.id.button3);

        names = new EditText[15];

        names[0] = findViewById(R.id.editTextText3);
        names[1] = findViewById(R.id.editTextText4);
        names[2] = findViewById(R.id.editTextText5);
        names[3] = findViewById(R.id.editTextText6);
        names[4] = findViewById(R.id.editTextText7);
        names[5] = findViewById(R.id.editTextText8);
        names[6] = findViewById(R.id.editTextText9);
        names[7] = findViewById(R.id.editTextText10);
        names[8] = findViewById(R.id.editTextText11);
        names[9] = findViewById(R.id.editTextText12);
        names[10] = findViewById(R.id.editTextText13);
        names[11] = findViewById(R.id.editTextText14);

        /*
        names[12] = findViewById(R.id.editTextText15);
        names[13] = findViewById(R.id.editTextText16);
        names[14] = findViewById(R.id.editTextText17);
        */

        Log.i("myError" , "视图初始化完毕");
    }

    protected void InitListener(){
        myClick mc = new myClick();
        setOK.setOnClickListener(mc);

        Log.i("myError" , "监听器初始化完毕");
    }

    protected String[] getAllString(){
        String[] allStr = new String[12];

        for(int i = 0; i < 12; ++i)
            allStr[i] = names[i].getText().toString();

        Log.i("myError" , "getAllString运行完毕");

        return allStr;
    }
}