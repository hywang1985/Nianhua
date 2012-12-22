package com.hywang.timeline.utils.time;

import java.util.List;
import java.util.Map;

/**
 * DOC hywang class global comment. Detailled comment
 */
public interface ITimeMeasureLogger {

    public void logToFile(Map<String, List<Map<Integer, Object>>> logValue, String logFilePath);
}
