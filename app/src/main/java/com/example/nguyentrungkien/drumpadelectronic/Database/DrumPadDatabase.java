package com.example.nguyentrungkien.drumpadelectronic.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DrumPadDatabase extends SQLiteOpenHelper {
    public static final String DB_PITCH = "db_pitch";
    public static final int DB_VERSION = 1;
    public static final String DB_TABLE = "tbl_pitch";
    public static final String TB_PITCHNAME = "pitchname";

    public static final String TB_PITCHA1 = "pitcha1";
    public static final String TB_PITCHA2 = "pitcha2";
    public static final String TB_PITCHA3 = "pitcha3";
    public static final String TB_PITCHA4 = "pitcha4";
    public static final String TB_PITCHA5 = "pitcha5";
    public static final String TB_PITCHA6 = "pitcha6";
    public static final String TB_PITCHA7 = "pitcha7";
    public static final String TB_PITCHA8 = "pitcha8";
    public static final String TB_PITCHA9 = "pitcha9";
    public static final String TB_PITCHA10 = "pitcha10";
    public static final String TB_PITCHA11 = "pitcha11";
    public static final String TB_PITCHA12 = "pitcha12";

    public static final String TB_PITCHB1 = "pitchb1";
    public static final String TB_PITCHB2 = "pitchb2";
    public static final String TB_PITCHB3 = "pitchb3";
    public static final String TB_PITCHB4 = "pitchb4";
    public static final String TB_PITCHB5 = "pitchb5";
    public static final String TB_PITCHB6 = "pitchb6";
    public static final String TB_PITCHB7 = "pitchb7";
    public static final String TB_PITCHB8 = "pitchb8";
    public static final String TB_PITCHB9 = "pitchb9";
    public static final String TB_PITCHB10 = "pitchb10";
    public static final String TB_PITCHB11 = "pitchb11";
    public static final String TB_PITCHB12 = "pitchb12";

    public static final String DB_TB = "preset";
    public static final String TB_RECORDNAME = "record_name";
    public static final String TB_PRESETNAME = "preset_name";
    public static final String DB_TBSOUND = "tbl_soundpackage";
    public static final String TB_SOUNDPACKAGENAME = "soundpackage_name";
    public static final String TB_SOUNDPACKAGEUNlOCK = "soundpackage_unlock";
    public static final String TB_SOUNDPACKAGEID = "soundpackage_id";
    public static final String TB_SOUNDPACKAGECONTENT = "soundpackage_content";


    public DrumPadDatabase(Context context) {
        super(context, DB_PITCH, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "create table " + DB_TABLE + " ( " + TB_PITCHNAME + " text primary key, " + TB_PITCHA1 + " text, " + TB_PITCHA2 + " text, "
                + TB_PITCHA3 + " text, " + TB_PITCHA4 + " text, " + TB_PITCHA5 + " text, " + TB_PITCHA6 + " text, "
                + TB_PITCHA7 + " text, " + TB_PITCHA8 + " text, " + TB_PITCHA9 + " text, " + TB_PITCHA10 + " text, "
                + TB_PITCHA11 + " text, " + TB_PITCHA12 + " text, " + TB_PITCHB1 + " text, " + TB_PITCHB2 + " text, "
                + TB_PITCHB3 + " text, " + TB_PITCHB4 + " text, " + TB_PITCHB5 + " text, " + TB_PITCHB6 + " text, "
                + TB_PITCHB7 + " text, " + TB_PITCHB8 + " text, " + TB_PITCHB9 + " text, " + TB_PITCHB10 + " text, "
                + TB_PITCHB11 + " text, "+ TB_PITCHB12 + " text )";

        sqLiteDatabase.execSQL(query);

        String queryPreset = "create table " + DB_TB + " ( " + TB_RECORDNAME + " text primary key, " + TB_PRESETNAME + " text )";

        sqLiteDatabase.execSQL(queryPreset);

        String querySoundPackage = "create table " + DB_TBSOUND + " ( " + TB_SOUNDPACKAGEID + " integer primary key," + TB_SOUNDPACKAGENAME + " text, " + TB_SOUNDPACKAGECONTENT + " text, " + TB_SOUNDPACKAGEUNlOCK +" text )";

        sqLiteDatabase.execSQL(querySoundPackage);
        String insertDefault = "insert into " + DB_TABLE + " values ( 'Default', '0', '0',  '0', '0', '0', '0', '0', '0', '0', '0'," +
                " '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' )";

        sqLiteDatabase.execSQL(insertDefault);

        String insertSoundPackage = "insert into " + DB_TBSOUND + " values ( 0, 'SoundPackage 1', 'abcxyz', 'yes')";
        String insertSoundPackage1 = "insert into " + DB_TBSOUND + " values ( 1, 'SoundPackage 2', 'abcxyz', 'no')";
        String insertSoundPackage2 = "insert into " + DB_TBSOUND + " values ( 2, 'SoundPackage 3', 'abcxyz', 'no')";

        sqLiteDatabase.execSQL(insertSoundPackage);
        sqLiteDatabase.execSQL(insertSoundPackage1);
        sqLiteDatabase.execSQL(insertSoundPackage2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + DB_TABLE);
        onCreate(sqLiteDatabase);
    }
}
