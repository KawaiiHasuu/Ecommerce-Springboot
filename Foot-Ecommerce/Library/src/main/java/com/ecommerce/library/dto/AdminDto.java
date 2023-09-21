package com.ecommerce.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data @NoArgsConstructor @AllArgsConstructor
public class AdminDto {
    @Size(min = 3, max = 10, message = "Tên không hợp lệ!(3-10 kí tự)")
    private String firstName;

    @Size(min = 3, max = 10, message = "Tên không hợp lệ!(3-10 kí tự)")
    private String lastName;

    private String username;

    @Size(min = 5, max = 15, message = "Mật khẩu không hợp lệ!(5-15 kí tự)")
    private String password;

    private String repeatPassword;
}
