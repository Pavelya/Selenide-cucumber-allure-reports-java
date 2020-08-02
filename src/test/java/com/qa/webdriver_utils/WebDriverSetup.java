package com.qa.webdriver_utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.qa.common.MainConfig;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverSetup {
    protected static MainConfig config = ConfigFactory.create(MainConfig.class);
    private static Logger logger = LoggerFactory.getLogger(WebDriverSetup.class);
    static String chromeDriverFileName;
    static String chromeDriverVersion = config.chromeDriverVersion();
    static String mainDownloadLink = config.mainDownloadLink();
    static String osBasedDownloadLink = mainDownloadLink + chromeDriverVersion + "/" + getChromeDriverFileName();
    static String subFolderBasedOs = getOS().toLowerCase().replace(" ", "_");
    static String resourcesFolder = config.resourcesFolder();
    static String downloadFolder = resourcesFolder + subFolderBasedOs;
    static String toFile = downloadFolder + "/" + getChromeDriverFileName();
    static String chromeDriverPath;

    public static String getChromeDriverFileName() {
        if (isWindows()) {
            chromeDriverFileName = "chromedriver_win32.zip";
        } else if (isMac()) {
            chromeDriverFileName = "chromedriver_mac64.zip";
        } else if (isLinux()) {
            chromeDriverFileName = "chromedriver_linux64.zip";
        }
        return chromeDriverFileName;
    }

    public static void downloadChromeDriver() {
        logger.info("Download chrome driver, if not exists");
        try {
            logger.info("Downloading chrome driver from: " + osBasedDownloadLink);
            logger.info("Saving chrome driver to: " + toFile);
            FileUtils.copyURLToFile(new URL(osBasedDownloadLink), new File(toFile).getAbsoluteFile(), 10000, 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unzip() {
        byte[] buffer = new byte[1024];

        try {

            // create output directory is not exists
            File folder = new File(downloadFolder).getAbsoluteFile();
            if (!folder.exists()) {
                folder.mkdir();
            }

            // get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(toFile));
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            while (ze != null) {

                String fileName = ze.getName();

                File newFile = new File(downloadFolder + File.separator + fileName);

                logger.info("file unzip : " + newFile.getAbsoluteFile());

                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                new File(newFile.getParent()).mkdirs();

                FileOutputStream fos = new FileOutputStream(newFile);

                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            logger.info("Unzip completed");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String makeWebDriverExecutable() {
        String pathToChromeDriverPath = downloadFolder + "/chromedriver";
        logger.info("Run chmod 777 command for : " + pathToChromeDriverPath);
        StringBuffer output = new StringBuffer();
        Process p;
        try {
            p = Runtime.getRuntime().exec("chmod 777 " + pathToChromeDriverPath);
            p.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public static void initCustomChrome() {
        downloadChromeDriver();
        unzip();
        makeWebDriverExecutable();

    }

    public static String getOS() {
        return System.getProperty("os.name").toLowerCase();
    }

    private static boolean isWindows() {
        return (getOS().indexOf("win") >= 0);
    }

    private static boolean isMac() {
        return (getOS().indexOf("mac") >= 0);
    }

    private static boolean isLinux() {
        String OS = getOS();
        return (getOS().indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }

    public static String getUnzipedFileName(){
        return isWindows()? "chromedriver.exe": "chromedriver";
    }
}