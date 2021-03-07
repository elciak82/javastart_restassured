package pl.javastart.restassured.test.http.methods;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class BasicHttpMethodsTests {

    @Test
    public void givenPetWhenPostPetThenPetIsCreatedTest() {
        String pet = "{\n" +
                "  \"id\": 111,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"wolf\"\n" +
                "  },\n" +
                "  \"name\": \"wolfie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photos.com/wolf1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"wolf-category\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

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

        String pet = "{\n" +
                "  \"id\": 111,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"wolf\"\n" +
                "  },\n" +
                "  \"name\": \"wolfie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photos.com/wolf1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"wolf-category\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        pet = "{\n" +
                "  \"id\": 111,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Wilk\"\n" +
                "  },\n" +
                "  \"name\": \"wolfie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photos.com/wolf1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"wolf-category\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given().log().all().body(pet).contentType("application/json")
                .when().put("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);
    }

    @Test
    public void givenExistingPetIdWhenDeletingPetThenIsDeletedTest() {

        String pet = "{\n" +
                "  \"id\": 111,\n" +
                "  \"category\": {\n" +
                "    \"id\": 1,\n" +
                "    \"name\": \"Wilk\"\n" +
                "  },\n" +
                "  \"name\": \"wolfie\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"https://photos.com/wolf1.jpg\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"wolf-category\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        given().log().all().body(pet).contentType("application/json")
                .when().post("https://swaggerpetstore.przyklady.javastart.pl/v2/pet")
                .then().log().all().statusCode(200);

        given().log().all().contentType("application/json")
                .pathParam("petId", 111)
                .when().delete("https://swaggerpetstore.przyklady.javastart.pl/v2/pet/{petId}")
                .then().log().all().statusCode(200);
    }
}
