package br.com.zup.edu.petmanager.controller;

import br.com.zup.edu.petmanager.controller.request.PetRequest;
import br.com.zup.edu.petmanager.controller.request.TipoPetRequest;
import br.com.zup.edu.petmanager.model.Pet;
import br.com.zup.edu.petmanager.repository.PetRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CadastrarPetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper; // responsavel por serializar e deserializar jsons

    @Autowired
    private PetRepository petRepository;


    @BeforeEach
    void setUp() {
        this.petRepository.deleteAll();
    }

    @DisplayName("deve cadastrar pet valido")
    @Test
    void test1() throws Exception {

       PetRequest petRequest = new PetRequest("Mufasa", "Poodle", TipoPetRequest.CAO,
                LocalDate.of(2023, 12,12));

        String payload = mapper.writeValueAsString(petRequest);

        MockHttpServletRequestBuilder request =  post("/pets/{id}")
                .contentType(MediaType.APPLICATION_JSON).content(payload);

        mockMvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated()
                ).andExpect(MockMvcResultMatchers.redirectedUrlPattern("http://localhost/pets/*"));

        List<Pet> pet = petRepository.findAll();

        assertEquals(1, pet.size());

    }
}