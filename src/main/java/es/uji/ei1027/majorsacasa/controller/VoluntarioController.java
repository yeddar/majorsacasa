package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/voluntario")
public class VoluntarioController implements UserService{
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private VoluntarioDao voluntarioDao;
    private UsuarioDao usuarioDao;
    private FsvDao fsvDao;
    private AsignacionVoluntarioDao asigVolDao;
    private DemandanteDao demandanteDao;


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

    @Autowired
    public void setAsignacionVoluntarioDao(AsignacionVoluntarioDao asigVolDao) { this.asigVolDao = asigVolDao;}

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) { this.demandanteDao = demandanteDao;}

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
        return "voluntario/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {

        // Checks
        VoluntarioValidator validator = new VoluntarioValidator();
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

    @RequestMapping(value = "/vol_update", method = RequestMethod.GET)
    public String volUpdate(Model model, HttpSession session) {
        String nick = (String)session.getAttribute("nick");
        model.addAttribute("voluntario", voluntarioDao.getVoluntario(nick));
        return "/voluntario/update";
    }

    @RequestMapping(value = "/vol_update", method = RequestMethod.POST)
    public String processVolUpdate(@ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {
        // Checks
        VoluntarioValidator validator = new VoluntarioValidator();
        validator.validate(voluntario, bindingResult);

        if (bindingResult.hasErrors())
            return "voluntario/update";


        voluntario.setStatus("ACEPTADO");
        System.out.print(voluntario.toString());
        voluntarioDao.updateVoluntario(voluntario);
        return "redirect:/";

    }

    @RequestMapping(value = "/remove_user")
    public String removeUser(Model model){
        model.addAttribute("user", new Usuario());
        return "/voluntario/delete_account";
    }

    @RequestMapping(value = "/remove_user", method = RequestMethod.POST)
    public String processDeleteVolunteer(HttpSession session, Model model,
                                         @ModelAttribute Usuario user,
                                         BindingResult bindingResult){
        String nick = (String)session.getAttribute("nick");
        user.setNick(nick);
        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(user, bindingResult);

        if(bindingResult.hasErrors()) {
            model.addAttribute("user", new Usuario());
            return "voluntario/delete_account";
        }
        // Comprobar que la contraseña es correcta
        String pass = user.getPass();
        Usuario auxuser = usuarioDao.getUsuario(nick);

        if (!(auxuser.getPass().equals(pass))) {
            model.addAttribute("user", new Usuario());
            bindingResult.rejectValue("pass", "incorrectpass", "Contraseña incorrecta");
            return  "voluntario/delete_account";
        }

        // Cambiar status a CANCELADO
        voluntarioDao.deleteVoluntario(nick);
        // Comprobamos asignaciones
        List<FranjaServicioVoluntario> franjas_ocupadas = fsvDao.getFsvOccupedByVol(nick);
        // Borramos si existen
        for(FranjaServicioVoluntario fsv : franjas_ocupadas)
            asigVolDao.setTypeStatus(fsv.getId(), "CANCELADO");

        // Importante invalidar sesión
        session.invalidate();
        return "redirect:/";
    }

    // Register Methods

    @RequestMapping(value = "/vol_register", method = RequestMethod.POST)
    public String processAddSubmit(Model model, HttpSession session,
                                   @ModelAttribute("voluntario") Voluntario voluntario,
                                   BindingResult bindingResult) {

        // Checks
        VoluntarioValidator validator = new VoluntarioValidator();
        System.out.println(validator);
        validator.validate(voluntario, bindingResult);

        // Validación de campos en formulario
        if (bindingResult.hasErrors())
            return "voluntario/add";

        // Comprobar si el nick está en uso
        if(getUserByNick(voluntario.getNick()) != null) {
            bindingResult.rejectValue("nick", "nick.exists", "El nick ya está en uso.");
            return "voluntario/add";
        }
        session.setAttribute("vol", voluntario);
        return "redirect:/voluntario/fsv/schedule";
    }

    @RequestMapping(value = "/vol_schedule")
    public String volRequest(HttpSession session) {
        Voluntario vol = (Voluntario) session.getAttribute("vol");

        // Obtener franjas de la sesión
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");
        session.removeAttribute("franjas");


        // Añadir voluntario si no se está logeado
        if (vol != null){
            vol.setRol(ROL_USUARIO.VOLUNTARIO);
            vol.setStatus("SIN REVISAR");
            // Alta voluntario en la base de datos
            usuarioDao.addUsuario(vol);
            voluntarioDao.addVoluntaio(vol);
            session.removeAttribute("vol");
        }

        // Añadir franjas
        String nick = (vol != null)? vol.getNick():(String)session.getAttribute("nick");

        for (FranjaServicioVoluntario fsv: franjas){
            if (fsvDao.getFsvByTime(nick, fsv.gethIni(), fsv.gethFin(), fsv.getDiaSemana()) == null) {
                fsv.setNick(nick);
                fsvDao.addFranjaServicioVoluntario(fsv);
            }
        }
        return "redirect:/";
    }
    //-----------------------//

    @RequestMapping(value = "/vol_applicants_list")
    public String volApplicantsList(HttpSession session, Model model) {

        String nick_vol = (String)session.getAttribute("nick");

        List<FranjaServicioVoluntario> franjas = fsvDao.getFsvByNick(nick_vol);

        // Filtrar por franjas asignadas a demandantes y obtener información de asignaciones del voluntario
        List<FranjaServicioVoluntario> franjas_asignadas = new ArrayList<>();
        List<AsignacionVoluntario> asignaciones = new ArrayList<>();
        for (FranjaServicioVoluntario fr : franjas) {
            AsignacionVoluntario asig = asigVolDao.getAcceptedAssignament(fr.getId());
            if(asig != null) { // Franja asignada
                franjas_asignadas.add(fr);
                asignaciones.add(asig);
            }
        }

        // Obtener listado demandantes asignados
        List<Demandante> demAsignados = new ArrayList<>();

        List<String> nickDemNoRepe = new ArrayList<>();
        for (AsignacionVoluntario asig : asignaciones){
            if (!(nickDemNoRepe.contains(asig.getNick_demandante())))
                nickDemNoRepe.add(asig.getNick_demandante());
        }
        for (String nick_dem : nickDemNoRepe) {
            System.out.println(nick_dem);
            demAsignados.add(demandanteDao.getDemandante(nick_dem));

        }

        model.addAttribute("demandantes", demAsignados);
        model.addAttribute("franjas", franjas_asignadas);
        return "voluntario/applicants_list";
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
    public String processDelete(@PathVariable String nick, HttpSession session) {
        voluntarioDao.deleteVoluntario(nick);
        // Comprobamos asignaciones
        List<FranjaServicioVoluntario> franjas_ocupadas = fsvDao.getFsvOccupedByVol(nick);
        // Borramos si existen
        for(FranjaServicioVoluntario fsv : franjas_ocupadas)
            asigVolDao.setTypeStatus(fsv.getId(), "CANCELADO");
        return "redirect:"+session.getAttribute("lastURL");
    }

    // Accept method

    @RequestMapping(value = "/accept/{nick}")
    public String processAccept(@PathVariable String nick, HttpSession session){
        voluntarioDao.acceptVol(nick);

        Voluntario voluntario = (Voluntario) voluntarioDao.getVoluntario(nick);
        log.info("Envío de correo a: " + voluntario.getEmail() +" explicando que su cuenta de voluntario ha sido aceptada");
        return "redirect:"+session.getAttribute("lastURL");
    }

    // View profile methods

    @RequestMapping(value = "/viewProfile/{nick}")
    public String viewProfile(@PathVariable String nick, Model model){
        Voluntario vol = voluntarioDao.getVoluntario(nick);
        model.addAttribute("voluntario", vol);
        return "voluntario/viewVoluntario";
    }

    @RequestMapping(value = "/viewFranjas/{nick}")
    public String viewFranjas (@PathVariable String nick, Model model){
        // recogemos las franjas del usuario (libres)
        List<FranjaServicioVoluntario> franjas_libres = fsvDao.getFsvFreeByVol(nick);
        //recogemos las franjas del usuario (asignadas)
        List<FranjaServicioVoluntario> franjas_ocupadas = fsvDao.getFsvOccupedByVol(nick);
        // añadimos al modelo
        model.addAttribute("franjas_libres", franjas_libres);
        model.addAttribute("franjas_ocupadas", franjas_ocupadas);
        // mandamos a la vista
        return "voluntario/fsv/listFranjasSupervisor";
    }

    // List supervisor methods

    @RequestMapping(value = "/listAceptados")
    public String viewAcepted(Model model, HttpSession session){
        model.addAttribute("voluntarios", voluntarioDao.getAcceptedVol());
        session.setAttribute("lastURL", "/voluntario/listAceptados");
        return "supervisorCAS/listAceptados";
    }

    @RequestMapping(value = "/listSinRevisar")
    public String viewSinRevisar(Model model, HttpSession session){
        // recogemos los voluntarios con estado sin revisar
        List<Voluntario> voluntarios = voluntarioDao.getVoluntarioSinRevisar();
        model.addAttribute("voluntarios", voluntarios);
        session.setAttribute("lastURL", "/voluntario/listSinRevisar");
        return "supervisorCAS/listSinRevisar";
    }

    @Override
    public Usuario getUserByNick(String nick) {
        return usuarioDao.getUsuario(nick);
    }
}
