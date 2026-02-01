package com.demo.soapempleados.config;

import com.demo.soapempleados.service.EmpleadoService;
import com.demo.soapempleados.service.EmpleadoServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {
    @Bean
    public EmpleadoService empleadoService(){
        return new EmpleadoServiceImpl();
    }

    @Bean
    public Endpoint empleadosEndpoint(Bus bus, EmpleadoService empleadoService){
        EndpointImpl endpoint= new EndpointImpl(bus, empleadoService);
        endpoint.publish("/empleados");
        return endpoint;
    }
}
