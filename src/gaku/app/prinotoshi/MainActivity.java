package gaku.app.prinotoshi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SurfaceView のインスタンスを実体化し、ContentView としてセットする
        //SurfaceViewTest surfaceView = new SurfaceViewTest(this);
        //PrinSurface surfaceView = new PrinSurface(this);
        //setContentView(surfaceView);// タイトルバーを非表示
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.v("create","create");
    }
    @Override
    protected void onResume() {
    super.onResume();
    // SurfaceView のインスタンスを実体化し、ContentView としてセットする
    //SurfaceViewTest surfaceView = new SurfaceViewTest(this);
    //PrinSurface surfaceView = new PrinSurface(this);
    //setContentView(surfaceView);

    }
    public void Start(View view){
    	//ゲームスタート
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClassName( "gaku.app.prinotoshi","gaku.app.prinotoshi.StartActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}