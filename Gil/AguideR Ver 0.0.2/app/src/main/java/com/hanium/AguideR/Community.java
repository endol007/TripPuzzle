package com.hanium.AguideR;

import java.util.ArrayList;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Community extends Activity implements View.OnClickListener {
    ListView comment_list;
    TextView time_text;
    EditText comment_edit;
    ImageView jrv_image_img;
    Comment_Adapter ca;
    ArrayList<Comment_Item> c_arr = new ArrayList<Comment_Item>();
    View header,footer;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_main);
//저는 처음에 값 설정할때 이닛이라는 함수로 그 안에 다 때려박습니다 후후..
        init();
    }

    public void init(){
        comment_list = (ListView)findViewById(R.id.jrv_comment_list);
        /*
         * 이부분이 오늘의 핵심. headr와 footer를 설정하는 겁니다.
         * header는 View 변수로 layout에 만들어 놓은 xml 을 인플레이터 시킵니다!
         * footer도 마찬가지.
         * inflater 한 View를 listview에 헤더와 푸터로 설정합니다.
         * addHeadrView 와 addFooterView 메소드를 사용해서 설정합니다.
         * 쉽게 말하면 Header 는 리스트뷰 위에 달릴것 Footer는 리스트뷰 아래에 달릴것!
         */
        //이곳이 바로 핵심 부분!! 후후.
        header = getLayoutInflater().inflate(R.layout.header, null, false);
        footer = getLayoutInflater().inflate(R.layout.footer, null, false);
        comment_list.addHeaderView(header);
        comment_list.addFooterView(footer);
        time_text = (TextView)header.findViewById(R.id.jrv_time_text);
        setTest();
        setList(); //listview 세팅 (아래 함수를 만들어 놓은곳 확인하세요)
        setHeader(); //header 세팅
        setFooter(); //footer 세팅
    }
    private void setTest(){
        /*
         * 의미없는 소스이니깐 확인 안하셔도 되요. 그냥 전체스크롤 예제를 위해 만들어 놓은겁니다! (댓글 수 처음에 늘려놓으려구)
         */
        Comment_Item ci = new Comment_Item();
        ci.setContent("댓글이 존재하지 않습니다");
        ci.setNickname("운영자");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("테스트를 위해 댓글을 다는겁니다.");
        ci.setNickname("운영자");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("별 의미없는 소스이니 안보셔도 되요.");
        ci.setNickname("운영자");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("댓글의 숫자를 높이기 위해서 작성한 코드입니다.");
        ci.setNickname("운영자");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("신발 너무 이쁘네요!!");
        ci.setNickname("조커");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("ㄴ저도 사고나서 너무 이쁜데 급하게 처분해야되서..");
        ci.setNickname("작성자");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("굳.. 완전 대박이네요!");
        ci.setNickname("독희");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("제가 ㅅㅅㅅㅅ!!");
        ci.setNickname("휘횽");
        c_arr.add(ci);
        ci = new Comment_Item();
        ci.setContent("판매중..");
        ci.setNickname("작성자");
        c_arr.add(ci);
    }
    private void setHeader(){
        /*헤더의 id 값을 받아오기 위해서는 평소에 findViewById를 바로 썻는데 그 앞에header.
         * 을 붙여서 header에 만들어 놓은 TextView의 아이디 값을 쓰겠다 이런 식으로 앞에
         * 꼭 붙여주셔야 합니다. 안그러면 id값을 받아 올 수가 없어요 ㅠㅠ
         */
        TextView jrv_title_text = (TextView)header.findViewById(R.id.jrv_title_text);
        jrv_title_text.setText("조던6 샴페인 팝니다!!");
        TextView jrv_content_text = (TextView)header.findViewById(R.id.jrv_content_text);
        jrv_content_text.setText("이름 : 조던6 샴페인\n가격 : 350,000원\n상태 : 새제품\n기타 : 문의사항 있으면 쪽지 보내주세요!");
        jrv_image_img = (ImageView)header.findViewById(R.id.jrv_image_img);
//drawable의 이미지를 Bitmap으로 바꾸는 함수입니다.
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.jd_6);
        jrv_image_img.setImageBitmap(bmp);
    }
    private void setFooter(){
//Footer도 마찬가지로 앞에 footer.를 붙여줄것!
        comment_edit = (EditText)footer.findViewById(R.id.jrv_comment_edit);
        Button commentinput_btn = (Button)footer.findViewById(R.id.jrv_commentinput_btn);
//implements를 맨위에 선언해 줬기 떄문에, setOnClickListener를 여기서 설정할 수 있습니다.
        commentinput_btn.setOnClickListener(this);
    }
    private void setList(){
        ca = new Comment_Adapter(getApplicationContext(), Community.this, Community.this, c_arr);
        comment_list.setAdapter(ca);
        comment_list.setSelection(c_arr.size()-1);
        comment_list.setDivider(null);
        comment_list.setSelectionFromTop(0,0);
    }
    @Override
    public void onClick(View v) {
//Click 되었을때 Id값으로 클릭처리를 할 수 있습니다.
//아까 setFooter에서 commentinput_btn 에 setOnClickListener를 달아 주었기 때문에 onClick이 사용 가능합니다.
        switch(v.getId()){
            case R.id.jrv_commentinput_btn:
                String temp = comment_edit.getText().toString();
                if(temp.equals("")){
                    Toast.makeText(Community.this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show();
                }else{
//EditText의 빈칸이 없을 경우 등록!
                    Comment_Item ci = new Comment_Item();
                    ci.setContent(temp);
                    ci.setNickname("닉네임");
                    c_arr.add(ci);
                    resetAdapter();
                    comment_edit.setText("");
                }
                break;
        }
    }
    public void resetAdapter(){
        ca.notifyDataSetChanged();
//Adapter의 데이터 값이 변화가 있을 때 사용하는 함수.
    }
    public void deleteArr(int p){
//Adapter에서 이 함수를 지울 떄 호출합니다. 지우고자 하는 댓글의 id 값을 넘겨주면 c_arr 의 데이터를 삭제!
        c_arr.remove(p);
//마찬가지로 변화가 있었기 때문에 Adapter 초기화(?)
        ca.notifyDataSetChanged();
    }

}
