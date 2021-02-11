package com.nishanth.recorderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nishanth.recorderapp.Fragments.AudioFragment;
import com.nishanth.recorderapp.MusicFiles.Music;
import com.nishanth.recorderapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class PlayerActivity extends AppCompatActivity {


    private int shuffle=0, repeat=0, play=0;

    private ImageView player_back,player_shuffle,player_previous,player_next, player_repeat;
    private FloatingActionButton player_playpause;
    private ArrayList<Music> musiclist=new ArrayList<>();

    private static MediaPlayer player;
    static Uri  uri;
    int position =-1;

    private TextView player_songname, player_songartist, player_durationplayed,player_totalduration;
    private SeekBar player_seekbar;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if(getIntent().getStringExtra("sender").equals("Audio"))
        {
            musiclist = AudioFragment.getMusiclist();

        }
        else {

        }
        getSupportActionBar().hide();
        player_back=findViewById(R.id.player_back);
        player_songname=findViewById(R.id.player_songname);
        player_songartist=findViewById(R.id.player_songartist);
        player_seekbar=findViewById(R.id.player_seekbar);
        player_playpause=findViewById(R.id.player_playpause);
        player_durationplayed=findViewById(R.id.player_durationplayed);
        player_totalduration=findViewById(R.id.player_totalduration);
        player_shuffle=findViewById(R.id.player_shuffle);
        player_previous=findViewById(R.id.player_previous);

        player_next=findViewById(R.id.player_next);
        player_repeat=findViewById(R.id.player_repeat);

        player_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shuffle==1)
                {
                    shuffle=0;
                    player_shuffle.setImageResource(R.drawable.ic_shuffle_off);

                }
                else
                {
                    if(repeat==1)
                    {
                        repeat=0;
                        player_repeat.setImageResource(R.drawable.ic_repeat_off);
                    }
                    shuffle=1;
                    player_shuffle.setImageResource(R.drawable.ic_shuffle_on);
                }
            }
        });

        player_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(repeat==1)
                {
                    repeat=0;
                    player_repeat.setImageResource(R.drawable.ic_repeat_off);

                }
                else
                {
                    if(shuffle==1)
                    {
                        shuffle=0;
                        player_shuffle.setImageResource(R.drawable.ic_shuffle_off);
                    }
                    repeat=1;
                    player_repeat.setImageResource(R.drawable.ic_repeat_on);
                }
            }
        });
        player_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent=new Intent(PlayerActivity.this, MainActivity.class);
                startActivity(intent);

                 */
                finish();
            }
        });


        position=Integer.parseInt(getIntent().getStringExtra("currpos"));
        // uri=Uri.parse(musiclist.get(position).getPath());
        // player =  MediaPlayer.create( getApplicationContext(),  R.raw.song);
        // player.start();

        dealwithgivenposition(position);
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(player!=null) {

                    int mcurrpos = player.getCurrentPosition() / 1000;
                    player_seekbar.setProgress(mcurrpos);

                    player_durationplayed.setText(getformattedtime(mcurrpos));
                }

                handler.postDelayed(this, 100);
            }

        });
        player_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                player.seekTo(progress*1000);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        player_playpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(play==1)
                {

                    play=0;
                    player_playpause.setImageResource(R.drawable.ic_play);
                    player.pause();
                }
                else
                {
                    play=1;

                    player_playpause.setImageResource(R.drawable.ic_pause);
                    player.start();

                }
            }
        });

        player_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                nextbtnclicked();


            }
        });

        player_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousbtnclicked();



            }
        });





    }

    private void previousbtnclicked() {
        if(repeat==0 &&  shuffle==0) {
            if (position == 0) {
                position = musiclist.size() - 1;
            } else
                position--;
            dealwithgivenposition(position);
        }
        else if(shuffle==1)
        {
            Random rand = new Random();
            int random = rand.nextInt(musiclist.size());
            position=random;
            dealwithgivenposition(position);
        }
        else if(repeat==1)
        {
            dealwithgivenposition(position);
        }
    }

    private void nextbtnclicked() {

        if(shuffle==0 && repeat==0) {
            if (position == musiclist.size() - 1) {
                position = 0;
            } else
                position++;
            dealwithgivenposition(position);
        }
        else if(shuffle==1)
        {
            Random rand = new Random();
            int random = rand.nextInt(musiclist.size());
            position=random;
            dealwithgivenposition(position);
        }
        else if(repeat==1)
        {
            dealwithgivenposition(position);
        }
    }

    private void dealwithgivenposition (int pos)
    {
        player_songname.setText(musiclist.get(pos).getTitle());
        player_songartist.setText(musiclist.get(pos).getArtist());
        player_playpause.setImageResource(R.drawable.ic_pause);
        uri=Uri.parse(musiclist.get(pos).getPath());

        if(player!=null) {
            player.stop();
            player.release();



        }

        player =  MediaPlayer.create( getApplicationContext(), uri);



        /*player = new MediaPlayer();
        try {
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.setDataSource(getApplicationContext(), uri);

        }
        catch (IllegalArgumentException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (SecurityException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "You might not set the URI correctly!", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            player.prepare();
        } catch (IllegalStateException e) {
            Toast.makeText(getApplicationContext(), "Error in preparing", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error in preparing", Toast.LENGTH_LONG).show();
        }*/
        play=1;
        player.start();


        player_totalduration.setText(getformattedtime(player.getDuration() / 1000));
        player_seekbar.setMax(player.getDuration() / 1000);

        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                nextbtnclicked();



            }
        });








    }

    private String getformattedtime(int mcurrpos) {

        String totalout="";
        String totalnew="";
        String seconds=String.valueOf(mcurrpos%60);
        String minutes=String.valueOf(mcurrpos/60);
        totalout=minutes+":"+seconds;
        totalnew=minutes+":0"+seconds;
        if(seconds.length()!=1)
            return totalout;
        else
            return totalnew;
    }



}