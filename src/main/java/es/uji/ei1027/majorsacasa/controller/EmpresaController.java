package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.EmpresaDao;
import es.uji.ei1027.majorsacasa.dao.UsuarioDao;
import es.uji.ei1027.majorsacasa.model.Empresa;
import es.uji.ei1027.majorsacasa.model.ROL_USUARIO;
import es.uji.ei1027.majorsacasa.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/empresa")
public class EmpresaController {
    private EmpresaDao empresaDao;
    private UsuarioDao usuarioDao;

    @Autowired
    public void setEmpresaDao(EmpresaDao empresaDao) {
        this.empresaDao = empresaDao;
    }

    @Autowired
    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    // List method

    @RequestMapping("/list")
    public String listEmpresas(Model model) {
        model.addAttribute("empresas", empresaDao.getEmpresas());
        return "empresa/list";
    }

    // Add methods

    @RequestMapping(value = "/add")
    public String addEmpresa(Model model) {
        model.addAttribute("empresa", new Empresa());
        return "empresa/add";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("empresa") Empresa empresa,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/add";

        Usuario user = new Usuario();
        user.setNick(empresa.getNick());
        user.setPass("123");
        user.setRol(ROL_USUARIO.EMPRESA);

        usuarioDao.addUsuario(user);
        System.out.print(empresa.toString());

        empresaDao.addEmpresa(empresa);

        return "redirect:/";
    }

    // Update methods

    @RequestMapping(value = "/update/{nick}", method = RequestMethod.GET)
    public String editEmpresa(Model model, @PathVariable String nick) {
        model.addAttribute("empresa", empresaDao.getEmpresa(nick));
        return "empresa/update";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String processUpdateSubmit(
            @ModelAttribute("empresa") Empresa empresa,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "empresa/update";
        empresaDao.updateEmpresa(empresa);
        return "redirect:list";
    }

    // Delete method

    @RequestMapping(value = "/delete/{nick}")
    public String processDelete(@PathVariable String nick) {
        empresaDao.deleteEmpresa(nick);
        return "redirect:../list";
    }


}
