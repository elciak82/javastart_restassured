package pl.javastart.restassured.test.http.methods;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {

    @BeforeClass
    public void setupConfiguration() { //wykona kod w podanej pod nią metodzie tylko raz, przed metodami testowymi
        RestAssured.baseURI = "https://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); // można usunąc .log().all() po given() i then(). Uruchamiając teraz dowolny test dalej logujemy żądanie i odpowiedź.
    }

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

        given().body(pet).contentType("application/json")
                .when().post("pet") //!!
                .then().statusCode(200);
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {
        given().pathParam("petId", 1)
                .when().get("pet/{petId}")
                .then().statusCode(200);
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

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

        pet.setName("wilk");

        given().body(pet).contentType("application/json")
                .when().put("pet")
                .then().statusCode(200);
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

        given().body(pet).contentType("application/json")
                .when().post("pet")
                .then().statusCode(200);

        given().contentType("application/json")
                .pathParam("petId", pet.getId())
                .when().delete("pet/{petId}")
                .then().statusCode(200);
    }
}
