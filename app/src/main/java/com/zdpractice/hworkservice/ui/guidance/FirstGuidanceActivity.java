package com.zdpractice.hworkservice.ui.guidance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.zdpractice.hworkservice.R;

public class FirstGuidanceActivity extends AppCompatActivity {

    private YinDaoSharedHelper yinDaoSharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
        //取消状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.grad_yindao);
        yinDaoSharedHelper =new YinDaoSharedHelper(getApplicationContext());
        yinDaoSharedHelper.save("123");
        Button btn = (Button) findViewById(R.id.btnKaishi);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstGuidanceActivity.this,GuidanceActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
