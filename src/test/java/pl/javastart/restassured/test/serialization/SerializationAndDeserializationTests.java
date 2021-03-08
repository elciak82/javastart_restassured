package pl.javastart.restassured.test.serialization;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pl.javastart.main.pojo.Category;
import pl.javastart.main.pojo.Pet;
import pl.javastart.main.pojo.Tag;

import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class SerializationAndDeserializationTests {

    @BeforeClass
    public void setupConfiguration() {
        RestAssured.baseURI = "http://swaggerpetstore.przyklady.javastart.pl";
        RestAssured.basePath = "v2";
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

        Pet actualPet = given().log().all().body(pet).contentType("application/json")
                .when().post("pet")
                .then().log().all().statusCode(200)
                .extract().as(Pet.class); //deserializacja potrzeban do asercji poniżej

        assertEquals(actualPet.getId(), pet.getId(), "Pet id");
        assertEquals(actualPet.getCategory().getId(), pet.getCategory().getId(), "Category id");
        assertEquals(actualPet.getCategory().getName(), pet.getCategory().getName(), "Category name");
        assertEquals(actualPet.getPhotoUrls().get(0), pet.getPhotoUrls().get(0), "Photo URL");
        assertEquals(actualPet.getTags().get(0).getId(), pet.getTags().get(0).getId(), "Pet tag id");
        assertEquals(actualPet.getTags().get(0).getName(), pet.getTags().get(0).getName(), "Pet tag name");
        assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status");
    }

    @Test
    public void givenExistingPetIdWhenGetPetThenReturnPetTest() {

        Category category = new Category();
        category.setId(2);
        category.setName("kot");

        Tag tag = new Tag();
        tag.setId(0);
        tag.setName("cat-category");

        Pet pet = new Pet();
        pet.setId(222);
        pet.setCategory(category);
        pet.setPhotoUrls(Collections.singletonList("https://photos.com/cat2.jpg"));
        pet.setTags(Collections.singletonList(tag));
        pet.setStatus("available");

        given().log().all().body(pet).contentType("application/json")
                .when().post("pet")
                .then().log().all().statusCode(200)
                .extract().as(Pet.class);



        Pet actualPet = given().log().method().log().uri()
                .pathParam("petId", pet.getId())
                .when().get("pet/{petId}")
                .then().log().all().statusCode(200)
                .extract().as(Pet.class);

        assertEquals(actualPet.getId(), pet.getId(), "Pet id");
        assertEquals(actualPet.getCategory().getId(), pet.getCategory().getId(), "Category id");
        assertEquals(actualPet.getCategory().getName(), pet.getCategory().getName(), "Category name");
        assertEquals(actualPet.getPhotoUrls().get(0), pet.getPhotoUrls().get(0), "Photo URL");
        assertEquals(actualPet.getTags().get(0).getId(), pet.getTags().get(0).getId(), "Pet tag id");
        assertEquals(actualPet.getTags().get(0).getName(), pet.getTags().get(0).getName(), "Pet tag name");
        assertEquals(actualPet.getStatus(), pet.getStatus(), "Pet status");

    }
}
