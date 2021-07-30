package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//informa o spring que vai ser uma classe uma classe responsavel pelas regras de negócio
// trata melhor suporte transacional, entre outros especificações de serviço

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private PersonRepository personRepository;

    private final PersonMapper personMapper = PersonMapper.INSTANCE;

    //passa o corpo da requisição, através do arguimento entidade person
    public MessageResponseDTO createPerson(PersonDTO personDTO){
        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);
        //o @Builder permite instanciar a classe sem usar o construtor, de uma forma mais encapsulada e ainda fazer um tratamento dos dados de entrada (.message())
        return createMsgResponse(savedPerson.getId(), "Person Created: ");
    }

    public MessageResponseDTO updatePerson(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        verifyPerson(id);

        Person personToSave = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(personToSave);

        return createMsgResponse(savedPerson.getId(), "Person Updated!");
    }

    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        // ele precisa retornar um objeto personDTO
        // a stream manipula/transforma dados em coleções
        // o map será responsável por converter cada linha da list em DTO
        //e o resultado será jogado numa lista (collectors)
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        //findById retorna um Optional, que nos permite fazer algumas verificações
//        Optional<Person> optionalPerson = personRepository.findById(id);
//        if(optionalPerson.isEmpty()){
//            throw  new PersonNotFoundException(id);
//        }
//        return personMapper.toDTO(optionalPerson.get());
        return personMapper.toDTO(verifyPerson(id));
    }

    public void delete(Long id) throws PersonNotFoundException {
        verifyPerson(id);
        personRepository.deleteById(id);
    }

    private Person verifyPerson(Long id) throws PersonNotFoundException {
        return personRepository
                .findById(id)
                .orElseThrow(()->new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMsgResponse(Long id, String msg) {
        return MessageResponseDTO
                .builder()
                .message(msg + id)
                .build();
    }
}
