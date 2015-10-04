package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by neerajakukday on 10/4/15.
 */
public class StartQuizActivity extends Activity {

    private ImageButton startQuiz;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startquiz_layout);

        mContext = this;
        startQuiz=(ImageButton)findViewById(R.id.startQuiz);

        startQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, QuizFragmentActivity.class);
                mContext.startActivity(intent);

            }
        });



    }
}
