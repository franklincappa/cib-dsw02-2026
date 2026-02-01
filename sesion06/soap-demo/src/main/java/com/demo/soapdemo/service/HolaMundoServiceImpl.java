package com.demo.soapdemo.service;

import jakarta.jws.WebService;

@WebService (endpointInterface = "com.demo.soapdemo.service.HolaMundoService" )
public class HolaMundoServiceImpl implements HolaMundoService {
    @Override
    public String holaMundo(){
        return "Hola Mundo desde un servicio SOAP";
    }

    @Override
    public String holaMundov2(){
        return "SOAP V2";
    }

    @Override
    public int sumar(int a, int b){
        return a+b;
    }
}
