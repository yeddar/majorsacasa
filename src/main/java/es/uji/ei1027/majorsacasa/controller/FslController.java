package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.FslDao;
import es.uji.ei1027.majorsacasa.model.Demandante;
import es.uji.ei1027.majorsacasa.model.FranjaServicioLimpieza;
import es.uji.ei1027.majorsacasa.model.FranjaServicioVoluntario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import org.springframework.validation.Validator;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

class FslValidator implements Validator{
    @Override
    public boolean supports(Class<?> aClass) {
        return FranjaServicioLimpieza.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        // Comprobar campos
        FranjaServicioLimpieza fslDetails = (FranjaServicioLimpieza) o;

        LocalTime hIni = fslDetails.gethIni();
        LocalTime hFin = fslDetails.gethFin();

        if(hIni == null)
            errors.rejectValue("hIni", "missing", "Se requiere una hora inicial");
        if(hFin == null)
            errors.rejectValue("hFin", "missing", "Se requiere una hora final");

        if(hIni != null && hFin != null) {
            if(hFin.isBefore(hIni) || Duration.between(hIni, hFin).toHours() < 1)
                errors.rejectValue("hIni" ,"invalidhours", "Debe haber una hora de diferencia entre la hora inicio y la hora fin.");
        }
    }
}

@Controller
@RequestMapping(value = "/franja-limpieza")
public class FslController {
    private FslDao fslDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setFslDao(FslDao fslDao) {
        this.fslDao = fslDao;
    }

    /*
    Add method
     */
    @RequestMapping(value = "/add/{nick}")
    public String addGET(@PathVariable String nick, Model model, HttpSession session){
        model.addAttribute("franjaNueva", new FranjaServicioLimpieza());
        model.addAttribute("demandante", nick);
        model.addAttribute("mensajeErrorDupFranjaLimpieza", session.getAttribute("mensajeErrorDupFranjaLimpieza"));
        return "empresa/fsl/add";
    }

    private boolean franjasDuplicadas(HttpSession session, String nick, FranjaServicioLimpieza fsl){
        HashMap<String, List<FranjaServicioLimpieza>> map_franjas = (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas");
        for(FranjaServicioLimpieza fr : map_franjas.get(nick)){
            if(fr.gethIni() == fsl.gethIni() && fr.getDiaSemana() == fsl.getDiaSemana() && fr.gethFin() == fsl.gethFin())
                return true;
        }
        return false;
    }

    @RequestMapping(value ="/add/{nick}", method = RequestMethod.POST)
    public String addPOST(@PathVariable String nick,
                          @RequestParam("rd_dia") String dia,
                          @ModelAttribute("franjaNueva") FranjaServicioLimpieza fsl, HttpSession session,
                          BindingResult bindingResult, Model model){

        FslValidator fslValidator = new FslValidator();
        fslValidator.validate(fsl, bindingResult);
        if (bindingResult.hasErrors()){
            //session.setAttribute("mensajeErrorDupFranjaLimpieza", true);
            //return "redirect:/franja-limpieza/add/"+nick;
            return "empresa/fsl/add";
        }else {
            session.setAttribute("mensajeErrorDupFranjaLimpieza", null);
        }


        // Metemos valores estaticos
        fsl.setNick_empresa((String) session.getAttribute("nick"));
        fsl.setNick_demandante(nick);
        fsl.setDiaSemana(dia);

        // La metemos en la cache (session)
        HashMap<String, List<FranjaServicioLimpieza>> map_franjas = (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas");
        HashMap<String, Integer> id_franjas = (HashMap<String, Integer>) session.getAttribute("id_franja");
        fsl.setId_franja(id_franjas.get(nick));
        id_franjas.put(nick, id_franjas.get(nick)+1);

        if(franjasDuplicadas(session, nick, fsl)){
            session.setAttribute("mensajeErrorDupFranjaLimpieza", true);
            return "redirect:/franja-limpieza/add/"+nick;
        }else {
            session.setAttribute("mensajeErrorDupFranjaLimpieza", null);
        }

        map_franjas.get(nick).add(fsl);
        session.setAttribute("franjas", map_franjas);
        session.setAttribute("id_franja", id_franjas);
        // Metemos en el modelo todos los datos
        model.addAttribute("tipo_empresa", "LIMPIEZA");
        model.addAttribute("demandante", nick);
        model.addAttribute("franjas_demandante", map_franjas.get(nick));

        // Devolvemos a la pagina del listado de feedback
        return "empresa/putFeedbackEmpresa";
    }

    /*
    delete from cache
     */
    @RequestMapping(value = "/borrar-franja/{nick}/{id}")
    public String deleteCache(@PathVariable String nick,
                              @PathVariable int id, HttpSession session, Model model){
        HashMap<String, List<FranjaServicioLimpieza>> mapFranjas = (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas");

        int pos = -1;
        for(FranjaServicioLimpieza fsl : mapFranjas.get(nick)){
            if(fsl.getId_franja() == id)
                pos = mapFranjas.get(nick).indexOf(fsl);
        }
        mapFranjas.get(nick).remove(pos);
        // ACTUALIZAMOS DATOS DE LA VISTA
        session.setAttribute("franjas", mapFranjas);
        model.addAttribute("tipo_empresa", "LIMPIEZA");
        model.addAttribute("demandante", nick);
        model.addAttribute("franjas_demandante", mapFranjas.get(nick));
        return "empresa/putFeedbackEmpresa";
    }

    /*
    List methods
     */

    // Empresa de limpieza
    @RequestMapping(value = "/viewFranjas/{nickDem}")
    public String viewFranjas(@PathVariable String nickDem, Model model, HttpSession session){
        // Coger todas las franjas del demandante
        String nickEmp = (String) session.getAttribute("nick");
        List<FranjaServicioLimpieza> franjas = fslDao.getFranjasByDemandanteAndEmpresa(nickDem, nickEmp);
        // Metemos en el modelo
        model.addAttribute("franjas", franjas);
        // Devolvemos la vista
        return "empresa/fsl/list";
    }

    // ComiteCAS
    @RequestMapping(value = "/viewFranjas/{nickEmp}/{nickDem}")
    public String viewFranjas(@PathVariable String nickDem, @PathVariable String nickEmp, Model model, HttpSession session){
        // Coger todas las franjas del demandante
        List<FranjaServicioLimpieza> franjas = fslDao.getFranjasByDemandanteAndEmpresa(nickDem, nickEmp);
        // Metemos en el modelo
        model.addAttribute("franjas", franjas);

        model.addAttribute("nickDem", nickDem);
        model.addAttribute("nickEmp", nickEmp);

        // Devolvemos la vista
        return "empresa/fsl/list";
    }

}
