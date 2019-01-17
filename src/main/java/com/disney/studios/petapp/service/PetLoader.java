package com.disney.studios.petapp.service;

import com.disney.studios.petapp.domain.internal.Dog;
import com.disney.studios.petapp.domain.internal.DogPicture;
import com.disney.studios.petapp.repository.DogPictureRepository;
import com.disney.studios.petapp.repository.DogRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.slf4j.LoggerFactory.getLogger;


@Component
public class PetLoader implements InitializingBean {

    private static final Logger LOGGER = getLogger(PetLoader.class);

    @Value("classpath:data/labrador.txt") private Resource labradors;
    @Value("classpath:data/pug.txt") private Resource pugs;
    @Value("classpath:data/retriever.txt") private Resource retrievers;
    @Value("classpath:data/yorkie.txt") private Resource yorkies;

    @Autowired private DogRepository dogRepo;
    @Autowired private DogPictureRepository dogPictureRepo;

    /**
     * Load the different breeds into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load the breeds.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        loadBreed("Labrador", labradors);
        loadBreed("Pug", pugs);
        loadBreed("Retriever", retrievers);
        loadBreed("Yorkie", yorkies);
    }

    /**
     * Reads the list of dogs in a category and (eventually) add them to the data source.
     * @param breed The breed that we are loading.
     * @param source The file holding the breeds.
     * @throws IOException In case things go horribly, horribly wrong.
     */
    private void loadBreed(String breed, Resource source) throws IOException {

        LOGGER.info("Loading breed {}", breed);
        Dog d = dogRepo.save(new Dog(breed));

        try (BufferedReader br = new BufferedReader(new InputStreamReader(source.getInputStream()))) {
            String url;
            while ((url = br.readLine()) != null) {
                try {
                    LOGGER.info("Loading picture url {}", url);
                    DogPicture picture = new DogPicture(url);
                    picture.setDog(d);
                    dogPictureRepo.save(picture);
                } catch (Exception ignored) {
                    LOGGER.error("Unable to load picture url {}", url);
                }
            }
        }
    }
}
