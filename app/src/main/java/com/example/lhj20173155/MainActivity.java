package com.example.lhj20173155;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    private ImageButton memo_insert;

    private Toolbar toolbar;
    private View header;
    private ActionBar actionBar;
    private ListView listView;
    public static MediaPlayer mp;

    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    private ListView m_oListView = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(mp == null)
        {
            mp = MediaPlayer.create(this,R.raw.bgm);
        }
        if(mp !=null && !mp.isPlaying())
        {
            mp.start();
            mp.setLooping(true);
        }


        //String[] strDate = {"2017-01-03", "1965-02-23", "2016-04-13"};
        memo_insert = (ImageButton) findViewById(R.id.memo_insert);
        listView = (ListView)findViewById(R.id.listView);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        myDBHelper = new myDBHelper(this);
        sqlDB  = myDBHelper.getReadableDatabase();
        header = getLayoutInflater().inflate(R.layout.listview_item, null, false);

        Cursor cursor;
        cursor = sqlDB.rawQuery("SELECT * FROM memoTBL;", null);

        int nDatCnt=0;
       final ArrayList<ItemData> oData=new ArrayList<>();
        while(cursor.moveToNext())
        {
            final ItemData oItem = new ItemData();
            oItem.strTitle = cursor.getString(1);
            oItem.strDate = cursor.getString(3);
            oItem.strContent = cursor.getString(2);
            oItem.iNumber = cursor.getInt(0);
            oData.add(oItem);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                    Intent intent = new Intent(getApplicationContext(), ContentActivity.class);

                    intent.putExtra("memo_title",oData.get(arg2).strTitle);
                    intent.putExtra("memo_content",oData.get(arg2).strContent);
                    intent.putExtra("memo_number",oData.get(arg2).iNumber);

                    startActivity(intent);

                }
            });

        }
        cursor.close();
        sqlDB.close();

        m_oListView = (ListView)findViewById(R.id.listView);

        final ListAdapter oAdapter =new ListAdapter(oData);
        m_oListView.setAdapter(oAdapter);

        memo_insert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                startActivity(intent);
            }
        });




    }
    private long time= 0;
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-time>=2000){
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        }else if(System.currentTimeMillis()-time<2000){
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

}
