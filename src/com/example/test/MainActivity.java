package com.example.test;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 非同期通信でPOSTリクエストをする
 *
 */
public class MainActivity extends Activity implements OnClickListener {

  private Button btn = null;
  private TextView tv = null;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn = (Button)findViewById(R.id.btn1);
    tv = (TextView)findViewById(R.id.tv1);

    btn.setOnClickListener(this);
  }

  @Override
  public void onClick(View v) {
    // ボタン押下時
    if( v == btn )
    {
      exec_post();
    }
  }

  // POST通信を実行（AsyncTaskによる非同期処理を使うバージョン）
  private void exec_post() {

    // 非同期タスクを定義
    HttpPostTask task = new HttpPostTask(
      this,
      "http://10.0.2.2/android_post_test.php",

      // タスク完了時に呼ばれるUIのハンドラ
      new HttpPostHandler(){

        @Override
        public void onPostCompleted(String response) {
          // 受信結果をUIに表示
          tv.setText( response );
        }

        @Override
        public void onPostFailed(String response) {
          tv.setText( response );
          Toast.makeText(
            getApplicationContext(),
            "エラーが発生しました。",
            Toast.LENGTH_LONG
          ).show();
        }
      }
    );
    task.addPostParam( "post_1", "ユーザID" );
    task.addPostParam( "post_2", "パスワード" );

    // タスクを開始
    task.execute();

  }
}
