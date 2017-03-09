package org.launchcode.techjobs.console;

        import org.apache.commons.csv.CSVFormat;
        import org.apache.commons.csv.CSVParser;
        import org.apache.commons.csv.CSVRecord;

        import java.io.*;
        import java.nio.charset.Charset;
        import java.util.*;



/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;
//tempcheck    private static Boolean isDataLoaded2 = false;

    private static ArrayList<HashMap<String, String>> allJobs;
    //tempcheck    private static ArrayList<HashMap<String, String>> allJobs2;
    private static String[] headers;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        ArrayList<String> values = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        boolean found;
        found = true;
        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        if(!column.equals("all")){
            for (HashMap<String, String> row : allJobs) {

                String aValue = row.get(column);
                String aValueUpper = aValue.toUpperCase();
                String valueUpper = value.toUpperCase();

       //         if (aValue.contains(value)) {
                if (aValueUpper.contains(valueUpper)) {
                    jobs.add(row);
                }
            }
        }else if (column.equals("all")) {
            for (HashMap<String, String> row : allJobs) {
                for (String headerLabel : headers) {

                    //             System.out.println("inside findByColumnsAndValue:" + headerLabel);

                    String aValue = row.get(headerLabel);
                    String aValueUpper = aValue.toUpperCase();
                    String valueUpper = value.toUpperCase();

             //       if (aValue.contains(value)) {
                    if (aValueUpper.contains(valueUpper)) {
                        jobs.add(row);
                        if (found) break; //once this job has been added no need to keep checking other fields
                    }
                }
            }
        }else{
            System.out.println("findByColumnAndValue : Problem with column requested");
        }

        return jobs;
    }


//tempcheck    public static ArrayList<HashMap<String, String>> findByAllColumnsAndValue(String column, String value) {

    //tempcheck        // load data, if not already loaded
//tempcheck        loadData2();

//tempcheck        String line;

//tempcheck        if (column.equals("all")){
//tempcheck            System.out.println("findByAllColumnsAndValue:  user selected all");
//tempcheck        }

    //       for (String headerLabel : headers) {
//
//            System.out.println("inside findByAllColumnsAndValue:" + headerLabel);
//        }

//tempcheck        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

//tempcheck        for (HashMap<String, String> row : allJobs2) {

//tempcheck            for (String headerLabel : headers) {

//tempcheck   //             System.out.println("inside findByAllColumnsAndValue:" + headerLabel);

//tempcheck                String aValue = row.get(headerLabel);

//tempcheck                if (aValue.contains(value)) {
//tempcheck                    jobs.add(row);
//tempcheck                }

//tempcheck            }

//tempcheck        }

//tempcheck        return jobs;
//tempcheck    }



    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
            List<CSVRecord> records = parser.getRecords();
            Integer numberOfColumns = records.get(0).size();
            //tempcheck           String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);
            headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                    //                System.out.println("inside loadData:" + headerLabel);
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
        return;
    }


    //tempcheck   private static void loadData2() {

    //tempcheck       // Only load data once
    //tempcheck       if (isDataLoaded2) {
//tempcheck            return;
//tempcheck        }

//tempcheck        try {

    //tempcheck            // Open the CSV file and set up pull out column header info and records
//tempcheck            Reader in = new FileReader(DATA_FILE);
//tempcheck            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);
//tempcheck            List<CSVRecord> records = parser.getRecords();
//tempcheck            Integer numberOfColumns = records.get(0).size();
//tempcheck            headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

//tempcheck            allJobs2 = new ArrayList<>();

//tempcheck            // Put the records into a more friendly format
//tempcheck            for (CSVRecord record : records) {
//tempcheck                HashMap<String, String> newJob = new HashMap<>();

//tempcheck                for (String headerLabel : headers) {
//tempcheck                    newJob.put(headerLabel, record.get(headerLabel));

//tempcheck                }

//tempcheck                allJobs2.add(newJob);
//tempcheck            }

//tempcheck            // flag the data as loaded, so we don't do it twice
//tempcheck            isDataLoaded2 = true;

//tempcheck        } catch (IOException e) {
//tempcheck            System.out.println("Failed to load job data2");
//tempcheck            e.printStackTrace();
//tempcheck        }
//tempcheck        return;
//tempcheck    }
}
