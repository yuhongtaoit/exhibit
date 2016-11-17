package edu.zstu.exhibit.util;

import javax.print.*;
import javax.print.attribute.*;
import java.io.InputStream;

/**
 * Created by aning on 16/6/2.
 */
public class Printer {

    public static boolean printQrcode(InputStream is) {
        boolean flag = false;
        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        //设置打印格式，因为未确定类型，所以选择autosense
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        //查找所有的可用的打印服务
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        //定位默认的打印服务
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        //显示打印对话框
        PrintService service = ServiceUI.printDialog(null, 200, 200, printService,
                defaultService, flavor, pras);
        if(service != null){
            try {
                DocPrintJob job = service.createPrintJob(); //创建打印作业
               // FileInputStream fis = new FileInputStream(); //构造待打印的文件流
                DocAttributeSet das = new HashDocAttributeSet();
                Doc doc = new SimpleDoc(is, flavor, das);
                job.print(doc, pras);
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }

    public static boolean printQrcode(InputStream[] is) {
        boolean flag = false;
        //构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        //设置打印格式，因为未确定类型，所以选择autosense
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        //查找所有的可用的打印服务
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        //定位默认的打印服务
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        //显示打印对话框
        PrintService service = ServiceUI.printDialog(null, 200, 200, printService,
                defaultService, flavor, pras);
        if(service != null){
            try {
                DocPrintJob job = service.createPrintJob(); //创建打印作业
                DocAttributeSet das = new HashDocAttributeSet();
                for(int i = 0;i<is.length;i++) {
                    Doc doc = new SimpleDoc(is, flavor, das);
                    job.print(doc, pras);
                }
                flag = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return flag;
    }
}
