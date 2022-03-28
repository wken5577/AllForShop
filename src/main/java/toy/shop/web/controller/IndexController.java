package toy.shop.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {


    @GetMapping("/")
    public String indexPage(HttpSession session, Model model) {
        model.addAttribute("user",session.getAttribute("user"));

        return "index";
    }

}
