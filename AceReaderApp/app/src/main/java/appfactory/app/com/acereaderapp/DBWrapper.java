package appfactory.app.com.acereaderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by neerajakukday on 10/3/15.
 */
public class DBWrapper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "AceReader";

    public DBWrapper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PASSAGES = "CREATE TABLE passages ( " +
                "passage_id INTEGER PRIMARY KEY, " +
                "difficulty_level TEXT, "+
                "content TEXT )";

        String CREATE_QUESTIONS = "CREATE TABLE questions ( " +
                "q_id INTEGER PRIMARY KEY, " +
                "sub_topic INTEGER, " +
                "question TEXT, "+
                "option_a TEXT, "+
                "option_b TEXT, " +
                "answer TEXT )";

        // create books table
        db.execSQL(CREATE_PASSAGES);
        db.execSQL(CREATE_QUESTIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS passages");
        db.execSQL("DROP TABLE IF EXISTS questions");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    private static final String TABLE_PASSAGES = "passages";
    private static final String TABLE_QUESTIONS = "questions";

    // Passage Table Columns names
    private static final String KEY_PID = "passage_id";
    private static final String KEY_DL = "difficulty_level";
    private static final String KEY_CONTENT = "content";

    // Question Table Columns names
    private static final String KEY_QID = "question_id";
    private static final String KEY_QPID = "passage_id";
    private static final String KEY_STID = "subtopic_id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_OPTIONA = "option_a";
    private static final String KEY_OPTIONB = "option_a";
    private static final String KEY_ANSWER = "answer";

    private static final String[] QCOLUMNS = {KEY_QID, KEY_QPID, KEY_STID,KEY_QUESTION,KEY_OPTIONA,KEY_OPTIONB, KEY_ANSWER};

    public void addPassages(Passages passages){
        Log.d("addPassages", passages.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_PID, passages.getPid());
        values.put(KEY_DL, passages.getDl());
        values.put(KEY_CONTENT, passages.getContent());


        Passages temp = getPassage(passages.getPid());
        // 3. insert
        if(temp==null) {
            db.insert(TABLE_PASSAGES, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }
        else{
            Log.e("exists","exists");
        }
        // 4. close
        db.close();
    }

    public Passages getPassage(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_PASSAGES, // a. table
                        COLUMNS, // b. column names
                        " passage_id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        Passages passage = new Passages();
        passage.setPid(Integer.parseInt(cursor.getString(0)));
        passage.setDl(Integer.parseInt(cursor.getString(1)));
        passage.setContent(cursor.getString(2));

        Log.d("getPassage(" + id + ")", passage.toString());

        // 5. return book
        return passage;
    }

    public List<Passages> getAllPassages() {
        List<Passages> passages = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM " + TABLE_PASSAGES;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Passages passage = null;
        if (cursor.moveToFirst()) {
            do {
                passage = new Passages();
                passage.setPid(Integer.parseInt(cursor.getString(0)));
                passage.setDl(Integer.parseInt(cursor.getString(1)));
                passage.setContent(cursor.getString(2));

                // Add book to books
                passages.add(passage);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPassages()", passages.toString());

        // return books
        return passages;
    }

    public void deletePassage(Passages passages) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_PASSAGES,
                KEY_PID + " = ?",
                new String[]{String.valueOf(passages.getPid())});

        // 3. close
        db.close();

        Log.d("deleteBook", passages.toString());

    }

    public void addQuestions(Questions questions){
        Log.d("addQuestions", questions.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_QID, questions.getQid());
        values.put(KEY_PID, questions.getPid());
        values.put(KEY_STID, questions.getStid());
        values.put(KEY_QUESTION, questions.getQuestion());
        values.put(KEY_OPTIONA, questions.getOption_a());
        values.put(KEY_OPTIONB, questions.getOption_b());
        values.put(KEY_ANSWER, questions.getAnswer());



        Passages temp = getPassage(questions.getPid());
        // 3. insert
        if(temp==null) {
            db.insert(TABLE_PASSAGES, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }
        else{
            Log.e("exists","exists");
        }
        // 4. close
        db.close();
    }


}
