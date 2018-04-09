package com.example.tucker.calcexam.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by John on 4/2/2018.
 */
@Database(name = CalcDataBase.NAME, version = CalcDataBase.VERSION)
public class CalcDataBase {
    public static final String NAME = "CalcExamDataBase";
    public static final int VERSION = 1;
}
