package io.quarkus.ts.spring.data.rest;

import io.quarkus.ts.spring.data.di.*;
import jakarta.enterprise.inject.spi.CDI;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.quarkus.test.bootstrap.RestService;
import io.quarkus.test.scenarios.QuarkusScenario;
import io.quarkus.test.services.QuarkusApplication;
import io.restassured.response.Response;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusScenario
public class SpringDiIT {

    private static Stream<Class<?>> beanClassProvider() {
        return Stream.of(
                Account.class,
                AccountService.class,
                AudioBookService.class,
                BookService.class,
                PersonDao.class,
                SpringPersonService.class,
                UserService.class);
    }

    private static Stream<Arguments> beanNameAndClassProvider() {
        return Stream.of(
                Arguments.of("account", Account.class),
                Arguments.of("accountServiceImpl", AccountService.class),
                Arguments.of("audioBookServiceGenerator", AudioBookService.class),
                Arguments.of("bookServiceGenerator", BookService.class),
                Arguments.of("personDaoImpl", PersonDao.class),
                Arguments.of("springPersonService", SpringPersonService.class),
                Arguments.of("userService", UserService.class));
    }

    @Test
    public void test() {
        boolean ahoj = true;
        Response response = app.given()
                .when().get("/greeting")
                .then()
                .statusCode(HttpStatus.SC_OK).extract().response();
    }

    @QuarkusApplication
    public static final RestService app = new RestService()
            .withProperty("quarkus.hibernate-orm.active", "false");

}
