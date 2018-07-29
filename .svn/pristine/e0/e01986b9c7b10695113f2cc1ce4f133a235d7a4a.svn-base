package com.hzdongcheng.persistent.sequence;


import android.database.SQLException;

public class SeqOperLogID extends BaseSequence {
    private static String SEQUENCE_NAME = "SeqOperLogID";

    public SeqOperLogID() {
    }

    @Override
    protected void DoGetCurrentMaxValue() throws SQLException {
        ExecuteSelectForUpdate(SEQUENCE_NAME);
    }
}

