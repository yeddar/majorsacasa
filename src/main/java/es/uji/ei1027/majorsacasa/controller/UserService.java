package es.uji.ei1027.majorsacasa.controller;

import es.uji.ei1027.majorsacasa.model.Usuario;
import org.springframework.stereotype.Component;


public interface UserService {
    Usuario getUserByNick(String nick);
}
