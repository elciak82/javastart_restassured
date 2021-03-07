package pl.javastart.restassured.test.http.methods;

import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class BasicHttpMethodsTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("wolf");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("wolf-category");

        Pet pet = new Pet();
        pet.setId(111);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/wolf1.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
        given().log().method().log().uri()
                .pathParam("petId", 1)
                .when().get("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200);
    }

    @Test
    public void givenExistingPetWhenUpdatePetNameThenPetIsChangedTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("wolf");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("wolf-category");

        Pet pet = new Pet();
        pet.setId(111);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/wolf1.jpg"));
        pet.setName("wolf");
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        pet.setName("wilk");

        given().log().all().body(pet).contentType("application/json")
                .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);
    }

    @Test
    public void givenExistingPetIdWhenDeletingPetThenIsDeletedTest() {

        Category category = new Category();
        category.setId(1);
        category.setName("wolf");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("wolf-category");

        Pet pet = new Pet();
        pet.setId(111);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/wolf1.jpg"));
        pet.setName("wilk");
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        given().log().all().contentType("application/json")
                .pathParam("petId", pet.getId())
                .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200);
    }
}
