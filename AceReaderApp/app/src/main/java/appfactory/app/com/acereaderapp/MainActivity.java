package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;


public class MainActivity extends Activity {

    private Button btnStart,btnReport;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.reader_layout);

        setContentView(R.layout.activity_main);

        DBWrapper db = new DBWrapper(this);
        db.getReadableDatabase();

        db.getReadableDatabase();
        //add Passage
        db.addPassages(new Passages(9, 1, SingletonData.passageDump));

        //add questions

        db.addQuestions(new Questions(1, 2, 1, "Which word has '\"a\"' as in the word '\"same\"'?", "many", "named", "option_b"));


        List<Passages> list = db.getAllPassages();
        List<Questions> questions = db.getAllQuestions();

        Log.d("size", list.size() + "");
        Log.d("Passage", list.get(0).getContent());
        Log.d("Questions",questions.get(0).getAnswer());

        mContext = this;
        btnStart = (Button) findViewById(R.id.button_start);
        btnReport = (Button) findViewById(R.id.button_reportcard);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecyclerViewActivity.class);
                mContext.startActivity(intent);

            }
        });


        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }

        });


    }
}
