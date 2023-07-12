package io.quarkus.ts.spring.data.rest;

import java.util.List;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import io.quarkus.arc.Arc;
import io.quarkus.arc.InjectableBean;
import io.quarkus.ts.spring.data.di.Account;
import io.quarkus.ts.spring.data.di.AccountService;
import io.quarkus.ts.spring.data.di.AudioBookService;
import io.quarkus.ts.spring.data.di.BookService;
import io.quarkus.ts.spring.data.di.PersonDao;
import io.quarkus.ts.spring.data.di.SpringBeansConfig;
import io.quarkus.ts.spring.data.di.SpringMainConfig;
import io.quarkus.ts.spring.data.di.SpringPersonService;
import io.quarkus.ts.spring.data.di.UserService;

@Path("/bean")
public class BeanResource {

    private static List<String> beanNameProvider() {
        return List.of(
                "account",
                "accountServiceImpl",
                "audioBookServiceGenerator",
                "bookServiceGenerator",
                "personDaoImpl",
                "springPersonService",
                "userService");
    }

    private static List<Class<?>> beanClassProvider() {
        return List.of(
                Account.class,
                AccountService.class,
                AudioBookService.class,
                BookService.class,
                PersonDao.class,
                SpringPersonService.class,
                UserService.class);
    }

    @GET
    @Path("/beanExists")
    @Produces("text/plain")
    public boolean beanExists() {
        for (Class<?> beanClass : beanClassProvider()) {
            final Object bean = CDI.current().select(beanClass).get();
            if (bean == null) {
                return false;
            }
        }
        return true;
    }

    @GET
    @Path("/givenAccountServiceAutowiredToUserService")
    @Produces("text/plain")
    public boolean givenAccountServiceAutowiredToUserService_WhenGetAccountServiceInvoked_ThenReturnValueIsNotNull() {
        UserService userService = CDI.current().select(UserService.class).get();
        return userService.getAccountService() != null;
    }

    @GET
    @Path("/givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection")
    @Produces("text/plain")
    public boolean givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection_WhenSpringPersonServiceRetrievedFromContext_ThenPersonDaoInitializedByTheSetter() {
        SpringPersonService personService = CDI.current().select(SpringPersonService.class).get();
        return personService != null && personService.getPersonDao() != null;
    }

    @GET
    @Path("/cdiAndArcWayReturnTheSameBean")
    @Produces("text/plain")
    public boolean cdiAndArcWayReturnTheSameBean() {
        for (Class<?> bean : beanClassProvider()) {
            Object bean1 = CDI.current().select(bean).get();
            Object bean2 = Arc.container().select(bean).get();
            if (bean1 == null || bean2 == null) {
                return false;
            }
            if (!bean1.equals(bean2)) {
                return false;
            }
        }
        return true;
    }

    @GET
    @Path("/cdiAndArcInstanceWayReturnTheSameBean")
    @Produces("text/plain")
    public boolean cdiAndArcInstanceWayReturnTheSameBean() {
        for (Class<?> bean : beanClassProvider()) {
            Object bean1 = CDI.current().select(bean).get();
            Object bean2 = Arc.container().instance(bean).get();
            if (bean1 == null || bean2 == null) {
                return false;
            }
            if (!bean1.equals(bean2)) {
                return false;
            }
        }
        return true;
    }

    @GET
    @Path("/cdiAndArcStringWayReturnTheSameBean")
    @Produces("text/plain")
    public boolean cdiAndArcStringWayReturnTheSameBean() {
        for (int i = 0; i < beanClassProvider().size(); i++) {
            Object bean1 = CDI.current().select(beanClassProvider().get(i)).get();
            Object bean2 = Arc.container().instance(beanNameProvider().get(i)).get();
            if (bean1 == null || bean2 == null) {
                return false;
            }
            if (!bean1.equals(bean2)) {
                return false;
            }
        }
        return true;
    }

    @GET
    @Path("/beanDeclaringClassMatch")
    @Produces("text/plain")
    public boolean beanDeclaringClassMatch() {
        final InjectableBean<Object> audioBookServiceGenerator = Arc.container().instance("audioBookServiceGenerator")
                .getBean();
        final InjectableBean<Object> bookServiceGenerator = Arc.container().instance("bookServiceGenerator").getBean();
        if (audioBookServiceGenerator == null || bookServiceGenerator == null) {
            return false;
        }
        if (!(SpringBeansConfig.class).equals(audioBookServiceGenerator.getBeanClass())) {
            return false;
        }
        return (SpringMainConfig.class).equals(bookServiceGenerator.getBeanClass());
    }
}
