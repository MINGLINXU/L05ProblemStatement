    package com.myapplicationdev.android.problemstatement;

    import androidx.appcompat.app.AppCompatActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.ListView;
    import android.widget.Spinner;

    import java.util.ArrayList;

    public class ShowSong extends AppCompatActivity {

        ListView lv;
        CustomAdapter ca;
        ArrayList<Song> songs = new ArrayList<Song>();
        Button btn5Star;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_show_song);
            setTitle("P05-NDPSongs ~ Show Song");
            lv = findViewById(R.id.lvDisplaySongs);
            btn5Star = findViewById(R.id.btnShow5Stars);

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
                public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {
                    Song data = songs.get(position);
                    Intent i = new Intent(ShowSong.this, ModifySong.class);
                    i.putExtra("data", data);
                    startActivityForResult(i, 9);
                }
            });

            btn5Star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper dbh = new DBHelper(ShowSong.this);
                    songs.clear();
                    songs.addAll(dbh.getAllSong("5"));
                    dbh.close();
                    ca.notifyDataSetChanged();
                }
            });


            final Spinner dynamicSpinner = (Spinner) findViewById(R.id.spinnerYear);
            ArrayList<String> alYear = new ArrayList<String>();
            alYear.add("Show all");
            for (int i = 0; i < songs.size(); i++){
                alYear.add(songs.get(i).getYear()+"");
            }


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, alYear);

            dynamicSpinner.setAdapter(adapter);

            dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    String year = dynamicSpinner.getSelectedItem().toString();
                    if(!year.equals("Show all")) {

                        DBHelper dbh = new DBHelper(ShowSong.this);
                        songs.clear();
                        songs.addAll(dbh.getAllSongByYear(year));
                        dbh.close();
                        ca.notifyDataSetChanged();
                    }else{
                        DBHelper dbh = new DBHelper(ShowSong.this);
                        songs.clear();
                        songs.addAll(dbh.getAllSongs());
                        dbh.close();
                        ca.notifyDataSetChanged();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode,
                                        Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK && requestCode == 9) {
                DBHelper dbh = new DBHelper(ShowSong.this);
                songs.clear();
                songs.addAll(dbh.getAllSongs());
                dbh.close();
                ca = new CustomAdapter(this, R.layout.row, songs);
                lv.setAdapter(ca);
            }
        }
    }
