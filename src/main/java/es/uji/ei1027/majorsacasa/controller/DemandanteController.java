package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.dao.DemandanteDao;
import org.springframework.beans.factory.annotation. Autowired ;
import org.springframework.stereotype. Controller ;
import org.springframework.ui. Model ;
import org.springframework.web.bind.annotation. RequestMapping ;


@Controller
@RequestMapping ( "/demandante" )
public class DemandanteController {
    private DemandanteDao demandanteDao ;
    @Autowired
    public void setNadadorDao(DemandanteDao demandanteDao) {
        this.demandanteDao =demandanteDao;
    }

    @RequestMapping ( "/list" )
    public String listNadadors(Model model) {
        model.addAttribute( "demandantes" , demandanteDao.getDemandantes());
        return "demandante/list" ;
    }

}