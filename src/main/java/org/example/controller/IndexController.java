package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.example.service.BookService;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final BookService bookService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("book", bookService.findAllDesc());
        return "index";
    }

    @GetMapping("/book/save")
    public String bookSave() {
        return "book-save";
    }
}
