package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import appfactory.app.com.acereaderapp.ttpengine.Speaker;

/**
 * Created by prasadshirsath on 10/3/15.
 */
public class ReaderActivity extends Activity implements Speaker.MyUtteranceProgressListener
{
    private ImageButton btnPlay,btnReplay,btnSettings;
    private TextView textView_reader;

    private final int CHECK_CODE = 0x1;
    private final int LONG_DURATION = 5000;
    private final int SHORT_DURATION = 1200;

    private Context mContext;

    private Speaker speaker;

    private String text="Hey are you having fun. We are here for hacking.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_layout);

        mContext=this;
        btnPlay=(ImageButton)findViewById(R.id.button_play);
        btnSettings=(ImageButton)findViewById(R.id.button_settings);
        textView_reader=(TextView)findViewById(R.id.textView_reader);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speaker.allow(true);
                String[] s = text.split("\\.");
                for( int i=0;i<s.length;i++)
                {
                    speaker.speak(s[i],i);

                }
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent();
                intent.setAction("com.android.settings.TTS_SETTINGS");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        checkTTS();
    }
    private void checkTTS(){
        Intent check = new Intent();
        check.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(check, CHECK_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CHECK_CODE){
            if(resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS){
                speaker = new Speaker(this);
                speaker.setInterface(this);
            }else {
                Intent install = new Intent();
                install.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(install);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        speaker.destroy();

    }



    @Override
    public void onTTSDone() {
        btnPlay.setImageResource(R.drawable.play);
        Log.d("TTS", "Stop");

    }

    @Override
    public void onTTSStart() {
        btnPlay.setImageResource(R.drawable.pause);
        Log.d("TTS","Start");
    }
}
