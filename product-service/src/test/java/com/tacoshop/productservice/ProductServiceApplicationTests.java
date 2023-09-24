package com.tacoshop.productservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tacoshop.productservice.dto.TacoRequest;
import com.tacoshop.productservice.dto.TacoResponse;
import com.tacoshop.productservice.repository.TacoRepository;
import com.tacoshop.productservice.service.TacoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;
import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2")
			.withStartupTimeout(Duration.ofMinutes(2));
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private TacoRepository tacoRepository;
	@Autowired
	private TacoService tacoService;

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateOneTaco() throws Exception {
		TacoRequest tacoRequest = TacoRequest.builder()
				.name("Taco Richard")
				.ingredients(new HashMap<String, Integer>() {{put("Beef", 3); put("Onion", 6);
					put("Shredded cheese", 4); put("Tomatoes", 7);}})
				.price(140.80)
				.build();
		String tacoRequestString = objectMapper.writeValueAsString(tacoRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/taco")
				.contentType(MediaType.APPLICATION_JSON)
				.content(tacoRequestString))
				.andExpect(status().isCreated());

		Assertions.assertTrue(tacoRepository.findAll().size() == 1);
	}

	@Test
	void shouldGetOneTaco() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/taco/all"))
				.andExpect(status().isOk());
	}

}
