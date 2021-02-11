package com.nishanth.recorderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.google.android.material.tabs.TabLayout;
import com.nishanth.recorderapp.Adapters.FragmentsAdapter;
import com.nishanth.recorderapp.Fragments.AudioFragment;
import com.nishanth.recorderapp.MusicFiles.Music;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {




    private TabLayout tablayout;
    private ViewPager viewpager;

    public static String fileName="recorded.3gp";
    String file= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+fileName;

    static ArrayList<Music> musiclist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musiclist=getAllAudio(MainActivity.this);

        tablayout=findViewById(R.id.tablayout);
        viewpager=findViewById(R.id.viewpager);

        viewpager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        tablayout.setupWithViewPager(viewpager);
    }

    private ArrayList<Music> getAllAudio(Context context) {

        ArrayList<Music> musiclist=new ArrayList<>();

        String[] projection={
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media._ID

        };
        Cursor cursor=context.getContentResolver().query(Uri.parse(file), projection, null, null, null);
        if(cursor!=null)
        {
            while(cursor.moveToNext())
            {

                String album=cursor.getString(0);
                String title=cursor.getString(1);
                String duration=cursor.getString(2);
                String path=cursor.getString(3);
                String artist=cursor.getString(4);
                String id=cursor.getString(5);
                musiclist.add(new Music(path, title, artist, album, duration, id));
            }
            cursor.close();
        }

        return musiclist;
    }

    public static ArrayList<Music> getMusiclist() {
        return musiclist;
    }

    public static void setMusiclist(ArrayList<Music> musiclist) {
        MainActivity.musiclist = musiclist;

        AudioFragment.setMusiclist(musiclist);
    }
}