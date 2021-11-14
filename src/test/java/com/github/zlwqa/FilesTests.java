package com.github.zlwqa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.github.zlwqa.TestData.textBCPNPTechPdf;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FilesTests extends TestBase {

    @Test
    @DisplayName("Скачивание PDF файла")
    void pdfFileDownloadTest() throws IOException {
        open("https://www.welcomebc.ca/Immigrate-to-B-C/B-C-Provincial-Nominee-Program/Documents#SI");
        File pdf = $(withText("Key Technology Occupations")).download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(1, parsedPdf.numberOfPages);
        Assertions.assertEquals(textBCPNPTechPdf, parsedPdf.text);
    }

    @Test
    @DisplayName("Скачивание XLS файла")
    void xlsFileDownloadTest() throws IOException {
        open("https://ekfgroup.com/price");
        File xls = $(withText("Скачать прайс-лист .XLS")).download();

        XLS parsedXls = new XLS(xls);
        boolean checkPassed = parsedXls.excel
                .getSheetAt(0)
                .getRow(0)
                .getCell(0)
                .getStringCellValue()
                .contains("test");
    assertTrue(checkPassed);
    }
}
