package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    @Size(min = 3, max = 15, message = "Tên cần trong khoảng 3-15 kí tự!")
    private String firstName;

    @Size(min = 3, max = 15, message = "Tên cần trong khoảng 3-15 kí tự!")
    private String lastName;
    private String username;

    @Size(min = 5, max = 20, message = "Mật khẩu cần trong khoảng 5-20 kí tự!")
    private String password;
    private String repeatPassword;
}
