package ru.test.todoclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class TodoClientApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TodoClientApplication.class);
		app.setWebApplicationType(WebApplicationType.NONE);
		app.run(args);
	}

	private Logger log = LoggerFactory.getLogger(TodoClientApplication.class);

	@Bean
	public CommandLineRunner process(ToDoRestClient client){
		return args -> {
			log.info("Test find all");
			Iterable<ToDo> toDos = client.findAll();
			assert toDos != null;
			toDos.forEach(toDo -> log.info(toDo.toString()));

			log.info("Test create");
			ToDo newToDo = client.upsert(new ToDo("Testing prog"));
			assert newToDo != null;
			log.info(newToDo.toString());

			log.info("Test find by id");
			ToDo toDo = client.findById(newToDo.getId());
			assert toDos !=null;
			log.info(toDo.toString());

			/*log.info("Test setCompleted");
			ToDo completed = client.setCompleted(newToDo.getId());
			assert completed.isCompleted();
			log.info(completed.toString());*/

			log.info("Test delete");
			client.delete(newToDo.getId());
			assert client.findById(newToDo.getId()) == null;
		};
	}

}
