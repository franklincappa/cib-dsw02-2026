package com.demo.soapdemo.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
public interface HolaMundoService {
    @WebMethod
    String holaMundo();

    @WebMethod
    String holaMundov2();

    @WebMethod
    int sumar (int a, int b);
}
