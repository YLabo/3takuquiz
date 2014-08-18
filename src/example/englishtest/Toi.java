//②最初の画面
package example.englishtest;

import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
//ダイアログ表示用のためｲﾝﾎﾟｰﾄ
//音声読み上げ用
															//音声読み上げよう
public class Toi extends Activity implements OnClickListener,TextToSpeech.OnInitListener
{
	//◆ﾋﾞｭｰ類**********************************************************
	Button kbt;//解答ボタン
	Button mbt,sbt;//前の問題へ（戻るボタン）　次の問題へ（進むボタン）
	Button exbt;//解説ボタン
	Button hbt;//ヒアリングボタン　（読み上げ機能用）
	TextView tv1,tv2,tv3,tv4;/*tv1問題数　tv2問題文表示用　tv3答え、解説表示用
								            tv4不正解の時の正解表示用*/
	LinearLayout layout;//ﾘﾆｱﾚｲｱｳﾄ
	RadioButton rb1,rb2,rb3;//ﾗｼﾞｵﾎﾞﾀﾝ　（3択）
	RadioGroup rg;//ﾗｼﾞｵﾎﾞﾀﾝｸﾞﾙｰﾌﾟ
	Intent in;//ｲﾝﾃﾝﾄ　（画面切り替え用　①ｽﾀｰﾄ画面　②問題画面　③最終成績）

	Builder dialog;//ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄ変数の宣言(解説･ヒント表示用）
	Builder alertdialog;//ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄ変数の宣言(ﾗｼﾞｵﾎﾞﾀﾝが選択されていない
								//時の表示用）
	TextToSpeech tts;//音声読み上げ用のクラスのｵﾌﾞｼﾞｪｸﾄ変数宣言
	MediaPlayer mp;//効果音用
 //********************************************************

	//◆変数類**********************************************************
	int q=1; //何問目かを管理する変数　（q uestion)
	int cn =0;//正解数を管理する変数 (c orrect number)
	int sn =0;//ユーザーが選択した肢の番号を管理する変数 (s number):
	String saitemn="OK";//戻るボタンが一度でも押されたら、"NG"にする。採点不可
	boolean saiten =true; //戻るﾎﾞﾀﾝが押されたらtrueにする。falseのとき採点不可。
	boolean hearing =true; //ﾋｱﾘﾝｸﾞﾎﾞﾀﾝが押されたらfalseにする。ﾊﾟｰﾌｪｸﾄ解答
									//（＝ﾋﾝﾄなし）の時用
	boolean kaisetsu =true; //解説・ﾋﾝﾄﾎﾞﾀﾝが押されたらfalseにする。ﾊﾟｰﾌｪｸﾄ解答
									//（＝ﾋﾝﾄなし）の時用
	//******************************************************************

	//◆問題文**********************************************************
	String toi[] =
	{//1問目　（配列toiの要素番号0)
	"【　　　】system failure, please contact the imformation technology department imm" +
	"ediately.",

    //2問目　（配列の要素番号1)　	//表示位置統一のため 2.3.5.6.8.9.10問目
	//\n（ｴｽｹｰﾌﾟｼｰｹﾝｽ使用）
	"We need to find out【　　　】the new area manager will be.\n",

	//3問目　（配列toiの要素番号2)
	"If we don't sell all our stock now, we【　　　】lose everything.\n",

	//4問目　（配列toiの要素番号3)
	"When I lived in Italy last July, I【　　　】a beach-side villa for the whole time.",

	//5問目　（配列toiの要素番号4)
	"Do you think Steve【　　　】be hired for the new vice-president's position?\n ",

	//6問目　（配列toiの要素番号5)
	"When I returned home I discovered that I【　　　】my wallet at the party.\n",

	//7問目　（配列toiの要素番号6)
	"These are the types of problems that【　　　】eliminated before production begins.",

	//8問目　（配列toiの要素番号7)
	"The new lighting system in the studio【　　　】very beautiful.\n",

	//9問目　（配列toiの要素番号8)
		"Those kids have got to understand that their homework is an【　　　】. \n",

	//10問目　（配列toiの要素番号9)
	    "Everyone at the party was in a【　　　】mood. \n"};
	//********************************************************************

	//◆問題文(読み上げ用）************************************************
		String toiyomi[] ={

		//Toiクラスに切り替わった瞬間に流れる説明英文　（配列toiの要素番号0)
		//"Choose one word or phrase that best completes the sentence.",

		//1問目　（配列toiの要素番号0)
		"【In event the of】system failure, please contact the imformation technology " +
		"department imm" +
		"ediately.",

	    //2問目　（配列の要素番号1)　	//表示位置統一のため 2.3.5.6.8.9.10問目\n
		//（ｴｽｹｰﾌﾟｼｰｹﾝｽ使用）
		"We need to find out【who】the new area manager will be.\n",

		//3問目　（配列toiの要素番号2)
		"If we don't sell all our stock now, we【will】lose everything.\n",

		//4問目　（配列toiの要素番号3)
		"When I lived in Italy last July, I【rented】a beach-side villa for the whole time.",

		//5問目　（配列toiの要素番号4)
		"Do you think Steve【will】be hired for the new vice-president's position?\n ",

		//6問目　（配列toiの要素番号5)
		"When I returned home I discovered that I【had left】my wallet at the party.\n",

		//7問目　（配列toiの要素番号6)
		"These are the types of problems that【need to be】eliminated before production" +
		" begins.",

		//8問目　（配列toiの要素番号7)
		"The new lighting system in the studio【is】very beautiful.\n",

		//9問目　（配列toiの要素番号8)
			"Those kids have got to understand that their homework is an【obligation】. \n",

		//10問目　（配列toiの要素番号9)
		    "Everyone at the party was in a【festive】mood. \n"};
		//****************************************************************

	//◆選択肢***********************************************************
		String asi[][] ={
		{"On behalf of","In case","In the event of"},//問1の肢
		{" whom","who's","who"},							//問2の肢
		{"are","did","will"},										//問3の肢
		{"rent ","rented","had been renting"},			//問4の肢
		{"would","will","must"},								//問5の肢
		{"leave","did leave","had left"},					//問6の肢
		{"need","need to be","needs to be"},			//問7の肢
		{"are","is","is being"},								//問8の肢
		{"obligate","obligation","obligator"},				//問9の肢
		{"festive ","fertile","furtive"},						/*問10の肢*/};
	//*******************************************************************

	//◆正解*************************************************************
		String answer[] ={
		"In event the of",//問1の答え
		"who",//問2の答え
		"will",//問3の答え
		"rented",//問4の答え
		"will",//問5の答え
		"had left",//問6の答え
		"need to be",//問7の答え
		"is",//問8の答え
		"obligation",//問9の答え
		"festive"};//問10の答え
		//問1～問10までの正解 (ﾗｼﾞｵﾎﾞﾀﾝの上から1,2,3とする
		int ans[]={3,3,3,2,2,3,2,2,2,1};
	//*******************************************************************

	//◆日本語訳（ダイアログ表示表)*****************************************
		String yaku[] ={
		//問1の訳
		"万一システム障害の場合には、速やかに情報技術部にへご連絡下さい",

		//問2の訳
		"新しい支部長が誰になるのか探らなくてはならない。",

		//問3の訳
		"すぐに在庫を全部売らなければ、私たちはすべてを失うでしょう。 ",

		//問4の訳
		"去年の 7 月にイタリアで過ごした時は、浜辺の別荘をずっと借りていました。",

		//問5の訳
		"スティーブが新副社長の地位に雇われると思いますか。",

		//問6の訳
		"家に帰った時、財布をパーティーに忘れてきたことが判りました。",

		//問7の訳
		"これらが生産を開始する前に取り除かれなければならない問題の種類です。",

		//問8の訳
		"スタジオの新しい照明システムは大変に美しい。",
		//問9の訳

		"あの子たちは宿題が義務であることを判らなければいけない。 ",

		//問10の訳
		"パーティーの全員がお祭りムードでした。 ",
		};
	//********************************************************************

	//◆解説･ヒント********************************************************
		String kaisetu[] =
		{
		//問1の解説
		"in the event ofで「万が一～の場合には」の意味。in case(万一～の場合には)は、" +
		"<主語+述語>が続くので注意。",

		//問2の解説
		"「誰が…だ」という時の疑問代名詞。あとの will be という（助）動詞に対する主格" +
		"が必要。",

		//問3の解説
		"lose が原形動詞なのでareは不可。意味上didも無理がある。If に続く節が don't " +
		"なので、would にする必要はない。",

		//問4の解説
		"難しく考えないように。普通に時制を一致させる。明確に定まった過去を表す when " +
		"節とともに現在完了は用いない。",

		//問5の解説
		"意味上適切なものはひとつ。would は時制が問題。",

		//問6の解説
		"全体が過去形で統一されているので、leaveは不可。leftはleaveの過去および過去分" +
		"詞。",

		//問7の解説
		"関係代名詞 that の示すものは types で複数形であるから、needs は続かない。" +
		"eliminated「取り除かれる」と受け身のニュアンスであるので、前に be 動詞がある" +
		"と推測できる。",

		//問8の解説
		"主語の単複を考える。system が本主語で、単数形。それ以上は何も難しく考える" +
		"必要はない。",

		//問9の解説
		"「義務」という意味の名詞になるように語尾を考える。",

		//問10の解説
		"party での話だから、fury「怒り」から派生している語よりも、festival「祭り」に関連した" +
		"語を選びたい。",
		};
	//***********************************************************************

	public void onCreate(Bundle savedInstanceState)
	{
      super.onCreate(savedInstanceState);
      setContentView(R.layout.toi);

      //◆ボタンｵﾌﾞｼﾞｪｸﾄ作成
      kbt = (Button)findViewById(R.id.button1);//解答ボタン
      mbt = (Button)findViewById(R.id.button2);//戻るボタン
      sbt = (Button)findViewById(R.id.button3);//進むボタン
      exbt= (Button)findViewById(R.id.button4);//解説ボタン
      hbt = (Button)findViewById(R.id.button5);//ヒアリングボタン

      //リスナ追加
      kbt.setOnClickListener(this);//解答ボタン
      mbt.setOnClickListener(this);//戻るボタン
      sbt.setOnClickListener(this);//進むボタン
      exbt.setOnClickListener(this);//解説ボタン
      hbt.setOnClickListener(this);//ヒアリングボタン
      //ボタン色変更
      /*kbt.setBackgroundColor(Color.GRAY);
      mbt.setBackgroundColor(Color.GREEN);
      sbt.setBackgroundColor(Color.RED);
      exbt.setBackgroundColor(Color.YELLOW);　ボタン形状が変わるため
      無効にした。*/
      kbt.setTextColor(Color.BLACK);
      mbt.setTextColor(Color.GREEN);
      sbt.setTextColor(Color.RED);
      exbt.setTextColor(Color.YELLOW);
      hbt.setTextColor(Color.BLUE);
      mbt.setEnabled(false);//戻るボタンのみ、問1初期値として無効にする

    //◆ﾃｷｽﾄﾋﾞｭｰ作成
      tv1 = (TextView)findViewById(R.id.textView1);//問題数表示用
      tv2 = (TextView)findViewById(R.id.textView2);//問題文表示用
      tv3 = (TextView)findViewById(R.id.textView3);//答え、解説表示用
      tv3.setText("");//初期値空欄に
      tv3.setTextSize(50);//サイズ変更
      tv3.setTextColor(Color.RED);//色変更　（黒⇒赤）
      tv4 = (TextView)findViewById(R.id.textView4);//不正解時の正解表示用
      tv4.setText("");//初期値空欄に
      tv4.setTextColor(Color.RED);//色変更　（黒⇒赤）
      //初期値を設定
      tv1.setText("Q"+1);//問題数に何問目かの数字をｾｯﾄ
	  tv2.setText(toi[0]);//問題に問題文をｾｯﾄ
	  //ﾃｷｽﾄ色変更
	  tv1.setTextColor(Color.WHITE);
	  tv2.setTextColor(Color.WHITE);

    //◆ﾗｼﾞｵﾎﾞﾀﾝｵﾌﾞｼﾞｪｸﾄ作成
	  rb1 = (RadioButton)findViewById(R.id.radio0);//1番目の肢
      rb2 = (RadioButton)findViewById(R.id.radio1);//2番目の肢
      rb3 = (RadioButton)findViewById(R.id.radio2);//3番目の肢

      //◆ﾗｼﾞｵﾎﾞﾀﾝｵﾌﾞｼﾞｪｸﾄ作成
      rb1.setText(asi[0][0]);//1番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット
	  rb2.setText(asi[0][1]);//2番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット;
	  rb3.setText(asi[0][2]);//3番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット
    	//リスナ搭載
	  rb1.setOnClickListener(this);
	  rb2.setOnClickListener(this);
	  rb3.setOnClickListener(this);
	  //初期値として何も選択されていない状態にしておく
	  rb1.setChecked(false);
	  rb2.setChecked(false);
	  rb3.setChecked(false);
	  //背景色変更
	  rb1.setBackgroundColor(Color.BLACK);
	  rb2.setBackgroundColor(Color.BLACK);
	  rb3.setBackgroundColor(Color.BLACK);
	//ﾃｷｽﾄ色変更
	  rb1.setTextColor(Color.WHITE);
	  rb2.setTextColor(Color.WHITE);
	  rb3.setTextColor(Color.WHITE);

	  // ◆ﾗｼﾞｵﾎﾞﾀﾝｸﾞﾙｰﾌﾟｵﾌﾞｼﾞｪｸﾄ作成
      rg =(RadioGroup)findViewById(R.id.radioGroup1);

    //◆ｲﾝﾃﾝﾄの受け取り*************************************************
      in = this.getIntent();
    //inで受け取った情報をgetExtras（）で受け取る 付加情報の受け取り
      Bundle b = in.getExtras();
      String str =b.getString("key1");//受け取る必要ないが。。。

    //◆ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄの作成（解説ボタンを押した時に表示する仕様）***
      dialog = new Builder(this);

      //◆ﾀﾞｲｱﾛｸﾞにタイトルを設定
      dialog.setTitle("3択10問英単語　解説･ヒント");

      //◆ﾀﾞｲｱﾛｸﾞにﾒｯｾｰｼﾞを設定(初期値設定)
      dialog.setMessage("【日本語訳】\n"+yaku[0]+"\n"+"\n"+"【解説】\n"+kaisetu[0]);

       //◆ﾀﾞｲｱﾛｸﾞにボタンを載せる(肯定) //リスナつんだ後はnull⇒thisへ
      dialog.setPositiveButton("閉じる",null);//肯定のボタンを閉じるとして利用

      //*****************************************************************

      //◆ﾀﾞｲｱﾛｸﾞｵﾌﾞｼﾞｪｸﾄの作成（ﾗｼﾞｵﾎﾞﾀﾝ未選択の場合に表示される用)***
      alertdialog = new Builder(this);//【10/18追加】

      //◆ﾀﾞｲｱﾛｸﾞにタイトルを設定
      alertdialog.setTitle("警告");

    //◆ﾀﾞｲｱﾛｸﾞにﾒｯｾｰｼﾞを設定(初期値設定)
      alertdialog.setMessage("選択肢から答えが一つも選択されていません。三択の" +
      		"選択肢の中から最も適切な回答にチェックを入れてから<解答する>ボタンを" +
      		"押してください。");

      //◆ﾀﾞｲｱﾛｸﾞにボタンを載せる(肯定) //リスナつんだ後はnull⇒thisへ
      alertdialog.setPositiveButton("閉じる",null);//肯定のボタンを閉じるとして利用

    // ****************************************************************

		//◆音声読みあげ*************************************************
      tts = new TextToSpeech(this,this);
	}

	public void onClick(View v)
	{// TODO 自動生成されたメソッド・スタブ

	//◆ﾗｼﾞｵﾎﾞﾀﾝの処理**************************************************
		if(v == rb1)	{sn = 1;}	//ﾗｼﾞｵﾎﾞﾀﾝ1が押されたときの処理 snを1に
		else if(v == rb2){	sn =2;}//ﾗｼﾞｵﾎﾞﾀﾝ2が押されたときの処理snを2に
		else if(v == rb3){sn =3;}//ﾗｼﾞｵﾎﾞﾀﾝ3が押されたときの処理snを3に
	//*****************************************************************

	//◆ﾎﾞﾀﾝの処理******************************************************
		else if(v ==mbt)//戻るボタン（次の問題）が押されたときの処理
		{
			//ﾗｼﾞｵﾎﾞﾀﾝがなにも選択されていない状態にする。
		    rb1.setChecked(false);rb2.setChecked(false);rb3.setChecked(false);

		    /*何番目のラジオボタンが選択されているかを一度0にする
		    (このコードを書かないとラジオボタン未選択時の警告表示が問題
		    が切り替わったときに適切に表示されない*/
		    sn =0;

			saiten = false;//戻るボタンが一度でも押されたら、フラグをfalseに変更。
			kbt.setEnabled(false);//解答ボタンを押せるように！

			  if(q ==2)
		  	  {
				//問2のときに戻るボタンが押されたら
				  mbt.setEnabled(false);//戻るボタンを有効に
				  kbt.setEnabled(true);//解答ボタンを有効に
				  q--;
			  }

			  else if(q ==10)
			  {
				//10問目から戻るボタンを押した場合、成績表示から次の問題へに表示修正
				  sbt.setText("次の問題へ");
				  kbt.setEnabled(true);//解答ボタンを有効に
				  q--;
			  }

			  else
			  {
				  mbt.setEnabled(true);//1問目から戻るボタンを有効にする。
				  kbt.setEnabled(true);//解答ボタンを有効に
				  q--;//問題番号を減らす

			  }

		  tv1.setText("Q"+q);//問題数に何問目かの数字をｾｯﾄ
       	  tv2.setText(toi[(q-1)]);//問題に問題文をｾｯﾄ

		  rb1.setText(asi[q-1][0]);//1番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット
		  rb2.setText(asi[q-1][1]);//2番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット;
		  rb3.setText(asi[q-1][2]);//3番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット

		  tv3.setText("");//ﾃｷｽﾄﾋﾞｭｰを空にする。
		  tv4.setText("");//ﾃｷｽﾄﾋﾞｭｰを空にする。
		}

		else if(v ==kbt)//解答ボタンが押されたときの処理
		{
			//ﾗｼﾞｵﾎﾞﾀﾝの番号が1,2,3でない場合の処理（ﾗｼﾞｵﾎﾞﾀﾝ未選択の場合）
			if(  !  (sn ==1 | sn ==2 | sn==3) )
			{
				alertdialog.show();//ダイアログ表示(ラジオボタン選択するよう警告）
				mp = MediaPlayer.create((Context)this, R.raw.pui);
				mp.seekTo(0);
		  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
			}

			else//ﾗｼﾞｵﾎﾞﾀﾝが選択されている時の処理 (1,2,3のいずれかが選択されている）
			{	//何問目かによって、選択したﾗｼﾞｵﾎﾞﾀﾝ番号と答えが合っているか
				//あっていないかを判別。あっていた場合は、ｃｎを増やす。
				if(q ==1)
				{if(sn ==ans[0]){cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
				tv3.setTextColor(Color.BLUE);tv3.setText("\n　 ○正解");mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　  正解:In event of");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==2)
				{
					if(sn ==ans[1])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　　 正解:who");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==3)
				{
					if(sn ==ans[2])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　　正解:will");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==4)
				{
					if(sn ==ans[3])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　 正解:rented");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==5)
				{
					if(sn ==ans[4])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　　正解:will");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==6)
				{
					if(sn ==ans[5])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　 正解:had left");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==7)
				{
					if(sn ==ans[6])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");
						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　正解:need to be");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==8)
				{
					if(sn ==ans[7])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　　 正解:is");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==9)
				{
					if(sn ==ans[8])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　　正解:obligation");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}

				if(q ==10)
				{
					if(sn ==ans[9])
					{	cn++;kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.BLUE);	tv3.setText("\n　 ○正解");

						mp = MediaPlayer.create((Context)this, R.raw.seikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
					else
					{	kbt.setEnabled(false);//解答ボタンを押せなくする
						tv3.setTextColor(Color.RED);tv3.setText("\n　 ×不正解");
						tv3.setTextSize(50);
						tv4.setTextSize(30);tv4.setText("　 　正解:festive");

						mp = MediaPlayer.create((Context)this, R.raw.fuseikai);
						mp.seekTo(0);
				  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
					}
				}
			}//if終わり
		}//メソッド終わり

		else if(v ==exbt)//解説ボタンが押されたときの処理　（ダイアログを表示）
		{
			kaisetsu = false;//解説・ﾋﾝﾄボタンが一度でも押されたら、フラグをfalseに変更。

			 dialog.show();//ダイアログ表示(解説･ヒント表示）

				mp = MediaPlayer.create((Context)this, R.raw.kaisetu);//解説ボタンを押したときになる
				mp.seekTo(0);
		  		  if(!mp.isPlaying()) mp.start();//もし再生されてない場合、再生
		}

		else if(v ==hbt)//英文を聞くボタンが押されたときの処理
		{
			hearing = false;//英文を聞くボタンが一度でも押されたら、フラグをfalseに変更。

			 if(q ==1)
			 {			//  ↓ここの数字が問題配列変数の要素番号となる
				 this.onInit(0);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==2)
			 {
				 this.onInit(1);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==3)
			 {
				 this.onInit(2);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==4)
			 {
				 this.onInit(3);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==5)
			 {
				 this.onInit(4);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==6)
			 {
				 this.onInit(5);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==7)
			 {
				 this.onInit(6);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==8)
			 {
				 this.onInit(7);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==9)
			 {
				 this.onInit(8);//このクラスのonInit(int型)呼び出し
			 }
			 else if(q ==10)
			 {
				 this.onInit(9);//このクラスのonInit(int型)呼び出し
			 }
		}

		else if(v ==sbt)//進むボタン（次の問題）が押されたときの処理
		{
			kbt.setEnabled(true);//解答ボタンを押せるようにボタンの状態を変更。

			//ﾗｼﾞｵﾎﾞﾀﾝがなにも選択されていない状態にする。
		    rb1.setChecked(false);rb2.setChecked(false);rb3.setChecked(false);

		    /*何番目のラジオボタンが選択されているかを一度0にする
		    (このコードを書かないとラジオボタン未選択時の警告表示が問題
		    が切り替わったときに適切に表示されない*/
		    sn =0;

		   		if(q ==10)//問10のときに次が押されたら成績ページへ
    	  		{
		   			Intent in = new Intent(this,Ending.class);
		   	    	in.putExtra("key1",""+cn);//二つ目の引数はString型。
		   	    	in.putExtra("key2",saiten);//二つ目の引数はboolean型。//10/18追加
		   	    	in.putExtra("key3",hearing);//二つ目の引数はboolean型。//10/18追加
		   	    	in.putExtra("key4",kaisetsu);//二つ目の引数はboolean型。//10/18追加

		   	    	//サブのクラスに送る。
		   	    	startActivityForResult(in, 1);//値を付加した場合、
		   	    	finish();//普通にfinish()だけでもO.K！
    	  		}

		   		else if(q==9) //Q10になった瞬間<次の問題>から成績発表へ文字変更。
		   		{			  //Q9の状態の時に<次の問題>を押したとき。
		   			sbt.setText("成績発表");//次の問題ボタンの名前を成績発表に変更。
		   			q++;
		   		}

		   		else if(q==1)
		   		{
		   			mbt.setEnabled(true);//1問目の時に2問への切りかわった時戻るボタンを有効に
		   			q++;
		   		}

              	else{q++;}//qの番号を増やす。
                  tv1.setText("Q"+q);//問題数に何問目かの数字をｾｯﾄ
		       	  tv2.setText(toi[(q-1)]);//問題に問題文をｾｯﾄ

		       	  rb1.setText(asi[q-1][0]);//1番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット
		    	  rb2.setText(asi[q-1][1]);//2番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット;
		    	  rb3.setText(asi[q-1][2]);//3番目のﾗｼﾞｵﾎﾞﾀﾝに文字セット

		    	  tv3.setText("");//ﾃｷｽﾄﾋﾞｭｰを空にする。
		    	  tv4.setText("");//ﾃｷｽﾄﾋﾞｭｰを空にする。
    	}
   	    //◆ﾀﾞｲｱﾛｸﾞにﾒｯｾｰｼﾞを設定
        for(int i=0;i<10;i++)
        {
        	if(q ==i)//何問目からによって、訳表示と解説表示の配列を指定し表示
        	dialog.setMessage("【日本語訳】\n"+yaku[(i-1)]+"\n"+"\n"+"【解説】" +
        	"\n"+kaisetu[(i-1)]);
        }
	}

	//音声読み上げ用　※初期設定
	public void onInit(int status)
	{
		// TODO 自動生成されたメソッド・スタブ
		Locale loc = new Locale("en",",");

		if(tts.isLanguageAvailable(loc)>= TextToSpeech.LANG_AVAILABLE)
		{
		    tts.setLanguage(loc);
		}
			//↓音声読み上げする文字　（引数の型はString型）
		    tts.speak(toiyomi[status], TextToSpeech.QUEUE_FLUSH, null);
		    //onInit（）メソッドのstatusのパラメーター（仮引数）で受け取った
		    //番号を問題文の変数toiの要素番号として設定。
	}

    @Override
    protected void onDestroy()
    {
     super.onDestroy();
     tts.shutdown();
    }
}