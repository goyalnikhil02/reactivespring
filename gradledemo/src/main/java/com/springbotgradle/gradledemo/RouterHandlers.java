package com.springbotgradle.gradledemo;

import java.time.Duration;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.springbotgradle.gradledemo.model.Person;
import com.springbotgradle.gradledemo.model.PersonEvent;
import com.springbotgradle.gradledemo.repo.EmployeeRepo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class RouterHandlers {

	private EmployeeRepo employeeRepository;

	public RouterHandlers(EmployeeRepo employeeRepository) {
		// super();
		this.employeeRepository = employeeRepository;
	}

	public Mono<ServerResponse> getAll(ServerRequest serverRequest) {
		return ServerResponse.ok().body(employeeRepository.findAll(), Person.class);
	}

	public Mono<ServerResponse> getId(ServerRequest serverRequest) {
		String empid = serverRequest.pathVariable("id");
		return ServerResponse.ok().body(employeeRepository.findById(Integer.parseInt(empid)), Person.class);
	}

	public Mono<ServerResponse> getEvents(ServerRequest serverRequest) {
		String empId = serverRequest.pathVariable("id");
		return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
				.body(employeeRepository.findById(Integer.parseInt(empId)).flatMapMany(employee -> {
					Flux<Long> interval = Flux.interval(Duration.ofSeconds(2));

					Flux<PersonEvent> employeeEventFlux = Flux
							.fromStream(Stream.generate(() -> new PersonEvent(employee, new Date())));
					return Flux.zip(interval, employeeEventFlux).map(Tuple2::getT2);
				}), PersonEvent.class);
	}
}
