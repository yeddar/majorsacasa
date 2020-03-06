package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.VoluntarioDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {
    private VoluntarioDao voluntarioDao;

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao=voluntarioDao;
    }

    @RequestMapping("/list")
    public String listVoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioDao.getVoluntarios());
        return "voluntario/list";
    }

}
