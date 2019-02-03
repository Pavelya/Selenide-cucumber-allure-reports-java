package com.qa.common;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureReportConfigurationSetup{
    
    protected static Logger logger = LoggerFactory.getLogger(AllureReportConfigurationSetup.class);

    // folders params
    static String allureReportResultsFolder = "allure-results";
    static String allureReportScreenshotsFolder = "allure-screenshots";

    public static void prepareAllureResultsFolder() {

        // step 1. delete allure results folder
        File allureResultsFolder = new File(allureReportResultsFolder);
        try {
            deleteAllureResultsFolder(allureResultsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 2. create allure results folder
        try {
            createAllureReportFolder(allureResultsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 3. delete allure screenshots folder
        File allureSnapshotsFolder = new File(allureReportScreenshotsFolder);
        try {
            deleteAllureResultsFolder(allureSnapshotsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 4. create allure screenshots folder
        try {
            createAllureReportFolder(allureSnapshotsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllureResultsFolder(File allureResultsFolder) throws IOException {

        if (allureResultsFolder.isDirectory()) {

            // directory is empty, then delete it
            if (allureResultsFolder.list().length == 0) {

                allureResultsFolder.delete();
                logger.info("Folder is deleted : " + allureResultsFolder.getAbsolutePath());

            } else {

                // list all the directory contents
                String files[] = allureResultsFolder.list();

                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(allureResultsFolder, temp);

                    // recursive delete
                    deleteAllureResultsFolder(fileDelete);
                }

                // check the directory again, if empty then delete it
                if (allureResultsFolder.list().length == 0) {
                    allureResultsFolder.delete();
                    logger.info("Directory is deleted : " + allureResultsFolder.getAbsolutePath());
                }
            }

        } else {
            // if file, then delete it
            allureResultsFolder.delete();
            logger.info("File is deleted : " + allureResultsFolder.getAbsolutePath());
        }
    }

    public static void createAllureReportFolder(File allureResultsFolder) throws IOException {

        if (!allureResultsFolder.exists()) {
            if (allureResultsFolder.mkdir()) {
                logger.info("Directory is created: " + allureResultsFolder);
            } else {
                logger.error("Failed to create directory: " + allureResultsFolder);
            }
        }
    }
}
