package appfactory.app.com.acereaderapp.ttpengine;

/**
 * Created by prasadshirsath on 10/3/15.
 */

import android.content.Context;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Speaker extends UtteranceProgressListener implements OnInitListener {

    private TextToSpeech tts;

    private boolean ready = false;

    private boolean allowed = false;

    private Context mContext = null;

    private MyUtteranceProgressListener utteranceProgressListener;

    public Speaker(Context context){
        tts = new TextToSpeech(context, this);

        mContext=context;
    }

    public boolean isAllowed(){
        return allowed;
    }

    public void allow(boolean allowed){
        this.allowed = allowed;
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            // Change this to match your
            // locale
            tts.setLanguage(Locale.US);
            ready = true;
        }else{
            ready = false;
        }
    }

    public void speak(String text){
        // Speak only if the TTS is ready
        // and the user has allowed speech
        if(ready && allowed) {
            HashMap<String, String> hash = new HashMap<String,String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));
            tts.setOnUtteranceProgressListener(this);
            HashMap<String, String> myHashAlarm = new HashMap();

            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_ALARM));
           // tts.speak(text, TextToSpeech.QUEUE_FLUSH, myHashAlarm);
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                    "" + text);
// myHashAlarm now contains two optional parameters
            tts.speak(text, TextToSpeech.QUEUE_ADD, myHashAlarm);


//
//            if (android.os.Build.VERSION.SDK_INT >= 21) {
//                tts.speak(text,TextToSpeech.QUEUE_FLUSH,null,null);
//            }else{
//                tts.speak(text, TextToSpeech.QUEUE_ADD, hash);
//            }
        }
    }

    public void pause(){
        if(ready && allowed) {
            HashMap<String, String> hash = new HashMap<String,String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));
            tts.setOnUtteranceProgressListener(this);
            HashMap<String, String> myHashAlarm = new HashMap();

            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                    "last_id");
            tts.speak("", TextToSpeech.QUEUE_FLUSH, myHashAlarm);
        }
    }
    public void done() {
        if(ready && allowed) {
            HashMap<String, String> hash = new HashMap<String,String>();
            hash.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_NOTIFICATION));
            tts.setOnUtteranceProgressListener(this);
            HashMap<String, String> myHashAlarm = new HashMap();

            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
                    String.valueOf(AudioManager.STREAM_ALARM));
            myHashAlarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                    "last_id");
            tts.speak("", TextToSpeech.QUEUE_ADD, myHashAlarm);

        }
    }

    // Free up resources
    public void destroy(){
        tts.shutdown();
    }
    public boolean isSpeaking(){
     return  tts.isSpeaking();
    }


    @Override
    public void onStart(final String s) {
        Log.d("TTS", "start");

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                utteranceProgressListener.onTTSStart(s);
            }
        });


    }

    @Override
    public void onDone(final String s) {
        Log.d("TTS", "done"+s);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                utteranceProgressListener.onTTSDone(s);
            }
        });

    }

    @Override
    public void onError(String s) {

    }




    public interface MyUtteranceProgressListener{
        public void onTTSDone(String s);
        public void onTTSStart(String s);

    }

    public void setInterface(MyUtteranceProgressListener utteranceProgressListener) {
        this.utteranceProgressListener = utteranceProgressListener;
    }

}