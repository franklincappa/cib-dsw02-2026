package com.demo.soapdemo.Config;

import com.demo.soapdemo.service.HolaMundoService;
import com.demo.soapdemo.service.HolaMundoServiceImpl;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CxfConfig {
    @Bean
    public HolaMundoService holaMundoService(){
        return new HolaMundoServiceImpl();
    }
    @Bean
    public Endpoint holaMundoEndpoint(Bus bus, HolaMundoService service){
        EndpointImpl endpoint= new EndpointImpl(bus, service);
        endpoint.publish("/holamundo");
        return endpoint;
    }

}
