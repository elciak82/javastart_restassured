package pl.javastart.restassured.test.tasks;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;

public class UserUpdateTests {

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

        given().log().all()
                .contentType("application/json")
                .body(user)
                .when().post("http://swaggerpetstore.przyklady.javastart.pl/v2/user")
                .then().log().all().statusCode(200);

        user.setFirstName("Ewelinka");
        user.setLastName("BlaBla");

        given().log().all()
                .contentType("application/json")
                .pathParam("username", user.getUsername())
                .body(user)
                .when().post("http://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);

        given().log().all()
                .contentType("application/json")
                .pathParam("username", user.getUsername())
                .when().get("http://swaggerpetstore.przyklady.javastart.pl/v2/user/{username}")
                .then().log().all().statusCode(200);
    }
}
