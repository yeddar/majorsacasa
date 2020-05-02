package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.dao.ServicioCateringDao;
import es.uji.ei1027.majorsacasa.dao.ServicioEmpresaDao;
import es.uji.ei1027.majorsacasa.dao.ServicioLimpiezaDao;
import es.uji.ei1027.majorsacasa.dao.ServicioSanitarioDao;
import es.uji.ei1027.majorsacasa.model.ServicioCatering;
import es.uji.ei1027.majorsacasa.model.ServicioEmpresa;
import es.uji.ei1027.majorsacasa.model.ServicioLimpieza;
import es.uji.ei1027.majorsacasa.model.ServicioSanitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/servEmpresa")
public class ServicioEmpresaController {
    private ServicioEmpresaDao servEmpDao;
    private ServicioCateringDao servCatDao;
    private ServicioSanitarioDao servSanDao;
    private ServicioLimpiezaDao servLimDao;
    //private final Logger log = LoggerFactory.getLogger(this.getClass());

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

    /* Add methods
     *
     */

    @RequestMapping(value = "/add")
    public String addDemandante(Model model) {
        model.addAttribute("servCatering", new ServicioCatering());
        model.addAttribute("servSanitario", new ServicioSanitario());
        model.addAttribute("servLimpieza", new ServicioLimpieza());
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
    public String processAddSubmit(@ModelAttribute("servCatering") ServicioCatering servCatering,
                                   @ModelAttribute("servSanitario") ServicioSanitario servSanitario,
                                   @ModelAttribute("servLimpieza") ServicioLimpieza servLimpieza,
                                   @RequestParam(value="servicios", required = false) String[] servicios,
                                   @RequestParam(value = "dias_catering", required = false) String[] dias_semana,
                                   @RequestParam(value = "fecha_catering", required = false) String fecha_catering,
                                   BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "servicio_empresa/add";

        if(servicios != null){
            for(String servicio : servicios) {
                if (servicio.equals("catering")) {
                    ServicioEmpresa servEmpresaCat = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaCat.setNick_empresa("emp1");
                    servEmpresaCat.setNick_demandante("demandante2");
                    servCatering.setNick_demandante("demandante2");
                    servCatering.setNick_empresa("emp1");

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
                    servEmpresaSan.setNick_empresa("emp8");
                    servEmpresaSan.setNick_demandante("demandante2");
                    servSanitario.setNick_demandante("demandante2");
                    servSanitario.setNick_empresa("emp8");

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_catering.equals("") ? null : LocalDate.parse(fecha_catering);
                    servEmpresaSan.setF_fin(f_fin);
                    servEmpresaSan.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaSan);
                    servSanDao.addServicioSanitario(servSanitario);

                } else if (servicio.equals("limpieza")) {
                    ServicioEmpresa servEmpresaLim = new ServicioEmpresa();

                    // AÑADIMOS DEMANDANTE Y EMPRESA
                    servEmpresaLim.setNick_empresa("empresa3");
                    servEmpresaLim.setNick_demandante("demandante2");
                    servLimpieza.setNick_demandante("demandante2");
                    servLimpieza.setNick_empresa("empresa3");

                    // AÑADIMOS VALORES DE FORMULARIO RESTANTES
                    LocalDate f_fin = fecha_catering.equals("") ? null : LocalDate.parse(fecha_catering);
                    servEmpresaLim.setF_fin(f_fin);
                    servEmpresaLim.setServ_status("SIN EVALUAR");

                    // AÑADIMOS A BASE DE DATOS
                    servEmpDao.addServicioEmpresa(servEmpresaLim);
                    servLimDao.addServicioLimpieza(servLimpieza);
                }
            }
        }

        return "redirect:../demandante/add";
    }

}
