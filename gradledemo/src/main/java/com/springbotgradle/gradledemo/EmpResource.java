package com.springbotgradle.gradledemo;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbotgradle.gradledemo.model.Person;
import com.springbotgradle.gradledemo.model.PersonEvent;
import com.springbotgradle.gradledemo.repo.EmployeeRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
@RequestMapping("/rest/employee")
public class EmpResource {

	@Autowired
	public EmployeeRepo empRepo;
	
	@GetMapping("/all")
	public  Flux<Person> getEmpList() {
		//Flux<String> fl = Flux.just("a", "b", "c");
		// Mono<String> mn = Mono.just("hello");
		//Employee emp1=new Employee();
		System.out.println(empRepo);

		return empRepo.findAll();
	}
	@GetMapping("/{id}")
    public Mono<Person> getId(@PathVariable("id") final Integer empId) {
        return empRepo.findById(empId);
    }
	
	@GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<PersonEvent> getEvents(@PathVariable("id")
                                    final Integer empId) {
		//Stream<String> streamGenerated =
				  Stream.generate(() -> "element").forEach(System.out::println);;
		
		//System.out.println("###################"+streamGenerated);
        return empRepo.findById(empId)
                .flatMapMany(employee -> {

                    Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

                    Flux<PersonEvent> employeeEventFlux =
                            Flux.fromStream(
                                    Stream.generate(() -> new PersonEvent(employee,
                                            new Date()))
                            );
           List<String> alist=new ArrayList<String>();
           alist.add("Nikhil");
           alist.add("Goyal");
           alist.add("RAM");
           
                    Flux<List<String>> flux =
                            Flux.fromStream(Stream.generate(() -> alist)
                            );

                    return Flux.zip(interval, employeeEventFlux)
                            .map(Tuple2::getT2);

                });
        
	}

}
