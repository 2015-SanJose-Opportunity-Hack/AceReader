package appfactory.app.com.acereaderapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class Quize_reader_popup extends Activity {

    ImageButton btn_close;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quize_reader_popup);

        //DataBase
        DBWrapper db = new DBWrapper(this);
        db.getReadableDatabase();

        List<Passages> list = db.getAllPassages();



        btn_close = (ImageButton)findViewById(R.id.btnClose);
        textView = (TextView)findViewById(R.id.textView_pop_reader);
        textView.setText(list.get(0).getContent());


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
