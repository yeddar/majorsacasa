package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.dao.VoluntarioDao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import es.uji.ei1027.majorsacasa.model.ROL_USUARIO;
import es.uji.ei1027.majorsacasa.model.Usuario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController {
    private VoluntarioDao voluntarioDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao = voluntarioDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @RequestMapping("/list")
    public String listVoluntarios(Model model) {
        model.addAttribute("voluntarios", voluntarioDao.getVoluntarios());
        return "voluntario/list";
    }

    @RequestMapping(value = "/add")
    public String addDemandante(Model model) {
        model.addAttribute("voluntario", new Voluntario());
        return "voluntario/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "voluntario/add";


        voluntario.setRol(ROL_USUARIO.VOLUNTARIO);
        voluntario.setStatus("SIN REVISAR");
        usuarioDao.addUsuario(voluntario);
        voluntarioDao.addVoluntaio(voluntario);
        return "redirect:list";
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
