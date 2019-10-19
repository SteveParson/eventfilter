package ca.steveparson;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Controller {

    Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void addRowsFromFile(String filename) throws Exception {
        String ext = Utility.getFileExtension(filename);

        switch (ext) {
            case "xml":
                if (!Utility.doesFileExist(filename))
                    throw new Exception();

                processXML(filename);
                break;

            case "json":
                if (!Utility.doesFileExist(filename))
                    throw new Exception();

                processJSON(filename);
                break;

            case "csv":
                if (!Utility.doesFileExist(filename))
                    throw new Exception();

                processCSV(filename);
                break;

            default:
                System.out.printf("Extension %s not supported.\n", ext);
                throw new Exception();
        }
    }

    public void processXML(String filename) {
        try {
            File f = new File(filename);
            SAXReader r = new SAXReader();
            Document d = r.read(f);
            Element e = d.getRootElement();
            List<Element> l = e.elements("report");

            for (Element subelement : l) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss z");
                Date date = df.parse(subelement.elementText("request-time"));
                long request_time = date.getTime() / 1000;

                LogEntry logEntry = new LogEntry(
                        Long.parseLong(subelement.elementText("max-hole-size")),
                        Long.parseLong(subelement.elementText("packets-serviced")),
                        Long.parseLong(subelement.elementText("packets-requested")),
                        request_time,
                        Long.parseLong(subelement.elementText("retries-request")),
                        subelement.elementText("client-guid"),
                        subelement.elementText("client-address"),
                        subelement.elementText("service-guid"));

                model.addToModel(logEntry);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void processJSON(String filename) {
        try {
            JSONParser jsonParser = new JSONParser();
            Reader r = new FileReader(filename);
            JSONArray jsonArray = (JSONArray) jsonParser.parse(r);

            for (Object arrayObject : jsonArray) {
                JSONObject jsonObject = (JSONObject) arrayObject;

                LogEntry l = new LogEntry(
                        (Long) jsonObject.get("max-hole-size"),
                        (Long) jsonObject.get("packets-serviced"),
                        (Long) jsonObject.get("packets-requested"),
                        (Long) jsonObject.get("request-time") / 1000,
                        (Long) jsonObject.get("retries-request"),
                        (String) jsonObject.get("client-guid"),
                        (String) jsonObject.get("client-address"),
                        (String) jsonObject.get("service-guid"));

                model.addToModel(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void processCSV(String filename) {
        try {
            Reader r = new FileReader(filename);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(r);

            for (CSVRecord record : records) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss z");
                Date date = df.parse(record.get("request-time"));
                long request_time = date.getTime() / 1000;

                LogEntry l = new LogEntry(
                        Long.parseLong(record.get("max-hole-size")),
                        Long.parseLong(record.get("packets-serviced")),
                        Long.parseLong(record.get("packets-requested")),
                        request_time,
                        Long.parseLong(record.get("retries-request")),
                        record.get("client-guid"),
                        record.get("client-address"),
                        record.get("service-guid"));

                model.addToModel(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}