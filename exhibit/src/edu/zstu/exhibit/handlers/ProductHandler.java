package edu.zstu.exhibit.handlers;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import edu.zstu.exhibit.domain.Directory;
import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.service.DirectoryService;
import edu.zstu.exhibit.service.ProductService;
import edu.zstu.exhibit.util.CompressUtil;
import edu.zstu.exhibit.util.Printer;
import edu.zstu.exhibit.util.QrcodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by aning on 16/5/27.
 */
@Controller
@RequestMapping("/product")
public class ProductHandler extends AbstractManageController {

    ProductService productService = new ProductService();
    DirectoryService directoryService = new DirectoryService();
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("productlist");
        map.put("products", productService.getProductList());
        return modelAndView;
    }

    @RequestMapping("/input")
    public String input(Map<String, Object> map) throws Exception {
        map.put("directorys", directoryService.getAllDirectorys());
        return "productinput";
    }

    @RequestMapping("/add")
    public ModelAndView add(Product product,
                            @RequestParam(value = "imageUp", required = false) MultipartFile img,
                            @RequestParam(value = "otherType", required = false) String otherType,
                            @RequestParam(value = "otherMaterial", required = false) String otherMaterial) throws Exception {
        ModelAndView modelAndView = new ModelAndView("success");
//        DirectoryService directoryService = new DirectoryService();
        if (otherType != null && !otherType.equals("")) {
//            directoryService.save(new Directory(otherType, 1));
            product.setProductType(otherType);
        }
        if (otherMaterial != null && !otherMaterial.equals("")) {
//            directoryService.save(new Directory(otherMaterial, 3));
            product.setProductMaterial(otherMaterial);
        }
        product.setCreatTime(product.getCreatTime() / 1000);
        //压缩图片
        if(!img.isEmpty())
            product.setProductPicture(CompressUtil.compressImg(img));

        //product.setProductPicture(img.getInputStream());
        productService.save(product);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/scan")
    public Map<String,Object> scan(@RequestParam(value = "salename", required = false, defaultValue="")String salename,@RequestParam("productBarcode") String productBarcode) {
        Map<String,Object> params = new HashMap<>();
        if(salename.equals("") || salename == null) {
            params.put("error", "请用画之都APP扫描");
            return params;
        }
        String creatTime = "";
        Product product = productService.getProductByProductBarcode(productBarcode);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        if(product != null) {
            creatTime = sdf.format(new Date(Long.valueOf(product.getCreatTime() + "000")));
            JSONObject json = (JSONObject) JSONObject.toJSON(product);
            json.put("creatTime", creatTime);
            params.put("product", json);
        } else
            params.put("product", null);
        return params;
    }

    @RequestMapping("/getpic")
    public void getPic(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        //blob就是你要显示的那张图片
        Blob blob = (Blob) productService.getPhotoById(productId).get("picture");
        if(blob == null) return;
        InputStream in = null;
        byte[] data;
        try {
            in = blob.getBinaryStream();
            OutputStream out = response.getOutputStream();
            data = new byte[(int) blob.length()];
            int i = 0;
            while ((i = in.read(data)) != -1) {
                out.write(data);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/del")
    public ModelAndView del(Map<String, Object> map, @RequestParam("productId") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("productlist");
        productService.del(id);
        map.put("products", productService.getProductList());
        return modelAndView;
    }

    @RequestMapping("/toupdate")
    public ModelAndView toupdate(Model model, @RequestParam("productId") int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("productUpdate");
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("directorys", directoryService.getAllDirectorys());
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(Product product,
                               @RequestParam(value = "imageUp", required = false) MultipartFile img,
                               @RequestParam(value = "otherType", required = false) String otherType,
                               @RequestParam(value = "otherMaterial", required = false) String otherMaterial,
                               @RequestParam("isImgUp") String isImgUp) throws Exception {
        ModelAndView modelAndView = new ModelAndView("success");
//        DirectoryService directoryService = new DirectoryService();
        if (otherType != null && !otherType.equals("")) {
//            directoryService.save(new Directory(otherType, 1));
            product.setProductType(otherType);
        }
        if (otherMaterial != null && !otherMaterial.equals("")) {
//            directoryService.save(new Directory(otherMaterial, 3));
            product.setProductMaterial(otherMaterial);
        }
        product.setCreatTime(product.getCreatTime() / 1000);
        if (isImgUp.equals("YES")) {
            product.setProductPicture(img.getInputStream());
        } else {
            try {
                product.setProductPicture(productService.getPhotoById(product.getId()).get("picture").getBinaryStream());
            } catch (NullPointerException e) {
                product.setProductPicture(null);
            }
        }
        productService.update(product);
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/print")
    public Map<String, Object> print(@RequestParam("productBarcodes") String productBarcodes) throws Exception {
        Map<String, Object> params = new HashMap<>();
        boolean flag = false;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String[] barcodes = productBarcodes.split(",");
        int barcodeLen = barcodes.length;
        int cout = 1;
        if (barcodeLen % 6 == 0)
            cout = barcodeLen / 6;
        else
            cout = barcodeLen / 6 + 1;

        QrcodeUtil qr = new QrcodeUtil(barcodes);
        Printer printer = new Printer();
        try {

            if (barcodes.length > 6) {
                flag = printer.printQrcode(qr.creats(cout));
            } else {
                flag = printer.printQrcode(qr.creat());
            }
        } catch (Exception e) {
            flag = false;
        }
        if (flag)
            params.put("status", "success");
        else {
            params.put("status", "fail");
            QrcodeUtil qr2 = new QrcodeUtil(barcodes);
            qr2.creatToLocal();
        }
        return params;
    }

    @RequestMapping("download")
    public ResponseEntity<byte[]> download(@RequestParam("productBarcodes") String productBarcodes) throws Exception {
        String[] barcodes = productBarcodes.split(",");
        int barcodeLen = barcodes.length;
        HttpHeaders headers = new HttpHeaders();
        int cout = 1;
        if (barcodeLen % 6 == 0)
            cout = barcodeLen / 6;
        else
            cout = barcodeLen / 6 + 1;

        if (barcodeLen > 6) {
            QrcodeUtil qr = new QrcodeUtil(barcodes);
            qr.creatToLocal();
            InputStream inputStream = null;
            String imgPath = "";
            String zipPath = System.getProperty("exhibit.root") + "/zip";
            File zipParentFile = new File(zipPath);
            if (!zipParentFile.exists())
                zipParentFile.mkdirs();
            File zipFile = new File(zipPath + File.separator + "temp.zip");
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile.getAbsolutePath()));
            zipOut.setComment("zip");
            for (int i = 0; i < cout; i++) {
                imgPath = System.getProperty("exhibit.root") + "/tmp/out" + i + ".png";
                inputStream = new FileInputStream(imgPath);
                zipOut.putNextEntry(new ZipEntry("out" + i + ".png"));
                int temp = 0;
                while ((temp = inputStream.read()) != -1) {
                    zipOut.write(temp);
                }
                inputStream.close();
            }
            zipOut.close();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "qrcode.zip");
            return new ResponseEntity<byte[]>(org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(zipFile))
                    , headers, HttpStatus.CREATED);

        } else {
            QrcodeUtil qr = new QrcodeUtil(barcodes);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "test.png");

            return new ResponseEntity<byte[]>(org.apache.commons.io.IOUtils.toByteArray(qr.creat())
                    , headers, HttpStatus.CREATED);
        }

    }
}

