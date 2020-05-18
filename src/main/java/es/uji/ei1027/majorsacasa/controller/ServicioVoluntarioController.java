package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.AsignacionVoluntarioDao;
import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import es.uji.ei1027.majorsacasa.dao.FsvDao;
import es.uji.ei1027.majorsacasa.dao.VoluntarioDao;
import es.uji.ei1027.majorsacasa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import es.uji.ei1027.majorsacasa.model.AsignacionVoluntario;
import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Controller
@RequestMapping("/servVoluntario")
public class ServicioVoluntarioController {
    private AsignacionVoluntarioDao asignacionVoluntarioDao;
    private VoluntarioDao voluntarioDao;
    private FsvDao franjaServicioVoluntarioDao;

    private DemandanteDao demandanteDao;

    @Autowired
    public void setAsignacionVoluntarioDao(AsignacionVoluntarioDao asignacionVoluntarioDao) {
        this.asignacionVoluntarioDao = asignacionVoluntarioDao;
    }

    @Autowired
    public void setVoluntarioDao(VoluntarioDao voluntarioDao) {
        this.voluntarioDao = voluntarioDao;
    }

    @Autowired
    public void setFranjaServicioVoluntarioDao(FsvDao franjaServicioVoluntarioDao) {
        this.franjaServicioVoluntarioDao = franjaServicioVoluntarioDao;
    }

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    /* Add methods
     *
     */
    private String getDia(String dia){
        if(dia.equals("L")){
            return "Lunes";
        }else if(dia.equals("M")){
            return "Martes";
        }else if (dia.equals("X")){
            return "Miércoles";
        }else if (dia.equals("J")){
            return "Jueves";
        }else if (dia.equals("V")){
            return "Viernes";
        }
        return "";
    }

    @RequestMapping(value = "/add")
    public String addServicioVoluntario(Model model) {
        List<FranjaServicioVoluntario> franjas = franjaServicioVoluntarioDao.getFsvFree();
        model.addAttribute("franjas", franjas);

        HashMap aficiones = new HashMap();
        for(FranjaServicioVoluntario franja : franjas){
            franja.setDiaSemana(getDia(franja.getDiaSemana()));
            Voluntario voluntario = voluntarioDao.getVoluntario(franja.getNick());
            aficiones.put(franja.getId(), voluntario.getAficiones());
        }
        model.addAttribute("aficiones", aficiones);

        //model.addAttribute("nick", nick);
        return "servicio_voluntario/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("franjas") ArrayList<FranjaServicioVoluntario> franjas,
                                   @ModelAttribute("aficiones") HashMap aficiones,
                                   @RequestParam(value = "cb_seleccion", required = false) int[] selecciones,
                                   @RequestParam(value = "fecha_voluntrario", required = false) String fecha_voluntario,
                                   @RequestParam(value = "rd_servicio") boolean decision,
                                   BindingResult bindingResult, Model model, HttpSession session){
        if (bindingResult.hasErrors())
            return "servVoluntario/add";

        if(decision){
            if(selecciones != null){
                for(int id : selecciones){
                    // COMPROBACION DE ERRORES SIN HACER
                    AsignacionVoluntario asignacionVoluntario = new AsignacionVoluntario();
                    LocalDate f_fin = fecha_voluntario.equals("") ? null : LocalDate.parse(fecha_voluntario);
                    asignacionVoluntario.setF_fin(f_fin);
                    asignacionVoluntario.setId_franja(id);

                    // ASIGNAMOS EL ULTIMO VOLUNTARIO REGISTRADO EN LA BASE DE DATOS
                    asignacionVoluntario.setNick_demandante((String) session.getAttribute("nick"));

                    asignacionVoluntario.setServ_status("SIN EVALUAR");
                    asignacionVoluntarioDao.addAsignacionVoluntario(asignacionVoluntario);
                }
            }else{
                //  NO SE SELECCIONARON, ERROR O NO HAY FRANJAS VACIAS
                throw new IllegalArgumentException("NO SELECCIONÓ FRANJAS");
            }
        }else{
            // SELECCIONO QUE NO QUIERE SERVICIO VOLUNTARIO
        }

        return "redirect:/servEmpresa/add";
    }

    /*
    Accept state
     */
    @RequestMapping(value="/accept/{idFranja}/{nickDem}")
    public String acceptState(@PathVariable int idFranja, @PathVariable String nickDem){
        asignacionVoluntarioDao.setTypeStatus(idFranja, "ESPERA VOLUNTARIO");
        return "redirect:/demandante/solicitudes/"+nickDem;
    }

    /*
    Cancel state
     */
    @RequestMapping(value="/cancel/{idFranja}/{nickDem}")
    public String cancelState(@PathVariable int idFranja, @PathVariable String nickDem){
        asignacionVoluntarioDao.setTypeStatus(idFranja, "CANCELADO");
        return "redirect:/demandante/solicitudes/"+nickDem;
    }


    @RequestMapping(value="/asignaciones/list")
    public String listWithDemandantes(Model model, HttpSession session){
        Usuario user = (Usuario) session.getAttribute("user");
        model.addAttribute("asignaciones", asignacionVoluntarioDao.getEsperaVoluntario(user.getNick()));
        System.out.print(asignacionVoluntarioDao.getEsperaVoluntario(user.getNick()).toString());
        return "voluntario/asignaciones/list";
    }



    @RequestMapping(value="/feed/{id_franja}", method = RequestMethod.GET)
    public String feedbackAsignacion(Model model, @PathVariable int id_franja){
        asignacionVoluntarioDao.setTypeStatus(id_franja, "ACEPTADO");
        /*LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            return "voluntario/asignaciones/list";
        }

         */
        return "redirect:/servVoluntario/asignaciones/list";
    }





}
