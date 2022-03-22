package com.qa.common.pageobject;

import static com.codeborne.selenide.Selenide.open;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseApify {

    @FindBy(xpath = "//*[@id=\"dataset\"]")
    protected SelenideElement dataSet;

    @FindBy(xpath = "//*[@id=\"react-target\"]/div[1]/div/div/div[3]/section/div/div/div[2]/section[1]/div/div[1]/dl[4]/dd")
    protected SelenideElement size;

    @FindBy(xpath = "//*[@id=\"react-target\"]/div[1]/div/div/div[2]/div[1]/div")
    protected SelenideElement status;


    public void openApify(String pathToFile) throws FileNotFoundException, IOException, CsvException {
        
            try (CSVReader reader = new CSVReader(new FileReader(pathToFile))) {
            String[] lineInArray;
            while ((lineInArray = reader.readNext()) != null) {                  
                open(lineInArray[0]);
                dataSet.click();
                String rowDataSetSize = size.getText();
                float dataSetSize;
                if (rowDataSetSize.contains("kB")){
                    dataSetSize = Float.parseFloat(rowDataSetSize.replace(" kB", ""))/1024;
                }
                else{
                dataSetSize = Float.parseFloat(rowDataSetSize.replace(" MB", ""));
            }
                System.out.println(lineInArray[0]+"|"+dataSetSize+"|"+ status.getText());
            }
        }
    }

}