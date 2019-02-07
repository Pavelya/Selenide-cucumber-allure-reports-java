package com.qa.common;

import java.io.File;
import java.io.IOException;

import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AllureReportConfig{
    
    protected static Logger logger = LoggerFactory.getLogger(AllureReportConfig.class);
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);

    // folders params
    static String resultsFolder = config.allure_results_folder();
    static String screenshotsFolder = config.allure_screenshots_folder();

    public static void prepareAllureResultsFolder() {

        // step 1. delete allure results folder
        File allureResultsFolder = new File(resultsFolder);
        try {
            deleteResultsFolder(allureResultsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 2. create allure results folder
        try {
            createReportFolder(allureResultsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 3. delete allure screenshots folder
        File allureSnapshotsFolder = new File(screenshotsFolder);
        try {
            deleteResultsFolder(allureSnapshotsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // step 4. create allure screenshots folder
        try {
            createReportFolder(allureSnapshotsFolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteResultsFolder(File allureResultsFolder) throws IOException {

        if (allureResultsFolder.isDirectory()) {

            // directory is empty, then delete it
            if (allureResultsFolder.list().length == 0) {

                allureResultsFolder.delete();
                logger.info("Directory is deleted : " + allureResultsFolder.getAbsolutePath());

            } else {

                // list all the directory contents
                String files[] = allureResultsFolder.list();

                for (String temp : files) {
                    // construct the file structure
                    File fileDelete = new File(allureResultsFolder, temp);

                    // recursive delete
                    deleteResultsFolder(fileDelete);
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

    public static void createReportFolder(File allureResultsFolder) throws IOException {

        if (!allureResultsFolder.exists()) {
            if (allureResultsFolder.mkdir()) {
                logger.info("Directory is created: " + allureResultsFolder);
            } else {
                logger.error("Failed to create directory: " + allureResultsFolder);
            }
        }
    }
}
