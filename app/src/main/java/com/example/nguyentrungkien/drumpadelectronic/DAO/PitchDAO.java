package com.example.nguyentrungkien.drumpadelectronic.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nguyentrungkien.drumpadelectronic.DTO.PitchDTO;
import com.example.nguyentrungkien.drumpadelectronic.DTO.PresetDTO;
import com.example.nguyentrungkien.drumpadelectronic.Database.DrumPadDatabase;

import java.util.ArrayList;
import java.util.List;

public class PitchDAO {

    DrumPadDatabase drumPadDatabase;
    SQLiteDatabase database;

    public PitchDAO(Context context) {
        drumPadDatabase = new DrumPadDatabase(context);
    }

    public void open()
    {
        database = drumPadDatabase.getWritableDatabase();
    }

    public void close()
    {
        drumPadDatabase.close();
    }

    public boolean AddPreset(PitchDTO pitchDTO)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DrumPadDatabase.TB_PITCHNAME,pitchDTO.getPicht_name());

        contentValues.put(DrumPadDatabase.TB_PITCHA1,pitchDTO.getPicht_a1());
        contentValues.put(DrumPadDatabase.TB_PITCHA2,pitchDTO.getPicht_a2());
        contentValues.put(DrumPadDatabase.TB_PITCHA3,pitchDTO.getPicht_a3());
        contentValues.put(DrumPadDatabase.TB_PITCHA4,pitchDTO.getPicht_a4());
        contentValues.put(DrumPadDatabase.TB_PITCHA5,pitchDTO.getPicht_a5());
        contentValues.put(DrumPadDatabase.TB_PITCHA6,pitchDTO.getPicht_a6());
        contentValues.put(DrumPadDatabase.TB_PITCHA7,pitchDTO.getPicht_a7());
        contentValues.put(DrumPadDatabase.TB_PITCHA8,pitchDTO.getPicht_a8());
        contentValues.put(DrumPadDatabase.TB_PITCHA9,pitchDTO.getPicht_a9());
        contentValues.put(DrumPadDatabase.TB_PITCHA10,pitchDTO.getPicht_a10());
        contentValues.put(DrumPadDatabase.TB_PITCHA11,pitchDTO.getPicht_a11());
        contentValues.put(DrumPadDatabase.TB_PITCHA12,pitchDTO.getPicht_a12());

        contentValues.put(DrumPadDatabase.TB_PITCHB1,pitchDTO.getPicht_b1());
        contentValues.put(DrumPadDatabase.TB_PITCHB2,pitchDTO.getPicht_b2());
        contentValues.put(DrumPadDatabase.TB_PITCHB3,pitchDTO.getPicht_b3());
        contentValues.put(DrumPadDatabase.TB_PITCHB4,pitchDTO.getPicht_b4());
        contentValues.put(DrumPadDatabase.TB_PITCHB5,pitchDTO.getPicht_b5());
        contentValues.put(DrumPadDatabase.TB_PITCHB6,pitchDTO.getPicht_b6());
        contentValues.put(DrumPadDatabase.TB_PITCHB7,pitchDTO.getPicht_b7());
        contentValues.put(DrumPadDatabase.TB_PITCHB8,pitchDTO.getPicht_b8());
        contentValues.put(DrumPadDatabase.TB_PITCHB9,pitchDTO.getPicht_b9());
        contentValues.put(DrumPadDatabase.TB_PITCHB10,pitchDTO.getPicht_b10());
        contentValues.put(DrumPadDatabase.TB_PITCHB11,pitchDTO.getPicht_b11());
        contentValues.put(DrumPadDatabase.TB_PITCHB12,pitchDTO.getPicht_b12());

        long id = database.insert(DrumPadDatabase.DB_TABLE,null,contentValues);

        Log.e("cccccccccc",id+"");

        if(id > 0)
        {
            Log.e("cccccccc","true");
            return true;
        }
        else
        {
            Log.e("cccccccc","false");

            return false;
        }
    }

    public List<PitchDTO> GetListPreset()
    {
        List<PitchDTO> list = new ArrayList<>();

        String query = "select * from " + DrumPadDatabase.DB_TABLE;
        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            PitchDTO pitchDTO = new PitchDTO();

            pitchDTO.setPicht_name(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHNAME)));

            pitchDTO.setPicht_a1(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA1)));
            pitchDTO.setPicht_a2(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA2)));
            pitchDTO.setPicht_a3(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA3)));
            pitchDTO.setPicht_a4(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA4)));
            pitchDTO.setPicht_a5(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA5)));
            pitchDTO.setPicht_a6(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA6)));
            pitchDTO.setPicht_a7(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA7)));
            pitchDTO.setPicht_a8(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA8)));
            pitchDTO.setPicht_a9(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA9)));
            pitchDTO.setPicht_a10(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA10)));
            pitchDTO.setPicht_a11(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA11)));
            pitchDTO.setPicht_a12(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA12)));

            pitchDTO.setPicht_b1(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB1)));
            pitchDTO.setPicht_b2(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB2)));
            pitchDTO.setPicht_b3(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB3)));
            pitchDTO.setPicht_b4(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB4)));
            pitchDTO.setPicht_b5(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB5)));
            pitchDTO.setPicht_b6(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB6)));
            pitchDTO.setPicht_b7(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB7)));
            pitchDTO.setPicht_b8(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB8)));
            pitchDTO.setPicht_b9(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB9)));
            pitchDTO.setPicht_b10(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB10)));
            pitchDTO.setPicht_b11(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB11)));
            pitchDTO.setPicht_b12(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB12)));

            list.add(pitchDTO);

            cursor.moveToNext();
        }
        return list;
    }

    public boolean CheckPresetName(String name){
        String query = "select * from " + DrumPadDatabase.DB_TABLE + " where " + DrumPadDatabase.TB_PITCHNAME + " ='" + name + "'";

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();

        if(cursor.getCount() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean CheckAmountRecord()
    {
        String query = "select * from " + DrumPadDatabase.DB_TABLE;

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();

        if(cursor.getCount() >= 5)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean UpdatePreset(PitchDTO pitchDTO)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DrumPadDatabase.TB_PITCHA1,pitchDTO.getPicht_a1());
        contentValues.put(DrumPadDatabase.TB_PITCHA2,pitchDTO.getPicht_a2());
        contentValues.put(DrumPadDatabase.TB_PITCHA3,pitchDTO.getPicht_a3());
        contentValues.put(DrumPadDatabase.TB_PITCHA4,pitchDTO.getPicht_a4());
        contentValues.put(DrumPadDatabase.TB_PITCHA5,pitchDTO.getPicht_a5());
        contentValues.put(DrumPadDatabase.TB_PITCHA6,pitchDTO.getPicht_a6());
        contentValues.put(DrumPadDatabase.TB_PITCHA7,pitchDTO.getPicht_a7());
        contentValues.put(DrumPadDatabase.TB_PITCHA8,pitchDTO.getPicht_a8());
        contentValues.put(DrumPadDatabase.TB_PITCHA9,pitchDTO.getPicht_a9());
        contentValues.put(DrumPadDatabase.TB_PITCHA10,pitchDTO.getPicht_a10());
        contentValues.put(DrumPadDatabase.TB_PITCHA11,pitchDTO.getPicht_a11());
        contentValues.put(DrumPadDatabase.TB_PITCHA12,pitchDTO.getPicht_a12());

        contentValues.put(DrumPadDatabase.TB_PITCHB1,pitchDTO.getPicht_b1());
        contentValues.put(DrumPadDatabase.TB_PITCHB2,pitchDTO.getPicht_b2());
        contentValues.put(DrumPadDatabase.TB_PITCHB3,pitchDTO.getPicht_b3());
        contentValues.put(DrumPadDatabase.TB_PITCHB4,pitchDTO.getPicht_b4());
        contentValues.put(DrumPadDatabase.TB_PITCHB5,pitchDTO.getPicht_b5());
        contentValues.put(DrumPadDatabase.TB_PITCHB6,pitchDTO.getPicht_b6());
        contentValues.put(DrumPadDatabase.TB_PITCHB7,pitchDTO.getPicht_b7());
        contentValues.put(DrumPadDatabase.TB_PITCHB8,pitchDTO.getPicht_b8());
        contentValues.put(DrumPadDatabase.TB_PITCHB9,pitchDTO.getPicht_b9());
        contentValues.put(DrumPadDatabase.TB_PITCHB10,pitchDTO.getPicht_b10());
        contentValues.put(DrumPadDatabase.TB_PITCHB11,pitchDTO.getPicht_b11());
        contentValues.put(DrumPadDatabase.TB_PITCHB12,pitchDTO.getPicht_b12());

        long id = database.update(DrumPadDatabase.DB_TABLE,contentValues,DrumPadDatabase.TB_PITCHNAME + "='" + pitchDTO.getPicht_name() + "'",null);

        if(id > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public PitchDTO CurrentPitch(String name)
    {
        String query = "select * from " + DrumPadDatabase.DB_TABLE + " where " + DrumPadDatabase.TB_PITCHNAME + " ='" + name + "'";

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();

        PitchDTO pitchDTO = new PitchDTO();

        pitchDTO.setPicht_name(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHNAME)));

        pitchDTO.setPicht_a1(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA1)));
        pitchDTO.setPicht_a2(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA2)));
        pitchDTO.setPicht_a3(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA3)));
        pitchDTO.setPicht_a4(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA4)));
        pitchDTO.setPicht_a5(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA5)));
        pitchDTO.setPicht_a6(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA6)));
        pitchDTO.setPicht_a7(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA7)));
        pitchDTO.setPicht_a8(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA8)));
        pitchDTO.setPicht_a9(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA9)));
        pitchDTO.setPicht_a10(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA10)));
        pitchDTO.setPicht_a11(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA11)));
        pitchDTO.setPicht_a12(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHA12)));

        pitchDTO.setPicht_b1(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB1)));
        pitchDTO.setPicht_b2(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB2)));
        pitchDTO.setPicht_b3(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB3)));
        pitchDTO.setPicht_b4(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB4)));
        pitchDTO.setPicht_b5(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB5)));
        pitchDTO.setPicht_b6(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB6)));
        pitchDTO.setPicht_b7(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB7)));
        pitchDTO.setPicht_b8(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB8)));
        pitchDTO.setPicht_b9(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB9)));
        pitchDTO.setPicht_b10(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB10)));
        pitchDTO.setPicht_b11(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB11)));
        pitchDTO.setPicht_b12(cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHB12)));

        return pitchDTO;
    }

    public String GetSpinnerListRecordName(PitchDTO pitchDTO)
    {
        String name = "";
        String presetName = "select * from " + DrumPadDatabase.DB_TABLE + " where "
                + DrumPadDatabase.TB_PITCHA1 + " ='" + pitchDTO.getPicht_a1() + "' and "
                + DrumPadDatabase.TB_PITCHA2 + " ='" + pitchDTO.getPicht_a2() + "' and "
                + DrumPadDatabase.TB_PITCHA3 + " ='" + pitchDTO.getPicht_a3() + "' and "
                + DrumPadDatabase.TB_PITCHA4 + " ='" + pitchDTO.getPicht_a4() + "' and "
                + DrumPadDatabase.TB_PITCHA5 + " ='" + pitchDTO.getPicht_a5() + "' and "
                + DrumPadDatabase.TB_PITCHA6 + " ='" + pitchDTO.getPicht_a6() + "' and "
                + DrumPadDatabase.TB_PITCHA7 + " ='" + pitchDTO.getPicht_a7() + "' and "
                + DrumPadDatabase.TB_PITCHA8 + " ='" + pitchDTO.getPicht_a8() + "' and "
                + DrumPadDatabase.TB_PITCHA9 + " ='" + pitchDTO.getPicht_a9() + "' and "
                + DrumPadDatabase.TB_PITCHA10 + " ='" + pitchDTO.getPicht_a10() + "' and "
                + DrumPadDatabase.TB_PITCHA11 + " ='" + pitchDTO.getPicht_a11() + "' and "
                + DrumPadDatabase.TB_PITCHA12 + " ='" + pitchDTO.getPicht_a12() + "' and "
                + DrumPadDatabase.TB_PITCHB1 + " ='" + pitchDTO.getPicht_b1() + "' and "
                + DrumPadDatabase.TB_PITCHB2 + " ='" + pitchDTO.getPicht_b2() + "' and "
                + DrumPadDatabase.TB_PITCHB3 + " ='" + pitchDTO.getPicht_b3() + "' and "
                + DrumPadDatabase.TB_PITCHB4 + " ='" + pitchDTO.getPicht_b4() + "' and "
                + DrumPadDatabase.TB_PITCHB5 + " ='" + pitchDTO.getPicht_b5() + "' and "
                + DrumPadDatabase.TB_PITCHB6 + " ='" + pitchDTO.getPicht_b6() + "' and "
                + DrumPadDatabase.TB_PITCHB7 + " ='" + pitchDTO.getPicht_b7() + "' and "
                + DrumPadDatabase.TB_PITCHB8 + " ='" + pitchDTO.getPicht_b8() + "' and "
                + DrumPadDatabase.TB_PITCHB9 + " ='" + pitchDTO.getPicht_b9() + "' and "
                + DrumPadDatabase.TB_PITCHB10 + " ='" + pitchDTO.getPicht_b10() + "' and "
                + DrumPadDatabase.TB_PITCHB11 + " ='" + pitchDTO.getPicht_b11() + "' and "
                + DrumPadDatabase.TB_PITCHB12 + " ='" + pitchDTO.getPicht_b12() + "' and "
                + DrumPadDatabase.TB_PITCHNAME + " !='default'";

        Cursor cursor = database.rawQuery(presetName,null);

        cursor.moveToFirst();

        if(cursor.getCount() >= 1)
        {
            name = cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PITCHNAME));
        }
        else
        {
            Log.e("aaaaaaaaaaaaaaa",cursor.getCount()+"");

            name = "default";
        }


        return name;
    }

    public void UpdateDefault(PitchDTO pitchDTO)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DrumPadDatabase.TB_PITCHA1,pitchDTO.getPicht_a1());
        contentValues.put(DrumPadDatabase.TB_PITCHA2,pitchDTO.getPicht_a2());
        contentValues.put(DrumPadDatabase.TB_PITCHA3,pitchDTO.getPicht_a3());
        contentValues.put(DrumPadDatabase.TB_PITCHA4,pitchDTO.getPicht_a4());
        contentValues.put(DrumPadDatabase.TB_PITCHA5,pitchDTO.getPicht_a5());
        contentValues.put(DrumPadDatabase.TB_PITCHA6,pitchDTO.getPicht_a6());
        contentValues.put(DrumPadDatabase.TB_PITCHA7,pitchDTO.getPicht_a7());
        contentValues.put(DrumPadDatabase.TB_PITCHA8,pitchDTO.getPicht_a8());
        contentValues.put(DrumPadDatabase.TB_PITCHA9,pitchDTO.getPicht_a9());
        contentValues.put(DrumPadDatabase.TB_PITCHA10,pitchDTO.getPicht_a10());
        contentValues.put(DrumPadDatabase.TB_PITCHA11,pitchDTO.getPicht_a11());
        contentValues.put(DrumPadDatabase.TB_PITCHA12,pitchDTO.getPicht_a12());


        contentValues.put(DrumPadDatabase.TB_PITCHB1,pitchDTO.getPicht_b1());
        contentValues.put(DrumPadDatabase.TB_PITCHB2,pitchDTO.getPicht_b2());
        contentValues.put(DrumPadDatabase.TB_PITCHB3,pitchDTO.getPicht_b3());
        contentValues.put(DrumPadDatabase.TB_PITCHB4,pitchDTO.getPicht_b4());
        contentValues.put(DrumPadDatabase.TB_PITCHB5,pitchDTO.getPicht_b5());
        contentValues.put(DrumPadDatabase.TB_PITCHB6,pitchDTO.getPicht_b6());
        contentValues.put(DrumPadDatabase.TB_PITCHB7,pitchDTO.getPicht_b7());
        contentValues.put(DrumPadDatabase.TB_PITCHB8,pitchDTO.getPicht_b8());
        contentValues.put(DrumPadDatabase.TB_PITCHB9,pitchDTO.getPicht_b9());
        contentValues.put(DrumPadDatabase.TB_PITCHB10,pitchDTO.getPicht_b10());
        contentValues.put(DrumPadDatabase.TB_PITCHB11,pitchDTO.getPicht_b11());
        contentValues.put(DrumPadDatabase.TB_PITCHB12,pitchDTO.getPicht_b12());

        database.update(DrumPadDatabase.DB_TABLE,contentValues,DrumPadDatabase.TB_PITCHNAME + " ='" + pitchDTO.getPicht_name() + "'",null);
    }


    public void addRecordPresetName(PresetDTO presetDTO)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DrumPadDatabase.TB_RECORDNAME,presetDTO.getRecord_name());
        contentValues.put(DrumPadDatabase.TB_PRESETNAME,presetDTO.getPreset_name());

        database.insert(DrumPadDatabase.DB_TB,null,contentValues);
    }

    public String GetPresetName(String name)
    {
        String preset_name = "";

        String query = "select * from " + DrumPadDatabase.DB_TB + " where " + DrumPadDatabase.TB_RECORDNAME + " ='" + name + "'";

        Cursor cursor = database.rawQuery(query,null);

        cursor.moveToFirst();
        if(cursor.getCount() != 0)
        {
            preset_name = cursor.getString(cursor.getColumnIndex(DrumPadDatabase.TB_PRESETNAME));
        }

        return preset_name;
    }
}
