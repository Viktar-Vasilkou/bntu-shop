package by.bntu.fitr.povt.vasilkou.bntu_shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    @NotBlank(message = "Address should be not empty")
    public String address;
}
