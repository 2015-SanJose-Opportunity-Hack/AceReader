package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class QuizFragmentActivity extends Activity implements Quiz_Fragment.OnButtonClickListener {

    DBWrapper db;
    List<Questions> questions;

//    private  static int questionNo = 0;
//    private  static int score = 0;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_fragment_activity);

        mContext=this;
       db = new DBWrapper(this);
        questions = db.getAllQuestions();
         if(getFragmentManager().findFragmentByTag("quiz_frag"+SingletonData.getCurrent_question())==null) {


             // Create an instance of ExampleFragment
             Quiz_Fragment quiz_fragment = Quiz_Fragment.newInstance(questions.get(SingletonData.getCurrent_question()), SingletonData.getCurrent_question(),SingletonData.getScore());
//
//            // In case this activity was started with special instructions from an Intent,
//            // pass the Intent's extras to the fragment as arguments
             quiz_fragment.setArguments(getIntent().getExtras());
             //quiz_fragment.setMortgagePayment(mortgagePayment.calculateMortgage(mortgageInput));

             FragmentManager manager = getFragmentManager();//create an instance of fragment manager
             FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
             transaction.add(R.id.frame_layout, quiz_fragment, "quiz_frag" + SingletonData.getCurrent_question());
             //can navigate back to previouse fragment
             transaction.addToBackStack(null);
             transaction.commit();
         }

    }


    @Override
    public void onButtonClickListener(View view) {
        if(SingletonData.getCurrent_question()>=4){
            Intent intent = new Intent(mContext, ResultActivity.class);
            intent.putExtra("FINAL_SCORE",SingletonData.getScore());
            mContext.startActivity(intent);
            Toast.makeText(getBaseContext(),"Quiz over! Thank you.",Toast.LENGTH_SHORT).show();
//            questionNo = 0;
//            score = 0;

        }else {
            SingletonData.setCurrent_question(SingletonData.getCurrent_question() + 1);
            // Create an instance of ExampleFragment
            Quiz_Fragment quiz_fragment = Quiz_Fragment.newInstance(questions.get(SingletonData.getCurrent_question()), SingletonData.getCurrent_question(),SingletonData.getScore());
//
//            // In case this activity was started with special instructions from an Intent,
//            // pass the Intent's extras to the fragment as arguments
            quiz_fragment.setArguments(getIntent().getExtras());
            //quiz_fragment.setMortgagePayment(mortgagePayment.calculateMortgage(mortgageInput));

            FragmentManager manager = getFragmentManager();//create an instance of fragment manager
            FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
            transaction.add(R.id.frame_layout, quiz_fragment, "quiz_frag" + SingletonData.getCurrent_question());
            //can navigate back to previouse fragment
            transaction.commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_read) {

            Intent i = new Intent(this,Quize_reader_popup.class);

            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


//    public void incrementScore() {
//        score++;
//    }
}
