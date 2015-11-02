package com.example.zcy.sb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.inputmethodservice.AbstractInputMethodService;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;


public class
        Main_ColorActivity extends Activity {
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main__color);
        TextView tv = null;
        tv = (TextView) findViewById(R.id.textView);
        tv.setTextColor(0xffd0eeee);
        Animation mAnimationRight = AnimationUtils.loadAnimation(
                Main_ColorActivity.this, R.anim.scale);
        tv.setAnimation(mAnimationRight);
        initMediaPlayer(); // 初始化MediaPlayer
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start(); // 开始播放
        }
        ImageButton button1 = (ImageButton) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(Main_ColorActivity.this, Settings.class);
                startActivity(i);
            }
        });
        ImageButton button5 = (ImageButton) findViewById(R.id.button2);
        button5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent i = new Intent(Main_ColorActivity.this, Jiayemian.class);
                startActivity(i);
            }
        });
        ImageButton button6 = (ImageButton) findViewById(R.id.button3);
        button6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder
                        (Main_ColorActivity.this);
                dialog.setTitle("最高分");
                dialog.setMessage("当前最高分：" + load());
                dialog.setCancelable(false);
                dialog.setNegativeButton("返回主菜单", new DialogInterface.
                        OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main__color, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public int load() {
        SharedPreferences pref = getSharedPreferences("data",
                MODE_PRIVATE);
        int scale = pref.getInt("最高分", 0);
        return scale;
    }
    private void initMediaPlayer() {
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.zhuyin1);  // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}