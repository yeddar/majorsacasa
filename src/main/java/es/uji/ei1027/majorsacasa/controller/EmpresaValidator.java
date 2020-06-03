package es.uji.ei1027.majorsacasa.controller;


import es.uji.ei1027.majorsacasa.model.Empresa;
import es.uji.ei1027.majorsacasa.model.Voluntario;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class EmpresaValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Empresa.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        // Checks volunteer
        Empresa emp = (Empresa) o;


        // telefono
        boolean valido = true;
        for (int i = 0; i < emp.getTlf().length(); i++){
            if (!Character.isDigit(emp.getTlf().charAt(i))) {
                valido = false;
                break;
            }
        }

        if(emp.getTlf().length() != 9 || !valido)
            errors.rejectValue("tlf", "inválido", "Teléfono inválido.");

        // email
        if (!emp.getEmail().matches("[-\\w\\.]+@\\w+\\.\\w+"))
            errors.rejectValue("email", "inválido", "Dirección de correo inválida.");

        // nick
        if(!emp.getNick().matches("^[A-Za-z][A-Za-z0-9]*$"))
            errors.rejectValue("nick", "inválido", "El nick no puede contener espacios en blanco");


    }
}
