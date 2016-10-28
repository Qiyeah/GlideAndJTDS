package com.sunlines.qi.keydemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;

import com.sunlines.qi.utils.LoadImageUtils;

/**
 * Created by temporary on 2016/10/28.
 */

public class NextActivity extends AppCompatActivity {
    ImageView mImageView = null;
    Button alpha,rotate,scale,translate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        mImageView = (ImageView) findViewById(R.id.next_img);
        LoadImageUtils utils = new LoadImageUtils(this);
        utils.assetsApply2ImageView("img2.jpg", mImageView);
    }
}
