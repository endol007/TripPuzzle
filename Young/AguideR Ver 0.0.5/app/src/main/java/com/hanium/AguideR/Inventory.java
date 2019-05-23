package com.hanium.AguideR;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Inventory extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);
        GridView gridView=(GridView)findViewById(R.id.itemlist);

        gridView.setAdapter(new ImageAdapter(this));
    }

    //인벤토리 아이템 뷰 어댑터
    public class ImageAdapter extends BaseAdapter{
        private Context context ;

        private Integer [] images = {R.drawable.spear,R.drawable.sword,R.drawable.pistol};
        //인벤토리 아이템 목록

        public ImageAdapter(Context con){
            this.context = con ;
        }

        public int getCount(){
            return images.length;
        } //아이템 갯수

        public Object getItem(int pos){
            return null;
        }

        public long getItemId(int pos){
            return 0;
        }

        public View getView(int pos, View convertView, ViewGroup parent){
            ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(90,90));
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(2,2,2,2);

            }
            else{
                imageView = (ImageView)convertView;
            }
            imageView.setImageResource(images[pos]);
            return imageView;
        }

    }
}
