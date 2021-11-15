package com.github.zlwqa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;


import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.github.zlwqa.TestData.*;


public class FilesTests extends TestBase {

    @Test
    @DisplayName("Скачивание PDF файла")
    void pdfFileDownloadTest() throws IOException {
        open(downloadPagePDF);
        File pdf = $(withText("Key Technology Occupations")).download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals(1, parsedPdf.numberOfPages);
        Assertions.assertEquals(textBCPNPTechPdf, parsedPdf.text);
    }

    @Test
    @DisplayName("Скачивание XLS файла")
    void xlsFileDownloadTest() throws IOException {
        open(downloadPageXLS);
        File xls = $(byText("Скачать прайс-лист .XLS")).download();

        XLS parsedXls = new XLS(xls);

        Assertions.assertEquals(6, parsedXls.excel.getNumberOfSheets());
        Assertions.assertEquals("Продукция EKF", parsedXls.excel.getSheetAt(0).getSheetName());
        Assertions.assertEquals("Новинки", parsedXls.excel.getSheetAt(1).getSheetName());
        Assertions.assertEquals("Промоцена", parsedXls.excel.getSheetAt(2).getSheetName());
        Assertions.assertEquals("Рекламная продукция", parsedXls.excel.getSheetAt(3).getSheetName());
        Assertions.assertEquals("Тарифные зоны", parsedXls.excel.getSheetAt(4).getSheetName());
        Assertions.assertEquals("Подсказки", parsedXls.excel.getSheetAt(5).getSheetName());
    }
}
