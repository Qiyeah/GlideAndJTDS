package com.sunlines.qi.keydemo;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {
    ImageView mImageView = null;
    Button alpha,rotate,scale,translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectTask dt = new ConnectTask();
        dt.execute();

        /*alpha = (Button) findViewById(R.id.alpha);
        rotate = (Button) findViewById(R.id.rotate);
        scale = (Button) findViewById(R.id.scale);
        translate = (Button) findViewById(R.id.translate);

        mImageView = (ImageView) findViewById(R.id.main_img);
        LoadImageUtils utils = new LoadImageUtils(this);
        utils.assetsApply2ImageView("img1.jpg", mImageView);

        alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_alpha);
                mImageView.startAnimation(animation);
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_rotate);
                mImageView.startAnimation(animation);
            }
        });
        scale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_scale);
                mImageView.startAnimation(animation);
            }
        });
        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.tween_translate);
                mImageView.startAnimation(animation);
            }
        });*/



       /* Animation animation = AnimationUtils.loadAnimation(this, R.anim.app_clean_animation);
        mImageView.startAnimation(animation);
        animation = null;*/
    }

    class ConnectTask extends AsyncTask<Integer, Integer, String>
    {
        Connection con = null;
        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //建立于数据库的连接
            try {
                Class.forName( "net.sourceforge.jtds.jdbc.Driver");
                con = DriverManager.getConnection( "jdbc:jtds:sqlserver://192.168.1.63:1433/test1", "sa", "tiger");    //10.0.2.2是使用模拟器是用的本机（pc）的IP，androidSample是我的数据库名称
                testConnection(con);//测试数据库连接
            } catch (ClassNotFoundException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                Log.e("Class.forName::ClassNotFoundException", e1.getMessage());
            }catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return "执行完毕";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        public void testConnection(Connection con) throws java.sql.SQLException {
            try {
                String sql = "SELECT * FROM Equipment";//查询表名为“Seed.Person”的所有内容
                Statement stmt = con.createStatement();//创建Statement
                ResultSet rs = stmt.executeQuery(sql);//ResultSet类似Cursor

                while (rs.next()) {//<CODE>ResultSet</CODE>最初指向第一行
                    Log.e("&&&&&", rs.getString("id"));
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage().toString());
            } finally {
                if (con != null)
                    try {
                        con.close();
                    } catch (SQLException e) {
                    }
            }
        }
    }
}
