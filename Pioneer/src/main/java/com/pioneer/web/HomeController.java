package com.pioneer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    
    @RequestMapping("/index")
    public String index(ModelMap map) {
        return "index";
    }
    @RequestMapping("/")
    public String home(ModelMap map) {
        return "redirect:/map/index "; 
    }
}