package com.demo.soapempleados.service;

import com.demo.soapempleados.model.Empleado;
import com.demo.soapempleados.repository.EmpleadoRepository;
import jakarta.jws.WebService;

import java.util.List;

@WebService (endpointInterface = "com.demo.soapempleados.service.EmpleadoService")
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository){
        this.empleadoRepository=empleadoRepository;
    }

    @Override
    public List<Empleado> listarEmpleadosVsBD(){
        return empleadoRepository.listar();
    }

    @Override
    public List<Empleado> listarEmpleados(){
        return List.of(
                new Empleado(1,"12345678","Edgar", "Divar", "Av Los Angeles", "email@gmail.com", "123456789", "TI"),
                new Empleado(2,"12345678","Manuel", "Divar", "Av Los Angeles", "email@gmail.com", "123456789", "TI"),
                new Empleado(3,"12345678","Eliana", "Divar", "Av Los Angeles", "email@gmail.com", "123456789", "TI"),
                new Empleado(4,"12345678","Luiz", "Divar", "Av Los Angeles", "email@gmail.com", "123456789", "TI"),
                new Empleado(5,"12345678","Briyit", "Divar", "Av Los Angeles", "email@gmail.com", "123456789", "TI")
                );
    }


    @Override
    public Empleado registrarEmpleado(Empleado empleado){
        return empleadoRepository.insertar(empleado);
    }


}
