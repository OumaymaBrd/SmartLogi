package org.example.smartspring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.example.smartspring.repository")
@EntityScan(basePackages = "org.example.smartspring.entities")
public class SmartSpringApplication implements CommandLineRunner {



    public static void main(String[] args) {
        SpringApplication.run(SmartSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        productRepository.save(new Product(null, "test"));
//        productRepository.save(new Product(null,"samaka"));
//        List<Product> products=productRepository.findAll();
//        products.forEach(p->{
//            System.out.println(p.toString());
//        tessuqwguw
//        });
//        System.out.println("Product saved!");
//        Product product=productRepository.findById(Long.valueOf(1)).get();
        System.out.println("All is good");
    }
}
