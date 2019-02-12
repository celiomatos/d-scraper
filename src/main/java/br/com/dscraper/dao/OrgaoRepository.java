package br.com.dscraper.dao;

import br.com.dscraper.model.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {

    @Query(value = "select distinct(org.codigo), org.orgao, sum(valor) " +
            "from pagamentos rp inner join orgaos org using(idorgao) " +
            "where dtpagamento >= '?1' and dtpagamento <= '?2'and removido = false " +
            "group by org.codigo, org.orgao order by org.orgao", nativeQuery = true)
    List<Object[]> getOrgaoValueByDate(String dtInicial, String dtFinal);

    Optional<Orgao> findByCodigo(String codigo);
}
