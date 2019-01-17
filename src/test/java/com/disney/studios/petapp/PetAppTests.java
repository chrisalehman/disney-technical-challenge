package com.disney.studios.petapp;

import com.disney.studios.petapp.domain.external.DogPicture;
import com.disney.studios.petapp.domain.external.Vote;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureTestDatabase(replace = NONE)
@ContextConfiguration(initializers = { PetAppTests.Initializer.class })
public class PetAppTests {

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer =
        (PostgreSQLContainer) new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres")
            .withStartupTimeout(Duration.ofSeconds(300));

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(@NotNull ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(context.getEnvironment());
        }
    }

    /** GET PICTURES **/

    @Test
    public void testGetAllPictures() {

        Map<String,List<DogPicture>> response =
            given().
                when().
                get("/pictures").
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                path("picturesByBreed");

        assertTrue(response.keySet().size() > 0);
    }

    /** GET PICTURES BY BREED */

    @Test
    public void testGetPicturesByBreed_Labrador() {

        List<DogPicture> response =
            given().
                queryParam("breed", "Labrador").
                when().
                get("/pictures").
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                path("pictures");

        assertTrue(response.size() > 0);
    }

    @Test
    public void testGetPicturesByBreed_unknown_resultEmpty() {

        List<DogPicture> response =
            given().
                queryParam("breed", "xxx").
                when().
                get("/pictures").
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                path("pictures");

        assertTrue(response.isEmpty());
    }

    @Test
    public void testGetPicturesByBreed_unrecognizedParam() {

        Map<String,List<DogPicture>> response =
            given().
                when().
                get("/pictures").
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                path("picturesByBreed");

        assertTrue(response.keySet().size() > 0);
    }

    /** GET PICTURE BY ID **/

    @Test
    public void testGetPictureById() {

        DogPicture picture =
            given().
                when().
                get("/pictures/{id}", 1L).
                then().
                statusCode(200).
                contentType(JSON).
                extract().response().as(DogPicture.class);

        assertNotNull(picture);
        assertEquals(picture.getId(), (Long) 1L);
    }

    @Test
    public void testGetPictureById_notFound() {
        given().
            when().
            get("/picture/{id}", Integer.MAX_VALUE).
            then().
            statusCode(404).
            contentType(JSON);
    }

    /** VOTE **/

    @Test
    public void voteForPicture_upVoteAndDownVote() {

        Vote request = new Vote();
        request.setClientName("test_client1");
        request.setDogPictureId(1L);
        request.setVoteUp(true);

        // vote up
        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(200);

        // picture has one vote
        DogPicture picture =
            given().
                when().
                get("/pictures/{id}", 1L).
                then().
                statusCode(200).
                contentType(JSON).
                extract().response().as(DogPicture.class);

        assertNotNull(picture);
        assertEquals(picture.getId(), (Long) 1L);
        assertEquals(picture.getVotes(), (Integer) 1);

        // vote down
        request.setVoteUp(false);
        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(200);

        // picture has zero votes
        picture =
            given().
                when().
                get("/pictures/{id}", 1L).
                then().
                statusCode(200).
                contentType(JSON).
                extract().response().as(DogPicture.class);

        assertNotNull(picture);
        assertEquals(picture.getId(), (Long) 1L);
        assertEquals(picture.getVotes(), (Integer) 0);
    }

    public void voteForPictures_validateFetchSortOrder() {

        Vote request = new Vote();
        request.setClientName("test_client2");
        request.setDogPictureId(3L);
        request.setVoteUp(true);

        // up vote picture 1
        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(200).
            body("id", Matchers.any(Integer.class));

        request.setClientName("test_client2");

        // up vote picture 2
        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(200).
            body("id", Matchers.any(Integer.class));

        // fetch Labrador pictures
        List<DogPicture> response =
            given().
                queryParam("breed", "Labrador").
                when().
                get("/pictures").
                then().
                statusCode(200).
                contentType(JSON).
                extract().
                path("pictures");

        // assert sorted in desc order by votes
        assertTrue(response.size() > 0);
        assertTrue(response.get(0).getVotes() > response.get(1).getVotes());
    }

    @Test
    public void voteForPicture_duplicateVoteNoOp() throws Exception {

        Vote request = new Vote();
        request.setClientName("test_client1");
        request.setDogPictureId(2L);
        request.setVoteUp(true);

        given().
            contentType(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(200);

        given().
            contentType(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(400).
            body("message", Matchers.equalTo("Only one vote allowed per client and picture"));


        // fetch picture
        DogPicture picture =
            given().
                when().
                get("/pictures/{id}", 2L).
                then().
                statusCode(200).
                contentType(JSON).
                extract().response().as(DogPicture.class);

        assertNotNull(picture);
        assertEquals(picture.getId(), (Long) 2L);
        assertEquals(picture.getVotes(), (Integer) 1);
    }

    @Test
    public void voteForPicture_missingRequiredParams() {

        Vote request = new Vote();
        request.setClientName("test_client99");
        request.setDogPictureId(1L);

        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(400).
            // TODO create a better error message
            body("message", Matchers.equalTo("Validation failed for object='vote'. Error count: 1"));
    }

    @Test
    public void voteForPicture_clientNotFound() {

        Vote request = new Vote();
        request.setClientName("test_client99");
        request.setDogPictureId(1L);
        request.setVoteUp(true);

        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(404);
    }

    @Test
    public void voteForPicture_pictureNotFound() {

        Vote request = new Vote();
        request.setClientName("test_client1");
        request.setDogPictureId(Long.MAX_VALUE);
        request.setVoteUp(true);

        given().
            contentType(ContentType.JSON).
            accept(ContentType.JSON).
            body(request).
            when().
            post("/votes").
            then().
            statusCode(404);
    }
}