package com.Marissa.FAQ.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping(value = "/index")
    public String entry(){
        return "index";
    }

    @RequestMapping(value = "/detail")
    public String detail(){
        return "detail";
    }

    @RequestMapping("/file")
    public String file(){
        return "file";
    }

    @RequestMapping("/home")
    public String home(){
        return "home";
    }

    @RequestMapping("/mutifile")
    public String mutifile(){
        return "   mutifile";
    }

    @RequestMapping("/")
    public String t(){
        return "index";
    }

}