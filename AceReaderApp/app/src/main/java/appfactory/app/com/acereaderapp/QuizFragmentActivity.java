package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import java.util.List;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class QuizFragmentActivity extends Activity implements Quiz_Fragment.OnButtonClickListener {

    DBWrapper db;
    List<Questions> questions;

    private  static int questionNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_fragment_activity);


       db = new DBWrapper(this);
        questions = db.getAllQuestions();
         if(getFragmentManager().findFragmentByTag("quiz_frag"+questionNo)==null) {


             // Create an instance of ExampleFragment
             Quiz_Fragment quiz_fragment = Quiz_Fragment.newInstance(questions.get(questionNo), questionNo);
//
//            // In case this activity was started with special instructions from an Intent,
//            // pass the Intent's extras to the fragment as arguments
             quiz_fragment.setArguments(getIntent().getExtras());
             //quiz_fragment.setMortgagePayment(mortgagePayment.calculateMortgage(mortgageInput));

             FragmentManager manager = getFragmentManager();//create an instance of fragment manager
             FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
             transaction.add(R.id.frame_layout, quiz_fragment, "quiz_frag" + questionNo);
             //can navigate back to previouse fragment
             transaction.addToBackStack(null);
             transaction.commit();
         }

    }


    @Override
    public void onButtonClickListener(View view) {
        questionNo++;
        // Create an instance of ExampleFragment
        Quiz_Fragment quiz_fragment = Quiz_Fragment.newInstance(questions.get(questionNo),questionNo);
//
//            // In case this activity was started with special instructions from an Intent,
//            // pass the Intent's extras to the fragment as arguments
        quiz_fragment.setArguments(getIntent().getExtras());
        //quiz_fragment.setMortgagePayment(mortgagePayment.calculateMortgage(mortgageInput));

        FragmentManager manager = getFragmentManager();//create an instance of fragment manager
        FragmentTransaction transaction = manager.beginTransaction();//create an instance of Fragment-transaction
        transaction.add(R.id.frame_layout, quiz_fragment, "quiz_frag" + questionNo);
        //can navigate back to previouse fragment
        transaction.commit();

    }
}
