package com.hanium.AguideR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import java.text.SimpleDateFormat;
import java.util.Date;

//원래 MainActivity2 였음 커뮤니티 부분

public class Community2 extends AppCompatActivity
{

    EditText etname,ettel,etmenu1,etmenu2,etmenu3,etaddr;
    RadioButton rb1,rb2,rb3;
    Restaurant res;

    @Override
    protected void onCreate(Bundle savedInstanceState)

    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.community2);
        etname = (EditText)findViewById(R.id.etname);
        ettel = (EditText)findViewById(R.id.ettel);
        etmenu1 = (EditText)findViewById(R.id.etmenu1);
        etmenu2 = (EditText)findViewById(R.id.etmenu2);
        etmenu3 = (EditText)findViewById(R.id.etmenu3);
        etaddr = (EditText)findViewById(R.id.etaddr);
        rb1 = (RadioButton)findViewById(R.id.radio1);
        rb2 = (RadioButton)findViewById(R.id.radio2);
        rb3 = (RadioButton)findViewById(R.id.radio3);
        setTitle("새 글 등록");

        //ArrayList<String> dt = Intent.getStringExtra("restlist");

    }


    public void onClick(View v)
    {
        if (v.getId() == R.id.btnCancel)
        {
            finish();
        }
        else
        {
            if(rb1.isChecked())
            {
                setcategorynum(1);
            }
            else if (rb2.isChecked())
            {
                setcategorynum(2);
            }
            else if (rb3.isChecked())
            {
                setcategorynum(3);
            }
            res.setMenu(etmenu1.getText().toString());
            res.setMenu(etmenu2.getText().toString());
            res.setMenu(etmenu3.getText().toString());
            res.setDate(finddate());
            Intent intent = getIntent();
            intent.putExtra("newrest",res);  //Parcelable한 Restaurant를 첨부
            setResult(RESULT_OK,intent);
            finish();
        }
    }



    public void setcategorynum(int n)
    {
        res = new Restaurant(etname.getText().toString(),
                ettel.getText().toString(),
                etaddr.getText().toString(),
                n);
    }

    public String finddate()
    {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss a");
        String fmdate = sdf.format(date);
        return fmdate;
    }

}