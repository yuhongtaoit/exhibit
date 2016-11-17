package edu.zstu.exhibit.handlers;

import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.domain.Qrcode;
import edu.zstu.exhibit.service.ProductService;
import edu.zstu.exhibit.service.QrcodeService;
import edu.zstu.exhibit.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by aning on 16/6/4.
 */
@RequestMapping("qrcode")
@Controller
public class QrcodeHandler extends AbstractManageController {

    QrcodeService qrcodeService = new QrcodeService();

    @RequestMapping("/list")
    public ModelAndView list() throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        return modelAndView;
    }

    @RequestMapping("/tolist")
    public ModelAndView tolist(Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        List<Qrcode> qrcodes = qrcodeService.getQrcodeList();
        map.put("qrcodes", getQrcodesByProduct(qrcodes));
        return modelAndView;
    }

    @RequestMapping("/del")
    public ModelAndView del(Map<String, Object> map, @RequestParam("qrcodeId") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        qrcodeService.del(id);
        map.put("qrcodes", getQrcodesByProduct(qrcodeService.getQrcodeList()));
        return modelAndView;
    }

    @RequestMapping("/del2")
    public ModelAndView del2(Map<String, Object> map, @RequestParam("qrcodeId") int id, @RequestParam("customName") String customName) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        qrcodeService.del(id);
        map.put("qrcode", getQrcodesByProduct(qrcodeService.getQrcodeListByCustomName(customName)));
        return modelAndView;
    }

    @RequestMapping("/delAll")
    public ModelAndView delAll(Map<String, Object> map, @RequestParam("customName") String customName) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        qrcodeService.delByCustomName(customName);
        return modelAndView;
    }

    @RequestMapping("/delByIds")
    public ModelAndView delByIds(Map<String, Object> map,
                                 @RequestParam("qrcodeIds") String qrcodeIds,
                                 @RequestParam("customName") String customName) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        String[] ids = qrcodeIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            qrcodeService.del(Integer.parseInt(ids[i]));
        }
        map.put("qrcode", getQrcodesByProduct(qrcodeService.getQrcodeListByCustomName(customName)));
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView search(@RequestParam("customName") String customName, Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("qrcodelist");
        List<Qrcode> qrcodes = getQrcodesByProduct(qrcodeService.getQrcodeListByCustomName(customName));

        map.put("qrcode", qrcodes);

        return modelAndView;
    }

    @RequestMapping("/download")
    public String download(HttpServletResponse response, @RequestParam("customName") String customName) throws Exception {
        String fileName = "扫码记录";
        //填充qrcodes数据
        List<Qrcode> qrcodes = qrcodeService.getQrcodeListByCustomName(customName);
        List<Map<String, Object>> list = createExcelRecord(qrcodes);
        String columnNames[] = {"产品图片", "产品类型", "产品条码", "产品编号", "尺寸(内)", "尺寸(外)", "框条尺寸",
                "框条材质", "外框编号", "装量", "体积", "单价", "包装方式", "产品描述","客户备注"};//列名
        String columnNamesEng[] = {"PICTURE", "DESCRIPTION", "BARCODE", "ITEM NO.", "PRODUCT OUTER SIZE", "PRODUCT INNER SIZE",
                "FRAME SIZE", "FRAME MATERIAL", "FRAME NO.", "PCS/CTN", "CBM/CTN(M3)", "UNIT PRICE(FOB NINGBO)", "PACKAGE", "REMARKS","COMMENT"};//列名
        String keys[] = {"productPicture","productType", "productBarcode", "productCode", "productSizeIn", "productSizeOut",
                "productSize", "productMaterial", "outframeCode", "productNumber",
                "productVolume", "unitPrice", "productPackage", "productDescribe","comment"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list, keys, columnNames, columnNamesEng).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
        ServletOutputStream out = response.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (final IOException e) {
            throw e;
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
        return null;
    }

    private List<Map<String, Object>> createExcelRecord(List<Qrcode> qrcodes) throws SQLException {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        ProductService productService = new ProductService();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        Qrcode qrcode = null;
        for (int j = 0; j < qrcodes.size(); j++) {
            qrcode = qrcodes.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            Product product = productService.getProductByProductBarcode(qrcode.getProductBarcode());
            Blob blob = (Blob) productService.getPhotoById(product.getId()).get("picture");
            InputStream in = null;
            if(blob != null)
                in = blob.getBinaryStream();
            mapValue.put("productPicture", in);
            mapValue.put("productType", product.getProductType());
            mapValue.put("productBarcode", qrcode.getProductBarcode());
            mapValue.put("productCode", product.getProductCode());
            mapValue.put("productSizeIn", product.getProductSizeIn() + product.getSizeInUnit());
            mapValue.put("productSizeOut", product.getProductSizeOut() + product.getSizeOutUnit());
            mapValue.put("productSize", product.getProductSize() + product.getSizeUnit());
            mapValue.put("productMaterial", product.getProductMaterial());
            mapValue.put("outframeCode", product.getOutframeCode());
            mapValue.put("unitPrice", product.getUnitPrice() + product.getPriceUnit());
            mapValue.put("productNumber", product.getProductNumber());
            mapValue.put("productVolume", product.getProductVolume());
            mapValue.put("productPackage", product.getProductPackage());
            mapValue.put("productDescribe", product.getProductDescribe());
            mapValue.put("comment", qrcode.getComment());


            listmap.add(mapValue);
        }
        return listmap;
    }

    public List<Qrcode> getQrcodesByProduct(List<Qrcode> qrcodes) throws Exception {
        ProductService productService = new ProductService();
        for (int i = 0; i < qrcodes.size(); i++) {
            Product product = productService.getProductByProductBarcode(qrcodes.get(i).getProductBarcode());
            if (product != null) {
                qrcodes.get(i).setProductId(product.getId());
                qrcodes.get(i).setProductType(product.getProductType());
                qrcodes.get(i).setProductCode(product.getProductCode());
                qrcodes.get(i).setProductSizeIn(product.getProductSizeIn());
                qrcodes.get(i).setProductSizeOut(product.getProductSizeOut());
                qrcodes.get(i).setProductSize(product.getProductSize());
                qrcodes.get(i).setProductMaterial(product.getProductMaterial());
                qrcodes.get(i).setOutframeCode(product.getOutframeCode());
                qrcodes.get(i).setUnitPrice(product.getUnitPrice());
                qrcodes.get(i).setProductNumber(product.getProductNumber());
                qrcodes.get(i).setProductVolume(product.getProductVolume());
                qrcodes.get(i).setProductPackage(product.getProductPackage());
                qrcodes.get(i).setProductDescribe(product.getProductDescribe());
                qrcodes.get(i).setSizeInUnit(product.getSizeInUnit());
                qrcodes.get(i).setSizeOutUnit(product.getSizeOutUnit());
                qrcodes.get(i).setSizeUnit(product.getSizeUnit());
                qrcodes.get(i).setPriceUnit(product.getPriceUnit());
            }
        }
        return qrcodes;
    }


}
