package com.github.zlwqa;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.github.zlwqa.TestData.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class FilesTests extends TestBase {

    @Test
    @DisplayName("Отображение необходимого кол-во страниц и их содержимого в скачанном PDF файле")
    void checkNumberOfPagesAndContentInPDFFileTest() throws IOException {
        open(downloadPagePDF);

        File pdf = $(withText("Key Technology Occupations")).download();
        PDF parsedPdf = new PDF(pdf);

        Assertions.assertEquals(1, parsedPdf.numberOfPages);
        Assertions.assertEquals(textBCPNPTechPdf, parsedPdf.text);
    }

    @Test
    @DisplayName("Отображение необходимого кол-ва таблиц и их названий в скачанном XLS файле")
    void checkNumberOfSheetsAndTheirNamesInXLSFileTest() throws IOException {
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

    @Test
    @DisplayName("Отображение названия файла в ZIP архиве")
    void checkNameFileInZipArchiveTest() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("archive.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Assertions.assertEquals("1 file.xls", entry.getName());
                System.out.println(entry.getName()); // Для отладки
            }
        }
    }

    @Test
    @DisplayName("Скачивание текстового файла и проверка его содержимого")
    void checkContainsTXTFile() throws IOException {
        open(downloadPageTXT);
        File download = $$("[class='btn btn-outline-primary btn-xs']").first().download();
        String fileContent = IOUtils.toString(new FileReader(download));
        assertTrue(fileContent.contains("Quisque venenatis justo sit amet tortor condimentum"));
    }

    @Test
    @DisplayName("Загрузка файла")
    void checkFileNameAfterUpload() {
        open(uploadLink);
        $("#uploadFile").uploadFromClasspath("sample3.txt");
        $("#uploadedFilePath").shouldHave(text("sample3.txt"));
    }
}
