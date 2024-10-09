package com.my.labseq;

import com.codahale.metrics.health.HealthCheck;
import com.my.labseq.resources.labseqResource;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class sequenceApplication extends Application<sequenceConfiguration> {

    public static void main(final String[] args) throws Exception {
        new sequenceApplication().run(args);
    }

    @Override
    public String getName() {
        return "sequence";
    }

    @Override
    public void initialize(final Bootstrap<sequenceConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final sequenceConfiguration configuration,
                    final Environment environment) {
        
        //Adicionar o resource labseqResource.java
        environment.jersey().register(new labseqResource());
        
        //Remover o aviso do health check
        environment.healthChecks().register("Status",new HealthCheck() {
            @Override
            protected HealthCheck.Result check() throws Exception {
                return HealthCheck.Result.healthy();
            }
        });
    }

}
