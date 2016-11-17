package edu.zstu.exhibit.handlers;

import edu.zstu.exhibit.domain.User;
import edu.zstu.exhibit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by aning on 16/5/31.
 */
@Controller
@RequestMapping("/user")
public class UserHandler {

    UserService userService = new UserService();

    @RequestMapping("/login")
    public ModelAndView login(@RequestParam("logInName") String logInName, @RequestParam("password") String password) {
        ModelAndView modelAndView = null;
        User user = new UserService().getUserByLogInName(logInName);
        if (user != null) {
            if (user.getUserPassword().equals(password) && user.getUserType()==0) {
                modelAndView = new ModelAndView("main");
                return modelAndView;
            }
        }
        modelAndView = new ModelAndView("error");
        return modelAndView;
    }

    @RequestMapping("/back")
    public ModelAndView back() {
        ModelAndView modelAndView = new ModelAndView("main");
        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView list(Map<String, Object> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("userlist");
        map.put("users", userService.getUserList());
        return modelAndView;
    }


    @RequestMapping("/input")
    public String input() {
        return "userinput";
    }

    @RequestMapping("/add")
    public ModelAndView add(User user, Map<String, String> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("success");
        System.out.println(user);
        userService.save(user);
        map.put("part", "userman");
        return modelAndView;
    }

    @RequestMapping("/del")
    public ModelAndView del(Map<String, Object> map, @RequestParam("userId")int id) throws Exception {
        ModelAndView modelAndView = new ModelAndView("userlist");
        userService.del(id);
        map.put("users", userService.getUserList());
        return modelAndView;
    }


    @RequestMapping("/toupdate")
    public ModelAndView toupdate(Model model, @RequestParam("userId")int id) {
        ModelAndView modelAndView = new ModelAndView("userUpdate");
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(User user, Map<String, String> map) throws Exception {
        ModelAndView modelAndView = new ModelAndView("success");
        userService.update(user);
        map.put("part", "userman");
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/saleIn")
    public Map<String, Object> saleIn(@RequestParam("loginName") String loginName, @RequestParam("password")String password) {
        Map<String, Object> params = new HashMap<>();

        User user = userService.getUserByLogInName(loginName);
        int status = -1;
        if (user != null)
            if(user.getUserPassword().equals(password) && user.getUserType() == 1)
                status = 1;

        params.put("status", status);
        return params;
    }
}
