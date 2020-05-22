package com.myapplicationdev.android.problemstatement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ShowSong extends AppCompatActivity {

    ListView lv;
    CustomAdapter ca;
    ArrayList<Song> songs = new ArrayList<Song>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_song);
        setTitle("P05-NDPSongs ~ Show Song");
        lv = findViewById(R.id.lvDisplaySongs);

        ca = new CustomAdapter(getApplicationContext(), R.layout.row, songs);
        lv.setAdapter(ca);

        DBHelper dbh = new DBHelper(ShowSong.this);

        ArrayList<Song> data = dbh.getAllSongs();
        dbh.close();
        for (int i = 0; i < data.size(); i++) {
            songs.add(new Song(data.get(i).get_id(), data.get(i).getTitle(), data.get(i).getSingers(),
                    data.get(i).getYear(), data.get(i).getStars()));
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = songs.get(position);
                Intent i = new Intent(ShowSong.this,
                        ModifySong.class);
                i.putExtra("data", data);
                startActivityForResult(i, 9);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && requestCode == 9){
                lv = findViewById(R.id.lvDisplaySongs);

                ca = new CustomAdapter(getApplicationContext(), R.layout.row, songs);
                lv.setAdapter(ca);

                DBHelper dbh = new DBHelper(ShowSong.this);

                ArrayList<Song> newData = dbh.getAllSongs();
                dbh.close();
                for (int i = 0; i < newData.size(); i++) {
                    songs.add(new Song(newData.get(i).get_id(), newData.get(i).getTitle(), newData.get(i).getSingers(),
                            newData.get(i).getYear(), newData.get(i).getStars()));
                }
            }

    }
}
