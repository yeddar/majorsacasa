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

        //nick en bbdd


        // edad
        if(d.getEdad() < 20 || d.getEdad() > 120)
            errors.rejectValue("edad", "inválido", "Edad inválida.");

        // telefono
        boolean valido = true;
        for (int i = 0; i < d.getTlf().length(); i++){
            if (!Character.isDigit(d.getTlf().charAt(i))) {
                valido = false;
                break;
            }
        }
        //if(d.getTlf().length() != 9 && d.getTlf().contains("[a-zA-Z]+"))
        if(d.getTlf().length() != 9 || !valido)
            errors.rejectValue("tlf", "inválido", "Teléfono inválido.");

        // email
        if (!d.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))
            errors.rejectValue("email", "inválido", "Dirección de correo inválido.");


    }
}
