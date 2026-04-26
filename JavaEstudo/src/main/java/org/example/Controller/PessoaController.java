package org.example.Controller;

import org.example.DTO.PessoaDTO;
import org.example.Entity.Pessoa;
import org.example.Service.PessoaService;
import org.example.Service.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.List;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    private PessoaService pessoaService = new PessoaService();
    private ProdutoService produtoService = new ProdutoService();

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pessoas", pessoaService.buscarTodasPessoasParaExibicao());
        return "pessoas/lista";
    }

    @GetMapping("/novo")
    public String formulario(Model model) {
        model.addAttribute("pessoa", new Pessoa(null, "", 0, 0.0, new LinkedHashSet<>()));
        model.addAttribute("todosProdutos", produtoService.listarTudo());
        return "pessoas/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Pessoa pessoa,
                         @RequestParam(value = "produtosSelecionados", required = false) List<Long> produtosIds) {
        pessoaService.salvarPessoaComValidacao(pessoa, produtosIds);
        return "redirect:/pessoas";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("pessoa", pessoaService.buscarPessoaCompleta(id));
        model.addAttribute("todosProdutos", produtoService.listarTudo());
        return "pessoas/formulario";
    }

}
