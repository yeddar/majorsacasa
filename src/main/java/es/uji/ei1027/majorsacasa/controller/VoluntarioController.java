package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.FsvDao;
import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.dao.VoluntarioDao;
import es.uji.ei1027.majorsacasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {
    private VoluntarioDao voluntarioDao;
    private UsuarioDao usuarioDao;
    private FsvDao fsvDao;

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao = voluntarioDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    public void setFsvDao(FsvDao fsvDao) { this.fsvDao = fsvDao;}

    @RequestMapping("/list")
    public String listVoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioDao.getVoluntarios());
        return "voluntario/list";
    }

    @RequestMapping(value = "/add")
    public String addVoluntario(Model model) {
        model.addAttribute("voluntario", new Voluntario());
        return "voluntario/add";
    }

    @RequestMapping(value = "/register")
    public String volunteerReg(Model model) {
        model.addAttribute("voluntario", new Voluntario());
        return "voluntario/volunteer_register";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {

        // Checks
        UserValidator validator = new UserValidator();
        validator.validate(voluntario, bindingResult);

        if (bindingResult.hasErrors())
            return "voluntario/add";

        // Static fields
        voluntario.setRol(ROL_USUARIO.VOLUNTARIO);
        voluntario.setStatus("SIN REVISAR");
        usuarioDao.addUsuario(voluntario);
        voluntarioDao.addVoluntaio(voluntario);


        return "redirect:list";
    }

    @RequestMapping(value = "/vol_register", method = RequestMethod.POST)
    public String processAddSubmit(Model model, HttpSession session,
                                   @ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {

        // Checks
        UserValidator validator = new UserValidator();
        validator.validate(voluntario, bindingResult);

        if (bindingResult.hasErrors())
            return "voluntario/volunteer_register";

        session.setAttribute("vol", voluntario);
        return "redirect:/voluntario/fsv/schedule";
    }

    @RequestMapping(value = "/vol_request")
    public String volRequest(HttpSession session) {

        // Obtener de la sesión
        Voluntario vol = (Voluntario)session.getAttribute("vol");
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");

        // Borrar atributos sesión
        session.removeAttribute("vol");
        session.removeAttribute("franjas");

        // Añadir voluntario
        vol.setRol(ROL_USUARIO.VOLUNTARIO);
        vol.setStatus("SIN REVISAR");
        System.out.println(vol.toString());
        usuarioDao.addUsuario(vol);
        voluntarioDao.addVoluntaio(vol);

        // Añadir franjas
        for (FranjaServicioVoluntario fsv: franjas){
            fsv.setNick(vol.getNick());;
            fsvDao.addFranjaServicioVoluntario(fsv);
        }
        return "redirect:list";
    }

    @RequestMapping(value = "/modif_franjas")
    public String modifFranjas(HttpSession session) {
        String nick = (String)session.getAttribute("nick");
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");

        for(FranjaServicioVoluntario fsv : franjas){
            if(fsvDao.getFsv(fsv.getId()) == null){
                fsvDao.addFranjaServicioVoluntario(fsv);
            }
        }
        session.removeAttribute("franjas");
        return "/index.html";

    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editVoluntario(Model model, @PathVariable String nick) {
        model.addAttribute("voluntario", voluntarioDao.getVoluntario(nick));
        return "voluntario/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("voluntario") Voluntario voluntario,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntario/update";
        voluntarioDao.updateVoluntario(voluntario);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        voluntarioDao.deleteVoluntario(nick);
        return "redirect:../list";
    }


}
