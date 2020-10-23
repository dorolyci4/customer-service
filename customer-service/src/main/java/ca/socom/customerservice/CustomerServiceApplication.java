package ca.socom.customerservice;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

//Entity
@Entity
@Data @AllArgsConstructor @NoArgsConstructor @ToString
class Customer 
{   
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Id	private Long id; private String name; String email;
}

// ne pas afficher toutes les colonnes

@Projection(name="p1",types = Customer.class)
interface CustomerProjection{
	public Long getId();
	public String getName();
}

// interface repository Rest full
@RepositoryRestResource
interface CustomerRepository extends JpaRepository<Customer, Long>{
}

@SpringBootApplication
public class CustomerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	// pour afficher l'id de la classe dans Spring Data Rest - RepositoryRestConfiguration
	@Bean
	CommandLineRunner start(CustomerRepository customerRepository, RepositoryRestConfiguration configuration) {   //  CommandLineRunner start(CustomerRepository customerRepository) {
	  return args->{
		configuration.exposeIdsFor(Customer.class);	
	    customerRepository.save(new Customer(null,"HP","hp@gmail.com"));
		customerRepository.save(new Customer(null,"IBM","ibm@gmail.com"));
	    customerRepository.save(new Customer(null,"DELL","dell@hotmail.com"));
	    customerRepository.findAll().forEach(System.out::println);
	};
	}
}
