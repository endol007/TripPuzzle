package com.hanium.AguideR;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GameMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_main);

        Button inventorybtn = findViewById(R.id.inventorybtn);
        Button comubtn = findViewById(R.id.comubtn);

            comubtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(GameMain.this,Community.class);
                        startActivity(intent);
                }
            }
            );

            inventorybtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GameMain.this,Inventory.class);
                    startActivity(intent);
                }
            });

    }
}
