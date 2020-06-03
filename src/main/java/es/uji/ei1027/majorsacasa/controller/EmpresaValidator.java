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
            errors.rejectValue("nick", "inválido", "El nick no puede contener espacios en blanco.");

        // nif
        if(!isNifNumValid(emp.getNif()))
            errors.rejectValue("nif", "inválido", "El NIF no es válido.");


    }

    public static boolean isNifNumValid(String nif){
        //Si el largo del NIF es diferente a 9, acaba el método.
        if (nif.length()!=9){
            return false;
        }

        String secuenciaLetrasNIF = "TRWAGMYFPDXBNJZSQVHLCKE";
        nif = nif.toUpperCase();

        //Posición inicial: 0 (primero en la cadena de texto).
        //Longitud: cadena de texto menos última posición. Así obtenemos solo el número.
        String numeroNIF = nif.substring(0, nif.length()-1);

        //Si es un NIE reemplazamos letra inicial por su valor numérico.
        numeroNIF = numeroNIF.replace("X", "0").replace("Y", "1").replace("Z", "2");

        //Obtenemos la letra con un char que nos servirá también para el índice de las secuenciaLetrasNIF
        char letraNIF = nif.charAt(8);
        int i = Integer.parseInt(numeroNIF) % 23;
        return letraNIF == secuenciaLetrasNIF.charAt(i);
    }

}
