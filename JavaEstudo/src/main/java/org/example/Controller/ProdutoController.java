package org.example.Controller;

import org.example.Entity.Produto;
import org.example.Service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service = new ProdutoService();

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("produtos", service.listarTudo());
        return "produtos/lista";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("produto", new Produto(null, "", null));
        return "produtos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto) {
        service.salvarProduto(produto);
        return "redirect:/produtos";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        service.excluirProduto(id);
        return "redirect:/produtos";
    }
}
