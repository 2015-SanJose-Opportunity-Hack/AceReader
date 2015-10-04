package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class Quiz_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_QUESTION = "Question";
    private static final String ARG_OPTION_ONE = "Option_a";
    private static final String ARG_OPTION_TWO = "Option_b";
    private static final String ARG_ANSWER = "Answer";


    private TextView textview_question;
    private Button button_option_one;
    private Button button_option_two;
    private Button button_score;
    private Button button_question_no;
    private ImageButton button_stop_quiz;


    private ImageButton button_option_next;

    private  OnButtonClickListener mCallback;

    QuizFragmentActivity quizFragmentActivity = new QuizFragmentActivity();

    // TODO: Rename and change types of parameters
    private String question;
    private String option_a;
    private String option_b;
    private String answer;
    private  int question_no=0;
    private int score=0;


    // TODO: Rename and change types and number of parameters
    public static Quiz_Fragment newInstance(Questions questions,int qustionNo,int score) {
        Quiz_Fragment fragment = new Quiz_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, questions.getQuestion());
        args.putString(ARG_OPTION_ONE, questions.getOption_a());
        args.putString(ARG_OPTION_TWO, questions.getOption_b());
        args.putString(ARG_ANSWER, questions.getAnswer());

        fragment.setArguments(args);


        fragment.setQuestion(questions,qustionNo,score);

        return fragment;
    }

    void setQuestion(Questions questions,int question_no,int score){

        this.question = questions.getQuestion();
        this.option_a = questions.getOption_a();
        this.option_b = questions.getOption_b();
        this.answer = questions.getAnswer();
        this.question_no=question_no;
        this.score=score;
    }

    public Quiz_Fragment(){
        //required
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            option_a = getArguments().getString(ARG_OPTION_ONE);
            option_b = getArguments().getString(ARG_OPTION_TWO);
            answer = getArguments().getString(ARG_ANSWER);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_quiz_fragment, container, false);
        button_option_one = (Button)view.findViewById(R.id.button_option1);
        button_option_two = (Button)view.findViewById(R.id.button_option2);
        button_option_next = (ImageButton)view.findViewById(R.id.btn_next);
        textview_question = (TextView)view.findViewById(R.id.textView_question);
        button_score = (Button)view.findViewById(R.id.button_score);
        button_question_no = (Button)view.findViewById(R.id.button_question_no);

        textview_question.setText(question);

        button_option_one.setText(option_a);
        button_option_two.setText(option_b);
        button_score.setText("Score: "+score);
        button_question_no.setText(1+question_no+"/5");


        button_option_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (answer.equals("option_a")) {
                    button_option_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);

                   // ((QuizFragmentActivity)getActivity()).incrementScore();
                    SingletonData.setScore(SingletonData.getScore() + 1);

                } else {
                    button_option_one.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                }
                disableOptions();


            }
        });

        button_option_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answer.equals("option_b")){
                    button_option_two.setCompoundDrawablesWithIntrinsicBounds(R.drawable.tick, 0, 0, 0);
                    //((QuizFragmentActivity)getActivity()).incrementScore();
                    //((QuizFragmentActivity)getActivity()).incrementScore();
                    SingletonData.setScore(SingletonData.getScore()+1);

                }
                else {
                    button_option_two.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cross, 0, 0, 0);
                }
                disableOptions();


            }
        });


        button_option_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onButtonClickListener(view);

            }
        });

        return view;
    }

    void disableOptions() {
        button_option_two.setClickable(false);
        button_option_one.setClickable(false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mCallback != null) {
            mCallback.onButtonClickListener(view);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallback = (OnButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    // The container Activity must implement this interface so the frag can deliver messages
    public interface OnButtonClickListener {
        /**
         * Called by HeadlinesFragment when a list item is selected
         */
        public void onButtonClickListener(View view);
    }



}
