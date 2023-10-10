package com.tacoshop.inventoryservice;

import com.tacoshop.inventoryservice.model.Inventory;
import com.tacoshop.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setIngredientName("Tomato");
			inventory.setQuantity(5);
			inventory.setPrice(0.5);

			inventoryRepository.save(inventory);
		};


	}

}
