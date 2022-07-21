package com.techsolvi;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Verticle;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        System.out.println("This is the MainVerticle of the project");

        initializeServerVerticle()
            .compose(this::initializeUserVerticle)
            .onComplete( handler -> {
                if(handler.succeeded()){
                    System.out.println("Main Verticle Deployment Successful");
                    startPromise.complete();
                }else{
                    System.out.println("Main Verticle Deployment Failed");
                    startPromise.fail(handler.cause().getMessage());
                }
            });
    }

    public Future<Void> initializeServerVerticle(){

        Verticle serverVerticle = new ServerVerticle();
        vertx.deployVerticle(serverVerticle)
                .compose( string -> {
                    System.out.println("[initializeServerVerticle] :"+string);
                    return Future.future(p->p.complete());
                });

        return Future.future(p->p.complete());
    }

    public Future<Void> initializeUserVerticle(Void v){

        Verticle userVerticle = new UserVerticle();
        vertx.deployVerticle(userVerticle)
                .compose( string -> {
                    System.out.println("[initializeUserVerticle] :"+string);
                    return Future.future(p->p.complete());
                });

        return Future.future(p->p.complete());
    }

}
