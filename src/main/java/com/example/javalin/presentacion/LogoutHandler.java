package com.example.javalin.presentacion;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class LogoutHandler implements Handler {

  public void handle(@NotNull Context context) throws Exception{

    String idSesion = context.header("Authorization");

    if(idSesion != null && SesionManager.get().contieneSesion(idSesion)){
      SesionManager.get().eliminar(idSesion);
      context.status(200).result("Sesion se ha cerrado");
    }
    else{
      context.status(401).result("La seson no es correcta o ya se cerro la sesion");
    }

  }
}
