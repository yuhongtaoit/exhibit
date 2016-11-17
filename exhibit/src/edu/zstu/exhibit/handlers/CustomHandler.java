package edu.zstu.exhibit.handlers;

import edu.zstu.exhibit.domain.Product;
import edu.zstu.exhibit.domain.Qrcode;
import edu.zstu.exhibit.domain.QrcodeForm;
import edu.zstu.exhibit.service.ProductService;
import edu.zstu.exhibit.service.QrcodeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by aning on 16/6/24.
 */
@Controller
@RequestMapping("/custom")
public class CustomHandler extends AbstractManageController {
    QrcodeService qrcodeService = new QrcodeService();

    @ResponseBody
    @RequestMapping("/save")
    public Map<String, Object> save(@RequestParam("customName") String customName,
                                    @RequestParam("loginName") String saleName,
                                    @RequestParam(value = "comment", required = false) String comment,
                                    @RequestParam(value = "productBarcodes", required = false) String productBarcodes) throws Exception {
        Map<String, Object> params = new HashMap<>();
        int stutus = -1;
//        List<Qrcode> qrcodeList = qrcodeService.getQrcodeListByCustomName(customName);
//
//        if (qrcodeList.size() > 0 || productBarcodes == null) {
//            params.put("stutus", stutus);
//        } else {
        stutus = qrcodeService.save(customName, saleName, productBarcodes, comment);
        params.put("stutus", stutus);
//        }
        return params;
    }


    /*@ResponseBody
    @RequestMapping("/save")
    public Map<String, Object> save(@RequestParam("customName") String customName,
                                    @RequestParam("loginName") String saleName,
                                   QrcodeForm qrcodeForm) throws Exception {
        Map<String, Object> params = new HashMap<>();
        int stutus = -1;
        List<Qrcode> qrcodeList = qrcodeService.getQrcodeListByCustomName(customName);

        if (qrcodeList.size() > 0 || qrcodeForm == null) {
            params.put("stutus", stutus);
        } else {
            stutus = qrcodeService.save(customName, saleName, qrcodeForm);
            params.put("stutus", stutus);
        }
        return params;
    }*/

    @ResponseBody
    @RequestMapping("/search")
    public Map<String, Object> search(@RequestParam("loginName") String saleName) throws Exception {
        ProductService productService = new ProductService();
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> products = productService.getProductByProductSaleName(saleName);
        Map<String, Integer> customDif = productService.getDiffCustomName(saleName);
        int i = 0;
        for (Map.Entry<String, Integer> entry : customDif.entrySet()) {
            List<Map<String, Object>> l = new ArrayList<>();
            for (int j = 0; j < entry.getValue(); i++, j++) {
                l.add(products.get(i));
            }
            params.put(entry.getKey(), l);
        }
        if (products != null) {
            return params;
        } else
            params.put("products", null);
        return params;
    }

    @ResponseBody
    @RequestMapping("/del")
    public Map<String, Object> del(@RequestParam("loginName") String saleName, @RequestParam("qrcodeId") String qrcodeId) throws SQLException {
        String[] ids = qrcodeId.split(",");
        ProductService productService = new ProductService();
        for (int i = 0; i < ids.length; i++) {
            qrcodeService.del(Integer.parseInt(ids[i]));
        }
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> products = productService.getProductByProductSaleName(saleName);
        if (products != null) {
            params.put("products", products);
        } else
            params.put("products", null);
        return params;
    }
}

