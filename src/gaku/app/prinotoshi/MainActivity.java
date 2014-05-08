package gaku.app.prinotoshi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity {

	public ImageView item0;
	
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
        item0 = (ImageView)findViewById(R.id.item0);
    }
    
    public void result(View view){
    	Intent intent = new Intent(this,Result.class);
    	startActivity(intent);
    }
    
    public void select(View view){
    	switch(view.getId()){
    	case R.id.item1:
    		item0.setImageResource(R.drawable.muteki5);
    		break;
    	case R.id.item2:
    		Log.v("item","2");
    		break;
    	case R.id.item3:
    		Log.v("item","3");
    		break;
    	case R.id.item4:
    		Log.v("item","4");
    		break;
    	case R.id.item5:
    		Log.v("item","5");
    		break;
    	case R.id.item6:
    		Log.v("item","6");
    		break;
    	case R.id.item7:
    		Log.v("item","7");
    		break;
    	case R.id.item8:
    		Log.v("item","8");
    		break;
    	case R.id.item9:
    		Log.v("item","9");
    		break;
    	}
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