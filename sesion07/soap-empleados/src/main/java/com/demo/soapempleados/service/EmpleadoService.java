package com.demo.soapempleados.service;

import com.demo.soapempleados.model.Empleado;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

import java.util.List;

@WebService
public interface EmpleadoService {
    @WebMethod
    List<Empleado> listarEmpleados();

    @WebMethod
    List<Empleado> listarEmpleadosVsBD();

    @WebMethod
    Empleado registrarEmpleado(Empleado empleado);

}
