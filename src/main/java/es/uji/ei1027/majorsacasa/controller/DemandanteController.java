package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/demandante")
public class DemandanteController {
    private DemandanteDao demandanteDao;

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    // List method

    @RequestMapping("/list")
    public String listDemandantes(Model model) {
        model.addAttribute("demandantes", demandanteDao.getDemandantes());
        return "demandante/list";
    }

    // Add methods

    @RequestMapping(value = "/add")
    public String addDemandante(Model model) {
        model.addAttribute("demandante", new Demandante());
        return "demandante/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("demandante") Demandante demandante,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "demandante/add";

        demandanteDao.addDemandante(demandante);
        return "redirect:/";
    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editDemandante(Model model, @PathVariable String nick) {
        model.addAttribute("demandante", demandanteDao.getDemandante(nick));
        return "demandante/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("demandante") Demandante demandante,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "demandante/update";
        demandanteDao.updateDemandante(demandante);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        demandanteDao.deleteDemandante(nick);
        return "redirect:../list";
    }


}