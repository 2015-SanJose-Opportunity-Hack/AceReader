package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.jlmd.animatedcircleloadingview.AnimatedCircleLoadingView;

public class ResultActivity extends Activity {

    private Button playAgain;
    private Context mcontext;
    private double percent;
    private AnimatedCircleLoadingView animatedCircleLoadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //percent=getIntent().getIntExtra("FINAL_SCORE",0);

        percent = SingletonData.getScore();
        Log.d("Fix", "Score: " + percent);

        percent = ((percent/5)*100);

        Log.d("Fix","Percent: "+percent);
        mcontext=this;
        SingletonData.setScore(0);
        SingletonData.setCurrent_question(0);

        playAgain = (Button)findViewById(R.id.playAgain);

        animatedCircleLoadingView = (AnimatedCircleLoadingView) findViewById(R.id.circle_loading_view);
        startLoading();
        startPercentMockThread();

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, StartQuizActivity.class);
                mcontext.startActivity(intent);

                finish();

            }
        });

    }
    private void startLoading() {
        animatedCircleLoadingView.startDeterminate();

    }

    private void startPercentMockThread() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    for (int i = 0; i <= percent; i++) {
                        Thread.sleep(65);
                        changePercent(i);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();
    }

    private void changePercent(final int percent) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.setPercent(percent);
            }
        });
    }

    public void resetLoading() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                animatedCircleLoadingView.resetLoading();
            }
        });
    }
}
