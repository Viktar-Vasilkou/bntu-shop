package by.bntu.fitr.povt.vasilkou.bntu_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {
    @NotBlank(message = "Login shouldn't be empty")
    @Size(min = 3, max = 20, message = "Login should be between 3 and 20 characters")
    private String login;

    @NotBlank(message = "Password shouldn't be empty")
    @Size(min = 8, max = 20, message = "Login should be between 8 and 20 characters")
    private String password;
}
