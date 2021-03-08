package pl.javastart.restassured.test.tasks;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;

public class UserCreationTests {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "http://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void givenCorrectUserDataWhenCreateUserThenUserIsCreatedTest() {

        User user = new User();
        user.setId(445);
        user.setUsername("firstuser");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(1);

        given().contentType("application/json").body(user)
                .when().post("user")
                .then().statusCode(200);

        given().contentType("application/json").pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then().statusCode(200);
    }

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {

        User user = new User();
        user.setId(446);
        user.setUsername("secondUser");
        user.setFirstName("Dariusz");
        user.setLastName("Zabawny");
        user.setEmail("jestemZabawny@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(1);

        given().contentType("application/json").body(user)
                .when().post("user")
                .then().statusCode(200);

        given().contentType("application/json").pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then().statusCode(200);
    }
}
