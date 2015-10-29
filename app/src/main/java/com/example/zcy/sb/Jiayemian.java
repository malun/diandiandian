package com.example.zcy.sb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by zcy on 2015/9/25.
 */
public class Jiayemian extends Activity{
    int n=0;
    int i;
    Boolean g=true;
    Boolean kg=true;
    int k=1;
    int e=0;
    int time=60;
    int p=0;
    int q=0;
    SuiJi sJ=new SuiJi();
    public static final int UPDATE_TEXT = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TEXT:
//  在这里可以进行UI 操作
                    ADV();
                    break;
                default:
                    break;
            }
        }
    };
    void ADV(){
        AlertDialog.Builder dialog = new AlertDialog.Builder
                (Jiayemian.this);
        dialog.setTitle("游戏结束！");
        dialog.setMessage("分数：" + n+"!目前最高分："+load());
        dialog.setCancelable(false);
                dialog.setNegativeButton("返回主菜单", new DialogInterface.
                                OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dialog.show();
        n = 0;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiajiemian);
        final int[] f={R.drawable.true_4_1,R.drawable.true_4_2,R.drawable.true_5_1,R.drawable.true_5_2,R.drawable.true6_2,R.drawable.true6_1};
        final ImageView t4 = (ImageView) findViewById(R.id.textView4);
        final TextView t5 = (TextView) findViewById(R.id.textView5);
        final TextView t6 = (TextView) findViewById(R.id.textView6);
        final int UPDATE_TEXT1=1;
        final Handler handler1 = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case UPDATE_TEXT1:
//  在这里可以进行UI 操作
                        t6.setText("time:"+time);
                        break;
                    default:
                        break;
                }
            }
        };
        class MyThread implements Runnable {
            Thread t;
            public void run() {

                while (time > 0) {
                    if (kg == false) break;
                        Message message1 = new Message();
                        message1.what = UPDATE_TEXT1;
                        handler1.sendMessage(message1);
                    time--;
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                    kg = false;
                if (time == 0) {
                        p = load();
                        if (p > n) {
                            q = p;
                        } else {
                            q = n;
                        }
                        save(q);
                        Message message = new Message();
                        message.what = UPDATE_TEXT;
                        handler.sendMessage(message);
                }
            }
            void start() {
                t = new Thread(new MyThread());
                t.start();
            }
        }
        int l=sJ.chongXi();
        t4.setBackgroundResource(f[l]);
         i = f[l];
        final MyThread mt=new MyThread();
        mt.start();
        ImageButton button6=(ImageButton)findViewById(R.id.button9);
            button6.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GameRunning gr = new GameRunning(R.drawable.true_4_1,R.drawable.true_5_2,R.drawable.true6_2);
                    g = gr.select(i);
                    if (g) {
                        e++;
                        if(e>=10)
                        {
                            k=2;
                        }
                        n=n+k;

                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i = f[m];
                    } else {
                        e=0;
                        k=1;
                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i =f[m];
                    }
                }
            });
            ImageButton button7 = (ImageButton) findViewById(R.id.button10);
            button7.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GameRunning gr = new GameRunning(R.drawable.true_4_2,R.drawable.true_5_1,R.drawable.true6_2);
                    g = gr.select(i);
                    if (g) {
                        e++;
                        if(e>=10)
                        {
                            k=2;
                        }
                        n=n+k;
                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i =f[m];
                    } else {
                        e=0;
                        k=1;
                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i =f[m];
                    }
                }
            });
            ImageButton button8 = (ImageButton) findViewById(R.id.button11);
            button8.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    GameRunning gr = new GameRunning(R.drawable.true_4_2,R.drawable.true_5_2,R.drawable.true6_1);
                    g= gr.select(i);
                    if (g) {
                        e++;
                        if(e>=10)
                        {
                            k=2;
                        }
                        n=n+k;
                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i =f[m];
                    } else {
                        e=0;
                        k=1;
                        int m=sJ.chongXi();
                        t4.setBackgroundResource(f[m]);
                        t5.setText("分数:"+n);
                        i =f[m];
                    }
                }
            });
        ImageButton button9=(ImageButton)findViewById(R.id.imageButton);
        button9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                kg=false;
                AlertDialog.Builder dialog = new AlertDialog.Builder
                        (Jiayemian.this);
                dialog.setTitle("游戏暂停！");
                dialog.setMessage("快点开始吧！" );
                dialog.setCancelable(false);
                dialog.setPositiveButton("继续", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kg=true;
                       mt.start();
                    }
                });
                dialog.show();
            }
        });
        }
    public void save(int s){
        SharedPreferences.Editor editor = getSharedPreferences("data",
                MODE_PRIVATE).edit();
        editor.putInt("最高分", s);
        editor.commit();
    }
    public int load(){
        SharedPreferences pref = getSharedPreferences("data",
                MODE_PRIVATE);
        int scale = pref.getInt("最高分", 0);
        return scale;
    }
    }
