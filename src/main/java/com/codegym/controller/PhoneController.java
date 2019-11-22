package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Phone;
import com.codegym.service.CategoryService;
import com.codegym.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PhoneController {

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute("categories")
    public Iterable<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping("/create-phone")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("/phone/create");
        modelAndView.addObject("phone", new Phone());
        return modelAndView;
    }

    @PostMapping("/create-phone")
    public ModelAndView savePhone(@ModelAttribute("phone") Phone phone) {
        phoneService.save(phone);
        ModelAndView modelAndView = new ModelAndView("/phone/create");
        modelAndView.addObject("phone", new Phone());
        modelAndView.addObject("message", "New phone created successfully");
        return modelAndView;
    }

    @GetMapping("/phones")
    public ModelAndView listPhones(@RequestParam("s") Optional<String> s, @PageableDefault(size = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/phone/list");
        Page<Phone> phones;
        if (s.isPresent()) {
            phones = phoneService.findAllByNameContaining(s.get(), pageable);
            modelAndView.addObject("issrch", "xxxx");
        } else {
            phones = phoneService.findAll(pageable);
        }
        modelAndView.addObject("phones", phones);
        return modelAndView;
    }

    @GetMapping("/searchByCategory")
    public ModelAndView getPhoneByCategory(@RequestParam("searchCategory") Long searchCategory, @PageableDefault(size = 5) Pageable pageable) {
        Page<Phone> phones;
        if(searchCategory == -1){
            phones = phoneService.findAll(pageable);
        }else{
            Category searcCategory = categoryService.findById(searchCategory);
            phones = phoneService.findAllByCategory(searcCategory,pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("phones",phones);
        modelAndView.addObject("searchCategory",searchCategory);
        modelAndView.addObject("categories", categories());
        return modelAndView;
    }

    @GetMapping("/sortByPrice")
    public ModelAndView getListSortedByPrice(@RequestParam("sortDirection2") String sort, @PageableDefault(size = 5) Pageable pageable) {
        Page<Phone> phones;
        if (sort.equals("no")) {
            phones = phoneService.findAll(pageable);
        } else if (sort.equals("asc")) {
            phones = phoneService.findAllByOrderByPriceAsc(pageable);
        } else {
            phones = phoneService.findAllByOrderByPriceDesc(pageable);
        }
        ModelAndView modelAndView = new ModelAndView("/phone/list");
        modelAndView.addObject("phones", phones);
        modelAndView.addObject("sortDirection2", sort);
        return modelAndView;
    }

    @GetMapping("/edit-phone/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Phone phone = phoneService.findById(id);
        if (phone != null) {
            ModelAndView modelAndView = new ModelAndView("/phone/edit");
            modelAndView.addObject("phone", phone);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-phone")
    public ModelAndView updatePhone(@ModelAttribute("phone") Phone phone) {
        phoneService.save(phone);
        ModelAndView modelAndView = new ModelAndView("/phone/edit");
        modelAndView.addObject("phone", phone);
        modelAndView.addObject("message", "Phone update successful");
        return modelAndView;
    }

    @GetMapping("/delete-phone/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Phone phone = phoneService.findById(id);
        if (phone != null) {
            ModelAndView modelAndView = new ModelAndView("/phone/delete");
            modelAndView.addObject("phone", phone);
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-phone")
    public String deletePhone(@ModelAttribute("phone") Phone phone) {
        phoneService.remove(phone.getId());
        return "redirect:phones";
    }

    @GetMapping("/detail-phone/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("phone", phoneService.findById(id));
        return "/phone/detail";
    }
}
