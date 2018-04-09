package com.example.tucker.calcexam.database.Models;

import android.util.Base64;

import com.example.tucker.calcexam.database.CalcDataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by John on 4/2/2018.
 */

@Table(database = CalcDataBase.class)
public class Historial extends BaseModel{

    @Column
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String operationHistory;
}
