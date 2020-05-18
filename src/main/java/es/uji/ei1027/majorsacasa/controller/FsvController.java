package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.FsvDao;
import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
@RequestMapping("/voluntario/fsv")
public class FsvController {
    private FsvDao fsvDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    @RequestMapping(value = "/delete/{id}")
    public String delFsv(@PathVariable int id) {
        fsvDao.deleteFsv(id);
        return "redirect:../list";
    }

    @RequestMapping(value = "/schedule")
    public String addSchedule(Model model, HttpSession session) {
        model.addAttribute("fsv", new FranjaServicioVoluntario());
        session.setAttribute("franjas", new ArrayList<FranjaServicioVoluntario>());
        session.setAttribute("id", 0);
        return "voluntario/fsv/add_schedule";
    }


    @RequestMapping(value = "/add_franja", method = RequestMethod.POST)
    public String addFranja(Model model,
                            HttpSession session,
                            @ModelAttribute("fsv") FranjaServicioVoluntario fsv,
                            @RequestParam(value="dia_semana") String diaSemana) {

        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");
        int id = (int) session.getAttribute("id");

        fsv.setId(id);
        fsv.setDiaSemana(diaSemana);
        System.out.println("holaa");
        // Añadir franja a lista
        franjas.add(fsv);
        session.setAttribute("franjas", franjas);
        session.setAttribute("id", id+1); // Incremento id lista
        model.addAttribute("franjas", franjas);


        return "voluntario/fsv/add_schedule";
    }

    @RequestMapping(value = "/del_franja/{id}")
    public String delFranja(Model modelo, HttpSession session, @PathVariable int id) {
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");

        franjas.removeIf(fr -> fr.getId() == id);

        session.setAttribute("franjas", franjas);
        modelo.addAttribute("franjas",franjas);
        modelo.addAttribute("fsv", new FranjaServicioVoluntario());
        return "voluntario/fsv/add_schedule";
    }



    @RequestMapping("/list")
    public String listFsv(Model model) {
        model.addAttribute("fsvList", fsvDao.getFsvList());
        return "voluntario/fsv/list";
    }

}