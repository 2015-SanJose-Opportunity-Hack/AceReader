package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appfactory.app.com.acereaderapp.ttpengine.Speaker;

/**
 * Created by prasadshirsath on 10/3/15.
 */
public class ReaderActivity extends Activity implements Speaker.MyUtteranceProgressListener
{
    private ImageButton btnPlay,btnReplay,btnSettings;
    private Button btnStartQuiz;
    private TextView textView_reader;

    private final int CHECK_CODE = 0x1;


    private Context mContext;

    private Speaker speaker;

    //private String text="Amazing! I apologize for disturbing you again. Keep going guys.";

    private String text ="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reader_layout);


        //DataBase
        DBWrapper db = new DBWrapper(this);
        db.getReadableDatabase();

        List<Passages> list = db.getAllPassages();

//        Log.d("size", list.size() + "");
//        Log.d("Passage", list.get(0).getContent());
//

        mContext=this;
        btnPlay=(ImageButton)findViewById(R.id.button_play);
        btnSettings=(ImageButton)findViewById(R.id.button_settings);
        btnReplay=(ImageButton)findViewById(R.id.button_replay);
        textView_reader=(TextView)findViewById(R.id.textView_reader);
        btnStartQuiz=(Button)findViewById(R.id.button_startQuiz);

        textView_reader.setText(list.get(0).getContent());
        text = list.get(0).getContent();

        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                text = text.replaceAll("<font color=#cc0029>", "");
                text = text.replaceAll("</font>", "");

                if (!speaker.isSpeaking()) {
                    speaker.allow(true);
                    Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                    Matcher reMatcher = re.matcher(text);
                    while (reMatcher.find()) {
                        speaker.speak(reMatcher.group());
                    }
                    speaker.done();
                }else {
                    speaker.pause();
                }
            }
        });
        btnReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = text.replaceAll("<font color=#cc0029>", "");
                text = text.replaceAll("</font>", "");

                if (speaker.isSpeaking()) {
                    speaker.pause();
                }
                speaker.allow(true);
                Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
                Matcher reMatcher = re.matcher(text);
                while (reMatcher.find()) {
                    speaker.speak(reMatcher.group());
                }
                speaker.done();
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

        btnStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StartQuizActivity.class);
                mContext.startActivity(intent);
                finish();

            }
        });
        checkTTS();

        //calculate
        int noOfQuiz = 3;
        HashMap<Integer,Integer> temp = new HashMap<>();
        temp.put(1, 5);
        temp.put(2, 3);
        temp.put(3, 4);
        QuizData quizData = new QuizData(noOfQuiz,temp);
        Integer test = SingletonData.updateLevel(quizData);
        Log.d("New Level",test+"");

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
    public void onTTSDone(String s) {
        if(s.equals("last_id"))
            btnPlay.setImageResource(R.drawable.play);
        Log.d("TTS", "Stop");
        text=text.replaceAll("<font color=#cc0029>","");
        text=text.replaceAll("</font>","");

    }

    @Override
    public void onTTSStart(String s) {
        btnPlay.setImageResource(R.drawable.pause);
        btnPlay.setTag(R.drawable.pause);


        text=text.replace(s, "<font color=#cc0029>" + s + "</font>");
        textView_reader.setText(Html.fromHtml(text));

        Log.d("TTS", "Start");
    }


    //click

//    private void init() {
//        String definition = "Clickable words in text view ".trim();
//        TextView definitionView = (TextView) findViewById(R.id.textView_reader);
//        definitionView.setMovementMethod(LinkMovementMethod.getInstance());
//        definitionView.setText(definition, TextView.BufferType.SPANNABLE);
//        Spannable spans = (Spannable) definitionView.getText();
//        BreakIterator iterator = BreakIterator.getLineInstance(Locale.US);
//        iterator.setText(definition);
//        int start = iterator.first();
//        for (int end = iterator.next(); end != BreakIterator.DONE; start = end, end = iterator
//                .next()) {
//            String possibleWord = definition.substring(start, end);
//            if (Character.isLetterOrDigit(possibleWord.charAt(0))) {
//                ClickableSpan clickSpan = getClickableSpan(possibleWord);
//                spans.setSpan(clickSpan, start, end,
//                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }
//        }
//    }
//
//    private ClickableSpan getClickableSpan(final String word) {
//        return new ClickableSpan() {
//            final String mWord;
//            {
//                mWord = word;
//            }
//
//            @Override
//            public void onClick(View widget) {
//                Log.d("tapped on:", mWord);
//                Toast.makeText(widget.getContext(), mWord, Toast.LENGTH_SHORT)
//                        .show();
//            }
//
//            public void updateDrawState(TextPaint ds) {
//                super.updateDrawState(ds);
//            }
//        };
//    }

}
