package br.com.dscraper.util;

import java.math.BigDecimal;

public class NumberUtil {

    public static BigDecimal strToBigDecimal(String vl) {
        BigDecimal value = null;
        if (vl != null && (!vl.isEmpty())) {
            try {
                vl = vl.replaceAll("[R$, .]", "");
                if (vl.length() > 2) {
                    vl = vl.substring(0, vl.length() - 2)
                            + "."
                            + vl.substring(vl.length() - 2);

                    value = new BigDecimal(vl);
                }
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
        return value;
    }
}
