package br.com.impacta.lab.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.impacta.lab.models.Produto;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    public static List<Produto> bancoDeDados = new ArrayList<>();

    @PostMapping("")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        bancoDeDados.add(produto);
        return ResponseEntity.ok(produto);
    }

    @GetMapping("")
    public ResponseEntity<List<Produto>> listarProdutos() {
        return ResponseEntity.ok(bancoDeDados);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Produto> consultarProdutoPorCodigo(@PathVariable("codigo") Integer codigo) {

        Produto produtoBuscado = bancoDeDados.stream().filter(produto -> codigo.equals(produto.getCodigo())).findAny()
                .orElse(null);

        if (produtoBuscado != null)
            return ResponseEntity.ok(produtoBuscado);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Produto> alterarProduto(@PathVariable("codigo") Integer codigo,
            @RequestBody Produto novoProduto) {

        Produto produtoAlterado = bancoDeDados.stream().filter(produto -> codigo.equals(produto.getCodigo())).findAny()
                .orElse(null);

        if (produtoAlterado != null) {
            produtoAlterado.setDescricao(novoProduto.getDescricao());
            produtoAlterado.setValor(novoProduto.getValor());

            return ResponseEntity.ok(produtoAlterado);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Produto> deletarProduto(@PathVariable("codigo") Integer codigo) {
        Produto produtoDeletar = bancoDeDados.stream().filter(produto -> codigo.equals(produto.getCodigo())).findAny()
                .orElse(null);

        if (produtoDeletar != null) {
            bancoDeDados.remove(produtoDeletar);

            return ResponseEntity.ok(produtoDeletar);
        }

        return ResponseEntity.notFound().build();
    }
}
