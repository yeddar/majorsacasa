package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/common")
public class CommonController {

    private UsuarioDao usuarioDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @RequestMapping(value = "/rootIndex")
    public String allView() {
        return "root/rootIndex";
    }

    @RequestMapping(value = "/home")
    public String goHome() {
        return "/home";
    }

}
