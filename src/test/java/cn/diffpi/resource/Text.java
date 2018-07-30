package cn.diffpi.resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author super
 * @date 2018/6/26 17:13
 */
public class Text {
    public static void main(String[] args) {
//        Text obj = new Text();
//        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
//        File file = new File("G:\\123.xls");
//        List excelList = obj.readExcel(file);
//        System.out.println("list中的数据打印出来");
//        for (int i = 0; i < excelList.size(); i++) {
//            List list = (List) excelList.get(i);
//            for (int j = 0; j < list.size(); j++) {
//                System.out.print(list.get(j));
//            }
//            System.out.println();
//        }
//        changeName("C:\\Users\\superhe\\Desktop\\新建文件夹");
        File file = new File("D:\\下载\\BaiduNetdiskDownload\\webSocket-8视频\\视频\\视频\\05 用户列表和退出.avi");
        File newFile = new File("D:\\下载\\BaiduNetdiskDownload\\webSocket-8视频\\视频\\回收站\\2018-07-30 14:27:3505 用户列表和退出.avi");
        if (newFile.exists()) {
            System.out.println("视频已经存在");
        } else {
            if (file.renameTo(newFile)) {
                System.out.println("移动成功");
            } else {
                System.out.println("移动失败");
            }
        }
    }
    // 去读Excel的方法readExcel，该方法的入口参数为一个File对象
    public List readExcel(File file) {
        try {
            // 创建输入流，读取Excel
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList=new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                for (int i = 0; i < sheet.getRows(); i++) {
                    List innerList=new ArrayList();
                    // sheet.getColumns()返回该页的总列数
                    for (int j = 0; j < sheet.getColumns(); j++) {
                        String cellinfo = sheet.getCell(j, i).getContents();
                        if(cellinfo.isEmpty()){
                            continue;
                        }
                        innerList.add(cellinfo);
                        System.out.print(cellinfo);
                    }
                    outerList.add(i, innerList);
                    System.out.println();
                }
                return outerList;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeName(String path) {
        File file = new File(path);
        File[] files = file.listFiles();
        for (File file1 : files) {
            File temp = new File(file1.getAbsolutePath() + ".torrent");
            file1.renameTo(temp);
        }
    }

}
