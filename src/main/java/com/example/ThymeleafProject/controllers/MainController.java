package com.example.ThymeleafProject.controllers;

import com.example.ThymeleafProject.entities.UserEntity;
import com.example.ThymeleafProject.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequestMapping("/main")
public class MainController {
    @Autowired
    private UserService userService;

    public MainController(UserService userService) {
        super();
        this.userService = userService;
    }

    /*============================================================*/
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    /*============================================================*/
    @GetMapping("/user")
    public String get(Model model) {
        List<UserEntity> userdata = userService.getAll();

        model.addAttribute("userdata", userdata);
        model.addAttribute("pageTitle", "Add New User");
        return "users";
    }

    /*============================================================*/
    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        model.addAttribute("pageTitle", "Insert Data");
        return "insert";
    }

    @PostMapping("/save")
    public String addDta(@Valid @ModelAttribute("userEntity") UserEntity userEntity,
                         RedirectAttributes r, Model model) {
        if (!isValidCharacters(userEntity.getName())) {
            model.addAttribute("usernameError", "Invalid characters in the username");
            model.addAttribute("pageTitle", "Insert Valid Data");
            return "insert";
        } else if (!userService.isEmailUnique(userEntity.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            model.addAttribute("pageTitle", "Insert Valid Data");
            return "insert";
        } else if (!isValidCharacters(userEntity.getCity())) {
            model.addAttribute("cityError", "Numbers are not allowed ");
            model.addAttribute("pageTitle", "Insert Valid Data");
            return "insert";
        } else if (userEntity.getPhone() != null && !userEntity.getPhone().isEmpty())  {
            if (!isValidPhoneNumber(userEntity.getPhone())) {
                model.addAttribute("phoneNumberError", "Phone number must have 10 digits ");
                return "insert";
            }
        }

        userService.add(userEntity);
        r.addFlashAttribute("massage", "Record Inserted Successfully..!");
        return "redirect:/main/user";
    }

    /*-------------------------- patterns ----------------------------------------------------*/
    public boolean isValidCharacters(String value) {
        String pattern = "^[a-zA-Z]+$";
        return value.matches(pattern);
    }

    public boolean isValidNumber(String value) {
        String pattern = "^[0-9]+$";
        return value.matches(pattern);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String pattern = "^[0-9]{1,10}$";
        return phoneNumber.matches(pattern);
    }

    /*============================================================*/
    @GetMapping("/edit1/{id}")
    public String editStud(@PathVariable("id") Long id, Model model) {
        model.addAttribute("student", userService);
        return "insert";
    }

    /*============================================================*/
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, RedirectAttributes r) {
        userService.delete(id);
        r.addFlashAttribute("massage", "Record Deleted Successfully..!");
        return "redirect:/main/user";

    }
    /*=========== test =================================================*/


    /*===============================================*/
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes r) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("pageTitle", "Update User : " + id);
        return "Update";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("user") UserEntity userEntity, Model model, RedirectAttributes r) {
        // get data
        UserEntity user = userService.getUserById(id);
        user.setId(id);
        /*============validation=========*/
        if (!isValidCharacters(userEntity.getName())) {
            model.addAttribute("usernameError", "Invalid characters in the username");
            model.addAttribute("pageTitle", "Insert Valid Data");
            return "Update";
        } else if (!isValidCharacters(userEntity.getCity())) {
            model.addAttribute("cityError", "Numbers are not allowed ");
            model.addAttribute("pageTitle", "Insert Valid Data");
            return "Update";
        } else if (userEntity.getPhone() != null && !userEntity.getPhone().isEmpty())  {
            if (!isValidPhoneNumber(userEntity.getPhone())) {
                model.addAttribute("phoneNumberError", "Phone number must have 10 digits Only");
                return "Update";
            }
        }
/*===========------validation ----============*/
        user.setName(userEntity.getName());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setCity(userEntity.getCity());
        r.addFlashAttribute("massage", "Record Updated Successfully..!");
        userService.updateUser(user);
        return "redirect:/main/user";
    }

}
