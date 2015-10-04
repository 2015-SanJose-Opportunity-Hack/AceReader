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
    private static final int DATABASE_VERSION = 2;
    // Database Name
    private static final String DATABASE_NAME = "AceReader";



    public DBWrapper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.e("oncreate DBwrapper**","true");
        String CREATE_PASSAGES = "CREATE TABLE passages ( " +
                "passage_id INTEGER PRIMARY KEY, " +
                "difficulty_level TEXT, "+
                "content TEXT )";

        String CREATE_QUESTIONS = "CREATE TABLE questions ( " +
                "q_id INTEGER PRIMARY KEY, " +
                "passage_id INTEGER, " +
                "sub_topic INTEGER, " +
                "question TEXT, "+
                "option_a TEXT, "+
                "option_b TEXT, " +
                "answer TEXT )";

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
    private static final String KEY_QID = "q_id";
    private static final String KEY_QPID = "passage_id";
    private static final String KEY_STID = "sub_topic";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_OPTIONA = "option_a";
    private static final String KEY_OPTIONB = "option_b";
    private static final String KEY_ANSWER = "answer";

    private static final String[] COLUMNS = {KEY_PID, KEY_DL, KEY_CONTENT};

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
            Log.e("Error","exists");
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

        Passages passage=null;
        if (cursor.moveToFirst()) {

            passage = new Passages();
            passage.setPid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_PID))));
            passage.setDl(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_DL))));
            passage.setContent(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_CONTENT)));

            Log.d("getPassage(" + id + ")", passage.toString());
        }

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

                passages.add(passage);
            } while (cursor.moveToNext());
        }

        Log.d("getAllPassages()", passages.toString());

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

        Log.d("deletePassage", passages.toString());

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


        Questions temp = getQuestion(questions.getQid());
        // 3. insert
        if(temp==null) {
            db.insert(TABLE_QUESTIONS, // table
                    null, //nullColumnHack
                    values); // key/value -> keys = column names/ values = column values
        }
        else{
            Log.e("Error","Entry Exists");
        }
        // 4. close
        db.close();
    }

    public List<Questions> getAllQuestions() {
        List<Questions> questions = new LinkedList<>();

        // 1. build the query
        String query = "SELECT  * FROM questions ORDER BY RANDOM() LIMIT 5";

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Questions question = null;
        if (cursor.moveToFirst()) {
            do {
                question = new Questions();
                question.setQid(Integer.parseInt(cursor.getString(0)));
                question.setPid(Integer.parseInt(cursor.getString(1)));
                question.setStid(Integer.parseInt(cursor.getString(2)));
                question.setQuestion(cursor.getString(3));
                question.setOption_a(cursor.getString(4));
                question.setOption_b(cursor.getString(5));
                question.setAnswer(cursor.getString(6));

                questions.add(question);
            } while (cursor.moveToNext());
        }

        Log.d("getAllQuestion()", question.toString());

        return questions;
    }

    public Questions getQuestion(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_QUESTIONS, // a. table
                        QCOLUMNS, // b. column names
                        " q_id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        Questions question=null;
        if (cursor.moveToFirst()) {

            question = new Questions();
            question.setQid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_QID))));
            question.setPid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_PID))));
            question.setStid(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_STID))));
            question.setQuestion(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_QUESTION)));
            question.setOption_a(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_OPTIONA)));
            question.setOption_a(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_OPTIONB)));
            question.setAnswer(cursor.getString(cursor.getColumnIndex(DBWrapper.KEY_ANSWER)));

            Log.d("getQuestion(" + id + ")", question.toString());
        }

        return question;
    }

}
