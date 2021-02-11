package com.nishanth.recorderapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nishanth.recorderapp.MainActivity;
import com.nishanth.recorderapp.R;

import java.io.File;
import java.io.IOException;


public class RecordFragment extends Fragment {

    MediaRecorder recorder;
    public static String fileName="recorded.3gp";
    String file= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+fileName;

    TextView record, stop, list, startpause;
    int start=1,pause=0;


    public RecordFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view=inflater.inflate(R.layout.fragment_record, container, false);
         record=view.findViewById(R.id.record);

        stop=view.findViewById(R.id.stop);
       list=view.findViewById(R.id.list);

       recorder=new MediaRecorder();
       recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
       recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
       recorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
       recorder.setOutputFile(file);

       record.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               try {
                   recorder.prepare();
                   recorder.start();
               } catch (IOException e) {
                   e.printStackTrace();
               }


           }
       });

       stop.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               recorder.stop();

               recorder.release();
           }
       });
       list.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               Intent i = new Intent(getActivity(), MainActivity.class);
               startActivity(i);
           }
       });

         return view;
    }
}