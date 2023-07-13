package com.example.guessriddle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;
import java.lang.*;

public class GameStart extends AppCompatActivity {

    protected Button guess;
    protected Data _data;
    protected EditText charLine, song_guess;
    protected TextView main_display;
    protected char[] allChars;
    protected Set<String> records = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("myError","开始运行GameStart界面");
        setContentView(R.layout.activity_game_start);

        // 获取整个 Data 资源
        Intent itt = this.getIntent();
        Bundle bundle = itt.getExtras();
        Log.i("myError","获取bundle完毕");

        _data = (Data) bundle.getSerializable("Data");
        Log.i("myError","初始化_data完毕");
        Log.i("myError(Data)",_data.toString());

        InitView();
        InitListener();
    }

    class myClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            // 这个按钮并没有用到意图，可以通过返回键回到主页面
            // Intent it = new Intent();

            if(v.getId() == R.id.button4){
                // 代码主要逻辑都要写在这里
                Log.i("myError","开始运行任务主逻辑");
                String songNow = song_guess.getText().toString();
                records.add(songNow);
                Log.i("myError","songNow获取完毕");
                Log.i("myError(Data)",songNow);

                // 首先把所有字母都解析一遍
                Log.i("myError","准备读取Alpha");
                readAlpha();

                // 之后把所有歌名都检索一遍后返回一个数值(不光是检测歌曲名，还有字母显示)
                Log.i("myError","准备分析songNow和Alpha");
                Data newestData = TextGuess(songNow);

                // 输出所有信息(最新进展，恭喜猜中之类的)
                Log.i("myError","准备输出newestData");
                PrintData(newestData);
            }
        }
    }

    protected void InitView(){
        guess = findViewById(R.id.button4);

        charLine = findViewById(R.id.editTextText);
        song_guess = findViewById(R.id.editTextText2);

        main_display = findViewById(R.id.textView5);
        Log.i("myError" , "视图初始化完毕");
    }

    protected void InitListener(){
        myClick mc = new myClick();
        guess.setOnClickListener(mc);
        Log.i("myError" , "监听器初始化完毕");
    }

    protected void readAlpha(){
        // 正则表达式分割
        // (char[]) allChars = xxx;
        String tmp = charLine.getText().toString();

        Log.i("myError","正则表达式拆分Alpha");
        String[] allCharStr = tmp.split(",");
        Log.i("myError","正则表达式拆分Alpha完毕");
        for(String str:allCharStr) {
            Log.i("myError(Data)", str);
            // 空输入自动返回
            if(str.length() == 0){
                return ;
            }
        }



        Set<Character> usefulChar = new HashSet<>();

        for(String str : allCharStr){
            // 如果你输入的是一个字符串，那么我们只留下第一个
            usefulChar.add(str.charAt(0));
        }
        Log.i("myError","Alpha已截获");

        // 至此我们将所有不重复的 char 放入到 allChars 里(避免你们乱搞给我弄越界了)
        int i = 0;
        allChars = new char[usefulChar.size()];
        for(Character ch : usefulChar){
            allChars[i] = ch;
            ++i;
        }
        Log.i("myError","Alpha字符集获取完毕");

        // 只有我们的 _data 内才是真实的数据，其他的都是临时量
        _data.setAlpha(allChars);
        Log.i("myError","Alpha字符集设置完毕");
    }

    protected Data TextGuess(String song_guessed){
        Data nData = new Data();

        // 检索逻辑块

        /* 初始化部分，可以重复检索 */
        // 我们要用到的所有正确集合
        Log.i("myError","获取_data初始数据");
        Map<Integer,String> data_map = _data.getSongs(114514);
        Map<Integer,String> n_map = new HashMap<>();
        char[] all_alphas = _data.getAlpha(114514);
        Log.i("myError","获取_data初始数据完毕");


        Integer i = 0;
        Log.i("myError","开始初始化n_map(全遮盖)");
        for(String song : data_map.values()){
            // 有几个字符我们就用几个 * 去替代
            int len = song.length();
            StringBuilder strBuilder = new StringBuilder();

            for(int j = 0; j < len; ++j) {
                if(song.charAt(j) == ' '){
                    strBuilder.append(" ");
                }
                else {
                    strBuilder.append("*");
                }
            }

            String str = strBuilder.toString();
            Log.i("myError(Data)",str);

            n_map.put(i, str);
            ++i;
        }
        Log.i("myError","n_map初始化完毕");



            /* 检测是否有对应的字母 */
        int count_index = 0;
        Log.i("myError", "主存储体values开始分析");
        // 主存储体 (String[]) values,n_values
        String[] values = data_map.values().toArray(new String[12]);
        String[] n_values = n_map.values().toArray(new String[12]);
        Log.i("myError", "主存储体values初始化预备");
        for (String str : values) {
            Log.i("myError(Data)", str);
        }

        if(charLine.getText().length() != 0) {
            // 检测每一个歌名
            for (int j = 0; j < 12; ++j) {
                // 检测歌名里的每一个字符
                for (char ch : values[j].toCharArray()) {
                    // 一次匹配所有的字符
                    for (char tests : all_alphas) {
                        if (ch == tests) {
                            // String 不可变，所以我们用 StringBuilder
                            StringBuilder strBuilder = new StringBuilder(n_values[j]);
                            strBuilder.setCharAt(count_index, ch);
                            n_values[j] = strBuilder.toString();
                        }
                    }
                    // 迭代标志
                    ++count_index;
                }
                // 每次执行完之后清零
                count_index = 0;
            }
        }
        else{
            // 啥也没有的状态，直接输出就可以了
            for (int j = 0; j < 12; ++j) {
                n_values[j] = n_map.get(j);
            }
        }
        Log.i("myError","主存储体values分析完毕");

        nData.setData(n_values);
        nData.setAlpha(all_alphas);
        Log.i("myError","主存储体values写入_data完毕");

        return nData;
    }

    protected void PrintData(Data nData){
        // 输出各类消息
        StringBuilder strBuilder = new StringBuilder("最新结果:\n");
        // 覆盖后的数据
        Map<Integer,String> tmp = nData.getSongs(114514);
        // 覆盖前的数据
        Map<Integer,String> tmp2 = _data.getSongs(114514);
        int flag = 0;

        for(int i = 0;i < 12;++i) {
            // 两者是同步的关系
            String str = tmp.get(i), exam = tmp2.get(i);

            for(String string_record : records){
                if(string_record.equals(exam)){
                    flag = 1;
                }
            }
            if(flag == 1) {
                strBuilder.append(exam + "\n");
            }
            else {
                strBuilder.append(str + "\n");
            }

            flag = 0;
        }
        Log.i("myError","strBuilder准备字符串完成");

        String str = strBuilder.toString();
        Log.i("myError(Data)",str);

        main_display.setText(str.toCharArray(),0,str.length());
    }
}