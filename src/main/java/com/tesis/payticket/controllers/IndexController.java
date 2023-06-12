package com.tesis.payticket.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class IndexController {

    @GetMapping(value = {"/","/index"})
    public String index() {
        return "index";
    }
    @GetMapping(value = {"/ubicacion/ver"})
    public String verMap() {
        return "form";
    }

}
