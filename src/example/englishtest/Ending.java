//③最後の画面　（点数表示用） ※このページのみ画像表示するためグラフィックレイ
//アウトじゃない
package example.englishtest;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//ダイアログ表示用
//ダイアログ表示用
//ページ切り替え用
//画像表示のため
//画像表示のため
//効果音追加
//ボタン処理クリックのため

public class Ending extends Activity implements OnClickListener
{
	TextView tv;
	Button bt,ebt;//bt:最初から再度やる　ebt:終了ボタン
	int score =0;//最終の点数を表示する変数

	//ivｵﾌﾞｼﾞｪｸﾄ変数宣言
	ImageView iv;

	//ﾘﾆｱﾚｲｱｳﾄ
    LinearLayout layout;

    Bitmap bmp;//画像貼り付け用ｵﾌﾞｼﾞｪｸﾄ変数宣言

    Builder dialog;//ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄ変数の宣言(終了時表示用）

    MediaPlayer mp;//効果音用にメディアプレーヤクラスのMPｵﾌﾞｼﾞｪｸﾄ変数宣言

	public void onCreate(Bundle savedInstanceState)
	{
	      super.onCreate(savedInstanceState);

	      layout = new LinearLayout(this);
	      layout.setOrientation(LinearLayout.VERTICAL);//レイアウトを垂直に

          layout.setBackgroundColor(Color.BLACK);//背景色黒に
	      setContentView(layout);

	      /*ｵﾌﾞｼﾞｪｸﾄ作成　再生させるファイル名を指定　音楽ファイル形式は
	      waveでもMP3でも可能。*/
	      mp = MediaPlayer.create((Context)this, R.raw.announce);

	      mp.seekTo(0);
  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生

	      //ｲﾝﾃﾝﾄの受け取り
	      Intent in = this.getIntent();
          //inで受け取った情報をgetExtras（）で受け取る 付加情報の受け取り
	      Bundle b = in.getExtras();
	      String str =b.getString("key1");//Toiクラスのint型変数cnをString型
	      												//として受け取り
	      boolean saiten = b.getBoolean("key2");//boolean型の受け皿を準備。
	      score = Integer.parseInt(str);//受け取った値を再度int型へ

	      boolean hearing = b.getBoolean("key3");//boolean型の受け皿を準備。
	      boolean kaisetsu= b.getBoolean("key4");//boolean型の受け皿を準備。

	      /*（最後に登場するキャラクター）res→drawableフォルダに元データ格納
	      【00点の時】 ⇒りらっくま				rirakkuma
	      【01点の時】 ⇒りらっくま				rirakkuma
	      【02点の時】 ⇒くまおもーにんぐ	kumaom
	      【03点の時】 ⇒くまおらんち			kumaol
	      【04点の時】 ⇒くまおでぃな			kumaod
	      【05点の時】 ⇒くまおでざーと		kumaode
	      【06点の時】 ⇒ととろ					totorod
	      【07点の時】 ⇒きいろいとり			tori
	      【08点の時】 ⇒こりら					kori
	      【09点の時】 ⇒きゅうり				kyuri
	      【10点の時】 ⇒T店長					takasa ※完全にノーﾋﾝﾄの場合は。。。
	       */

	      //画像の貼り付け準備
	      if(score ==1)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.rirakkuma);
	      }
	      else if(score ==2)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kumaom);
		  }
	      else if(score ==3)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kumaol);
		  }
	      else if(score ==4)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kumaod);
		  }
	      else if(score ==5)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kumaode);
		  }
	      else if(score ==6)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.totorod);
		  }
	      else if(score ==7)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kitori);
		  }
	      else if(score ==8)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kori);
		  }
	      else if(score ==9)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kyuri);
		  }
	      else if(score ==10)
	      {
	    	  if(hearing && kaisetsu)//英文を聞かず　かつ解説・ﾋﾝﾄを見ない場合　
	    	  {
	    		  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.kanpeki);
		      }

	    	  else
	    	  {
	        	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.takasa);
	    	  }
		  }
	      else if(score ==0)
	      {
	    	  bmp = BitmapFactory.decodeResource(getResources(),R.drawable.rirakkuma);
		  }

	      bt = new Button(this);
		  bt.setOnClickListener(this);
		  bt.setText("最初からもう一度やる");
		  bt.setTextColor(Color.RED);

    	  ebt =new Button(this);
		  ebt.setOnClickListener(this);
		  ebt.setText("終了");
		  ebt.setTextColor(Color.BLUE);

	      tv = new TextView(this);//ﾃｷｽﾄﾋﾞｭｰｵﾌﾞｼﾞｪｸﾄ作成
	      tv.setTextColor(Color.WHITE);//ﾃｷｽﾄ色変更

	      if(saiten)//採点がtrueの場合
	     {
	    	 tv.setText("　 【成績発表】 \n　　  "+str+"/10 点");
		     tv.setTextSize(37);
	     }

	     else//採点がfalseの場合　採点できない表示を出す。
	     {
	    	 tv.setTextSize(23);
		     tv.setText("一度でも<前の問題>ボタンを押したので採点不可です。\n");

		     bmp = BitmapFactory.decodeResource(getResources(),R.drawable.totorod);
		     /*socreの変数が保持されているため、きゅうりに変更。これを書かないと
		      * 前の問題に戻るボタンが押される前まの正解数に応じて対象のキャラクタ
		      * ーが選択されてしまうため書いた*/
	     }

	      //画像のｵﾌﾞｼﾞｪｸﾄを作成

	    	  iv = new ImageView(this);
	    	  iv.setImageBitmap(bmp);

	    	//◆ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄの作成（終了するボタンを押した時に表示する仕様）***
	          dialog = new Builder(this);

	          //◆ﾀﾞｲｱﾛｸﾞにタイトルを設定
	          dialog.setTitle("★お疲れ様でした★");

	          //◆ﾀﾞｲｱﾛｸﾞにﾒｯｾｰｼﾞを設定(初期値設定)
	          dialog.setMessage("またねヾ(@^▽^@)ﾉ~~~~~~~~~");

	           //◆ﾀﾞｲｱﾛｸﾞにボタンを載せる(肯定) //リスナつんだ後はnull⇒thisへ
	          dialog.setPositiveButton("閉じる",null);//肯定のボタンを閉じるとして利用

	     //配置
	      layout.addView(tv);
	      layout.addView(iv);
	      layout.addView(bt);
	      layout.addView(ebt);
      }

	public void onClick(View v)
	{
		if(v ==bt)//（最初から再度テスト⇒Toiクラスヘインテントを使って画面を飛ばす）
		{
			Intent in = new Intent(this,Toi.class);
   	    	in.putExtra("key1","送る文字");//文字は送る必要はないが。。。

   	    	//サブのクラスに送る。
   	    	startActivityForResult(in, 1);//値を付加した場合、
   	    	finish();//普通にfinish()だけでもO.K！
		}
		else if(v ==ebt)
		{
			dialog.show();
			//this.onClick(DialogInterface.BUTTON_POSITIVE,1);
			System.exit(3);
		}
	}

	public void onClick(int buttonPositive, int i)
	{
		// TODO 自動生成されたメソッド・スタブ

    	if(i==DialogInterface.BUTTON_POSITIVE)
    	{
    		//閉じるボタンを押した時の処理　（ｱﾌﾟﾘ強制終了）
    	}
   	}
}
