package es.uji.ei1027.majorsacasa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class IndexController {
    @RequestMapping(value="/", method = RequestMethod.GET)
    String index(){
        return "/common/home";
    }

    @RequestMapping(value = "/team")
    public String goWeAre() {
        return "/common/team";
    }
}
