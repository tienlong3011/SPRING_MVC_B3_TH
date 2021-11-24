package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.service.CustomerService;
import com.codegym.service.ICustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
//Annotation @Controller giúp Spring xác định    lớp hiện tại là một Controller.
@Controller
@RequestMapping("/customer")
public class CustomerController {
    private  final ICustomerService customerService = new CustomerService();
//    Annotation @GetMapping xác định phương thức Index sẽ đón nhận các request có HTTP method là GET và URI pattern là "/"
    @GetMapping("")
    public String index(Model model){
        List<Customer> customerList = customerService.findAll();
        model.addAttribute("customers",customerList);
        return "/index";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("customer",new Customer());
        return "/create";
    }

    @PostMapping("/save")
    public String save(Customer customer, RedirectAttributes redirect){
        customer.setId((int) (Math.random()*10000));
        customerService.save(customer);
        redirect.addFlashAttribute("success","Successful new creation");
        return "redirect:/customer";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable int id,Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/edit";
    }

    @PostMapping("/update")
    public String update(Customer customer,RedirectAttributes redirect){
        customerService.update(customer.getId(),customer);
        redirect.addFlashAttribute("success","Successfully edited");
        return "redirect:/customer";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id,Model model){
        model.addAttribute("customer",customerService.findById(id));
        return "/delete";
    }

    @PostMapping("/delete")
    public String remove(Customer customer, RedirectAttributes redirect){
        customerService.remove(customer.getId());
        redirect.addFlashAttribute("success","Successfully remove");
        return "redirect:/customer";
    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "/view";
    }

}
