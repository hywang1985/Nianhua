package com.hywang.timeline.utils.time;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import com.hywang.timeline.utils.time.ITimeMeasureLogger;
import com.hywang.timeline.utils.time.TimeMeasure.ELogFileColumnConstant;

/**
 * DOC hywang class global comment. Detailled comment
 */
@Component
public class TimeMeasureLoggerImpl implements ITimeMeasureLogger {

    public void logToFile(Map<String, List<Map<Integer, Object>>> logValue, String logFilePath) {
        WritableWorkbook wirteWorkbook = null;
        WritableSheet sheetToWrite = null;
        Workbook readWorkbook = null;
        try {
            File outputFile = new File(logFilePath);
            if (!outputFile.exists()) {
                outputFile.createNewFile();
                wirteWorkbook = Workbook.createWorkbook(outputFile);
            } else {
                readWorkbook = Workbook.getWorkbook(outputFile);
                wirteWorkbook = Workbook.createWorkbook(outputFile, readWorkbook);
            }
            int sheetsNum = wirteWorkbook.getSheets().length;
            if (sheetsNum > 0) {
                /* need to loop the exsits sheets,select the sheet to write recode */
                for (WritableSheet current : wirteWorkbook.getSheets()) {
                    if (current.getName().equals("Commandlinelog")) { //$NON-NLS-N$
                        sheetToWrite = current;
                        break;
                    }
                }
                /* if no sheet,need to create sheet */
            } else {
                sheetToWrite = initWritableWorkbook(wirteWorkbook);
            }

            /* loop map to get all recodes */

            for (String timerKey : logValue.keySet()) {
                List<Map<Integer, Object>> values = logValue.get(timerKey);
                for (Map<Integer, Object> row : values) {
                    int rowSize = sheetToWrite.getRows();
                    for (int columnIndex = 0; columnIndex <= 3; columnIndex++) {
                        Object obj = row.get(columnIndex);
                        WritableCell cellToAdd = null;
                        if (obj instanceof String) {
                            cellToAdd = new Label(columnIndex, rowSize, (String) obj);
                        }
                        if (obj instanceof Long) {
                            cellToAdd = new jxl.write.Number(columnIndex, rowSize, (Long) obj);

                        }
                        if (obj instanceof Date) {
                            cellToAdd = new DateTime(columnIndex, rowSize, (Date) obj, DateTime.GMT);
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //$NON-NLS-N$
                            String time = df.format((Date) obj);
                            cellToAdd = new Label(columnIndex, rowSize, time);
                        }

                        if (cellToAdd != null) {
                            sheetToWrite.addCell(cellToAdd);
                        }
                    }
                }
            }

            wirteWorkbook.write();
            wirteWorkbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
        }
    }

    private static WritableSheet initWritableWorkbook(WritableWorkbook wirteWorkbook) throws WriteException,
            RowsExceededException {
        WritableSheet sheetToWrite;
        sheetToWrite = wirteWorkbook.createSheet("Commandlinelog", 0);
        // set column width
        sheetToWrite.setColumnView(0, 40);
        sheetToWrite.setColumnView(1, 25);
        sheetToWrite.setColumnView(2, 25);
        sheetToWrite.setColumnView(3, 25);
        WritableFont wf = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE,
                jxl.format.Colour.BLACK); // define format,font,underline and color
        WritableCellFormat wcf = new WritableCellFormat(wf); // define cell
        wcf.setBackground(jxl.format.Colour.BLUE_GREY); // set background of cell
        wcf.setAlignment(jxl.format.Alignment.CENTRE); // set Alignment

        WritableFont wfTitle = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE,
                jxl.format.Colour.RED);
        WritableCellFormat wcfTitle = new WritableCellFormat(wfTitle);
        wcfTitle.setBackground(jxl.format.Colour.LIGHT_BLUE);
        wcfTitle.setAlignment(jxl.format.Alignment.CENTRE);
        initTitleAndColumns(sheetToWrite, wcf, wcfTitle);
        return sheetToWrite;
    }

    private static void initTitleAndColumns(WritableSheet sheetToWrite, WritableCellFormat wcf, WritableCellFormat wcfTitle)
            throws WriteException, RowsExceededException {
        Label title = new Label(ELogFileColumnConstant.TITLE.locationY, ELogFileColumnConstant.TITLE.locationX,
                ELogFileColumnConstant.TITLE.label, wcfTitle);
        Label stepColumn = new Label(ELogFileColumnConstant.STEP.locationY, ELogFileColumnConstant.STEP.locationX,
                ELogFileColumnConstant.STEP.label, wcf);
        Label timeUsed = new Label(ELogFileColumnConstant.TIME_USED.locationY, ELogFileColumnConstant.TIME_USED.locationX,
                ELogFileColumnConstant.TIME_USED.label, wcf);
        Label memoUsed = new Label(ELogFileColumnConstant.MEMO_USED.locationY, ELogFileColumnConstant.MEMO_USED.locationX,
                ELogFileColumnConstant.MEMO_USED.label, wcf);
        Label timeTrace = new Label(ELogFileColumnConstant.TIMETRACE.locationY, ELogFileColumnConstant.TIMETRACE.locationX,
                ELogFileColumnConstant.TIMETRACE.label, wcf);
        sheetToWrite.mergeCells(0, 0, 3, 0);
        sheetToWrite.addCell(title);
        sheetToWrite.addCell(stepColumn);
        sheetToWrite.addCell(timeUsed);
        sheetToWrite.addCell(memoUsed);
        sheetToWrite.addCell(timeTrace);
    }

}
