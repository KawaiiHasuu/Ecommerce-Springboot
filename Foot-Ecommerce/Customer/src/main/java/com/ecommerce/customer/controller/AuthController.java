package com.ecommerce.customer.controller;

import com.ecommerce.library.dto.CustomerDto;
import com.ecommerce.library.model.Customer;
import com.ecommerce.library.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("customerDto", new CustomerDto());
        return "register";
    }

    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute("customerDto")CustomerDto customerDto,
                                  BindingResult result,
                                  Model model){
        try{
            if (result.hasErrors()){
                model.addAttribute("customerDto", customerDto);
                return "register";
            }

            Customer customer = customerService.findByUsername(customerDto.getUsername());
            if (customer != null){
                model.addAttribute("username", "Tên tài khoản đã được sử dụng");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
            if (customerDto.getPassword().equals(customerDto.getRepeatPassword())){
                customerDto.setPassword(passwordEncoder.encode(customerDto.getPassword()));
                customerService.save(customerDto);
                model.addAttribute("success", "Đăng ký thành công");
                return "register";
            } else{
                model.addAttribute("password", "Mật khẩu bạn nhập không giống nhau");
                model.addAttribute("customerDto", customerDto);
                return "register";
            }
        } catch (Exception e){
            model.addAttribute("error", "Hệ thống hiện đang lỗi");
            model.addAttribute("customerDto", customerDto);
        }
        return "register";
    }
}
