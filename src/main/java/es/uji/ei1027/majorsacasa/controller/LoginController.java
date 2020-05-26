package es.uji.ei1027.majorsacasa.controller;


import javax.servlet.http.HttpSession;

import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import es.uji.ei1027.majorsacasa.dao.EmpresaDao;
import es.uji.ei1027.majorsacasa.dao.VoluntarioDao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import es.uji.ei1027.majorsacasa.model.Empresa;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.Usuario;

class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Usuario.class.isAssignableFrom(cls);
    }
    @Override
    public void validate(Object obj, Errors errors) {
        // Combrobar que campos no queden vacíos
        Usuario userDetails = (Usuario) obj;
        if(userDetails.getNick().trim().equals(""))
            errors.rejectValue("nick", "obligatorio", "El nick es un campo obligatorio");
        if(userDetails.getPass().trim().equals(""))
            errors.rejectValue("pass", "obligatorio", "La contraseña es un campo obligatorio");
    }
}

@Controller
public class LoginController {
    @Autowired
    private UsuarioDao userDao;

    private DemandanteDao demandanteDao;
    private VoluntarioDao voluntarioDao;
    private EmpresaDao empresaDao;


    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao = voluntarioDao;
    }

    @Autowired
    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }




    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new Usuario());
        return "login";
    }

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public String checkLogin(@ModelAttribute("user") Usuario user,
                             BindingResult bindingResult, HttpSession session) {
        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }

        // Comprobar que el login es correcto
        user = userDao.loadUserByNick(user.getNick(), user.getPass());
        boolean cancelado = false;
        if(user !=null) {
            if (user.getRol().equals("VOLUNTARIO")) {
                Voluntario v = voluntarioDao.getVoluntario(user.getNick());
                if (v.getStatus().equals("CANCELADO"))
                    cancelado = true;
            } else if (user.getRol().equals("DEMANDANTE")) {
                Demandante d = demandanteDao.getDemandante(user.getNick());
                if (d.getStatus().equals("CANCELADO"))
                    cancelado = true;
            }
        }


        if (user == null || cancelado) {
            bindingResult.rejectValue("pass", "badpw", "Contraseña incorrecta");
            return "login";
        }
        // Autenticación correctat.
        // Guardamos los datos de el usuario autenticado en la session.
        session.setAttribute("user", user);
        session.setAttribute("nick", user.getNick());
        session.setAttribute("pass", user.getPass());

        // Torna a la pàgina principal
        String nextUrl = (String)session.getAttribute("nextURL");

        if(nextUrl != null) {
            session.removeAttribute("nextURL"); // Borramos atributo que no vamos a volver a usar
            return "redirect:" + nextUrl;
        }
        return "common/home";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}


