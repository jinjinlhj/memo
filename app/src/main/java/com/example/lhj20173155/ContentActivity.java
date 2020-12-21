package com.example.lhj20173155;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.TextView;
import android.widget.Toast;


public class ContentActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;
    TextView mContent,mTitle,memo_update, memo_delete;
    myDBHelper myDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        actionBar.setDisplayHomeAsUpEnabled(true);

        mContent = (TextView)findViewById(R.id.content);
        mTitle = (TextView)findViewById(R.id.title);
        memo_update = (TextView)findViewById(R.id.memo_update);
        memo_delete = (TextView)findViewById(R.id.memo_delete);

        final String title;
        final String content;
        int number=-1;
        Intent intent = getIntent();
        if(intent != null)
        {
            title = intent.getStringExtra("memo_title");
            content = intent.getStringExtra("memo_content");
            number = intent.getIntExtra("memo_number",-1);

            if(title.toString().equals(""))
                mTitle.setText("제목이 없습니다.");
            else
                mTitle.setText(title);
            if(content.toString().equals(""))
                mContent.setText("내용이 없습니다.");
            else
                mContent.setText(content);


            final int finalNumber1 = number;
            memo_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                    intent.putExtra("memo_title",title);
                    intent.putExtra("memo_content",content);
                    intent.putExtra("memo_number", finalNumber1);

                    startActivity(intent);
                }
            });

        }
        myDBHelper = new myDBHelper(this);
        final int finalNumber = number;
        memo_delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sqlDB = myDBHelper.getWritableDatabase();
                if(finalNumber !=-1)
                {
                    sqlDB.execSQL("DELETE FROM memoTBL WHERE iNumber="+finalNumber+";");
                }
                sqlDB.close();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                MainActivity.mp.pause();
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:{
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}