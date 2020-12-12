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
public class OrderDto {
    @Size(min = 10, max = 255, message = "Address should contain more than 10 characters")
    @NotBlank(message = "Address should be not empty")
    public String address;
}
