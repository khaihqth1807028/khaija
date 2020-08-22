package heleyquin.controller;

import heleyquin.entity.Product;
import heleyquin.service.ImpProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping(path = "/admin/product")
public class ProductController {
    @Autowired
    ImpProductService service;
    @RequestMapping(method = RequestMethod.GET)
    public String getAll(Model model, @RequestParam(name = "page", defaultValue = "1") Optional<Integer> page, @RequestParam(name = "limit", defaultValue = "1") Optional<Integer> limit) {
        int pageNumber = page.orElse(1);
        int limitSize = limit.orElse(1);
        Page<Product> products = service.getAll(pageNumber - 1, limitSize);
        model.addAttribute("products", products);
        int totalPages = products.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "admin/product/list";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public String getDetail(Model model, @PathVariable int id) {
        Product product = service.getProductById(id);
        if(product == null) {
            return "error";
        }
        model.addAttribute("product", product);
        return "admin/product/detail";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        return "admin/product/create";
    }
    @RequestMapping(method = RequestMethod.POST)
    public String save(@ModelAttribute Product product) {
        service.save(product);
        return "redirect:/admin/product";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/edit/{id}")
    public String edit(Model model, @PathVariable int id) {
        Product product = service.getProductById(id);
        if(product == null) {
            return "error";
        }
        model.addAttribute("product", product);
        return "admin/product/edit";
    }
    @RequestMapping(method = RequestMethod.POST, path = "/{id}")
    public String update(@ModelAttribute Product product, @PathVariable int id) {
        if(service.update(product, id)) {
            return "redirect:/admin/product";
        }
        return "error";
    }
    @RequestMapping(method = RequestMethod.GET, path = "/delete/{id}")
    public String delete(@ModelAttribute Product product, @PathVariable int id) {
        if(service.delete(id)){
            return "redirect:/admin/product";
        }
        return "error";
    }
}
