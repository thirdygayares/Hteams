package com.example.hteams.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {

    //STUDENTS TABLE
    public static final String STUDENTSTABLE = "STUDENTSTABLE";
    public static final String ID_STUDENTS = "ID_STUDENTS";
    public static final String STUDENT_IMAGE = "STUDENT_IMAGE";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String SECTION = "SECTION";
    public static final String COURSE = "COURSE";
    public static final String COLLEGE = "COLLEGE";

    //GROUP TABLE
    public static final String GROUPTABLE = "GROUPTABLE";
    public static final String ID_GROUP = "ID_GROUP";
    public static final String GROUPPHOTO = "GROUPPHOTO";
    public static final String GROUPNAME = "GROUPNAME";
    public static final String SUBJECT = "SUBJECT";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String PROFESSORS = "PROFESSORS";
    public static final String LEADER_ID = "LEADER_ID";
    public static final String CREATOR = "CREATOR";
    public static final String CREATED = "CREATED";


    //SUBJECT TABLE
    public static final String SUBJECTTABLE = "SUBJECTTABLE";
    public static final String ID_SUBJECT = "ID_SUBJECT";
    public static final String NAME_SUBJECT = "NAME_SUBJECT";
    public static final String TEACHER = "TEACHER";
    //public static final String SECTION = "SECTION";

    //SAVED TABLE
    public static final String SAVEDTABLE = "SAVEDTABLE";
    public static final String ID_SAVED = "ID_SAVED";
    public static final String UPDATES_ID = "UPDATES_ID";


    //NOTIFICATIONTABLE
    public static final String NOTIFICATIONTABLE = "NOTIFICATIONTABLE";
    public static final String NOTIFICATION_ID = "NOTIFICATION_ID";
    public static final String TYPE = "TYPE";
    public static final String NOTIFICATION_NAME = "NOTIFICATION_NAME";
    public static final String STATUS = "STATUS";


    //EVENT TABLE
    public static final String EVENTTABLE = "NOTIFICATIONTABLE";
    public static final String ID_EVENT = "ID_EVENT";
//    public static final String ID_GROUP = "ID_GROUP";
//    public static final String TYPE = "TYPE";
    public static final String LOGNAME = "LOGNAME";
    public static final String TIME = "TIME";
    public static final String DATE = "DATE";
 //   public static final String STATUS = "STATUS";


    //PARTICIPANTTABLE
    public static final String PARTICIPANTTABLE = "PARTICIPANTTABLE";
    public static final String PARTICIPANT_ID = "PARTICIPANT_ID";
    //public static final String ID_GROUP = "ID_GROUP";
    //public static final String ID_STUDENTS = "ID_STUDENTS";
    public static final String ACCEPTED = "ACCEPTED";

    //TABLE
    public static final String TABLETABLE = "TABLETABLE";
    public static final String ID_TABLE = "ID_TABLE";
    //public static final String ID_GROUP = "ID_GROUP";
    public static final String TABLENAME = "TABLENAME";

    //TASKTABLE
    public static final String TASKTABLE = "TASKTABLE";
    public static final String ID_TASK = "ID_TASK";
    //public static final String ID_GROUP = "ID_GROUP";
//    public static final String ID_TABLE = "ID_TABLE";
//    public static final String ID_STUDENTS = "ID_STUDENTS";
    public static final String TASK_NAME = "TASK_NAME";
//  public static final String STATUS = "STATUS";
    public static final String DUE = "DUE";
    public static final String DUEDATE = "DUEDATE";
    public static final String DUETIME = "DUETIME";

    //UPDATESTABLE
    public static final String UPDATESTABLE = "UPDATESTABLE";
    public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_TASK = "ID_TASK";
    //public static final String ID_GROUP = "ID_GROUP";
    //public static final String ID_TABLE = "ID_TABLE";
    //public static final String ID_STUDENTS = "ID_STUDENTS";
    public static final String POSTDATE = "POSTDATE";
    public static final String VIEWS_COUNT = "VIEWS_COUNT";


    //UPDATESLIKESTABLE
    public static final String UPDATESLIKESTABLE = "UPDATESLIKESTABLE";
    public static final String ID_LIKES = "ID_LIKES";
//    public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_STUDENTS = "ID_STUDENTS";

    //COMMENTSTABLE
    public static final String COMMENTSTABLE = "COMMENTSTABLE";
    public static final String ID_COMMENTS = "ID_COMMENTS";
    //public static final String ID_STUDENTS = "ID_STUDENTS";
    public static final String COMMENT = "COMMENT";

    //LINKTABLE
    public static final String LINKTABLE = "LINKTABLE";
    public static final String ID_LINK = "ID_LINK";
    //public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_GROUP = "ID_GROUP";
    public static final String CUSTOMNAME = "CUSTOMNAME";
    public static final String WEBLINK = "WEBLINK";
    public static final String SITENAME = "SITENAME";

    //LISTTABLE
    public static final String LISTTABLE = "LISTTABLE";
    public static final String ID_LIST = "ID_LIST";
    //public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_GROUP = "ID_GROUP";
    public static final String LISTNAME = "LISTNAME";
//    public static final String STATUS = "STATUS";

    //FILESTABLE
    public static final String FILESTABLE = "FILESTABLE";
    public static final String ID_FILES = "ID_FILES";
    //public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_GROUP = "ID_GROUP";
    public static final String FILES = "FILES";

    //PHOTOSTABLE
    public static final String PHOTOSTABLE = "PHOTOSTABLE";
    public static final String ID_PHOTOS = "ID_PHOTOS";
    //public static final String ID_UPDATES = "ID_UPDATES";
    //public static final String ID_GROUP = "ID_GROUP";
    public static final String PHOTOS = "PHOTOS";


    public DatabaseHelper(@Nullable Context context) { super(context, "hteams.db", null, 0);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATING STUDENTSTABLE
        String createStudentTable = "CREATE TABLE " + STUDENTSTABLE + "(" + ID_STUDENTS + " STRING PRIMARY KEY , " + STUDENT_IMAGE + " STRING,  " + NAME + " STRING, " + EMAIL + " STRING, " + SECTION + " STRING ," + COURSE + " STRING ,  " + COLLEGE  + " STRING )";
        db.execSQL(createStudentTable);

        //CREATING GROUPTABLE
        String createGroupTable = "CREATE TABLE " + GROUPTABLE + "(" + ID_GROUP + " INT PRIMARY KEY AUTOINCREMENT , " + GROUPPHOTO + " STRING,  " + GROUPNAME + " STRING, " + SUBJECT + " STRING, " + DESCRIPTION + " STRING ," + PROFESSORS + " STRING ,  " + LEADER_ID  + " STRING,"  + CREATOR + "STRING," + CREATED + "  DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) )";
        db.execSQL(createGroupTable);

        //CREATING SUBJECT  TABLE
        String createSubjectTable = "CREATE TABLE " + SUBJECTTABLE + "(" + ID_SUBJECT + " INT PRIMARY KEY AUTOINCREMENT, " + NAME_SUBJECT + " STRING,  " + TEACHER + " STRING, " + SECTION + " STRING )";
        db.execSQL(createSubjectTable);

        //CREATING SAVED TABLE
        String createSaveTable = "CREATE TABLE " + SAVEDTABLE + "(" + ID_SAVED + " INT PRIMARY KEY AUTOINCREMENT, " + UPDATES_ID + " INT )";
        db.execSQL(createSaveTable);

        //CREATING NOTIFICATION TABLE
        String createNotificationTable = "CREATE TABLE " + NOTIFICATIONTABLE + "(" + NOTIFICATION_ID + " INT PRIMARY KEY AUTOINCREMENT, " + TYPE + " STRING,  " + NOTIFICATION_NAME + " STRING, " + STATUS + " BOOLEAN DEFAULT 'false' )";
        db.execSQL(createNotificationTable);

        //CREATING EVENT TABLE
        String createEventTable = "CREATE TABLE " + EVENTTABLE + "(" + ID_EVENT + " INT PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INT,  " + LOGNAME + " STRING, " + TIME + " STRING, " + DATE + " STRING )";
        db.execSQL(createEventTable);

        //CREATING PARTICIPANT TABLE
        String createParticipantTable = "CREATE TABLE " + PARTICIPANTTABLE + "(" + PARTICIPANT_ID + " INT PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INT,  " + ID_STUDENTS + " STRING, " + ACCEPTED + " BOOLEAN DEFAULT 'false' )";
        db.execSQL(createParticipantTable);

        //CREATING TABLETABLE
        String createTableTable = "CREATE TABLE " + TABLETABLE + "(" + ID_TABLE + " INT PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INT,  " + TABLENAME + " STRING )";
        db.execSQL(createTableTable);


        //CREATING TASK TABLE
        String createTaskTable = "CREATE TABLE " + TASKTABLE + "(" + ID_TASK + " INT PRIMARY KEY AUTOINCREMENT , " + ID_GROUP + " INT,  " + ID_TABLE + " INT, " + ID_STUDENTS + " STRING, " + TASK_NAME + " STRING ," + STATUS + " STRING ,  " + DUE  + " BOOLEAN Default 'false'," + DUEDATE + " STRING," + DUETIME + "STRING," + CREATED + "  DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) )";
        db.execSQL(createTaskTable);

        //CREATING UPDATES TABLE
        String createupdatestable = "CREATE TABLE " + UPDATESTABLE + "(" + ID_UPDATES + " INT PRIMARY KEY , " + ID_TASK + " INT,  " + ID_GROUP + " INT, " + ID_STUDENTS + " STRING, " + POSTDATE + " DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) ," + VIEWS_COUNT + " INT  )";
        db.execSQL(createupdatestable);

        //CREATING UPDATES_LIKES TABLE
        String createUpdatesLikeTable = "CREATE TABLE " + UPDATESLIKESTABLE + "(" + ID_LIKES + " INT PRIMARY KEY AUTOINCREMENT, " + ID_UPDATES + " INT,  " + ID_STUDENTS + " STRING )";
        db.execSQL(createUpdatesLikeTable);

        //CREATING COMMENTS TABLE
        String createCommentTable = "CREATE TABLE " + COMMENTSTABLE + "(" + ID_COMMENTS + " INT PRIMARY KEY AUTOINCREMENT, " + ID_STUDENTS + " STRING,  " + COMMENT + " STRING )";
        db.execSQL(createCommentTable);

        //CREATING LINK TABLE
        String createLinkTable = "CREATE TABLE " + LINKTABLE + "(" + ID_LINK + " INT PRIMARY KEY , " + ID_UPDATES + " INT,  " + ID_GROUP + " INT, " + CUSTOMNAME + " STRING, " + WEBLINK + " STRING, " + SITENAME + " STRING  )";
        db.execSQL(createLinkTable);

        //CREATING LIST TABLE

        String createListTable = "CREATE TABLE " + LISTTABLE + "(" + ID_LIST + " INT PRIMARY KEY , " + ID_GROUP + " INT,  " + ID_UPDATES + " INT, " + LISTNAME + " STRING, " + STATUS + " BOOLEAN DEFAULT 'false'  )";
        db.execSQL(createListTable);

        //CREATING FILES TABLE
        String createFilesTable = "CREATE TABLE " + FILESTABLE + "(" + ID_FILES + " INT PRIMARY KEY , " + ID_GROUP + " INT,  " + ID_UPDATES + " INT, " + FILES + " STRING)";
        db.execSQL(createFilesTable);

        //CREATING PHOTOS TABLE
        String createPhotoTable = "CREATE TABLE " + PHOTOSTABLE + "(" + ID_PHOTOS + " INT PRIMARY KEY , " + ID_GROUP + " INT,  " + ID_UPDATES + " INT, " + PHOTOS + " STRING)";
        db.execSQL(createPhotoTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + GROUPTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SUBJECTTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + SAVEDTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATIONTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EVENTTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PARTICIPANTTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TASKTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UPDATESTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UPDATESLIKESTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMMENTSTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LINKTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + LISTTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + FILESTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + PHOTOSTABLE);

        onCreate(db);
    }
}
