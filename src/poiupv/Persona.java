/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poiupv;

import model.User;


/**
 *
 * @author spele
 */
public class Persona {
    private static Persona instance;
    private User personaAct;
    
    private Persona() {} // constructor privado

    public static Persona getInstance() {
        if (instance == null) {
            instance = new Persona();
        }
        return instance;
    }

    public User getUser() {
        return personaAct;
    }

    public void setUser(User user) {
        personaAct = user;
    }
}
