package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.RespEmpresaDao;
import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.ROL_USUARIO;
import es.uji.ei1027.majorsacasa.model.RespEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/respEmpresa")
public class RespEmpresaController {
    private RespEmpresaDao respEmpresaDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setRespEmpresaDao(RespEmpresaDao respEmpresaDao) {
        this.respEmpresaDao = respEmpresaDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    // List method

    @RequestMapping("/list")
    public String listRespEmpresas(Model model) {
        model.addAttribute("respEmpresas", respEmpresaDao.getRespEmpresas());
        return "respEmpresa/list";
    }

    // Add methods

    @RequestMapping(value = "/add")
    public String addRespEmpresa(Model model) {
        model.addAttribute("respEmpresa", new RespEmpresa());
        return "respEmpresa/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("respEmpresa") RespEmpresa respEmpresa,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "respEmpresa/add";

        respEmpresa.setRol(ROL_USUARIO.RESP_EMPRESA);
        System.out.println(respEmpresa.toString());
        usuarioDao.addUsuario(respEmpresa);
        respEmpresaDao.addRespEmpresa(respEmpresa);
        return "redirect:/";
    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editRespEmpresa(Model model, @PathVariable String nick) {
        model.addAttribute("respEmpresa", respEmpresaDao.getRespEmpresa(nick));
        return "respEmpresa/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("respEmpresa") RespEmpresa respEmpresa,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "respEmpresa/update";
        respEmpresaDao.updateRespEmpresa(respEmpresa);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        respEmpresaDao.deleteRespEmpresa(nick);
        return "redirect:../list";
    }


}
