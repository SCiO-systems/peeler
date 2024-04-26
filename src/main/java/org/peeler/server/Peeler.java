package org.peeler.server;


import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.main.Main;
import org.apache.camel.spi.PropertiesComponent;
import org.apache.camel.support.DefaultRegistry;
import org.peeler.routes.PeelerRoute;

public class Peeler extends Main {
    public static void main(String[] args) throws Exception {
        CamelContext camelContext = new DefaultCamelContext();
        Peeler peeler = new Peeler();


        PropertiesComponent properties = camelContext.getPropertiesComponent();
        properties.setLocation("classpath:/env.properties");
        System.out.println("hello world");

        DefaultRegistry registry = (DefaultRegistry) camelContext.getRegistry();


        try {
            camelContext.addRoutes(new PeelerRoute());
            camelContext.start();
            //ProducerTemplate template = camelContext.createProducerTemplate();
            peeler.run();
            // ((DefaultCamelContext) camelContext).startAllRoutes();
        } catch (Exception e) {
            System.out.println("Peeler failed: {}" + e.getMessage());
        }

    }
}