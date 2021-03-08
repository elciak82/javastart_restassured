package pl.javastart.restassured.test.tasks;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

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
        user.setUsername("pierwszy");
        user.setFirstName("Krzysztof");
        user.setLastName("Kowalski");
        user.setEmail("krzysztof@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(1);

        given().contentType("application/json").body(user)
                .when().post("user")
                .then()
                .assertThat().body("code", equalTo(200))
                .assertThat().body("type", equalTo("unknown"))
                .assertThat().body("message", equalTo("445"));

        given().contentType("application/json").pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then()
                .assertThat().body("id", equalTo(445))
                .assertThat().body("username", equalTo("pierwszy"))
                .assertThat().body("firstName", equalTo("Krzysztof"))
                .assertThat().body("lastName", equalTo("Kowalski"))
                .assertThat().body("email", equalTo("krzysztof@test.com"))
                .assertThat().body("password", equalTo("password"))
                .assertThat().body("phone", equalTo("+123456789"))
                .assertThat().body("userStatus", equalTo(1));
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
