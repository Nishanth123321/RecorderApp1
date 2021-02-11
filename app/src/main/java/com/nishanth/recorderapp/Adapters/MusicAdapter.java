package com.nishanth.recorderapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nishanth.recorderapp.MusicFiles.Music;
import com.nishanth.recorderapp.R;

import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicAdapterView> {

    public ArrayList<Music> musiclist;
    private Context context;

    public MusicAdapter(ArrayList<Music> musiclist, Context context) {
        this.musiclist = musiclist;
        this.context = context;
    }

    public ArrayList<Music> getMusiclist() {
        return musiclist;
    }

    public void setMusiclist(ArrayList<Music> musiclist) {
        this.musiclist = musiclist;
        notifyDataSetChanged();
    }

    public MusicAdapter() {
    }

    @NonNull
    @Override
    public MusicAdapterView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.music_item, parent, false);
        return new MusicAdapterView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapterView holder, int position) {

        holder.music_name.setText(musiclist.get(position).getTitle());
        Glide.with(context).asBitmap().load(R.drawable.ic_musicicon).into(holder.music_img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, com.nishanth.musiczone.PlayerActivity.class);
                intent.putExtra("sender", "Audio");
                intent.putExtra("currpos",String.valueOf(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return musiclist.size();
    }

    public class MusicAdapterView extends RecyclerView.ViewHolder {
        private TextView music_name;
        private ImageView music_img;
        public MusicAdapterView(@NonNull View itemView) {
            super(itemView);
            music_name=itemView.findViewById(R.id.music_name);
            music_img=itemView.findViewById(R.id.music_img);
        }
    }

    void updatemusiclist(ArrayList<Music> newlist)
    {
        musiclist=new ArrayList<>();
        musiclist.addAll(newlist);
        notifyDataSetChanged();
    }
}
