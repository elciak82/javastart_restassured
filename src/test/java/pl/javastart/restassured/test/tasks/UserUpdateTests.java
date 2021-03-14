package pl.javastart.restassured.test.tasks;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserUpdateTests extends TestBase {

    @Test
    public void givenCorrectUserDataWhenFirstNameLastNameAreUpdatedThenUserDataIsUpdatedTest() {

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder(); //Tworzymy instancję klasy RequestSpecBuilder
        requestSpecBuilder.setContentType("application/json"); // Dodajemy nagłówek ContentType
        RequestSpecification defaultSpecification = requestSpecBuilder.build(); // Przy pomocy metody build() tworzymy instancję RequestSpecification

        User user = new User();
        user.setId(446);
        user.setUsername("secondUser");
        user.setFirstName("Dariusz");
        user.setLastName("Zabawny");
        user.setEmail("jestemZabawny@test.com");
        user.setPassword("password");
        user.setPhone("+123456789");
        user.setUserStatus(1);

        ResponseSpecBuilder postResponseSpecBuilder = new ResponseSpecBuilder(); // dla post i put
        postResponseSpecBuilder
                .expectBody("code", equalTo(200))
                .expectBody("type", equalTo("unknown"))
                .expectBody("message", equalTo("446"))
                .expectStatusCode(200);
        ResponseSpecification userCreationResponseSpecification = postResponseSpecBuilder.build();

        given().spec(defaultSpecification)
                .body(user)
                .when().post("user")
                .then().assertThat().spec(userCreationResponseSpecification);

        user.setFirstName("Ewelinka");
        user.setLastName("BlaBla");

        given().spec(defaultSpecification)
                .pathParam("username", user.getUsername())
                .body(user)
                .when().put("user/{username}")
                .then().assertThat().spec(userCreationResponseSpecification);

        ResponseSpecBuilder updateResponseSpecBuilder = new ResponseSpecBuilder();
        updateResponseSpecBuilder
                .expectBody("id", equalTo(446))
                .expectBody("username", equalTo("secondUser"))
                .expectBody("firstName", equalTo("Ewelinka"))
                .expectBody("lastName", equalTo("BlaBla"))
                .expectBody("email", equalTo("jestemZabawny@test.com"))
                .expectBody("password", equalTo("password"))
                .expectBody("phone", equalTo("+123456789"))
                .expectBody("userStatus", equalTo(1))
                .expectStatusCode(200);
        ResponseSpecification getResponseSpecification = updateResponseSpecBuilder.build();

        given().spec(defaultSpecification)
                .pathParam("username", user.getUsername())
                .when().get("user/{username}")
                .then().assertThat().spec(getResponseSpecification);
    }
}
