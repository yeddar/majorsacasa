package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

class ServicioCateringValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return ServicioCatering.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServicioCatering servicioCatering = (ServicioCatering) o;

        if(servicioCatering.getDias_semana().equals("-----"))
            errors.rejectValue("dias_catering", "obligatorio","No introdujo días para el servicio de catering.");
    }
}

class ServicioSanitarioValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return ServicioSanitario.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServicioSanitario servicioSanitario = (ServicioSanitario) o;

        if(servicioSanitario.getNecesidad().trim().equals(""))
            errors.rejectValue("necesidad", "obligatorio", "No introdujo una breve descripción.");
    }
}

class ServicioLimpiezaValidator implements Validator{

    @Override
    public boolean supports(Class<?> aClass) {
        return ServicioLimpieza.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ServicioLimpieza servicioLimpieza = (ServicioLimpieza) o;

        if(servicioLimpieza.getHoras() <= 0)
            errors.rejectValue("horas", "obligatorio", "No introdujo las horas semanales o fueron incorrectas");
    }
}

@Controller
@RequestMapping("/servEmpresa")
public class ServicioEmpresaController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private UsuarioDao usuarioDao;
    private ServicioEmpresaDao servEmpDao;
    private ServicioCateringDao servCatDao;
    private ServicioSanitarioDao servSanDao;
    private ServicioLimpiezaDao servLimDao;
    private EmpresaDao empDao;
    private DemandanteDao demandanteDao;
    private FslDao fslDao;

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Autowired
    public void setServicioEmpresaDao(ServicioEmpresaDao servEmpDao) {
        this.servEmpDao = servEmpDao;
    }

    @Autowired
    public void setServicioCateringDao(ServicioCateringDao servCatDao) {
        this.servCatDao = servCatDao;
    }

    @Autowired
    public void setServicioSanitarioDao(ServicioSanitarioDao servSanDao) {
        this.servSanDao = servSanDao;
    }

    @Autowired
    public void setServicioLimpiezaDao(ServicioLimpiezaDao servLimDao) {
        this.servLimDao = servLimDao;
    }

    @Autowired
    public void setEmpresaDao(EmpresaDao empDao) {
        this.empDao = empDao;
    }

    @Autowired
    public void setDemandanteDao(DemandanteDao demandanteDao) {
        this.demandanteDao = demandanteDao;
    }

    @Autowired
    public void setFslDao(FslDao fslDao) {
        this.fslDao = fslDao;
    }

    /* Add methods
     *
     */

    private Empresa seleccionEmpresa(String tipo_empresa){
        List<Empresa> empresas = empDao.getEmpresaByType(tipo_empresa);
        Empresa mayorVacantes = new Empresa();
        mayorVacantes.setVacantes(0);
        for (Empresa empresa : empresas){
            if(empresa.getVacantes() > mayorVacantes.getVacantes())
                mayorVacantes=empresa;
        }
        return mayorVacantes;
    }

    @RequestMapping(value = "/add")
    public String addServicioEmpresa(Model model) {
        model.addAttribute("servCatering", new ServicioCatering());
        model.addAttribute("servSanitario", new ServicioSanitario());
        model.addAttribute("servLimpieza", new ServicioLimpieza());
        return "servicio_empresa/add";
    }

    private String constructor_cadena(String[] dias_semana){
        if(dias_semana == null)
            return "-----";

        List dias_lista = Arrays.asList(dias_semana);
        String dias = "";

        if(dias_lista.contains("Lunes"))
            dias += 'L';
        else
            dias += '-';
        if(dias_lista.contains("Martes"))
            dias += 'M';
        else
            dias += '-';
        if(dias_lista.contains("Miercoles"))
            dias += 'X';
        else
            dias += '-';
        if(dias_lista.contains("Jueves"))
            dias += 'J';
        else
            dias += '-';
        if(dias_lista.contains("Viernes"))
            dias += 'V';
        else
            dias += '-';

        return dias;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("servCatering") ServicioCatering servCatering,
                                   @ModelAttribute("servSanitario") ServicioSanitario servSanitario,
                                   @ModelAttribute("servLimpieza") ServicioLimpieza servLimpieza,
                                   @RequestParam(value="servicios", required = false) String[] servicios,
                                   @RequestParam(value = "dias_catering", required = false) String[] dias_semana,
                                   @RequestParam(value = "fecha_catering", required = false) String fecha_catering,
                                   @RequestParam(value = "fecha_sanitario", required = false) String fecha_sanitario,
                                   @RequestParam(value = "fecha_limpieza", required = false) String fecha_limpieza,
                                   BindingResult bindingResult, HttpSession session) {

        Demandante demandante = (Demandante) session.getAttribute("demandante_registro");
        int total = 0;

        if(servicios != null) {
            for (String servicio : servicios) {
                if (servicio.equals("catering")) {
                    ServicioEmpresa servEmpresaCat = new ServicioEmpresa();
                    Empresa empresa_seleccionada = seleccionEmpresa("CATERING");

                    if(empresa_seleccionada.getNick() == null)
                        throw new IllegalArgumentException("NO DISPONEMOS DE EMPRESAS PARA OFRECER ESTE SERVICIO.");

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaCat.setNick_empresa(empresa_seleccionada.getNick()); // ESTATICO
                    servEmpresaCat.setNick_demandante(demandante.getNick());
                    servCatering.setNick_demandante(demandante.getNick());
                    servCatering.setNick_empresa(empresa_seleccionada.getNick()); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_catering.equals("") ? null : LocalDate.parse(fecha_catering);
                    servEmpresaCat.setF_fin(f_fin);

                    // COMPROBAMOS QUE SE HAN MARCADO DÍAS
                    String dias = constructor_cadena(dias_semana);
                    servCatering.setDias_semana(dias);
                    servEmpresaCat.setServ_status("SIN EVALUAR");

                    // VALIDAMOS
                    //ServicioCateringValidator cateringValidator = new ServicioCateringValidator();
                    //cateringValidator.validate(servCatering, bindingResult);
                    //if (bindingResult.hasErrors())
                    //    return "servEmpresa/add";

                    // AÑADIMOS A BASE DE DATOS
                    session.setAttribute("servEmpCat", servEmpresaCat);
                    session.setAttribute("servCat", servCatering);
                    session.setAttribute("cantidadCatering", 5*dias_semana.length*4);
                    total += 5*dias_semana.length*4;
                } else if (servicio.equals("sanitario")) {
                    ServicioEmpresa servEmpresaSan = new ServicioEmpresa();
                    Empresa empresa_seleccionada = seleccionEmpresa("SANITARIA");

                    if(empresa_seleccionada.getNick() == null)
                        throw new IllegalArgumentException("NO DISPONEMOS DE EMPRESAS PARA OFRECER ESTE SERVICIO.");

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaSan.setNick_empresa(empresa_seleccionada.getNick()); // ESTATICO
                    servEmpresaSan.setNick_demandante(demandante.getNick());
                    servSanitario.setNick_demandante(demandante.getNick());
                    servSanitario.setNick_empresa(empresa_seleccionada.getNick()); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_sanitario.equals("") ? null : LocalDate.parse(fecha_sanitario);
                    servEmpresaSan.setF_fin(f_fin);
                    servEmpresaSan.setServ_status("SIN EVALUAR");

                    // VALIDAMOS
                    ServicioSanitarioValidator sanitarioValidator = new ServicioSanitarioValidator();
                    sanitarioValidator.validate(servSanitario, bindingResult);
                    if (bindingResult.hasErrors())
                        return "servEmpresa/add";

                    // AÑADIMOS A BASE DE DATOS
                    session.setAttribute("servEmpSan", servEmpresaSan);
                    session.setAttribute("servSan", servSanitario);
                    session.setAttribute("cantidadSanitario", 5);
                    total += 5;
                } else if (servicio.equals("limpieza")) {
                    ServicioEmpresa servEmpresaLim = new ServicioEmpresa();
                    Empresa empresa_seleccionada = seleccionEmpresa("LIMPIEZA");

                    if(empresa_seleccionada.getNick() == null)
                        throw new IllegalArgumentException("NO DISPONEMOS DE EMPRESAS PARA OFRECER ESTE SERVICIO.");

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaLim.setNick_empresa(empresa_seleccionada.getNick());
                    servEmpresaLim.setNick_demandante(demandante.getNick());
                    servLimpieza.setNick_demandante(demandante.getNick());
                    servLimpieza.setNick_empresa(empresa_seleccionada.getNick());

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_limpieza.equals("") ? null : LocalDate.parse(fecha_limpieza);
                    servEmpresaLim.setF_fin(f_fin);
                    servEmpresaLim.setServ_status("SIN EVALUAR");

                    // VALIDAMOS
                    ServicioLimpiezaValidator limpiezaValidator = new ServicioLimpiezaValidator();
                    limpiezaValidator.validate(servLimpieza, bindingResult);
                    if (bindingResult.hasErrors())
                        return "servEmpresa/add";

                    // AÑADIMOS A BASE DE DATOS
                    session.setAttribute("servEmpLim", servEmpresaLim);
                    session.setAttribute("servLim", servLimpieza);
                    session.setAttribute("cantidadLimpieza", 5*servLimpieza.getHoras()*4);
                    total += 5*servLimpieza.getHoras()*4;
                }
            }
        }

        session.setAttribute("pagoTotal", total);
        return "redirect:/demandante/pago-servicios";
    }

    // List by type method
    @RequestMapping("/listWithType")
    public String listEmpresas(Model model) {
        List<ServicioEmpresa> serviciosEmpresa = servEmpDao.getServiciosEmpresa();
        model.addAttribute("servicios_empresa",serviciosEmpresa );

        HashMap servsTipo = new HashMap();
        for(ServicioEmpresa servicioEmpresa : serviciosEmpresa){
            String nickEmpresa = servicioEmpresa.getNick_empresa();
            Empresa empresa = empDao.getEmpresa(nickEmpresa);
            servsTipo.put(nickEmpresa,empresa.getTipo_empresa());
        }
        model.addAttribute("servsTipo", servsTipo);

        return "servicio_empresa/listWithType";
    }

    /*
    View methods
     */
    @RequestMapping(value = "/view/{nickEmp}/{nickDem}")
    public String view(@PathVariable String nickEmp, @PathVariable String nickDem, Model model){
        String tipo = empDao.getEmpresa(nickEmp).getTipo_empresa();
        ServicioEmpresa servEmp = servEmpDao.getServicioEmpresaStatus(nickDem, nickEmp);
        model.addAttribute("status", servEmp.getServ_status());

        if(tipo.equals("CATERING")){
            ServicioCatering servCat = servCatDao.getServicioCatering(nickEmp, nickDem);
            model.addAttribute("servicioCatering", servCat);
            return "comiteCAS/viewServEmpCatering";

        }else if(tipo.equals("SANITARIA")){
            ServicioSanitario servSan = servSanDao.getServicioSanitario(nickEmp, nickDem);
            model.addAttribute("servicioSanitario", servSan);
            return "comiteCAS/viewServEmpSanitario";

        }else{
            ServicioLimpieza servLimp = servLimDao.getServicioLimpieza(nickEmp, nickDem);
            model.addAttribute("servicioLimpieza", servLimp);
            return "comiteCAS/viewServEmpLimpieza";
        }
    }

    /*
    Accept state
     */
    @RequestMapping(value = "/accept/{nickEmp}/{nickDem}")
    public String acceptState(@PathVariable String nickEmp, @PathVariable String nickDem){
        servEmpDao.setTypeStatus(nickEmp, nickDem, "ESPERA EMPRESA");
        Empresa emp = empDao.getEmpresa(nickEmp);
        //MENSAJE A LA EMPRESA
        log.info("SERVICIO DE CORREO: Se envió un correo avisando sobre una nueva asignación a la empresa "+emp.getNombre()+" para el demandante "+nickDem+".");
        return "redirect:/demandante/solicitudes/"+nickDem;
    }

    /*
    Cancel state
     */
    @RequestMapping(value = "/cancel/{nickEmp}/{nickDem}")
    public String cancelState(@PathVariable String nickDem, @PathVariable String nickEmp){
        servEmpDao.setTypeStatus(nickEmp, nickDem, "CANCELADO");
        servEmpDao.setF_Fin(nickEmp,nickDem);
        return "redirect:/demandante/solicitudes/"+nickDem;
    }

    @RequestMapping(value = "/cancel/{nickDem}")
    public String cancelByEmpresa(@PathVariable String nickDem, HttpSession session){
        servEmpDao.setTypeStatus((String) session.getAttribute("nick"), nickDem, "CANCELADO");
        servEmpDao.setF_Fin((String) session.getAttribute("nick"),nickDem);
        return "redirect:"+session.getAttribute("lastURL");
    }

    /*
    Feedback methods
    */
    @RequestMapping(value = "/loadFeedback/{nick}")
    public String getFeedbackView(@PathVariable String nick, Model model, HttpSession session){
        // cogemos el tipo de empresa y lo pasamos al modelo
        String tipoEmpresa = empDao.getEmpresa((String) session.getAttribute("nick")).getTipo_empresa();
        model.addAttribute("tipo_empresa", tipoEmpresa);
        if(tipoEmpresa.equals("LIMPIEZA")){
            HashMap<String, List<FranjaServicioLimpieza>> franjas_dem = session.getAttribute("franjas") != null ? (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas") :new HashMap<>();
            HashMap<String, Integer> id_franja = (HashMap<String, Integer>) session.getAttribute("id_franja") != null ? (HashMap<String, Integer>) session.getAttribute("id_franja") : new HashMap<>();
            if(!franjas_dem.containsKey(nick))
                franjas_dem.put(nick,new ArrayList<>());

            if(!id_franja.containsKey(nick))
                id_franja.put(nick, 0);

            session.setAttribute("franjas", franjas_dem);
            session.setAttribute("id_franja", id_franja);
            model.addAttribute("franjas_demandante", franjas_dem.get(nick));
        }
        model.addAttribute("demandante", nick);
        return "empresa/putFeedbackEmpresa";
    }

    @RequestMapping(value = "/feedback-catering/{nick}", method = RequestMethod.POST)
    public String setFeedbackCatering(@RequestParam(value="hora_servicio") String hora_servicio,
                                      @PathVariable String nick, HttpSession session) throws ParseException {
        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO CATERING
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date d = dateFormat.parse(hora_servicio);
        servCatDao.setFeedback(nickEmp, nick, d);
        empDao.decreaseVacantes(empDao.getEmpresa(nickEmp));
        log.info("SERVICIO DE CORREO: Se envió un correo avisando al demandante "+nick+" sobre que su solicitud de servicio de catering fue aceptada y respondida por la empresa asignada.");
        return "redirect:/empresa/listPendientes";
    }

    @RequestMapping(value = "/feedback-sanitario/{nick}", method = RequestMethod.POST)
    public String setFeedbackSanitario(@RequestParam(value="dia_visita") String dia_visita,
                                       @RequestParam(value="franja") String franja,
                                       @PathVariable String nick, HttpSession session){
        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO SANITARIO
        char franja_dia = franja.equals("manana") ? 'M':'T';
        servSanDao.setFeedback(nickEmp, nick, LocalDate.parse(dia_visita), franja_dia);
        empDao.decreaseVacantes(empDao.getEmpresa(nickEmp));
        log.info("SERVICIO DE CORREO: Se envió un correo avisando al demandante "+nick+" sobre que su solicitud de servicio sanitario fue aceptada y respondida por la empresa asignada.");
        return "redirect:/empresa/listPendientes";
    }

    @RequestMapping(value = "/feedback-limpieza/{nick}")
    public String setFeedbackLimpieza(@PathVariable String nick, HttpSession session){

        String nickEmp = (String) session.getAttribute("nick");
        // ACTUALIZAMOS EL ESTADO DE LA TABLA SERVICIO EMPRESA
        servEmpDao.setTypeStatus(nickEmp, nick, "ACEPTADO");
        servEmpDao.setF_Ini(nickEmp, nick);
        // ACTUALIZAMOS LOS VALORES DEL FEEDBACK DE LA TABLA SERVICIO LIMPIEZA
        HashMap<String, Integer> id_franja = (HashMap<String, Integer>) session.getAttribute("id_franja");
        HashMap<String, List<FranjaServicioLimpieza>> franjas_creadas = (HashMap<String, List<FranjaServicioLimpieza>>) session.getAttribute("franjas");
        for(FranjaServicioLimpieza fsl : franjas_creadas.get(nick)){
            fslDao.addFranja(fsl);
        }
        // BORRAMOS DE LA CACHE EL USUARIO
        franjas_creadas.remove(nick);
        id_franja.remove(nick);
        session.setAttribute("franjas", franjas_creadas);
        session.setAttribute("id_franja", id_franja);

        // ACTUALIZAMOS LAS VACANTES DE LA EMPRESA
        empDao.decreaseVacantes(empDao.getEmpresa(nickEmp));
        log.info("SERVICIO DE CORREO: Se envió un correo avisando al demandante "+nick+" sobre que su solicitud de servicio de limpieza fue aceptada y respondida por la empresa asignada.");
        return "redirect:/empresa/listPendientes";
    }
}
