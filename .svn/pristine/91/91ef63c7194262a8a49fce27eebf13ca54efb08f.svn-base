package com.hzdongcheng.persistent.sequence;

import android.database.SQLException;

import java.util.HashMap;
import java.util.Map;

public class SequenceGenerator {
    // / <summary>
    // / 流水号
    // / </summary>
    public final static String SEQ_OPERLOGID = "SeqOperLogID"; // 操作员日志序号
    public final static String SEQ_REPORTWATERID = "SeqReportWaterID"; // 报告流水编号
    public final static String SEQ_WATERID = "SeqWaterID"; // 流水编号
    public final static String SEQ_TRADEWATERID = "SeqTradeWaterID"; // 交易流水编号

    private static SequenceGenerator instance = null;
    // 程序运行时创建一个静态的只读的辅助对象
    private final static Object syncRoot = new Object();

    private SequenceGenerator() {
    }

    public synchronized static SequenceGenerator GetIntance() {
        if (instance == null)
            instance = new SequenceGenerator();
        return instance;
    }

    private Map<String, BaseSequence> keyList = new HashMap<String, BaseSequence>(
            10);

    public long GetNextKey(String className) throws SQLException {
        BaseSequence sequence = null;
        sequence = keyList.get(className);

        if (sequence == null) {
            synchronized (keyList) {
                // Double check to avoid a race condition
                sequence = keyList.get(className);
                if (sequence == null) {

                    try {
                        sequence = (BaseSequence) Class.forName("com.hzdongcheng.persistent.sequence." + className)
                                .newInstance();
                    } catch (ClassNotFoundException e) {
                        return -1;
                    } catch (InstantiationException | IllegalAccessException ignored) {
                    }
                    if (sequence != null) {
                        sequence.InitValue();
                    }

                    keyList.put(className, sequence);
                }
            }
        }

        return sequence != null ? sequence.GetNextKey() : 0;
    }
}
