package com.nishanth.recorderapp.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nishanth.recorderapp.Adapters.MusicAdapter;
import com.nishanth.recorderapp.MainActivity;
import com.nishanth.recorderapp.MusicFiles.Music;
import com.nishanth.recorderapp.R;

import java.util.ArrayList;


public class AudioFragment extends Fragment {



    public static ArrayList<Music> musiclist;

    public static ArrayList<Music> getMusiclist() {
        return musiclist;
    }

    public static void setMusiclist(ArrayList<Music> musiclist) {
        AudioFragment.musiclist = musiclist;


    }

    private  RecyclerView songs_list_recycler;

    public static MusicAdapter adapter;

    public MusicAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MusicAdapter adapter) {
        this.adapter = adapter;
    }
    public AudioFragment() {
        // Required empty public constructor
    }






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_audio, container, false);
        musiclist= MainActivity.getMusiclist();
        songs_list_recycler=view.findViewById(R.id.songs_list_recycler);
        adapter=new MusicAdapter(musiclist, view.getContext());

        songs_list_recycler.setAdapter(adapter);
        songs_list_recycler.setHasFixedSize(true);
        songs_list_recycler.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));


        return view;
    }
}