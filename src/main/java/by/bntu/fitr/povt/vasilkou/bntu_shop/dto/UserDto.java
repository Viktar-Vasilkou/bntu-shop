package by.bntu.fitr.povt.vasilkou.bntu_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    @NotBlank(message = "Name shouldn't be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    private String name;

    @NotBlank(message = "Surname shouldn't be empty")
    @Size(min = 3, max = 20, message = "Surname should be between 3 and 20 characters")
    private String surname;

    @NotBlank(message = "Phone number shouldn't be empty")
    @Size(min = 9, max = 9, message = "Phone number should contain 9 digits")
    @Pattern(regexp = "\\d+", message = "Phone number should contain only digits")
    private String phoneNumber;

    @NotBlank(message = "Login shouldn't be empty")
    @Size(min = 3, max = 20, message = "Login should be between 3 and 20 characters")
    private String login;
}
