package com.dev.api_loja.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorTotal = BigDecimal.ZERO;

    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CarrinhoItem> itensCarrinho = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public void adicionarItem(CarrinhoItem itemCarrinho) {
        this.itensCarrinho.add(itemCarrinho);
        itemCarrinho.setCarrinho(this);
        atualizaValorTotal();
    }

    public void removeItem(CarrinhoItem itemCarrinho) {
        this.itensCarrinho.remove(itemCarrinho);
        itemCarrinho.setCarrinho(null);
        atualizaValorTotal();
    }

    // recalcula o valor total do carrinho
    public void atualizaValorTotal() {
        this.valorTotal = itensCarrinho.stream().map(item -> {
            BigDecimal precoUnitario = item.getPrecoUnitario();

            if (precoUnitario == null) {
                return BigDecimal.ZERO;
            }

            return precoUnitario.multiply(BigDecimal.valueOf(item.getQuantidade()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
