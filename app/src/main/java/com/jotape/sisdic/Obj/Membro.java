package com.jotape.sisdic.Obj;

import java.util.List;

/**
 * Created by João Bittencourt on 12/04/2017.
 */

public class Membro {

    String name;
    String email;
    String phone;
    int tarefasCompletadas;

    public Membro(String name, String email, String phone){
        this.name =  name;
        this.email = email;
        this.phone = phone;
    }

    Membro(){

    }

    public int getTarefasCompletadas() {
        return tarefasCompletadas;
    }

    public void setTarefasCompletadas(int tarefasCompletadas) {
        this.tarefasCompletadas = tarefasCompletadas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}

