package com.hzdongcheng.persistent.sequence;


import android.database.SQLException;

public class SeqReportWaterID extends BaseSequence {
	private static String SEQUENCE_NAME = "SeqReportWaterID";

	public SeqReportWaterID() {
	}

	@Override
	protected void DoGetCurrentMaxValue() throws SQLException {
		ExecuteSelectForUpdate(SEQUENCE_NAME);
	}
}
