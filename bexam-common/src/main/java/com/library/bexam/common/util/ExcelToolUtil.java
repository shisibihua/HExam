package com.library.bexam.common.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * excel文件工具类
 * @author caoqian
 * @date 20190106
 */
public class ExcelToolUtil {

    private Logger logger= LoggerFactory.getLogger(ExcelToolUtil.class);
    private static ExcelToolUtil INSTANCE=null;
    public static ExcelToolUtil getInstance(){
        if(null==INSTANCE){
            synchronized (ExcelToolUtil.class){
                if(null==INSTANCE){
                    INSTANCE=new ExcelToolUtil();
                }
            }
        }
        return INSTANCE;
    }
    // 导出锁定，防止导出过多而内存溢出
    private static final Object EXPORT_LOCK = new Object();
    // 保存文件扩展名
    public static final String EXCEL_EXT_NAME = ".xls";
    // 允许写入的最大行数
    public static final int MAX_ROW = 65536;
    // 允许写入的最大sheet数
    public static final int MAX_SHEET = 255;
    // 默认最大行数：10000行
    public int default_row = 10000;
    //操作成功
    public final static String RESULT_SUCCESS = "success";
    //文件读取错误
    public final static String RESULT_FILEERROR = "fileError";
    //文件删除错误
    public final static String RESULT_FILEDELETEERROR = "fileDeleteError";
    //导入失败
    public final static String RESULT_IMPORTERROR = "importError";
    /**
     * 下载文件
     * @param path
     * @param fileName
     * @param res
     * @return
     */
    public boolean downLoadFile(String path,String fileName,HttpServletResponse res){
        res.setHeader("content-type", "application/octet-stream");
        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        boolean download=true;
        try {
            os = res.getOutputStream();
            String filePath=path+fileName;
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            download=false;
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    ;
                }
            }
        }
        return download;
    }
    /**
     * 导出excel文件流，分文件导出
     *
     * @param title    标题
     * @param headers  列名
     * @param list     行数据，必须按照列名的顺序排列
     * @param path     文件输出路径
     * @param fileName 文件名（不含扩展名）
     * @throws java.io.IOException
     */
    public String[] exportTableByFile(String title, String[] headers, List<String[]> list, String path, String fileName)
            throws IOException {
        // 多文件导出锁定
        synchronized (EXPORT_LOCK) {
            // 文件路径
            String[] filepaths = null;

            // 如果大于默认行数。则分文件进行
            if (list.size() > default_row) {
                // 进行分文件筛选
                int page = list.size() / default_row;
                if (list.size() % default_row != 0) {// 如果有余数则加1页
                    page += 1;
                }

                // 创建文件数组
                filepaths = new String[page];

                for (int i = 0; i < page; i++) {
                    List<String[]> templist = null;
                    if (i == page - 1) {
                        templist = list.subList(i * default_row,
                                list.size() - 1);
                    } else {
                        templist = list.subList(i * default_row, (i + 1)
                                * default_row - 1);
                    }
                    // 导出文件
                    String filepath = exportExcelFile(title, headers, templist,
                            path + fileName + "_" + (i + 1) + "-" + page
                                    + EXCEL_EXT_NAME);
                    // 将文件名写入数组
                    filepaths[i] = filepath;
                }
            } else {
                // 直接导出
                String filepath = exportExcelFile(title, headers, list, path
                        + fileName + EXCEL_EXT_NAME);
                // 将文件名写入数组
                filepaths = new String[]{filepath};
            }
            // 返回文件数组
            return filepaths;
        }
    }

    private String exportExcelFile(String title, String[] headers,
                                   List<String[]> list, String filepath) throws IOException {

        // 创建工作对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 创建sheet
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        // sheet.setDefaultColumnWidth((short) 15);
        /************************************** 一、产生表头****************************/
        HSSFRow tableTitle = sheet.createRow(0);
        HSSFCell cellTitle = tableTitle.createCell(0);
        // 生成一个样式
        HSSFCellStyle styleTitle = workbook.createCellStyle();
        // 设置这些样式
        styleTitle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleTitle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        styleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 设置表头样式
        cellTitle.setCellStyle(styleTitle);

        //创建字体样式
        HSSFFont titleFont = workbook.createFont();
        HSSFFont cellFont = workbook.createFont();
        HSSFFont dataFont = workbook.createFont();
        titleFont.setFontHeightInPoints((short) 18);//设置字体大小
        cellFont.setFontHeightInPoints((short) 12);
        dataFont.setFontHeightInPoints((short) 10);

        //设置表头字体
        styleTitle.setFont(titleFont);

        // 设置表头内容
        cellTitle.setCellValue(title);
        // 合并单元格，合并标题的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.length - 1));

        /*************************************** 二、产生表格列标题*****************************/
        HSSFRow lieRow = sheet.createRow(1);// 在第二行创建

        // 设置列名称
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = lieRow.createCell(i);
            //创建格式
            HSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFont(cellFont);
            cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//左右居中
            //设置格式
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headers[i]);

        }

        /**************************************** 三、遍历数据集，写入数据*************************/
        // 设置单元格样式，
        HSSFCellStyle dataStyle = workbook.createCellStyle();

        dataStyle.setFont(dataFont);
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        dataStyle.setDataFormat(dataFormat.getFormat("@"));// 设置单元格格式为文本

        for (int i = 0; i < list.size(); i++) {
            HSSFRow dataRow = sheet.createRow(i + 2);// 每行的起始数+2
            // HSSFCell datas[] = new HSSFCell[headers.length];
            // 取出一个数据
            String[] dataArr = list.get(i);
            for (int j = 0; j < headers.length; j++) {
                HSSFCell dataCell = dataRow.createCell(j);
                dataCell.setCellValue(dataArr[j]);
                dataCell.setCellStyle(dataStyle);
            }
            //if (i == list.size()) { System.out.println("正在写入数据：" + i); }
        }

        /**
         * 调整列宽为自动列宽
         * 用for循环添加 有多少列就将n改为多少
         */
        try{
            int n = 13;
            for (int i = 0; i < n; i++) {
                sheet.autoSizeColumn((short) i);
            }
        }catch (Exception e){
            logger.error("自动宽度调整异常",e);
        }
        File testFile=null;
        try {
            /************************************* 四、写入数据流***********************/
            testFile = new File(filepath);
            if (!testFile.exists()) {
                testFile.createNewFile();
                logger.error(">>>>>>>>>>>>>>>>>>>>>>>错误模板excel存在》》》" + filepath);
            }
            OutputStream stream = new FileOutputStream(testFile);
            workbook.write(stream);
            stream.close();
            workbook.close();
        }catch (Exception e){
            logger.error("生成错误excel异常",e);
        }
        return testFile.getAbsolutePath();
    }
}
