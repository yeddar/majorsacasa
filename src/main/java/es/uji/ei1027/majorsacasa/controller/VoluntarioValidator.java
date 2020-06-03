package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Usuario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VoluntarioValidator implements Validator {

    //@Autowired
    //@Qualifier("voluntario")
    //private VoluntarioController userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Voluntario.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        // Checks volunteer
        Voluntario vol = (Voluntario) o;

        // TODO: Me gustaría hacerlo todo de una. Me salta error porque no existe el método missing_fields
        // edad
        if(vol.getEdad() < 18)
            errors.rejectValue("edad", "inválido", "Edad inválida.");

        // telefono
        boolean valido = true;
        for (int i = 0; i < vol.getTlf().length(); i++){
            if (!Character.isDigit(vol.getTlf().charAt(i))) {
                valido = false;
                break;
            }
        }
        //if(d.getTlf().length() != 9 && d.getTlf().contains("[a-zA-Z]+"))
        if(vol.getTlf().length() != 9 || !valido)
            errors.rejectValue("tlf", "inválido", "Teléfono inválido.");

        // email
        if (!vol.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))
            errors.rejectValue("email", "inválido", "Dirección de correo inválida.");

        // nick
        if(!vol.getNick().matches("^[A-Za-z][A-Za-z0-9]*$"))
            errors.rejectValue("nick", "inválido", "El nick no puede contener espacios en blanco");


    }
}
