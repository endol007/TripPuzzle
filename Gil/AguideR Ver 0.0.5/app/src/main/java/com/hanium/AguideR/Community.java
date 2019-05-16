package com.hanium.AguideR;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Community extends AppCompatActivity
{
    ArrayList<String> restdata = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayList<Restaurant> restlist = new ArrayList<Restaurant>();
    ListView listView;
    final int REST_INFO = 21;
    final int NEW_REST = 22;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        tv = (TextView)findViewById(R.id.tv);
        setTitle("커뮤니티");
        setListView();
    }


    public void onClick(View v)
    {
        Intent intent = new Intent(Community.this,Community2.class);
        intent.putExtra("restlist",restdata);
        startActivityForResult(intent,NEW_REST);
    }
    public void setListView()
    {
        listView = (ListView)findViewById(R.id.listview);

        //데이터를 만들고
        //data.add("무언가");

        //어댑터 만듬
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,restdata);
        listView.setAdapter(adapter);

        //꾹 눌렀을때 삭제
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {

                //정보를 삭제하는지 묻는 대화상자 나타남
                AlertDialog.Builder dlg = new AlertDialog.Builder(view.getContext());
                dlg.setTitle("삭제확인")
                        .setIcon(R.drawable.potato)
                        .setMessage("선택한 정보를 정말 삭제하시겠습니까?")
                        .setNegativeButton("취소",null)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                //삭제 클릭시 아래꺼
                                restdata.remove(position);
                                restlist.remove(position);
                                adapter.notifyDataSetChanged();
                                tv.setText("커뮤니티 커멘트("+restdata.size()+"개)");
                                Snackbar.make(view,"삭제되었습니다.",2000).show();
                            }
                        })
                        .show();
                return true;
            }
        });

        //클릭시 상세정보가 나타남
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(Community.this,Community3.class);
                Restaurant res = restlist.get(position);
                intent.putExtra("restinfo",res);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_REST)
        {
            if(resultCode == RESULT_OK)
            {
                Restaurant res = data.getParcelableExtra("newrest"); //새 레스토랑 받아옴
                restdata.add(res.getName());
                restlist.add(res);
                adapter.notifyDataSetChanged();
                tv.setText("커뮤니티 커멘트("+restdata.size()+"개)");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}