package com.ecommerce.admin.controller;

import com.ecommerce.library.dto.ProductDto;
import com.ecommerce.library.model.Category;
import com.ecommerce.library.model.Product;
import com.ecommerce.library.service.CategoryService;
import com.ecommerce.library.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public String products(Model model, Principal principal){
        if (principal==null){
            return "redirect:/login";
        }
        List<ProductDto> productDtoList = productService.findAll();
        model.addAttribute("title", "Quản lí sản phẩm");
        model.addAttribute("products", productDtoList);
        model.addAttribute("size", productDtoList.size());
        return "products";
    }

    @GetMapping("/products/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }

        Page<ProductDto> products = productService.pageProducts(pageNo);
        model.addAttribute("title", "Quản lí sản phẩm");
        model.addAttribute("size", products.getSize());
        model.addAttribute("totalPages", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "/products";
    }

    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo")int pageNo,@RequestParam("keyword") String keyword, Model model, Principal principal){
        if (principal == null) {
            return "redirect:/login";
        }
        Page<ProductDto> products = productService.searchProducts(pageNo, keyword);
        model.addAttribute("title", "Kết quả tìm kiếm");
        model.addAttribute("products", products);
        model.addAttribute("size", products.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", products.getTotalPages());
        return "result-products";
    }

    @GetMapping("/add-product")
    public String addProductForm(Model model, Principal principal){
        if (principal == null){
            return "redirect:/login";
        }
        List<Category> categories = categoryService.findAllByActivated();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new ProductDto());
        return "add-product";
    }

    @PostMapping("/save-product")
    public String saveProduct(@ModelAttribute("product") ProductDto productDto, @RequestParam("imageProduct") MultipartFile imageProduct,
                              RedirectAttributes attributes){
        try{
            productService.save(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Thêm sản phẩm thành công");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Thêm sản phẩm không thành công");
        }
        return "redirect:/products";
    }


    @GetMapping("/update-product/{id}")
    public String updateProductForm(@PathVariable("id") Long id, Model model, Principal principal){
        if(principal == null){
            return "redirect:/login";
        }
        model.addAttribute("title", "Cập nhật sản phẩm");
        List<Category> categories = categoryService.findAllByActivated();
        ProductDto productDto = productService.getById(id);
        model.addAttribute("categories", categories);
        model.addAttribute("productDto", productDto);
        return "update-product";
    }

    @PostMapping("/update-product/{id}")
    public String processUpdate(@PathVariable("id") Long id,
                                @ModelAttribute("productDto") ProductDto productDto,
                                RedirectAttributes attributes,
                                @RequestParam("imageProduct")MultipartFile imageProduct){
        try{
            productService.update(imageProduct, productDto);
            attributes.addFlashAttribute("success", "Cập nhật thành công");
        } catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Cập nhật không thành công");
        }
        return "redirect:/products";
    }


    @RequestMapping(value = "/enable-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String enabledProduct(@PathVariable("id") Long id, RedirectAttributes attributes){
        try{
            productService.enableById(id);
            attributes.addFlashAttribute("success", "Kích hoạt thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Kích hoạt thất bại");
        }
        return "redirect:/products";
    }


    @RequestMapping(value = "/delete-product/{id}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String deletedProduct(@PathVariable("id")Long id, RedirectAttributes attributes){
        try{
            productService.deleteById(id);
            attributes.addFlashAttribute("success", "Xóa thành công");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Xóa thất bại");
        }
        return "redirect:/products";
    }

}
