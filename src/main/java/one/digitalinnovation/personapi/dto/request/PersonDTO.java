package one.digitalinnovation.personapi.dto.request;



//DTO significa Data Transfer Objects, que são classes resnsaveis pelo recebimento de dados de entrada
//também faz a validação da própria requisição http pela cadmaa de controller

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
//import org.hibernate.validator.constraints.br.CPF;
//
//import javax.validation.Valid;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String firstName;

    @NotEmpty
    @Size(min = 2, max = 100)
    private String lastName;

    @NotEmpty
    @CPF
    private String cpf;

    private String birthDate;

    //validação através das anotações de cada um dos atributos da classe PhoneDTO através da ca classe PersonController
    @Valid
    @NotEmpty
    private List<PhoneDTO> phones;
}
