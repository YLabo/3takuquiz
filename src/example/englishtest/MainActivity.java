/*◎◎********************************************************
◆タイトル		：3択10問英単語（英単語力診断テスト）
◆内容		：10問のクイズに答えて、ユーザーの英単語力を判定するプログラム
**********************************************************************

<画面構成>
①スタート画面（最初の説明画面）
②問題表示画面(メインとなる画面）)
③成績発表用画面


<ビュー類と機能説明>
･ラジオボタン1,2,3：選択肢表示（初期値はチェックが入っていない状態）
･英文を聞くボタン：押すと、解説文を読み上げてくれる。
（注意：TextToSpeechはAndroid1.6以降の機能で、動作させる実機に音声データがイ
ンストールされている必要有。
	▼参考URL
	http://techbooster.jpn.org/andriod/application/550/）
･解答するボタン：ラジオボタンに解答を入れてから押す。ラジオボタンが	選択されて
いない状態では警告のダイアログを表示
･前の問題ボタン：復習用に実装。但し、一度でも押すとスコアが表示されない仕様と
した。Q1のとき押せない状態にした。
･解説・ヒント：まったくわからない場合や解答後のポイント確認用。押すとダイアログが
出現し、解答と解説が表示される
・次の問題	：問題の先送り。Q10で解答ボタンを押した場合、表示が成績発表（成績発
表）　と変わる

<コメント>
･全部で10問なので、ファイル読込み機能の使用はなし。
･クラスは、○MainActivity　○Toi　○Endingの3つ
･問題文は、音声読み上げように答え入りのString型の配列を余分に準備した。
･効果音：成績発表時と解答ボタンを押して、正解と不正解の音を区別し鳴らすように
してある。
　▼参考URL
　https://sites.google.com/site/androidseminarpck/botan-ibento

<改善点,他実装したい機能>
・デザインを変更（現状、シンプルすぎてつまらない）。ボタン形状の変更。
	（→XMLの基礎知識の補充必須。）＝ボタンのレイアウト変更は難。
・TextToSpeech(テキスト読み上げ機能)の発音が悪い。
	①読み上げに関して：String型の＿はUnderbarと拾い読みしていまう。
	　また、英文法上の,カンマの後にスペースを適切に入れないと「カンマ」と発音する。
	②【】は英語で発音されない。

・問題文の表示アルゴリズムの改善。
　（テキストファイルからの読み込みなど何通りか方法を試してみる）
・コードの見直し　（長いので、ﾒｿｯﾄﾞの使用、配列の使用など）
･問題を一覧表示して選択できる機能
･ランダムに出題される機能
･弱点問題だけを優先的に出題する機能　（⇒エビングハウスの忘却曲線理論を利用
した学習システムにする）
*/

//①最初の画面　
package example.englishtest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity implements OnGestureListener,
OnClickListener
{
	GestureDetector gd;//ｼﾞｪｽﾁｬｰﾃﾞｨﾃｸﾀｰｵﾌﾞｼﾞｪｸﾄ変数宣言

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gd = new GestureDetector(this);//ｼﾞｪｽﾁｬｰﾃﾞｨﾃｸﾀｰｵﾌﾞｼﾞｪｸﾄ作成

        TextView tv1=(TextView)findViewById(R.id.textView1);//タイトル表示用
        TextView tv2 =(TextView)findViewById(R.id.textView2);//説明文表示用
        TextView tv3 =(TextView)findViewById(R.id.textView3);//スタート用
        tv1.setTextSize(35);
        tv1.setGravity(Gravity.CENTER);//センタ-へ
        tv1.setTextColor(Color.WHITE);//テキストの色を白色へ
        tv1.setText("◎3択10問英単語◎");//タイトル変更

        tv2.setTextColor(Color.WHITE);//テキストの色変更
	    tv2.setTextSize(15);//テキストサイズ変更
        tv2.setText//文字送り
        ("\n【説明】\n" +
       	"①空欄【】に当てはまる最も適切な単語/語句を選択肢の中から選び" +
       	"<解答する>ボタンを押してください。その後、<次の問題>ボタンを押してください。" +
       	"\n\n"+
       	"②<解説・ヒント>ボタンを押すと日本語訳と解説が表示されます。\n\n"+
       	"③<英文を聞く>ボタンを押すと、空欄【】箇所を含む英文が流れます。\n(Q1は" +
       	"サービス1回だけ英文が流れます。）\n\n"+
       	"④10問全て解答すると点数が表示されます。\n\n" +
       	"⑤画面を縦にしてご利用ください。" +
       	"\n\n【ご注意】\n" +
       	"◆1回でも<前の問題>ボタンを押すと、採点がされません。\n\n" +
       	"◆<解説・ヒント>,<英文を聞く>ボタンを使わずに全問正解すると。。。\n");

        tv3.setTextSize(18);//テキストサイズ変更
        tv3.setGravity(Gravity.CENTER);//センタ-へ
        tv3.setTextColor(Color.GREEN);//色を緑へ
        tv3.setText("★画面をタッチするとスタートします");//文字送り
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    //●画面を触ったときに呼び出されるﾒｿｯﾄﾞ
	public boolean onDown(MotionEvent e)
	{
		Intent in = new Intent(this,Toi.class);//ｲﾝﾃﾝﾄｵﾌﾞｼﾞｪｸﾄの作成。Toiクラスへ
    	in.putExtra("key1","テストで送る文字1");//実際はExtraは不要だが...

    	//サブのクラスに送る。
    	startActivityForResult(in, 1);//ｲﾝﾃﾝﾄのｵﾌﾞｼﾞｪｸﾄと1を送る。二つ目の引数
    	finish();//普通にfinish()だけでもO.K！

		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY)
	{
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void onLongPress(MotionEvent e)
	{
		// TODO 自動生成されたメソッド・スタブ
	}

	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY)
	{
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public void onShowPress(MotionEvent e)
	{
		// TODO 自動生成されたメソッド・スタブ
	}

	public boolean onSingleTapUp(MotionEvent e)
	{
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	public boolean onTouchEvent(MotionEvent me)
	{
		gd.onTouchEvent(me);
		return true;
	}

	public void onClick(View v)
	{
		// TODO 自動生成されたメソッド・スタブ
	}
}