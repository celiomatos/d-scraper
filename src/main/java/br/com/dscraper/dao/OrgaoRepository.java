package br.com.dscraper.dao;

import br.com.dscraper.model.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrgaoRepository extends JpaRepository<Orgao, Long> {

    @Query(value = "select distinct(org.org_codigo), org.org_orgao, sum(rp.pag_valor) " +
            "from scraper.pagamento rp inner join scraper.orgao org on(rp.pag_org_id = org.org_id) " +
            "where rp.pag_date >= ?1 and rp.pag_date <= ?2 and rp.pag_removido = false " +
            "group by org.org_codigo, org.org_orgao order by org.org_orgao", nativeQuery = true)
    List<Object[]> getOrgaoValueByDate(Date dtInicial, Date dtFinal);

    Optional<Orgao> findByCodigo(String codigo);
}
