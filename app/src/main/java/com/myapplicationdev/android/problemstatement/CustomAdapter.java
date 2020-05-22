package com.myapplicationdev.android.problemstatement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Song> {
    Context context;
    ArrayList<Song> songs;
    int resource;
    ImageView ivMusic, iv1, iv2, iv3, iv4, iv5;
    TextView tvYear, tvTitle, tvSinger;

    public CustomAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.songs = songs;
        this.resource = resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(resource, parent, false);

        //Match the UI components with Java variables
        ivMusic = rowView.findViewById(R.id.ImageViewMusic);
        iv1 = rowView.findViewById(R.id.imageView1star);
        iv2 = rowView.findViewById(R.id.imageView2star);
        iv3 = rowView.findViewById(R.id.imageView3star);
        iv4 = rowView.findViewById(R.id.imageView4star);
        iv5 = rowView.findViewById(R.id.imageView5star);
        tvYear = rowView.findViewById(R.id.tvYears);
        tvTitle = rowView.findViewById(R.id.tvTitles);
        tvSinger = rowView.findViewById(R.id.tvSingers);


        Song song = songs.get(position);

        //Check if the property for starts >= 5, if so, "light" up the stars
        if (song.getStars() >= 5) {
            iv5.setImageResource(android.R.drawable.star_on);
            iv4.setImageResource(android.R.drawable.star_on);
            iv3.setImageResource(android.R.drawable.star_on);
            iv2.setImageResource(android.R.drawable.star_on);
            iv1.setImageResource(android.R.drawable.star_on);
        }
        else if(song.getStars() == 4){
            iv4.setImageResource(android.R.drawable.star_on);
            iv3.setImageResource(android.R.drawable.star_on);
            iv2.setImageResource(android.R.drawable.star_on);
            iv1.setImageResource(android.R.drawable.star_on);
        }
        else if(song.getStars() == 3){
            iv3.setImageResource(android.R.drawable.star_on);
            iv2.setImageResource(android.R.drawable.star_on);
            iv1.setImageResource(android.R.drawable.star_on);
        }
        else if(song.getStars() == 2){
            iv2.setImageResource(android.R.drawable.star_on);
            iv1.setImageResource(android.R.drawable.star_on);
        }
        else if(song.getStars() == 1){
            iv1.setImageResource(android.R.drawable.star_on);
        }


        ivMusic.setImageResource(R.drawable.ic_library_music);
        tvYear.setText(String.valueOf(song.getYear()));
        tvTitle.setText(song.getTitle());
        tvSinger.setText(song.getSingers());
        return rowView;
    }


}
