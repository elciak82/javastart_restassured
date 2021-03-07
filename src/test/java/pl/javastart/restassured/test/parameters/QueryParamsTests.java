package pl.javastart.restassured.test.parameters;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Arrays;
import java.util.Collections;
import static org.testng.Assert.assertTrue;

import static io.restassured.RestAssured.given;

public class QueryParamsTests {

    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {

        Category category = new Category();
        category.setId(0);
        category.setName("Ciekawe czy Bartek to przeczyta? :D");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("fun-category");

        Pet pet = new Pet();
        pet.setId(666);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/horse1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("sold");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        Pet[] statusPets = given().log().all().body(pet).contentType("application/json")
                .queryParam("status", pet.getStatus())
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/findByStatus")
                .then().log().all().statusCode(200).extract().as(Pet[].class);

        assertTrue(Arrays.asList(statusPets).size() > 0, "List of pets");
    }
}
