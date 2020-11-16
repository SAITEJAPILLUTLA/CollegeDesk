package chillar.epizy.collegedesk

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

val dbName ="myDb"
val USERNAME="USERNAME"
val NAME="NAME"
val EMAIL="EMAIL"
val UID ="UID"
val PROFILEIMAGEURL="PROFILEIMAGEURL"
var PROFILETABLE="PROFILETABLE"
val TAG="adduseractivity"
val SEARCHHISTORYTABLE="SEARCHHISTORYTABLE"



class dbhandler(context: Context): SQLiteOpenHelper(context, dbName,null,1) {
    override fun onCreate(db: SQLiteDatabase?){
        var profileTable="CREATE TABLE ${PROFILETABLE} (" +
                "${UID} TEXT PRIMARY KEY," +
                " ${NAME} TEXT, ${EMAIL} TEXT ,${USERNAME} ,${PROFILEIMAGEURL} )";

        val historyTable="CREATE TABLE ${SEARCHHISTORYTABLE} (" +
                "${UID} TEXT PRIMARY KEY," +
                " ${NAME} TEXT, ${EMAIL} TEXT ,${USERNAME} ,${PROFILEIMAGEURL} )";


        if (db!=null){
            db.execSQL(profileTable)
            db.execSQL(historyTable)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {  //hello
    }
    fun insertUser(user: User){
        var db =this.writableDatabase
        var cv = ContentValues()
        cv.put(UID,user.uid)
        cv.put(NAME,user.name)
        cv.put(EMAIL,user.email)
        cv.put(PROFILEIMAGEURL,user.profileImageUrl)
        val result=db.insert(PROFILETABLE,null,cv)
        if (result==-1.toLong()){
            //Toast.makeText(currentContext,"failed",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"Failed to update to local DataBase")
        }else{
            Log.d(TAG,"Sucess to update to local DataBase")


        }

    }
    fun fetchUser ():MutableList<User>{
        var list :MutableList<User> = ArrayList()
        var db = this.readableDatabase
        val query ="Select * from "+ PROFILETABLE
        val result =db.rawQuery(query,null)
        while (result.moveToNext()) {
            var user = User()
            user.uid = result.getString(result.getColumnIndex(UID))
            user.name = result.getString(result.getColumnIndex(NAME))
            user.email = result.getString(result.getColumnIndex(EMAIL))
            user.profileImageUrl = result.getString(result.getColumnIndex(PROFILEIMAGEURL))
            user.username = result.getString(result.getColumnIndex(USERNAME))
            list.add(user)}
        result.close()
        db.close()
        return list
        }
    fun signout(){

        var db =this.writableDatabase
        var deleteHistory ="DELETE FROM ${PROFILETABLE}";
        db.execSQL(deleteHistory)
    }
    fun insertsearchUser(user: User){
        var db =this.writableDatabase
        var cv = ContentValues()
        cv.put(UID,user.uid)
        cv.put(NAME,user.name)
        cv.put(EMAIL,user.email)
        cv.put(PROFILEIMAGEURL,user.profileImageUrl)
        val result=db.insert(SEARCHHISTORYTABLE,null,cv)
        if (result==-1.toLong()){
            //Toast.makeText(currentContext,"failed",Toast.LENGTH_SHORT).show()
            Log.d(TAG,"Failed to update to local DataBase")
        }else{
            Log.d(TAG,"Sucess to update to local DataBase")


        }
    }
    fun fetchsearchUser ():MutableList<User>{
        var list :MutableList<User> = ArrayList()
        var db = this.readableDatabase
        val query ="Select * from "+ SEARCHHISTORYTABLE
        val result =db.rawQuery(query,null)
        while (result.moveToNext()) {
            var user = User()
            user.uid = result.getString(result.getColumnIndex(UID))
            user.name = result.getString(result.getColumnIndex(NAME))
            user.email = result.getString(result.getColumnIndex(EMAIL))
            user.profileImageUrl = result.getString(result.getColumnIndex(PROFILEIMAGEURL))
            user.username = result.getString(result.getColumnIndex(USERNAME))
            list.add(user)}
        result.close()
        db.close()
        return list
    }
}