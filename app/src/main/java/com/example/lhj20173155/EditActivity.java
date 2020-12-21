package com.example.lhj20173155;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    myDBHelper myDBHelper;
    TextView memo_cancel, memo_save;
    Toolbar toolbar;
    ActionBar actionBar;
    EditText editTitle, editContent;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        memo_cancel = (TextView)findViewById(R.id.memo_cancel);
        memo_save = (TextView)findViewById(R.id.memo_save);
        editContent = (EditText)findViewById(R.id.editContent);
        editTitle = (EditText)findViewById(R.id.editTitle);

        final String title;
        final String content;
        int number=-1;
        Intent intent = getIntent();
        if(intent!=null)
        {
            title = intent.getStringExtra("memo_title");
            content = intent.getStringExtra("memo_content");
            number = intent.getIntExtra("memo_number",-1);
            editTitle.setText(title);
            editContent.setText(content);
        }
        memo_cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myDBHelper = new myDBHelper(this);
        final int finalNumber = number;



        memo_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Date now = new Date();
                final SimpleDateFormat sFormat;

                sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sqlDB  = myDBHelper.getWritableDatabase();

                if(finalNumber !=-1){
                    if(!editContent.getText().toString().equals("")||!editTitle.getText().toString().equals(""))
                    {
                        sqlDB.execSQL("UPDATE memoTBL SET sTitle='"+editTitle.getText().toString()+"' WHERE iNumber="+finalNumber+";");
                        sqlDB.execSQL("UPDATE memoTBL SET sContent='"+editContent.getText().toString()+"' WHERE iNumber="+finalNumber+";");
                        sqlDB.execSQL("UPDATE memoTBL SET sDate='"+sFormat.format(now)+"' WHERE iNumber="+finalNumber+";");
                    }

                    //sqlDB.execSQL("DELETE FROM memoTBL WHERE iNumber="+finalNumber+";");
                }
                else
                {
                    if (!editContent.getText().toString().equals("")||!editTitle.getText().toString().equals(""))
                    {
                        sqlDB.execSQL("INSERT INTO memoTBL (sTitle, sContent, sDate) VALUES ( '"+editTitle.getText().toString() +"','"+editContent.getText().toString() +"','"+sFormat.format(now)+"');");
                    }
                    else
                        Toast.makeText(getApplicationContext(),"입력한 내용이 없어서 저장하지 않았습니다.",Toast.LENGTH_SHORT).show();
                }
                sqlDB.close();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(intent);
                MainActivity.mp.pause();

            }
        });

    }

}