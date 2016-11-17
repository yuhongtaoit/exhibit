package edu.zstu.exhibit.handlers;

import com.google.common.base.Charsets;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by aning on 16/6/1.
 */
public abstract class AbstractManageController {
    public void getCommonModelAndView(HttpServletResponse response, Object param) {
        Gson gson = new Gson();
        try {
            response.setCharacterEncoding(Charsets.UTF_8.name());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(param));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String timeStamp2Date(String timestampString){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String sd = sdf.format(new Date(Long.parseLong(timestampString)));
        return sd;
    }
}
