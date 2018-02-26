
package com.kawabata.tsukuba.randomvibration;
import android.content.Context;
import android.os.Vibrator;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    RandomVibration v2 = new RandomVibration();
    RepeatVibration v1 = new RepeatVibration();
    boolean stopF;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stopF=true;


        // textView = findViewById(R.id.text_view);




        // SeekBar
        SeekBar seekBar = findViewById(R.id.seekbar);
        // 初期値
        seekBar.setProgress(0);
        // 最大値
        seekBar.setMax(14);
        seekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {


                    //ツマミがドラッグされると呼ばれる
                    @Override
                    public void onProgressChanged(
                            SeekBar seekBar, int progress, boolean fromUser) {
                        // 68 % のようにフォーマト、
                        // この場合、Locale.USが汎用的に推奨される
                        //String str = String.format(Locale.US, "%d mode%",progress);
                        // textView.setText(str);
                        v1.setFreq(progress);
                        v1.doVibration();
                    }

                    //ツマミがタッチされた時に呼ばれる
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        stopF=false;
                    }

                    //ツマミがリリースされた時に呼ばれる
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {


                    }


                });




    }



    public void onStop(View v){
        v1.stopVibration();
        v2.stopVibration();
        stopF=false;
    }

    public void onClickRandom(View v){
        //v2.doVibration();
        stopF=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                v2.doVibration1();

                // マルチスレッドにしたい処理 ここまで
            }
        }).start();

    }






    abstract class Vibration{
        abstract void doVibration();

        long[] NumberData={160,150,140,130,120,110,100,90,80,70,60,50,40,30,10};

        void stopVibration(){
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).cancel();
        }



    }






    class RepeatVibration extends Vibration{
        int freq;
        void RepeatVibration(){

        }
        void setFreq(int frequancy){
            this.freq=frequancy;
        }

        void doVibration() {
            ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate( new long[]{NumberData[freq],NumberData[freq]}, 0 );
        }

    }




    class RandomVibration extends Vibration{
        long dv;
        long nv;
        Random rand= new Random();
        Random rand2=new Random();
        void doVibration() {
            doVibration1();
        }

        void doVibration1() {
            while(true) {
                if (stopF) {
                    this.dv = NumberData[rand.nextInt(12)];
                    this.nv = NumberData[rand2.nextInt(8)]+4;

                    ((Vibrator) getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{this.dv, this.nv}, -1);

                    //       doVibration();
                } else {
                    super.stopVibration();
                    break;
                }
            }

        }


    }


}
