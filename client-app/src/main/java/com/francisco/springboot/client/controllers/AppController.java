package com.francisco.springboot.client.controllers;

import com.francisco.springboot.client.models.Message;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class AppController {

    //Hacemos los endpoints protegidos

    @GetMapping("/list") //Este endpoint sería para leer (cualquier usuario y administrador)
    public List<Message> list(){
        return Collections.singletonList(new Message("Test List"));
    }

    @PostMapping("/create") //Este endpoint sería para escribir (Solo los administradores)
    public Message create(@RequestBody Message message){
        System.out.println("Mensaje guardado: " + message);
        return message;
    }

    //Con este metodo obtenemos el codigo de autorizacion
    @GetMapping("/authorized")
    public Map<String, String> authorizad(@RequestParam String code) {  //el parametro se debe llamar si o si "code"
        return Collections.singletonMap("code", code);
    }
}
