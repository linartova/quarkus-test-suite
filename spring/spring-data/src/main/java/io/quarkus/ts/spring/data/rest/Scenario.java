package io.quarkus.ts.spring.data.rest;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableBean;
import io.quarkus.ts.spring.data.di.*;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/greeting")
public class Scenario {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public boolean beanExists() {
        UserService userService = CDI.current().select(UserService.class).get();
        return userService != null;
    }

    @GET
    public boolean givenAccountServiceAutowiredToUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
        UserService userService = CDI.current().select(UserService.class).get();
        return userService.getAccountService() != null;
    }

    @GET
    public boolean givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection_WhenSpringPersonServiceRetrievedFromContext_ThenPersonDaoInitializedByTheSetter() {
        SpringPersonService personService = CDI.current().select(SpringPersonService.class).get();
        return personService != null && personService.getPersonDao() != null;
    }


    public boolean beanDeclaringClassMatch() {
        final InjectableBean<Object> audioBookServiceGenerator = Arc.container().instance("audioBookServiceGenerator")
                .getBean();
        final InjectableBean<Object> bookServiceGenerator = Arc.container().instance("bookServiceGenerator").getBean();
        return audioBookServiceGenerator != null && bookServiceGenerator != null &&
                SpringBeansConfig.class == audioBookServiceGenerator.getBeanClass() &&
                SpringMainConfig.class == bookServiceGenerator.getBeanClass();
    }
}
