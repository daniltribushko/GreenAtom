package com.example.GreenAtom.exceptions;

/**
 * @author Tribushko Danil
 */
public class RoleByNameNotFoundException extends AppException{
    public RoleByNameNotFoundException(String name) {
        super(404, "Role: " + name + " not found");
    }
}
