package br.com.dscraper.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
public class DateUtil {

    private static final SimpleDateFormat SDFD = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDateBr(Date date) {
        return SDFD.format(date);
    }

    public static Date strToDate(String dt) {

        Date date = null;

        if (dt != null && (!dt.isEmpty())) {
            try {
                String dh[] = dt.split(" ");

                String dtv[] = dh[0].trim().split("/");

                Calendar c = Calendar.getInstance();
                int ano;
                int mes;
                int dia;

                if (1 == dtv.length) {
                    ano = Integer.parseInt(dtv[0].substring(0, 4));
                    mes = Integer.parseInt(dtv[0].substring(4, 6)) - 1;
                    dia = Integer.parseInt(dtv[0].substring(6));
                } else {
                    ano = Integer.parseInt(dtv[2]);
                    mes = Integer.parseInt(dtv[1]) - 1;
                    dia = Integer.parseInt(dtv[0]);
                }
                int hora = 0;
                int min = 0;

                if (dh.length > 1) {
                    String hv[] = dh[1].trim().split(":");
                    hora = Integer.parseInt(hv[0]);
                    min = Integer.parseInt(hv[1]);
                }

                c.set(ano, mes, dia, hora, min, 0);
                c.set(Calendar.MILLISECOND, 0);
                date = c.getTime();
            } catch (Exception ex) {
                log.error("Convert data {}", dt);
            }
        }
        return date;
    }


    public static java.sql.Date utilDateToSqlDate(Date date){
        return new java.sql.Date(date.getTime());
    }

}
