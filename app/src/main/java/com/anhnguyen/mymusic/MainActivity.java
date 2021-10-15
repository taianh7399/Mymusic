package com.anhnguyen.mymusic;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView txttitle,txttimeSong,txttimeTotal;
    SeekBar seekBarSong;
    ImageView img;
    Animation animation;
    ImageButton btnprev,btnpaus,btnplay,btnnext;
    ArrayList<Song> song;
    int position=0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.disc_rotate);
        addSong();
        KhoiTaoMedia();
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position<0){
                    position=song.size()-1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause2);
                setTime();
                updateTime();

            }
        });
        btnprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position>song.size()-1){
                    position=0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMedia();
                mediaPlayer.start();
                btnplay.setImageResource(R.drawable.pause2);
                setTime();
                updateTime();
            }
        });
        btnpaus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnplay.setImageResource(R.drawable.play1);
                KhoiTaoMedia();
                img.clearAnimation();


            }
        });
        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    img.clearAnimation();
                    btnplay.setImageResource(R.drawable.play1);

                }else {
                    mediaPlayer.start();
                    btnplay.setImageResource(R.drawable.pause2);
                    img.startAnimation(animation);
                }

                setTime();
                updateTime();


            }
        });
        seekBarSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBarSong.getProgress());
            }
        });
    }
    private  void KhoiTaoMedia(){
        mediaPlayer=MediaPlayer.create(MainActivity.this,song.get(position).getFile());

        txttitle.setText(song.get(position).getTitle());
    }
    private  void updateTime(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               SimpleDateFormat simpleDateFormat=new SimpleDateFormat("mm:ss");
               txttimeSong.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
               mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       position--;
                       if(position<0){
                           position=song.size()-1;
                       }
                       if(mediaPlayer.isPlaying()){
                           mediaPlayer.stop();
                       }
                       KhoiTaoMedia();
                       mediaPlayer.start();
                       btnplay.setImageResource(R.drawable.pause2);
                       setTime();
                       updateTime();
                   }
               });
               seekBarSong.setProgress(mediaPlayer.getCurrentPosition());
               handler.postDelayed(this,500);
            }
        },100);
    }
    private  void setTime(){
        SimpleDateFormat dinhdangGio=new SimpleDateFormat("mm:ss");
        txttimeTotal.setText(dinhdangGio.format(mediaPlayer.getDuration()));
        seekBarSong.setMax(mediaPlayer.getDuration());
    }
    private void addSong(){
        song=new ArrayList<>();
        song.add(new Song("mot phut",R.raw.mot_phut));
        song.add(new Song("phia sau 1 co gai",R.raw.phia_sau_mot_co_gai));
        song.add(new Song("so rang em biet anh con yeu em",R.raw.so_rang_em_biet_anh_con_yeu_em));
        song.add(new Song("suyt nua thi",R.raw.suyt_nua_thi));
        song.add(new Song("tam su voi nguoi la",R.raw.tam_su_voi_nguoi_la));
        song.add(new Song("yeu mot nguoi sao buon den the",R.raw.yeu_mot_nguoi_saob_buon_den_the));
        song.add(new Song("anh sai roi",R.raw.anh_sai_roi));
        song.add(new Song("lạc trôi ",R.raw.lac_troi));
        song.add(new Song("lalala",R.raw.lalala_));
        song.add(new Song("va the la het",R.raw.va_the_la_het));
        song.add(new Song("ve khoi",R.raw.ve_khoi));
        song.add(new Song("em cua ngay hom qua",R.raw.em_cua_ngay_hom_qua));
    }

    private void anhXa() {
        txttitle=(TextView) findViewById(R.id.textView);
        txttimeSong=(TextView) findViewById(R.id.time);
        txttimeTotal=(TextView) findViewById(R.id.timeend);
        seekBarSong=(SeekBar) findViewById(R.id.seekbar);
        btnprev=(ImageButton) findViewById(R.id.previous);
        btnpaus=(ImageButton) findViewById(R.id.pause);
        btnplay=(ImageButton) findViewById(R.id.play);
        btnnext=(ImageButton) findViewById(R.id.next);
        img=(ImageView) findViewById(R.id.imageView);
    }
}