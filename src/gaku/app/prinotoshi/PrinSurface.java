package gaku.app.prinotoshi;

import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class PrinSurface extends SurfaceView implements SurfaceHolder.Callback {
    // このサンプルでは実行間隔を 0.016秒間隔（約 60 fps に相当）に設定してみた
    private static final long INTERVAL_PERIOD = 16;
    private ScheduledExecutorService scheduledExecutorService;
    private static final float FONT_SIZE = 24f;
    private Paint paintCircle, paintFps;
    private float x, y, r;
    private ArrayList<Long> intervalTime = new ArrayList<Long>(20);
    private float touchX = 0;
    private float touchY = 0;
    private int itemFlickFlag = 0;
    private int FlickFlag = 0;
    private float SetX = 0;
    private float SetY = 0;
    private int xFlickFlag = 0;
    private int yFlickFlag = 0;

    private SurfaceHolder holder;
    //画像読み込み
    Resources res = this.getContext().getResources();
    Bitmap prin = BitmapFactory.decodeResource(res, R.drawable.purin_2);
    Bitmap prin2 = BitmapFactory.decodeResource(res, R.drawable.prin2);
    Bitmap sara = BitmapFactory.decodeResource(res, R.drawable.sara);
    Bitmap desk = BitmapFactory.decodeResource(res, R.drawable.desk);

    public PrinSurface(Context context) {
        super(context);
        init();
    }

    public PrinSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PrinSurface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /*
     * コンストラクター引数が1〜3個の場合のすべてで共通となる初期化ルーチン
     */
    private void init() {
        /*
         * このクラス（SurfaceViewTest）では、SurfaceView の派生クラスを定義するだけでなく、
         * SurfaceHolder.Callback インターフェイスのコールバックも実装（implement）しているが、
         * SurfaceHolder であるこのクラスのインスタンスの呼び出し元のアクティビティ（通常はMainActivity）
         * に対して、関連するコールバック（surfaceChanged, surfaceCreated, surfaceDestroyed）
         * の呼び出し先がこのクラスのインスタンス（this）であることを呼出元アクティビティに登録する。
         */
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // fps 計測用の設定値の初期化
        for (int i = 0; i < 19; i++) {
            intervalTime.add(System.currentTimeMillis());
        }

        // 描画に関する各種設定
        paintCircle = new Paint();
        paintCircle.setStyle(Style.FILL);
        paintCircle.setColor(Color.WHITE);
        paintCircle.setAntiAlias(false);
        paintFps = new Paint();
        paintFps.setTypeface(Typeface.DEFAULT);
        paintFps.setTextSize(FONT_SIZE);
        paintFps.setColor(Color.BLACK);
        paintFps.setAntiAlias(true);
    }

    // コールバック内容の定義 (1/3)
    @Override
    public void surfaceCreated(final SurfaceHolder surfaceHolder) {
        x = 0;
        y = (float) (getHeight() / 3);
        DrawSurface(surfaceHolder);
        desk= Bitmap.createScaledBitmap(desk, getWidth(), getHeight(), true);
    }

    // コールバック内容の定義 (2/3)
    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
    }

    // コールバック内容の定義 (3/3)
    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        // SingleThreadScheduledExecutor を停止する
        scheduledExecutorService.shutdown();

        // 呼出元アクティビティ側のこのクラスのインスタンスに対するコールバック登録を解除する
        surfaceHolder.removeCallback(this);
    }

    public void DrawSurface(final SurfaceHolder surfaceHolder){
    	// SingleThreadScheduledExecutor による単一 Thread のインターバル実行
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
            	//横に移動
            	if(x < getWidth()/1.8){
            		x = x += getWidth()/25;
            	}
            	if(itemFlickFlag == 1){
            		FlickFlag ++;
            	}
                // fps（実測値）の計測
                intervalTime.add(System.currentTimeMillis());
                float fps = 20000 / (intervalTime.get(19) - intervalTime.get(0));
                intervalTime.remove(0);

                // ロックした Canvas の取得
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(desk, 0, 0, paintCircle);
                r = r > 120 ? 10 : r + 3;

                //canvas.drawCircle(x, y, r, paintCircle);
                // 100x100にリサイズ
                int imageSize = getWidth()/4;

                sara= Bitmap.createScaledBitmap(sara, imageSize, imageSize, false);
                prin= Bitmap.createScaledBitmap(prin, imageSize, imageSize, false);
                if(FlickFlag > 20){
                	itemFlickFlag = 0;
                	FlickFlag = 0;
                	x=0;
                	y=(float) (getHeight() / 3);
                	xFlickFlag = 0;
                	yFlickFlag = 0;
                }
                else if(FlickFlag > 10){
                	canvas.drawBitmap(sara, x-imageSize/2, SetY, paintCircle);
                	if(xFlickFlag == 1){
                		canvas.drawBitmap(prin2, x-imageSize/2, y, paintCircle);
                	}
                	else if(yFlickFlag == 1){
                		canvas.drawBitmap(prin, x-imageSize/2, y, paintCircle);
                	}
               	 	x += getWidth()/20;
                }
                else if(itemFlickFlag == 1){
                	prin2= Bitmap.createScaledBitmap(prin2, imageSize, imageSize, false);
                	canvas.drawBitmap(sara, x-imageSize/2, y+getHeight()/21, paintCircle);
                	if(xFlickFlag == 1){
                		canvas.drawBitmap(prin2, x-imageSize/2, y, paintCircle);
                		canvas.drawBitmap(prin, x-imageSize/2, (float) (getHeight() / 4), paintCircle);
                	}
                	else if(yFlickFlag == 1){
                		canvas.drawBitmap(prin, x-imageSize/2, y, paintCircle);
                	}

                	SetX = x-imageSize/2;
                	SetY = y;
                }
                else{
                	canvas.drawBitmap(sara, x-imageSize/2, y+imageSize, paintCircle);
                	canvas.drawBitmap(prin, x-imageSize/2, y-imageSize/2, paintCircle);
                }




                canvas.drawText(String.format("%.1f fps", fps), 0, FONT_SIZE, paintFps);
                // ロックした Canvas の解放
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }, 100, INTERVAL_PERIOD, TimeUnit.MILLISECONDS);
    }

    public int TouchFlag = 0;
    // タッチイベントに対応する処理
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
        	touchX = event.getX();
        	touchY = event.getY();

            //x = event.getX();
            //y = event.getY();
            if(x > getWidth()/1.85 && event.getX() > getWidth()/2.15 && event.getX() < getWidth()/1.45 && event.getY() > getHeight()/3.35 && event.getY() < getHeight()/2.65){
	            //x = 0;
	            //y = (float) (getHeight() / 3);
            	Log.v("TOUCH","Flagon");
            	TouchFlag = 1;
            }
            break;
        case MotionEvent.ACTION_UP:
        	if(TouchFlag == 1){

        		Log.v("YPos",String.valueOf(event.getY() - touchY));
	        	if(event.getY() - touchY > getHeight()/15){
	        		y += getHeight()/7;
	        		itemFlickFlag = 1;
	        		yFlickFlag = 1;
	        	}
	        	else if(event.getX() - touchX > getWidth()/14){
	        		y += getHeight()/7;
	        		itemFlickFlag = 1;
	        		xFlickFlag = 1;
	        	}
        	}
        	TouchFlag = 0;
        	break;
        }
        return true;
    }
}