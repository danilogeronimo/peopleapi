package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//informa o spring que vai ser uma classe uma classe responsavel pelas regras de negócio
// trata melhor suporte transacional, entre outros especificações de serviço

@Service
public class PersonService {

    private PersonRepository personRepository;

    //construtor para injeção de dependencias
    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    //passa o corpo da requisição, através do arguimento entidade person
    public MessageResponseDTO createPerson(Person person){
        Person savedPerson = personRepository.save(person);
        //o @Builder permite instanciar a classe sem usar o construtor, de uma forma mais encapsulada e ainda fazer um tratamento dos dados de entrada (.message())
        return MessageResponseDTO
                .builder()
                .message("Created person with ID " + savedPerson.getId())
                .build();
    }


}
