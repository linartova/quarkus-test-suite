package io.quarkus.ts.spring.data.rest;

import static org.hamcrest.core.Is.is;

import org.junit.jupiter.api.Test;

import io.quarkus.test.bootstrap.RestService;
import io.quarkus.test.scenarios.QuarkusScenario;
import io.quarkus.test.services.QuarkusApplication;

@QuarkusScenario
public class SpringDiIT {

    @Test
    public void testBeanExists() {
        app.given().get("/bean/beanExists").then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testGivenAccountServiceAutowiredToUserService() {
        app.given().get("/bean/givenAccountServiceAutowiredToUserService")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testGivenPersonDaoAutowiredToSpringPersonServiceBySetterInjection() {
        app.given().get("/bean/givenPersonDaoAutowiredToSpringPersonServiceBySetterInjection")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testCdiAndArcWayReturnTheSameBean() {
        app.given().get("/bean/cdiAndArcWayReturnTheSameBean")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testCdiAndArcInstanceWayReturnTheSameBean() {
        app.given().get("/bean/cdiAndArcInstanceWayReturnTheSameBean")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testCdiAndArcStringWayReturnTheSameBean() {
        app.given().get("/bean/cdiAndArcStringWayReturnTheSameBean")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @Test
    public void testBeanDeclaringClassMatch() {
        app.given().get("/bean/beanDeclaringClassMatch")
                .then()
                .statusCode(200)
                .body(is("true"));
    }

    @QuarkusApplication
    public static final RestService app = new RestService()
            .withProperty("quarkus.hibernate-orm.active", "false");

}
