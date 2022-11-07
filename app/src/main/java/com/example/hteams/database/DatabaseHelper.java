package com.example.hteams.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hteams.Testing.SubjectModel;
import com.example.hteams.Testing.Testing1;
import com.example.hteams.Testing.Testing1Model;
import com.example.hteams.model.ChooseParticipantModel;
import com.example.hteams.model.InviteModel;
import com.example.hteams.model.SQLITEADDTASKMODEL;
import com.example.hteams.model.SQLITECREATEGROUPMODEL;
import com.example.hteams.model.SQLITEPARTICIPANTMODEL;


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
    public static final String EVENTTABLE = "EVENTTABLE";
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
//    public static final String POSITION = "POSITION";

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
    public static final String POSITION = "POSITION";


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


    public DatabaseHelper(@Nullable Context context) { super(context, "hteams.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREATING STUDENTSTABLE
        String createStudentTable = "CREATE TABLE " + STUDENTSTABLE + "(" + ID_STUDENTS + " STRING PRIMARY KEY , " + STUDENT_IMAGE + " STRING,  " + NAME + " STRING, " + EMAIL + " STRING, " + SECTION + " STRING ," + COURSE + " STRING ,  " + COLLEGE  + " STRING )";
        db.execSQL(createStudentTable);

        //CREATING GROUPTABLE
        String createGroupTable = "CREATE TABLE " + GROUPTABLE + "(" + ID_GROUP + " INTEGER PRIMARY KEY AUTOINCREMENT , " + GROUPPHOTO + " STRING,  " + GROUPNAME + " STRING, " + SUBJECT + " STRING, " + DESCRIPTION + " STRING ," + PROFESSORS + " STRING ,  " + LEADER_ID  + " STRING,"  + CREATOR + " STRING," + CREATED + "  DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) )";
        db.execSQL(createGroupTable);

        //CREATING SUBJECT  TABLE
        String createSubjectTable = "CREATE TABLE " + SUBJECTTABLE + "(" + ID_SUBJECT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME_SUBJECT + " STRING,  " + TEACHER + " STRING, " + SECTION + " STRING )";
        db.execSQL(createSubjectTable);

        //CREATING SAVED TABLE
        String createSaveTable = "CREATE TABLE " + SAVEDTABLE + "(" + ID_SAVED + " INTEGER PRIMARY KEY AUTOINCREMENT, " + UPDATES_ID + " INTEGER )";
        db.execSQL(createSaveTable);

        //CREATING NOTIFICATION TABLE
        String createNotificationTable = "CREATE TABLE " + NOTIFICATIONTABLE + "(" + NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TYPE + " STRING,  " + NOTIFICATION_NAME + " STRING, " + STATUS + " BOOLEAN DEFAULT 'false' )";
        db.execSQL(createNotificationTable);

        //CREATING EVENT TABLE
        String createEventTable = "CREATE TABLE " + EVENTTABLE + "(" + ID_EVENT + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INTEGER,  " + LOGNAME + " STRING, " + TIME + " STRING, " + DATE + " STRING )";
        db.execSQL(createEventTable);

        //CREATING PARTICIPANT TABLE
        String createParticipantTable = "CREATE TABLE " + PARTICIPANTTABLE + "(" + PARTICIPANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INTEGER,  " + ID_STUDENTS + " STRING, " + ACCEPTED + " BOOLEAN DEFAULT 0 )";
        db.execSQL(createParticipantTable);

        //CREATING TABLETABLE
        String createTableTable = "CREATE TABLE " + TABLETABLE + "(" + ID_TABLE + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_GROUP + " INTEGER,  " + TABLENAME + " STRING, " + POSITION +" INTEGER, "+ CREATED + " DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW'))  )";
        db.execSQL(createTableTable);


        //CREATING TASK TABLE
        String createTaskTable = "CREATE TABLE " + TASKTABLE + "(" + ID_TASK + " INTEGER PRIMARY KEY AUTOINCREMENT , " + ID_GROUP + " INTEGER,  " + ID_TABLE + " INTEGER, " + ID_STUDENTS + " STRING, " + TASK_NAME + " STRING ," + STATUS + " STRING Default 'TO DO',  " + DUE  + " BOOLEAN Default 'false'," + DUEDATE + " STRING," + DUETIME + " STRING,"  + POSITION + " INT, "+ CREATED + "  DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) )";
        db.execSQL(createTaskTable);

        //CREATING UPDATES TABLE
        String createupdatestable = "CREATE TABLE " + UPDATESTABLE + "(" + ID_UPDATES + " INTEGER PRIMARY KEY , " + ID_TASK + " INTEGER,  " + ID_GROUP + " INTEGER, " + ID_STUDENTS + " STRING, " + POSTDATE + " DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) ," + VIEWS_COUNT + " INTEGER," + CREATED + " DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')) )";
        db.execSQL(createupdatestable);

        //CREATING UPDATES_LIKES TABLE
        String createUpdatesLikeTable = "CREATE TABLE " + UPDATESLIKESTABLE + "(" + ID_LIKES + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_UPDATES + " INTEGER,  " + ID_STUDENTS + " STRING )";
        db.execSQL(createUpdatesLikeTable);

        //CREATING COMMENTS TABLE
        String createCommentTable = "CREATE TABLE " + COMMENTSTABLE + "(" + ID_COMMENTS + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ID_STUDENTS + " STRING,  " + COMMENT + " STRING )";
        db.execSQL(createCommentTable);

        //CREATING LINK TABLE
        String createLinkTable = "CREATE TABLE " + LINKTABLE + "(" + ID_LINK + " INTEGER PRIMARY KEY , " + ID_UPDATES + " INTEGER,  " + ID_GROUP + " INTEGER, " + CUSTOMNAME + " STRING, " + WEBLINK + " STRING, " + SITENAME + " STRING," + CREATED + " DEFAULT (STRFTIME('%Y-%m-%d %H:%M:%f', 'NOW')))";
        db.execSQL(createLinkTable);

        //CREATING LIST TABLE
        String createListTable = "CREATE TABLE " + LISTTABLE + "(" + ID_LIST + " INTEGER PRIMARY KEY , " + ID_GROUP + " INTEGER,  " + ID_UPDATES + " INTEGER, " + LISTNAME + " STRING, " + STATUS + " BOOLEAN DEFAULT 'false'  )";
        db.execSQL(createListTable);

        //CREATING FILES TABLE
        String createFilesTable = "CREATE TABLE " + FILESTABLE + "(" + ID_FILES + " INTEGER PRIMARY KEY , " + ID_GROUP + " INTEGER,  " + ID_UPDATES + " INTEGER, " + FILES + " STRING)";
        db.execSQL(createFilesTable);

        //CREATING PHOTOS TABLE
        String createPhotoTable = "CREATE TABLE " + PHOTOSTABLE + "(" + ID_PHOTOS + " INTEGER PRIMARY KEY , " + ID_GROUP + " INTEGER,  " + ID_UPDATES + " INTEGER, " + PHOTOS + " STRING)";
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

    //TODO START OF ADDING DATA
    //add students
    public boolean addStudents(Testing1Model testing1Model){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_STUDENTS, testing1Model.getID_STUDENTS());
        cv.put(STUDENT_IMAGE, testing1Model.getSTUDENTS_IMAGE_STRING());
        cv.put(NAME, testing1Model.getNAME());
        cv.put(EMAIL, testing1Model.getEMAIL());
        cv.put(SECTION, testing1Model.getSECTION());
        cv.put(COURSE, testing1Model.getCOURSE());
        cv.put(COLLEGE, testing1Model.getCOLLEGE());

        long insert = db.insert(STUDENTSTABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //add students

    public boolean addSubject(SubjectModel subjectModel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME_SUBJECT, subjectModel.getNAME_SUBJECT());
        cv.put(TEACHER, subjectModel.getTEACHER());
        cv.put(SECTION, subjectModel.getSECTION());

        long insert = db.insert(SUBJECTTABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }


    //add Groups
    public boolean addGroups(SQLITECREATEGROUPMODEL sqlitecreategroupmodel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(GROUPPHOTO, sqlitecreategroupmodel.getGROUPPHOTO());
        cv.put(GROUPNAME, sqlitecreategroupmodel.getGROUPNAME());
        cv.put(SUBJECT, sqlitecreategroupmodel.getSUBJECT());
        cv.put(DESCRIPTION, sqlitecreategroupmodel.getDESCRIPTION());
        cv.put(PROFESSORS, sqlitecreategroupmodel.getPROFESSORS());
        cv.put(LEADER_ID, sqlitecreategroupmodel.getLEADER_ID());
        cv.put(CREATOR, sqlitecreategroupmodel.getCREATOR());

        long insert = db.insert(GROUPTABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //add participant
    public boolean addParticipant(SQLITEPARTICIPANTMODEL sqliteparticipantmodel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_GROUP, sqliteparticipantmodel.getID_GROUP());
        cv.put(ID_STUDENTS, sqliteparticipantmodel.getID());
        cv.put(ACCEPTED, sqliteparticipantmodel.getACCEPTED());

        long insert = db.insert(PARTICIPANTTABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //add table
    public boolean addTable(String Table, int group_id, int position){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLENAME, Table);
        cv.put(ID_GROUP, group_id);
        cv.put(POSITION, position);


        long insert = db.insert(TABLETABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //add table
    public boolean addTask(SQLITEADDTASKMODEL sqliteaddtaskmodel){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ID_GROUP, sqliteaddtaskmodel.getID_GROUP());
        cv.put(ID_TABLE, sqliteaddtaskmodel.getID_TABLE());
        cv.put(ID_STUDENTS, sqliteaddtaskmodel.getID_STUDENTS());
        cv.put(TASK_NAME, sqliteaddtaskmodel.getTASK_NAME());
        cv.put(STATUS, sqliteaddtaskmodel.getSTATUS());
        cv.put(DUEDATE, sqliteaddtaskmodel.getDueDate());
        cv.put(DUETIME, sqliteaddtaskmodel.getDueTime());
        cv.put(POSITION, sqliteaddtaskmodel.getPosition());
        long insert = db.insert(TASKTABLE, null, cv);
        if (insert == -1){
            return false;
        }else{
            return true;
        }
    }

    //TODO UPDATE PAGE
    //update participant from the task
    public boolean updateParticipant(String taskid,String participantID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateParticipant = new ContentValues();
        updateParticipant.put(ID_STUDENTS, participantID);
        db.update(TASKTABLE, updateParticipant,ID_TASK + " = ?",new String[] {taskid});
        return true;
    }

    //edit participant from the task
    public boolean updateStatus(String taskid,String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateParticipant = new ContentValues();
        updateParticipant.put(STATUS, status);
        db.update(TASKTABLE, updateParticipant,ID_TASK + " = ?",new String[] {taskid});
        return true;
    }

    //edit date from the task
    public boolean updateDue(String taskid,String duedate, String dueTime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateDue = new ContentValues();
        updateDue.put(DUEDATE, duedate);
        updateDue.put(DUETIME, dueTime);
        db.update(TASKTABLE, updateDue,ID_TASK + " = ?",new String[] {taskid});
        return true;
    }



    // TODO testing
    public Cursor checkifmaylaman(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + STUDENTSTABLE , null);
        return data;
    }

    //testing
    public Cursor getListContents(String section){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + STUDENTSTABLE + " WHERE SECTION = ?", new String[] {section});
        return data;
    }

    public Cursor getAll(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + STUDENTSTABLE , null);
        return data;
    }

    //get Id stidents , students image , name
    public Cursor getData(String SECTION){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT ID_STUDENTS,NAME,STUDENT_IMAGE FROM " + STUDENTSTABLE + " WHERE SECTION = ? " , new String[] {SECTION});
        return data;
    }

    //get subject of a one srcion
    public Cursor getSubject(String section){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.      rawQuery("SELECT * FROM " + SUBJECTTABLE + " WHERE SECTION = ?", new String[] {section});
        return data;
    }

    //find section or participant
    public Cursor getSection(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT SECTION FROM " + STUDENTSTABLE + " WHERE ID_STUDENTS = ?", new String[] {uid});
        return data;
    }

    //find name of current User
    public Cursor getCurrentName(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT NAME FROM " + STUDENTSTABLE + " WHERE ID_STUDENTS = ?", new String[] {uid});
        return data;
    }

    //find image of current User
    public Cursor getImageCurrentsUser(String uid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT STUDENT_IMAGE FROM " + STUDENTSTABLE + " WHERE ID_STUDENTS = ?", new String[] {uid});
        return data;
    }

    //getting professor name by cliking the bottomsheetdialog
    public Cursor getProfessor(String subjectId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT TEACHER, SECTION FROM " + SUBJECTTABLE + " WHERE ID_SUBJECT = ?", new String[] {subjectId});
        return data;
    }

    //getting the max id of group table to insert participant
    public  Cursor selectLastIdGroupTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT max(" + ID_GROUP + ") FROM " + GROUPTABLE, null );
        return  data;
        }

        //getting all the group of current user and accepted
    public  Cursor selectMyGroups(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + ID_GROUP + " FROM " + PARTICIPANTTABLE + " where " + ID_STUDENTS + " = ? AND " + ACCEPTED + " = 1" , new String[] {id} );
        return  data;
    }

    //this method ay alam na niya na nakuha niya yung id at accepted na ng user
    public  Cursor myGroup(String myGroups){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + GROUPTABLE + " where " + ID_GROUP + " = ? ", new String[] {myGroups} );
        return  data;
    }

    //get Image and group name
    public  Cursor DisplayGroupDetails(String myGroups){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + GROUPPHOTO + "," + GROUPNAME + " FROM " + GROUPTABLE + " where " + ID_GROUP + " = ? ", new String[] {myGroups} );
        return  data;
    }

    //getting the max id of task table to insert task
    public  Cursor selectLastTaskTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT max(" + ID_TABLE + "), "+ POSITION +" FROM " + TABLETABLE, null );
        return  data;
    }

    //getting the position of a task by group
    public  Cursor selectPosition(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT max(" + ID_TABLE + "), "+ POSITION +" FROM " + TABLETABLE, null );
        return  data;
    }

    //getting the position of a task by group
    public  Cursor getpositionbyfindinggroupandtable(String groupid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + POSITION   +" FROM " + TABLETABLE +" WHERE "+ ID_GROUP  + " = ? ORDER BY " + POSITION + " DESC LIMIT 1" , new String[] {groupid} );
        return  data;
    }


    //getting only the partcipant in a group
    //ex. if the leader the role of a member di niya makikita ang ibang section
    public  Cursor getParticipant(String myGroups){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + ID_STUDENTS + " FROM " + PARTICIPANTTABLE  + " WHERE " + ID_GROUP + " = ? AND " + ACCEPTED + " = 1" ,  new String[] {myGroups}  );
        return  data;
    }





    // Kung mayron na makita lang by section : ito naman kung sino lang kasali sa grupo
    public Cursor getNameImageParticipant(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + STUDENT_IMAGE + ", " + NAME + "  FROM " + STUDENTSTABLE + " WHERE " + ID_STUDENTS + " = ? ", new String[] {id});
        return data;
    }

    // Kung mayron na makita lang by section : ito naman kung sino lang kasali sa grupo
    public Cursor getAllTable(String groupID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLETABLE + " WHERE " + ID_GROUP + " = ? ", new String[] {groupID});
        return data;
    }

    // sa group page iretrieve na yung data ng mga task
    public Cursor getTaskTable(String position, String groupID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TASKTABLE + " WHERE " + POSITION + " = ? AND " + ID_GROUP + " = ? ", new String[] {position,groupID});
        return data;
    }

    // sa group page iretrieve na yung data ng mga task
    public Cursor getTaskName(String taskID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT " + TASK_NAME + " FROM " + TASKTABLE + " WHERE " + ID_TASK + " = ?  ", new String[] {taskID});
        return data;
    }


    // sa group page iretrieve na yung data
    // bibilangin ang group Table na table para mabilang kung ilan
    public Cursor getCountAllTable(String groupID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(" SELECT COUNT (*) FROM " + TABLETABLE + " WHERE " + ID_GROUP + " = ? ", new String[] {groupID});
        return data;
    }


    // retrieve the taskName via task ID
    public Cursor getTaskName(int taskID){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TASKTABLE + " WHERE " + ID_TASK + " = ? ", new String[] {String.valueOf(taskID)});
        return data;
    }

    // retrieve the taskName via task ID
    public Cursor getTableName(int tableid){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLETABLE + " WHERE " + ID_TABLE + " = ? ", new String[] {String.valueOf(tableid)});
        Log.d("TAG", "in table db " + tableid );
        return data;
    }

    // retrieve the tasktable via  ID group nad id table
    public Cursor getTaskTableforGroupPage(String groupId,  String Tableid, String statud ){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TASKTABLE + " WHERE " + ID_GROUP + " = ? AND " + ID_TABLE + " = ? AND " + STATUS + " = ? " , new String[] {groupId,Tableid,statud});

        return data;
    }



}
