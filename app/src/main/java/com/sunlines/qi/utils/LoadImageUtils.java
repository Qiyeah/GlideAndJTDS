package com.sunlines.qi.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.sunlines.qi.keydemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by sunline on 2016/6/14.
 */
public class LoadImageUtils {
    public static final int MAIN = 0;
    public static final int PAGE = 1;


    private Context mContext;
    public LoadImageUtils(Context context){
        mContext = context;
    }

    public void setBackground(final File file, final int viewType, final View view){
        new AsyncTask<Void,Void,Bitmap>(){
            private Bitmap bitmap;
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    if (PAGE == viewType){
                        bitmap = Glide.with(mContext).load(file).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).placeholder(R.drawable.error).into(430,270).get();
                    }else if (MAIN == viewType){
                        bitmap = Glide.
                                with(mContext).
                                load(file).
                                asBitmap().
                                skipMemoryCache(true).
                                diskCacheStrategy(DiskCacheStrategy.RESULT).
                                placeholder(R.drawable.error).
                                into(600,320).
                                get();
                    }else {
                        bitmap = Glide.with(mContext).load(file).asBitmap().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESULT).into(480,270).get();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (null != bitmap){
                    view.setBackground(new BitmapDrawable(bitmap));
                }
            }
        }.execute();
    }
    private Bitmap mBitmap;
    private String ss = "";
    public Bitmap decodeByFile(final File file){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("thread is run");
                    ss = "new thread";
                    mBitmap = Glide.
                            with(mContext).
                            load(file).
                            asBitmap().
                            skipMemoryCache(true).
                            diskCacheStrategy(DiskCacheStrategy.RESULT).
                            placeholder(R.drawable.error).
                            into(600,320).
                            get();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
       /* try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("ss = "+ss);
        return mBitmap;
    }
    public byte[] getByte(String fileName) {
        byte[] bt = null;
        InputStream inputStream = null;
        AssetManager manager = mContext.getResources().getAssets();
        ByteArrayOutputStream out = null;
        try {
            inputStream = manager.open(fileName);
            out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int len = 0;
            while ((len = inputStream.read(b)) != -1) {
                out.write(b, 0, len);
            }
            bt = out.toByteArray();
            //Log.i("byteArray", ""+bt);
        } catch (Exception e) {
            //e.printStackTrace();
            try {
                if (inputStream != null)
                    inputStream.close();
                if (out != null)
                    out.close();
            } catch (IOException e1) {
                //e1.printStackTrace();
            }
        }
        out = null;
        manager = null;
        return bt;
    }
    public void assetsApply2ImageView(String fileName, ImageView view){
        byte[] bytes = getByte(fileName);
        //Toast.makeText(mContext, "bytes.len = "+bytes.length, Toast.LENGTH_SHORT).show();
        Glide.with(mContext)
                .load(bytes)
                .bitmapTransform(new RoundedCornersTransformation(mContext,10,10))
                .override(720,480)
                .error(R.drawable.error)
                .into(view);
    }
}
