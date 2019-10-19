package ca.steveparson;

import java.util.HashMap;
import java.util.TreeMap;

public class Model {

    private TreeMap<Long, LogEntry> logEntryTreeMap = new TreeMap<>();
    private HashMap<String, Long> guidCountHashMap = new HashMap<>();

    HashMap<String, Long> getGuidMap() { return guidCountHashMap; }
    TreeMap<Long, LogEntry> getTreeMap() { return logEntryTreeMap; }

    void addToModel(LogEntry logEntry) {
        logEntryTreeMap.put(logEntry.request_time, logEntry);

        if (!guidCountHashMap.containsKey(logEntry.client_guid))
            guidCountHashMap.put(logEntry.client_guid, 0L);

        guidCountHashMap.put(logEntry.client_guid, guidCountHashMap.get(logEntry.client_guid) + 1);
    }
}
