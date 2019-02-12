package br.com.dscraper.service;

import br.com.dscraper.dao.ParametroRepository;
import br.com.dscraper.model.Parametro;
import br.com.dscraper.util.MyConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Slf4j
@Service
public class ParametroService {

    @Autowired
    private ParametroRepository parametroRepository;

    /**
     * @return
     */
    public String getParametroPagamento() {

        String result = null;

        Parametro p = parametroRepository.getOne(MyConstant.PAGAMENTO);

        if (p != null) {
            result = getMesAno(p, 1);
        }
        return result;
    }


    /**
     * @param parametro
     * @param add
     * @return
     */
    private String getMesAno(Parametro parametro, int add) {

        String result = parametro.getAtual();

        String nv[] = result.split("/");
        int mes = Integer.parseInt(nv[0]) - 1;
        int ano = Integer.parseInt(nv[1]);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/yyyy");

        Calendar c = Calendar.getInstance();
        c.set(Calendar.DATE, 1);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, ano);
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) + add);

        if (c.after(Calendar.getInstance())) {
            c.set(Calendar.MONTH, 0);
            c.set(Calendar.YEAR, (ano - 7));
            parametro.setAtual(sdf.format(c.getTime()));
        } else {
            parametro.setAtual(sdf.format(c.getTime()));
        }

        parametroRepository.save(parametro);

        return result;
    }

}
