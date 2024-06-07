package com.example.taskque_mobile_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class TasksDB {
    public static final String TASKS_ID = "T_ID";
    public static final String TASKS_TITLE = "TASKS_TILE";
    public static final String TASKS_DESCRIPTION = "TASKS_DES";

    public static final String NOTES_ID = "N_ID";
    public static final String NOTES_TITLE = "NOTES_TITLE";
    public static final String NOTES_DESCRIPTION = "NOTE_DES";

    public static final String LINKS_ID = "L_ID";
    public static final String LINKS_TITLE = "LINKS_TITLE";
    public static final String LINKS_LINK = "LINKS_LINK";

    public static final String TIMER_ID = "TIMER_ID";
    public static final String YEAR = "YEAR";
    public static final String MONTH = "MONTH";
    public static final String DAY_OF_MONTH = "DAY";
    public static final String HOUR_OF_DAY = "HOUR";
    public static final String MINUTE = "MINUTE";
    public static final String TYPE = "TYPE";

    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String REQUEST_ID = "REQUEST_ID";

    private final String DATABASE_NAME = "TasksDB";
    private final String TASKS_TABLE = "TasksTable";
    private final String NOTES_TABLE = "NotesTable";
    private final String LINKS_TABLE = "LinksTable";
    private final String TIMER_TABLE = "TimersTable";
    private final String REQUEST_TABLE = "RequestTable";
    private final String TODAY_TIMER_TABLE = "Today_timersTable";
    private final int DATABASE_VERSION = 10;

    private TasksDBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public TasksDB(Context context)
    {
        ourContext = context;
    }

    private class TasksDBHelper extends SQLiteOpenHelper {
        public TasksDBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {

            // only runs when the database is created

            String taskTable  = "CREATE TABLE "+TASKS_TABLE+" ("+
                    TASKS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASKS_TITLE+" TEXT NOT NULL, "+
                    TASKS_DESCRIPTION+" TEXT NOT NULL); ";
            db.execSQL(taskTable);

            String notesTable = "CREATE TABLE "+NOTES_TABLE+" ("+
                    NOTES_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASKS_ID+ " INTEGER,"+
                    NOTES_TITLE+" TEXT NOT NULL, "+
                    NOTES_DESCRIPTION+" TEXT NOT NULL," +
                    "FOREIGN KEY("+TASKS_ID+") REFERENCES "+TASKS_TABLE+" ("+TASKS_ID+")"+" ON DELETE CASCADE "+"); ";
            db.execSQL(notesTable);

            String linksTable = "CREATE TABLE "+LINKS_TABLE+" ("+
                    LINKS_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASKS_ID+ " INTEGER,"+
                    LINKS_TITLE+" TEXT NOT NULL, "+
                    LINKS_LINK+" TEXT NOT NULL," +
                    "FOREIGN KEY("+TASKS_ID+") REFERENCES "+TASKS_TABLE+" ("+TASKS_ID+")"+" ON DELETE CASCADE "+"); ";

            db.execSQL(linksTable);

            String timersTable = "CREATE TABLE "+TIMER_TABLE+" ("+
                    TIMER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASKS_ID+ " INTEGER,"+
                    YEAR+" INTEGER NOT NULL, "+
                    MONTH+" INTEGER NOT NULL, "+
                    DAY_OF_MONTH+" INTEGER NOT NULL, "+
                    HOUR_OF_DAY+" INTEGER NOT NULL, "+
                    MINUTE+" INTEGER NOT NULL, "+
                    TYPE+" TEXT NOT NULL," +
                    "FOREIGN KEY("+TASKS_ID+") REFERENCES "+TASKS_TABLE+" ("+TASKS_ID+")"+" ON DELETE CASCADE "+"); ";
            db.execSQL(timersTable);

            String today_timersTable = "CREATE TABLE "+TODAY_TIMER_TABLE+" ("+
                    TIMER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    TASKS_ID+ " INTEGER,"+
                    YEAR+" INTEGER NOT NULL, "+
                    MONTH+" INTEGER NOT NULL, "+
                    DAY_OF_MONTH+" INTEGER NOT NULL, "+
                    HOUR_OF_DAY+" INTEGER NOT NULL, "+
                    MINUTE+" INTEGER NOT NULL, "+
                    TYPE+" TEXT NOT NULL," +
                    "FOREIGN KEY("+TASKS_ID+") REFERENCES "+TASKS_TABLE+" ("+TASKS_ID+")"+" ON DELETE CASCADE "+"); ";
            db.execSQL(today_timersTable);

            String requestTable = "CREATE TABLE "+REQUEST_TABLE+" ("+
                    REQUEST_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    REQUEST_CODE+" INTEGER NOT NULL );";

            db.execSQL(requestTable);

            ContentValues cv = new ContentValues();
            cv.put(REQUEST_CODE,0);
            db.insert(REQUEST_TABLE,null,cv);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // if the version number gets changed
            // then this function is called

            db.execSQL("DROP TABLE IF EXISTS "+REQUEST_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+TIMER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+TODAY_TIMER_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+NOTES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+LINKS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS "+TASKS_TABLE);

            onCreate(db);
        }



        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public TasksDB open() throws SQLException
    {
        ourHelper = new TasksDBHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        ourHelper.close();
    }

    public long entryTasks(String title, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_TITLE,title);
        cv.put(TASKS_DESCRIPTION,description);

        return ourDatabase.insert(TASKS_TABLE,null,cv);
    }

    public long entryNotes(int task_id,String title, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(NOTES_TITLE,title);
        cv.put(NOTES_DESCRIPTION,description);

        return ourDatabase.insert(NOTES_TABLE,null,cv);
    }

    public long entryLinks(int task_id,String title, String link)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(LINKS_TITLE,title);
        cv.put(LINKS_LINK,link);

        return ourDatabase.insert(LINKS_TABLE,null,cv);
    }

    public long entryTimers(int task_id,int year,int month,int day,int hour,int min,String type)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(YEAR,year);
        cv.put(MONTH,month);
        cv.put(DAY_OF_MONTH,day);
        cv.put(HOUR_OF_DAY,hour);
        cv.put(MINUTE,min);
        cv.put(TYPE,type);

        return ourDatabase.insert(TIMER_TABLE,null,cv);
    }

    public long entryTodayTimers(int task_id,int year,int month,int day,int hour,int min,String type)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(YEAR,year);
        cv.put(MONTH,month);
        cv.put(DAY_OF_MONTH,day);
        cv.put(HOUR_OF_DAY,hour);
        cv.put(MINUTE,min);
        cv.put(TYPE,type);

        return ourDatabase.insert(TODAY_TIMER_TABLE,null,cv);
    }

    public long deleteTasksEntry (String taskID)
    {
        return ourDatabase.delete(TASKS_TABLE,TASKS_ID+"=?",new String[]{taskID});
    }

    public long deleteNotesEntry (String notesID)
    {
        return ourDatabase.delete(NOTES_TABLE,NOTES_ID+"=?",new String[]{notesID});
    }

    public long deleteLinksEntry (String linksID)
    {
        return ourDatabase.delete(LINKS_TABLE,LINKS_ID+"=?",new String[]{linksID});
    }

    public long deleteTimersEntry(String timersID)
    {
        return ourDatabase.delete(TIMER_TABLE,TIMER_ID+"=?",new String[]{timersID});
    }

    public long deleteTodayTimersEntry(String timersID)
    {
        return ourDatabase.delete(TODAY_TIMER_TABLE,TIMER_ID+"=?",new String[]{timersID});
    }

    public long updateTasksEntry(String taskID,String title, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_TITLE,title);
        cv.put(TASKS_DESCRIPTION,description);

        return ourDatabase.update(TASKS_TABLE,cv,TASKS_ID+"=?",new String[]{taskID});
    }

    public long updateNotesEntry(String notesID,int task_id,String title, String description)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(NOTES_TITLE,title);
        cv.put(NOTES_DESCRIPTION,description);

        return ourDatabase.update(NOTES_TABLE,cv,NOTES_ID+"=?",new String[]{notesID});
    }

    public long updateLinksEntry(String linksID,int task_id,String title, String link)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(LINKS_TITLE,title);
        cv.put(LINKS_LINK,link);

        return ourDatabase.update(LINKS_TABLE,cv,LINKS_ID+"=?",new String[]{linksID});
    }

    public long updateTimersEntry(String timersID,int task_id,int year,int month,int day,int hour,int min,String type)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(YEAR,year);
        cv.put(MONTH,month);
        cv.put(DAY_OF_MONTH,day);
        cv.put(HOUR_OF_DAY,hour);
        cv.put(MINUTE,min);
        cv.put(TYPE,type);

        return ourDatabase.update(TIMER_TABLE,cv,TIMER_ID+"=?",new String[]{timersID});
    }
    public long updateTodayTimersEntry(String timersID,int task_id,int year,int month,int day,int hour,int min,String type)
    {
        ContentValues cv = new ContentValues();
        cv.put(TASKS_ID,task_id);
        cv.put(YEAR,year);
        cv.put(MONTH,month);
        cv.put(DAY_OF_MONTH,day);
        cv.put(HOUR_OF_DAY,hour);
        cv.put(MINUTE,min);
        cv.put(TYPE,type);

        return ourDatabase.update(TODAY_TIMER_TABLE,cv,TIMER_ID+"=?",new String[]{timersID});
    }

    public long updateRequestCode(int requestCode)
    {
        ContentValues cv = new ContentValues();
        cv.put(REQUEST_CODE,requestCode);

        return ourDatabase.update(REQUEST_TABLE,cv,REQUEST_ID+"=?",new String[]{"1"});
    }

    //these functions are for testing
    //have to change afterwards

    public int getRequestCode()
    {
        String [] columns = new String[]{REQUEST_CODE};
        Cursor c = ourDatabase.query(REQUEST_TABLE,columns,null,null,null,null,null);

        int reQuestCode = 0;

        int iR = c.getColumnIndex(REQUEST_CODE);
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            reQuestCode = c.getInt(iR);
        }
        c.close();

        return reQuestCode;
    }

    public Tasks getTasksData(String taskID)
    {
        String [] columns = new String [] {TASKS_ID,TASKS_TITLE,TASKS_DESCRIPTION};

        Cursor c = ourDatabase.query(TASKS_TABLE,columns," T_ID =?",new String[]{taskID},null,null,null);


        Tasks tasks = new Tasks();

        int iRowID = c.getColumnIndex(TASKS_ID);
        int iName = c.getColumnIndex(TASKS_TITLE);
        int iCell = c.getColumnIndex(TASKS_DESCRIPTION);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            tasks = new Tasks(c.getInt(iRowID),c.getString(iName),c.getString(iCell));
        }
        c.close();
        return tasks;
    }

    // provide the Notes of a given taskID
    public ArrayList<Notes> getNotesData(String taskID)
    {
        String [] columns = new String [] {NOTES_ID,TASKS_ID,NOTES_TITLE,NOTES_DESCRIPTION};

        Cursor c = ourDatabase.query(NOTES_TABLE,columns," T_ID =?",new String[]{taskID},null,null,null);
        //String result = "";
        ArrayList<Notes>notes = new ArrayList<Notes>();


        int iRowID = c.getColumnIndex(NOTES_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iTitle = c.getColumnIndex(NOTES_TITLE);
        int iDes = c.getColumnIndex(NOTES_DESCRIPTION);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            notes.add(new Notes(c.getInt(iRowID),c.getInt(iTaskID),c.getString(iTitle),c.getString(iDes)));
            //result = result + c.getString(iRowID)+": "+c.getString(iTaskID)+" "+c.getString(iName)+
            //      " "+c.getString(iCell)+"\n";
        }
        c.close();
        return notes;
    }

    // provide the Links of a given taskID
    public ArrayList<Links> getLinksData(String taskID)
    {
        String [] columns = new String [] {LINKS_ID,TASKS_ID,LINKS_TITLE,LINKS_LINK};

        Cursor c = ourDatabase.query(LINKS_TABLE,columns," T_ID =?",new String[]{taskID},null,null,null);
        // String result = "";
        ArrayList<Links> links = new ArrayList<Links>();

        int iRowID = c.getColumnIndex(LINKS_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iTitle = c.getColumnIndex(LINKS_TITLE);
        int iLink = c.getColumnIndex(LINKS_LINK);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            links.add(new Links(c.getInt(iRowID),c.getInt(iTaskID),c.getString(iTitle),c.getString(iLink)));
            //result = result + c.getString(iRowID)+": "+c.getString(iTaskID)+" "+c.getString(iName)+
            //      " "+c.getString(iCell)+"\n";
        }
        c.close();
        return links;
    }

    public int getLatestTaskID()
    {
        Cursor c = ourDatabase.rawQuery("SELECT MAX(T_ID) FROM "+TASKS_TABLE,new String[]{});

        int maxTaskID = 0;
        int iID = c.getColumnIndex("MAX(T_ID)");

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            maxTaskID = c.getInt(iID);

        }
        c.close();
        return maxTaskID;

    }

    public int getLatestTimerID()
    {
        Cursor c = ourDatabase.rawQuery("SELECT MAX(TIMER_ID) FROM "+TIMER_TABLE,new String[]{});

        int maxTimerID = 0;
        int iID = c.getColumnIndex("MAX(TIMER_ID)");

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            maxTimerID = c.getInt(iID);

        }
        c.close();
        return maxTimerID;

    }


    public ArrayList<Timers> getTimersData()
    {
        //ourDatabase.rawQuery();
        Cursor c = ourDatabase.rawQuery("SELECT * FROM "+TIMER_TABLE+
                " ORDER BY "+YEAR+" ASC,"+MONTH+" ASC,"+DAY_OF_MONTH+" ASC,"+HOUR_OF_DAY+" ASC,"+MINUTE+" ASC ",new String[]{});

        ArrayList<Timers> timers = new ArrayList<Timers>();

        int iTimerID = c.getColumnIndex(TIMER_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iYear = c.getColumnIndex(YEAR);
        int iMonth = c.getColumnIndex(MONTH);
        int iDay = c.getColumnIndex(DAY_OF_MONTH);
        int iHour = c.getColumnIndex(HOUR_OF_DAY);
        int iMin = c.getColumnIndex(MINUTE);
        int iType = c.getColumnIndex(TYPE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            timers.add(new Timers(c.getInt(iTimerID),c.getInt(iTaskID),c.getInt(iYear),c.getInt(iMonth),
                    c.getInt(iDay),c.getInt(iHour),c.getInt(iMin),c.getString(iType)));

        }
        c.close();
        return timers;

    }

    public ArrayList<Timers> getTimersDataOfATask(String taskID)
    {
        String[] columns = new String[] {TIMER_ID,TASKS_ID,YEAR,MONTH,DAY_OF_MONTH,HOUR_OF_DAY,MINUTE,TYPE};
        Cursor c = ourDatabase.query(TIMER_TABLE,columns," T_ID =?",new String[]{taskID},null,null,null);

        ArrayList<Timers> timers = new ArrayList<Timers>();

        int iTimerID = c.getColumnIndex(TIMER_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iYear = c.getColumnIndex(YEAR);
        int iMonth = c.getColumnIndex(MONTH);
        int iDay = c.getColumnIndex(DAY_OF_MONTH);
        int iHour = c.getColumnIndex(HOUR_OF_DAY);
        int iMin = c.getColumnIndex(MINUTE);
        int iType = c.getColumnIndex(TYPE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            timers.add(new Timers(c.getInt(iTimerID),c.getInt(iTaskID),c.getInt(iYear),c.getInt(iMonth),
                    c.getInt(iDay),c.getInt(iHour),c.getInt(iMin),c.getString(iType)));

        }
        c.close();
        return timers;
    }

    public Timers getATimersData(String timerID)
    {
        String[] columns = new String[] {TIMER_ID,TASKS_ID,YEAR,MONTH,DAY_OF_MONTH,HOUR_OF_DAY,MINUTE,TYPE};
        Cursor c = ourDatabase.query(TIMER_TABLE,columns,TIMER_ID+" =?",new String[]{timerID},null,null,null);

        Timers timers = new Timers();

        int iTimerID = c.getColumnIndex(TIMER_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iYear = c.getColumnIndex(YEAR);
        int iMonth = c.getColumnIndex(MONTH);
        int iDay = c.getColumnIndex(DAY_OF_MONTH);
        int iHour = c.getColumnIndex(HOUR_OF_DAY);
        int iMin = c.getColumnIndex(MINUTE);
        int iType = c.getColumnIndex(TYPE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            timers = new Timers(c.getInt(iTimerID),c.getInt(iTaskID),c.getInt(iYear),c.getInt(iMonth),
                    c.getInt(iDay),c.getInt(iHour),c.getInt(iMin),c.getString(iType));

        }
        c.close();
        return timers;
    }

    public Timers getATodayTimersData(String timerID)
    {
        String[] columns = new String[] {TIMER_ID,TASKS_ID,YEAR,MONTH,DAY_OF_MONTH,HOUR_OF_DAY,MINUTE,TYPE};
        Cursor c = ourDatabase.query(TODAY_TIMER_TABLE,columns,TIMER_ID+" =?",new String[]{timerID},null,null,null);

        Timers timers = new Timers();

        int iTimerID = c.getColumnIndex(TIMER_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iYear = c.getColumnIndex(YEAR);
        int iMonth = c.getColumnIndex(MONTH);
        int iDay = c.getColumnIndex(DAY_OF_MONTH);
        int iHour = c.getColumnIndex(HOUR_OF_DAY);
        int iMin = c.getColumnIndex(MINUTE);
        int iType = c.getColumnIndex(TYPE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            timers = new Timers(c.getInt(iTimerID),c.getInt(iTaskID),c.getInt(iYear),c.getInt(iMonth),
                    c.getInt(iDay),c.getInt(iHour),c.getInt(iMin),c.getString(iType));

        }
        c.close();
        return timers;
    }

    public ArrayList<Timers> getTodayTimersData()
    {
        String[] columns = new String[] {TIMER_ID,TASKS_ID,YEAR,MONTH,DAY_OF_MONTH,HOUR_OF_DAY,MINUTE,TYPE};
        Cursor c = ourDatabase.query(TODAY_TIMER_TABLE,columns,null,null,null,null,null);

        ArrayList<Timers> timers = new ArrayList<Timers>();

        int iTimerID = c.getColumnIndex(TIMER_ID);
        int iTaskID = c.getColumnIndex(TASKS_ID);
        int iYear = c.getColumnIndex(YEAR);
        int iMonth = c.getColumnIndex(MONTH);
        int iDay = c.getColumnIndex(DAY_OF_MONTH);
        int iHour = c.getColumnIndex(HOUR_OF_DAY);
        int iMin = c.getColumnIndex(MINUTE);
        int iType = c.getColumnIndex(TYPE);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            timers.add(new Timers(c.getInt(iTimerID),c.getInt(iTaskID),c.getInt(iYear),c.getInt(iMonth),
                    c.getInt(iDay),c.getInt(iHour),c.getInt(iMin),c.getString(iType)));

        }
        c.close();
        return timers;
    }


}
