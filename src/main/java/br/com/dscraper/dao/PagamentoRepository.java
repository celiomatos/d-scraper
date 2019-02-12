package br.com.dscraper.dao;

import br.com.dscraper.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {

    @Query("select t from Pagamento t where t.orgao.codigo = ?1 " +
            "and t.dtpagamento >= ?2 and t.dtpagamento <= ?3 " +
            "and t.removido = false order by t.credor.nome")
    List<Pagamento> getPagamentos(String orgao, Date inicio, Date fim);
}
