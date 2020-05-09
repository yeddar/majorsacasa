package es.uji.ei1027.majorsacasa.controller;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import es.uji.ei1027.majorsacasa.model.Demandante;


public class DemandanteValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Demandante.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Demandante d = (Demandante) o;
        // Comprobar nombre
        if (d.getNombre().trim().equals(""))
            errors.rejectValue("nombre", "obligatorio", "Campo necesario");
    }
}
