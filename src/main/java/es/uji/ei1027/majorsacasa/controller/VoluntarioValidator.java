package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Usuario;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class VoluntarioValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Voluntario.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        // Checks volunteer
        Voluntario vol = (Voluntario) o;
        String nick = vol.getNick();
        String pass = vol.getPass();
        String name = vol.getNombre();
        int edad = vol.getEdad();
        String tlf = vol.getTlf();
        String email = vol.getEmail();

        // TODO: Me gustaría hacerlo todo de una. Me salta error porque no existe el método missing_fields
        //if (nick.trim().equals("") || pass.trim().equals("") || name.trim().equals("") | tlf.trim().equals("") | email.trim().equals(""))
        //    errors.rejectValue("missing_field", "Error. Debe rellenar todos los campos obligatorios");

        if (nick.trim().equals(""))
            errors.rejectValue("nick", "obligatorio", "Campo 'Nick' obligatorio");
        if (pass.trim().equals(""))
            errors.rejectValue("pass", "obligatorio", "Campo 'Contraseña' obligatorio");
        if (name.trim().equals(""))
            errors.rejectValue("pass", "obligatorio", "Campo 'Nombre y apellidos' obligatorio");
        if (tlf.trim().equals(""))
            errors.rejectValue("pass", "obligatorio", "Campo 'Teléfono' obligatorio");
        if (email.trim().equals(""))
            errors.rejectValue("pass", "obligatorio", "Campo 'Email' obligatorio");

    }
}
