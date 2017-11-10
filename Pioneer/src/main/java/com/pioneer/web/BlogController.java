package com.pioneer.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/blog")
public class BlogController {
    
    @RequestMapping("/index")
    public String index(ModelMap map) {
        return "module/blog/index";
    }
    @RequestMapping("/environmentSetup")
    public String environmentSetup(ModelMap map) {
        map.addAttribute("host", "http://blog.didispace.com");
        return "module/blog/environmentSetup";
    }
    @RequestMapping("/environmentSetup/loadblogDetails")
    public String loadblogDetails(ModelMap map) {
        return "module/blog/blogDetails";
    }
    @RequestMapping("/scheduleLogs")
    public String scheduleLogs(ModelMap map) {
        return "module/blog/scheduleLogs";
    }
    
}