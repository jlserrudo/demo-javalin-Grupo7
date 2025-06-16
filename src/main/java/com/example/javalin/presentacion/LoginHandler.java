package com.example.javalin.presentacion;

import com.example.javalin.modelo.Dueño;
import com.example.javalin.persistencia.RepositorioDueños;
import com.example.javalin.presentacion.dto.LoginRequest;
import com.example.javalin.presentacion.dto.LoginResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class LoginHandler implements Handler {

    private final RepositorioDueños repoDueños;

    public LoginHandler() {
        this.repoDueños = new RepositorioDueños();
    }

    @Override
    public void handle(@NotNull Context context) throws Exception {
        //validamos user/pass y buscamos datos de ese usuario para agregar en la sesión

        LoginRequest loginRequest = context.bodyAsClass(LoginRequest.class);

        //valido que en JSON el usuario y contraseña no sean nulos al hacer el POST
        if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank() ||
            loginRequest.getPassword() == null || loginRequest.getPassword().isBlank()){
            context.status(400).result("Los campos son obligatorios");
            return;
        }

        //Dueño dueño = repoDueños.obtenerJose(); //hardcode: siempre loguea a Jose
        //System.out.println("Login: " + loginRequest);
        //System.out.println("Login: " + dueño);

        // busca en el repositorio de usuarios, el usuario con su User y contraseña y devuelve el dueño
        Dueño dueño = repoDueños.buscarPorNombreYPassword(loginRequest.getUsername(),loginRequest.getPassword());
        // verifica que el dueño no sea null
        if (dueño == null){
            context.status(400).result("El Usuario y la contraseña no son correctas");
            return;
        }

        SesionManager sesionManager = SesionManager.get();
        String idSesion = sesionManager.crearSesion("dueño", dueño);

//        sesionManager.agregarAtributo(idSesion, "fechaInicio", new Date());
//        sesionManager.agregarAtributo(idSesion, "rol", repoRoles.getByUser(idUser));

        context.json(new LoginResponse(idSesion));

    }

}
