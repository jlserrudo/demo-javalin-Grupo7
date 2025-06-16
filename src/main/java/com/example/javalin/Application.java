package com.example.javalin;

import com.example.javalin.presentacion.GetMascotaIdHandler;
import com.example.javalin.presentacion.GetMascotasHandler;
import com.example.javalin.presentacion.LoginHandler;
import com.example.javalin.presentacion.LogoutHandler;
import com.example.javalin.presentacion.PostMascotaHandler;
import com.example.javalin.presentacion.GetPerfilSesionHandler;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
                    javalinConfig.plugins.enableCors(cors -> {
                        cors.add(it -> it.anyHost());
                    }); // para poder hacer requests de un dominio a otro

                    javalinConfig.staticFiles.add("/"); //recursos estaticos (HTML, CSS, JS, IMG)
                }
  
            )
            .get("/", ctx -> ctx.result("Hello World"))
            .start(8080);
        /*
        Javalin app = Javalin.create()
                .get("/", ctx -> ctx.result("Hello World"))
                .start(4567);
        */
        app.get("/api/mascotas", new GetMascotasHandler());
        app.get("/api/mascotas/{id}", new GetMascotaIdHandler());
        app.post("/api/mascotas", new PostMascotaHandler());

        app.get("/api/mis-datos", new GetPerfilSesionHandler());
        app.post("/api/login", new LoginHandler());
        app.post("/api/logout", new LogoutHandler()); // agrego un handle para desloguear la session

        app.exception(IllegalArgumentException.class, (e, ctx) -> {
            //tratar excepcion
        });


    }


}
