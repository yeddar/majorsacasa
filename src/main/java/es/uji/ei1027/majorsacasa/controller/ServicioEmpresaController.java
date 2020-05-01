package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.dao.ServicioEmpresaDao;
import es.uji.ei1027.majorsacasa.model.ServicioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/servEmpresa")
public class ServicioEmpresaController {
    private ServicioEmpresaDao servEmpDao;

    @Autowired
    public void setDemandanteDao(ServicioEmpresaDao servEmpDao) {
        this.servEmpDao = servEmpDao;
    }

    /* Add methods
     *
     */

    @RequestMapping(value = "/add")
    public String addDemandante(Model model) {
        model.addAttribute("servEmpresa", new ServicioEmpresa());
        return "servicio_empresa/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("servEmpresa") ServicioEmpresa servicioEmpresa,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "servicio_empresa/add";

        // CREAMOS OBJETO SERV_EMPRESA
        // SI CB_CATERING ESTA MARCADO
            // CREAMOS OBJETO SERVICIO CATERING
        // SI CB_LIMPIEZA ESTA MARCADO
            // CREAMOS OBJETO SERVICIO LIMPIEZA
        // SI CB_SANITARIO ESTA MARCADO
            // CREAMOS OBJETO SERVICIO SANITARIO

        return "";
    }

}
