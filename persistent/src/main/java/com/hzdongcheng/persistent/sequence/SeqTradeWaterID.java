package com.hzdongcheng.persistent.sequence;


import android.database.SQLException;

public class SeqTradeWaterID extends BaseSequence {
	private static String SEQUENCE_NAME = "SeqTradeWaterID";

	public SeqTradeWaterID() {
	}

	@Override
	protected void DoGetCurrentMaxValue() throws SQLException {
		ExecuteSelectForUpdate(SEQUENCE_NAME);
	}
}
