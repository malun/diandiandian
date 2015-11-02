package com.example.zcy.sb;

import android.media.Image;

/**
 * Created by zcy on 2015/10/22.
 */
public class GameRunning {
    Boolean n;
    int a;
    int b;
    int c;
    GameRunning(int a,int b,int c){
        this.a=a;
        this.b=b;
        this.c=c;
    }
    Boolean select(int e){
        if(e==a||e==b||e==c){
            n=true;
        }else
        {

                    n=false;


        }
        return n;
    }

}
