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

        //add Passage
        db.addPassages(new Passages(1, 1, SingletonData.passageDump));

        //add questions
        db.addQuestions(new Questions(1, 1, 1, "Which of the following words begin with the sound \"R\"?", "Wali", "rabbit", "option_b"));
        db.addQuestions(new Questions(2, 1, 1, "Which word has \"a\" as in the word \"same\"?", "many", "named", "option_b"));
        db.addQuestions(new Questions(3, 1, 1, "Which word has \"d\" as ithe last sound? ?", "lived", "eaten", "option_a"));
        db.addQuestions(new Questions(4, 1, 2, "How many syllables are in the word '\"become\"'? ", "2", "3", "option_a"));
        db.addQuestions(new Questions(5, 1, 2, "What word results from blending the sounds '\"wo\"', '\"l\"', and '\"f\"'?", "wolves", "wolf", "option_b"));
        db.addQuestions(new Questions(6, 1, 2, "What word do you get by blending the syllables: '\"be\"' + '\"come\"'", "because", "become", "option_b"));
        db.addQuestions(new Questions(7, 1, 3, "What word results form dropping '\"h\"' from '\"hill\"'?", "bill", "ill", "option_b"));
        db.addQuestions(new Questions(8, 1, 3, "What are the phonemes (sounds) in the word '\"give\"'?", "'\"g\"', '\"i\"', '\"v\"'", " '\"g\"', '\"i\"', '\"v\"', '\"e\"'", "option_a"));
        db.addQuestions(new Questions(9, 1, 3, "What new word  results from adding '\"f\"' to  the beginning of '\"ear\"'?", "fear", "gear", "option_a"));
        db.addQuestions(new Questions(10, 1, 3, "What new word results from substituting '\"r\"' for the first letter in the word '\"nice\"'?", "rice", "risk", "option_a"));
        db.addQuestions(new Questions(11, 1, 4, "What is the shared sound in these words? \n" +"\n" +"could\n" +"cause\n" +"consequence", "q", "k", "option_b"));
        db.addQuestions(new Questions(12, 1, 6, "Fill in the blank to correctly complete the sentence?\n" +"\n" +"The house is next to a ______, where wheat is grown", "field", "hole", "option_a"));
        db.addQuestions(new Questions(13, 1, 6, "Which order forms a meaningful sentence for the words: '\"cat, the, away, few, from, bird, the\"'. ", "The bird flew away from the cat.", "Flew away the bird from the cat ", "option_a"));
        db.addQuestions(new Questions(14, 1, 7, "What is the root of the following words:\n" +"playing\n" +"playful\n" +"player", "play", "ing", "option_a"));
        db.addQuestions(new Questions(15, 1, 8, "Which word matches these definitions:\n" +"\n" +"a. Information that tells us where someone or something is located or b. To speak to someone or c. A speech ", "address", "quote", "option_a"));
        db.addQuestions(new Questions(16, 1, 9, "What word has the same meaning as the word '\"friend\"'?", "foe", "companion", "option_b"));
        db.addQuestions(new Questions(17, 1, 10, "What word is the opposite of the word '\"happy\"'?", "mad", "sad", "option_b"));
        db.addQuestions(new Questions(18, 1, 11, "Where did Rabi the rabbit and Wali the wolf live?", "The plains", "The hills", "option_a"));
        db.addQuestions(new Questions(19, 1, 12, "Which of the following is true about what Rabi wanted?", "Rabbits are better than wolves", "For rabbits and wolves to be friends", "option_b"));
        db.addQuestions(new Questions(20, 1, 13, "Which of the following images best describes what happens at the end of the story?", "Wolves and rabbits playing together", "Rabbits running from the plains into the hills", "option_a"));


        List<Passages> list = db.getAllPassages();
        List<Questions> questions = db.getAllQuestions();

        Log.d("size", questions.size() + "");
        Log.d("Passage", list.get(0).getContent());
        Log.d("Questions123",questions.get(0).getOption_a());

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
                Intent intent = new Intent(mContext, ReportCardActivity.class);
                mContext.startActivity(intent);


            }

        });


    }
}
