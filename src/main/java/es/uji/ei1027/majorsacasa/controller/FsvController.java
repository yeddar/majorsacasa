package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AsignacionVoluntarioDao;
import es.uji.ei1027.majorsacasa.dao.FsvDao;
import es.uji.ei1027.majorsacasa.model.AsignacionVoluntario;
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
import java.util.List;

@Controller
@RequestMapping("/voluntario/fsv")
public class FsvController {
    private FsvDao fsvDao;
    private AsignacionVoluntarioDao asigVolDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setFsvDao(FsvDao fsvDao) {
        this.fsvDao = fsvDao;
    }

    @Autowired
    public void setAsignacionVoluntarioDao(AsignacionVoluntarioDao asigVolDao) {
        this.asigVolDao = asigVolDao;
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

    @RequestMapping(value = "/modif_schedule")
    public String modifSchedule(Model model, HttpSession session) {
        model.addAttribute("fsv", new FranjaServicioVoluntario());
        String nick_vol = (String)session.getAttribute("nick");

        // Incluir en el modelo franjas de la BBDD
        List<FranjaServicioVoluntario> franjas_actuales = fsvDao.getFsvByNick(nick_vol);
        model.addAttribute("franjas", franjas_actuales);

        if(session.getAttribute("franjas") == null) {
            // Lista de franjas con id's ficticios
            session.setAttribute("franjas", new ArrayList<FranjaServicioVoluntario>());
            session.setAttribute("id", 0);
        }


        return "voluntario/schedule";
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

        // Añadir franja a lista
        franjas.add(fsv);
        session.setAttribute("franjas", franjas);
        session.setAttribute("id", id+1); // Incremento id lista
        model.addAttribute("franjas_nuevas", franjas);

        String nick_vol = (String)session.getAttribute("nick");
        if (nick_vol != null) { // Si hay usuario logueado
            // Incluir en el modelo franjas de la BBDD
            List<FranjaServicioVoluntario> franjas_actuales = fsvDao.getFsvByNick(nick_vol);
            model.addAttribute("franjas", franjas_actuales);
        }

        return "voluntario/fsv/add_schedule";
    }

    @RequestMapping(value = "/del_franja/{id}")
    public String delFranja(Model modelo, HttpSession session, @PathVariable int id) {
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");

        // Borrar franja de la base de datos
        fsvDao.deleteFsv(id);

        String nick_vol = (String)session.getAttribute("nick");
        if (nick_vol != null) { // Si hay usuario logueado
            // Incluir en el modelo franjas de la BBDD
            List<FranjaServicioVoluntario> franjas_actuales = fsvDao.getFsvByNick(nick_vol);
            modelo.addAttribute("franjas", franjas_actuales);
        }

        session.setAttribute("franjas", franjas);
        modelo.addAttribute("franjas_nuevas",franjas);
        modelo.addAttribute("fsv", new FranjaServicioVoluntario());
        return "voluntario/fsv/add_schedule";

    }
    @RequestMapping(value = "/del_franja_nueva/{id}")
    public String delFranjaNueva(Model modelo, HttpSession session, @PathVariable int id) {
        ArrayList<FranjaServicioVoluntario> franjas = (ArrayList<FranjaServicioVoluntario>) session.getAttribute("franjas");

        // Borrar franja del listado
        franjas.removeIf(fr -> fr.getId() == id);

        String nick_vol = (String)session.getAttribute("nick");
        if (nick_vol != null) { // Si hay usuario logueado
            // Incluir en el modelo franjas de la BBDD
            List<FranjaServicioVoluntario> franjas_actuales = fsvDao.getFsvByNick(nick_vol);
            modelo.addAttribute("franjas", franjas_actuales);
        }

        session.setAttribute("franjas", franjas);
        modelo.addAttribute("franjas_nuevas",franjas);
        modelo.addAttribute("fsv", new FranjaServicioVoluntario());
        return "voluntario/fsv/add_schedule";

    }

    @RequestMapping(value = "/franjas_dem_list/{nick_dem}")
    public String volApplicantsList(HttpSession session, Model model, @PathVariable String nick_dem) {

        String nick_vol = (String) session.getAttribute("nick");

        // Obtener información de asignaciones del voluntario
        List<AsignacionVoluntario> asignaciones_vol = new ArrayList<>();
        List<FranjaServicioVoluntario> franjas_vol = fsvDao.getFsvByNick(nick_vol);
        for (FranjaServicioVoluntario fr : franjas_vol) {
            AsignacionVoluntario asig = asigVolDao.getByFranjaOrNull(fr.getId());
            if(asig != null) { // Franja asignada
                asignaciones_vol.add(asig);
            }
        }

        List<FranjaServicioVoluntario> franjas_dem = new ArrayList<>();
        for (AsignacionVoluntario asigVol: asignaciones_vol) {
            if(asigVol.getNick_demandante().equals(nick_dem)){ // Demandante encontrado
                franjas_dem.add(fsvDao.getFsv(asigVol.getId_franja())); // Se añade la franja a la lista
            }
        }
        model.addAttribute("franjas", franjas_dem);
        return "voluntario/fsv/listWithDemandante";
    }



    @RequestMapping("/list")
    public String listFsv(Model model) {
        model.addAttribute("fsvList", fsvDao.getFsvList());
        return "voluntario/fsv/list";
    }

}