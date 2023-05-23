package com.vannou.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.vannou.test.controller.FiremanController;
import com.vannou.test.entity.Fire;
import com.vannou.test.entity.Fireman;
import com.vannou.test.repository.FireRepository;
import com.vannou.test.repository.FiremanRepository;



@WebMvcTest(FiremanController.class)
class ControllersTests {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	FiremanRepository firemanRepository;

    @MockBean
	FireRepository fireRepository;

	@Test
	void testGetVeteranSimple() throws Exception {

		var fireman = mock(Fireman.class);
		when(fireman.getId()).thenReturn(1L);
		when(fireman.getName()).thenReturn("champion");
		when(firemanRepository.getVeteran()).thenReturn(Optional.of(fireman));

		mockMvc.perform(get("/fireman/veteran"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(fireman.getId()))
				.andExpect(jsonPath("$.name").value("champion"));
	}

    @Test   //test pas de veteran challenge 1
	void testGetVeteranEmpty() throws Exception {
		
		when(firemanRepository.getVeteran()).thenReturn(Optional.empty());
		mockMvc.perform(get("/fireman/veteran")).andExpect(status().isNotFound());
	}

    @Test   //Challenge #2 : statistiques
	void testGetFiremanStatistiques() throws Exception {
		
        List<Fire> fires = new ArrayList<Fire>();
		int nbFireCreated = 20;
        for (int i = 0; i < nbFireCreated; i++) {
            int severity = new Random().nextInt(10) + 1;
            Instant date = Instant.now();
            Fire fire = new Fire(severity, date);
            fireRepository.saveAndFlush(fire);
            fires.add(fire);
        }
        when(fireRepository.findAll()).thenReturn(fires);


        List<Fireman> firemans = new ArrayList<Fireman>();
        int nbFiremanCreated = 6;
        for (int i = 0; i < nbFiremanCreated; i++) {
            Fireman fireman = new Fireman("fireman"+i);
            firemans.add(fireman);
        }
         when(firemanRepository.findAll()).thenReturn(firemans);


         mockMvc.perform(get("/fireman/stats"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$.firemenCount").value(firemans.size()))
         .andExpect(jsonPath("$.firesCount").value(fires.size()));
	}

}