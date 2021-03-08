package pl.javastart.restassured.test.parameters;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Arrays;
import java.util.Collections;
import static org.testng.Assert.assertTrue;

import static io.restassured.RestAssured.given;

public class QueryParamsTests {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "http://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void givenExistingPetWithStatusSoldWhenGetPetWithSoldStatusThenPetWithStatusIsReturnedTest() {

        Category category = new Category();
        category.setId(0);
        category.setName("Fish");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("fish-category");

        Pet pet = new Pet();
        pet.setId(666);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/horse1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("sold");

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

        Pet[] statusPets = given().body(pet).contentType("application/json").queryParam("status", pet.getStatus())
                .when().get("pet/findByStatus")
                .then().statusCode(200).extract().as(Pet[].class);

        assertTrue(Arrays.asList(statusPets).size() > 0, "List of pets");
    }
}
