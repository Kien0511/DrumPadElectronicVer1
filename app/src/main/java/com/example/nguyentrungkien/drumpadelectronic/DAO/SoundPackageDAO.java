package com.example.nguyentrungkien.drumpadelectronic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nguyentrungkien.drumpadelectronic.DTO.SoundPackageDTO;
import com.example.nguyentrungkien.drumpadelectronic.Database.DrumPadDatabase;

public class SoundPackageDAO {
    DrumPadDatabase drumPadDatabase;
    SQLiteDatabase database;

    public SoundPackageDAO(Context context) {
        drumPadDatabase = new DrumPadDatabase(context);
    }

    public void open() {
        database = drumPadDatabase.getWritableDatabase();
    }

    public void close() {
        drumPadDatabase.close();
    }

    public SoundPackageDTO getSoundPackageDTO(int id) {
        String query = "select * from " + DrumPadDatabase.DB_TBSOUND + " where " + DrumPadDatabase.TB_SOUNDPACKAGEID + " = " + id;

        Cursor cursor = database.rawQuery(query, null);

        cursor.moveToFirst();


        SoundPackageDTO soundPackageDTO = new SoundPackageDTO();

        soundPackageDTO.setSoundpackage_id(cursor.getInt(cursor.getColumnIndex(DrumPadDatabase.TB_SOUNDPACKAGEID)));
        soundPackageDTO.setSoundpackage_name(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_SOUNDPACKAGENAME)));
        soundPackageDTO.setSoundpackage_content(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_SOUNDPACKAGECONTENT)));
        soundPackageDTO.setSoundpackage_unlock(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_SOUNDPACKAGEUNlOCK)));

        return soundPackageDTO;
    }

    public int countSoundPackage()
    {
        String query = "select * from " + DrumPadDatabase.DB_TBSOUND;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();

        return cursor.getCount();
    }

    public void updateSoundPackage(int id, String unlock)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DrumPadDatabase.TB_SOUNDPACKAGEUNlOCK,unlock);

        database.update(DrumPadDatabase.DB_TBSOUND,contentValues,DrumPadDatabase.TB_SOUNDPACKAGEID + "='" + id +"'",null);

    }

    public boolean checkUnlock(int id)
    {
        String query = "Select * from " + DrumPadDatabase.DB_TBSOUND + " where " + DrumPadDatabase.TB_SOUNDPACKAGEID + " ='" + id +"'";
        Cursor cursor = database.rawQuery(query,null);

        String result = "";
        result = cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_SOUNDPACKAGEUNlOCK));

        if(result.equals("yes"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
