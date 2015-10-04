package appfactory.app.com.acereaderapp;

/**
 * Created by prasadshirsath on 10/3/15.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends Activity {

    private List<Person> persons;
    private RecyclerView rv;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recyclerview_activity);

        mContext=this;

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Level One", "Story of Rabbit and Tortoise..", R.drawable.one,R.drawable.status_completed));
        persons.add(new Person("Level Two", "Story of Rabbit and Tortoise..", R.drawable.two,R.drawable.status_locked));
        persons.add(new Person("Level Three", "Story of Rabbit and Tortoise..", R.drawable.three,R.drawable.status_locked));

//        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.lavery));
//        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.lillie));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
        RVAdapter.OnItemClickListener onItemClickListener = new RVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                
            }
        };
        adapter.setOnItemClickListener(onItemClickListener);
    }
}