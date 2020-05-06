package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.dao.*;
import es.uji.ei1027.majorsacasa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/servEmpresa")
public class ServicioEmpresaController {
    private ServicioEmpresaDao servEmpDao;
    private ServicioCateringDao servCatDao;
    private ServicioSanitarioDao servSanDao;
    private ServicioLimpiezaDao servLimDao;
    private EmpresaDao empDao;
    //private final Logger log = LoggerFactory.getLogger(this.getClass());
    private DemandanteDao demandanteDao;
    private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    /* Add methods
     *
     */

    @RequestMapping(value = "/add/{nick_demandante}")
    public String addServicioEmpresa(@PathVariable("nick_demandante") String nick, Model model) {
        model.addAttribute("servCatering", new ServicioCatering());
        model.addAttribute("servSanitario", new ServicioSanitario());
        model.addAttribute("servLimpieza", new ServicioLimpieza());
        model.addAttribute("nick", nick);
        return "servicio_empresa/add";
    }

    private String constructor_cadena(String[] dias_semana){
        List dias_lista = new ArrayList<>();
        dias_lista = Arrays.asList(dias_semana);
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
    public String processAddSubmit(@RequestParam("nickDem") String nick,
                                   @ModelAttribute("servCatering") ServicioCatering servCatering,
                                   @ModelAttribute("servSanitario") ServicioSanitario servSanitario,
                                   @ModelAttribute("servLimpieza") ServicioLimpieza servLimpieza,
                                   @RequestParam(value="servicios", required = false) String[] servicios,
                                   @RequestParam(value = "dias_catering", required = false) String[] dias_semana,
                                   @RequestParam(value = "fecha_catering", required = false) String fecha_catering,
                                   @RequestParam(value = "fecha_sanitario", required = false) String fecha_sanitario,
                                   @RequestParam(value = "fecha_limpieza", required = false) String fecha_limpieza,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "servEmpresa/add";


        //Demandante demandante = demandanteDao.getLastDemandante();
        if(servicios != null) {
            for (String servicio : servicios) {
                if (servicio.equals("catering")) {
                    ServicioEmpresa servEmpresaCat = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaCat.setNick_empresa("empresa5"); // ESTATICO
                    servEmpresaCat.setNick_demandante(nick);
                    servCatering.setNick_demandante(nick);
                    servCatering.setNick_empresa("empresa5"); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_catering.equals("") ? null : LocalDate.parse(fecha_catering);
                    servEmpresaCat.setF_fin(f_fin);
                    String dias = constructor_cadena(dias_semana);
                    servCatering.setDias_semana(dias);
                    servEmpresaCat.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaCat);
                    servCatDao.addServicioCatering(servCatering);

                } else if (servicio.equals("sanitario")) {
                    ServicioEmpresa servEmpresaSan = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaSan.setNick_empresa("empresa1"); // ESTATICO
                    servEmpresaSan.setNick_demandante(nick);
                    servSanitario.setNick_demandante(nick);
                    servSanitario.setNick_empresa("empresa1"); // ESTATICO

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_sanitario.equals("") ? null : LocalDate.parse(fecha_sanitario);
                    servEmpresaSan.setF_fin(f_fin);
                    servEmpresaSan.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaSan);
                    servSanDao.addServicioSanitario(servSanitario);

                } else if (servicio.equals("limpieza")) {
                    ServicioEmpresa servEmpresaLim = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaLim.setNick_empresa("empresa3");
                    servEmpresaLim.setNick_demandante(nick);
                    servLimpieza.setNick_demandante(nick);
                    servLimpieza.setNick_empresa("empresa3");

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_limpieza.equals("") ? null : LocalDate.parse(fecha_limpieza);
                    servEmpresaLim.setF_fin(f_fin);
                    servEmpresaLim.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaLim);
                    servLimDao.addServicioLimpieza(servLimpieza);
                }
            }
        }

        return "redirect:/servVoluntario/add/"+nick;
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

}
