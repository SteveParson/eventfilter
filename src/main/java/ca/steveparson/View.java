package ca.steveparson;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class View {
    Model m;

    public View(Model m) {
        this.m = m;
    }

    public void dumpToFile(String filename) {
        try {
            BufferedWriter bw = Files.newBufferedWriter(Paths.get(filename));
            CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.DEFAULT.withHeader(
                    "client-address",
                    "client-guid",
                    "request-time",
                    "service-guid",
                    "retries-request",
                    "packets-requested",
                    "packets-serviced",
                    "max-hole-size"));

            for (Map.Entry<Long, LogEntry> entry : m.getTreeMap().entrySet()) {

                LogEntry value = entry.getValue();
                if (value.packets_serviced == 0)
                    continue;

                Date date = new Date(value.request_time * 1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
                String formattedDate = sdf.format(date);

                csvPrinter.printRecord(
                        value.client_address,
                        value.client_guid,
                        formattedDate,
                        value.service_guid,
                        value.retries_request,
                        value.packets_requested,
                        value.packets_serviced,
                        value.max_hole_size);
            }

            csvPrinter.flush();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void summarize() {
        System.out.println("Total number of records for each client-guid");
        for (Map.Entry<String, Long> entry : m.getGuidMap().entrySet())
            System.out.println(entry.getKey() + ", " + entry.getValue().toString());
    }
}
