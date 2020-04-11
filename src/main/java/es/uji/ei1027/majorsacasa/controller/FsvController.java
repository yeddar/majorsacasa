package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.FsvDao;
import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/voluntario/fsv")
public class FsvController {
    private FsvDao fsvDao;

    @Autowired
    public void setFsvDao(FsvDao fsvDao) {
        this.fsvDao = fsvDao;
    }

    @RequestMapping(value = "/add")
    public String addFsv(Model model) {
        model.addAttribute("fsv", new FranjaServicioVoluntario());
        return "voluntario/fsv/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("fsv") FranjaServicioVoluntario fsv,
                                   BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
            return "voluntario/fsv/add";
        }
        System.out.println(fsv.toString());
        fsvDao.addFranjaServicioVoluntario(fsv);
        return "redirect:list";
    }

    @RequestMapping("/list")
    public String listFsv(Model model) {
        model.addAttribute("fsvList", fsvDao.getFsvList());
        return "voluntario/fsv/list";
    }

}