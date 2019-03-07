--liquibase formatted sql

--changeset celio:89 splitStatements:false

create or replace function insercao_pagamentos() returns trigger language plpgsql as $$
  begin
  if (new.pag_date <= date '2011-12-31') then
	  insert into scraper.pagamento_2011 values (new.*);
  elseif (new.pag_date >= date '2012-01-01' and new.pag_date <= date '2012-12-31') then
	  insert into scraper.pagamento_2012 values (new.*);
  elseif (new.pag_date >= date '2013-01-01' and new.pag_date <= date '2013-12-31') then
	  insert into scraper.pagamento_2013 values (new.*);
  elseif (new.pag_date >= date '2014-01-01' and new.pag_date <= date '2014-12-31') then
	  insert into scraper.pagamento_2014 values (new.*);
  elseif (new.pag_date >= date '2015-01-01' and new.pag_date <= date '2015-12-31') then
	  insert into scraper.pagamento_2015 values (new.*);
  elseif (new.pag_date >= date '2016-01-01' and new.pag_date <= date '2016-12-31') then
	  insert into scraper.pagamento_2016 values (new.*);
  elseif (new.pag_date >= date '2016-01-01' and new.pag_date <= date '2017-12-31') then
	  insert into scraper.pagamento_2017 values (new.*);
  elseif (new.pag_date >= date '2018-01-01' and new.pag_date <= date '2018-12-31') then
	  insert into scraper.pagamento_2018 values (new.*);
  elseif (new.pag_date >= date '2019-01-01' and new.pag_date <= date '2019-12-31') then
	  insert into scraper.pagamento_2019 values (new.*);
  else
	  raise exception 'a data não se encontra nos limites estabelecidos para a inserção...';
  end if;
  return null;
  end;
$$;

--changeset celio:90

create trigger pagamento_anual_trigger
before insert on scraper.pagamento
for each row
  execute procedure insercao_pagamentos();

--changeset celio:91

SET constraint_exclusion = on;

--changeset celio:162

insert into scraper.parametro(par_id, par_descricao, par_atual, par_padrao)
    values (2, 'Pagamento Meses Anteriores', '01/2019', 'MM/yyyy');

--rollback delete from scraper.parametro where par_id = 2;

--changeset celio:122

insert into scraper.parametro(par_id, par_descricao, par_atual, par_padrao)
    values (3, 'Atualização de Notas de Empenho Anos Anteriores', '2018-2-', 'ano-idx-orgao01;orgao02;orgaoN');

--rollback delete from scraper.parametro where par_id = 3;

--changeset celio:123

insert into scraper.parametro(par_id, par_descricao, par_atual, par_padrao)
    values (4, 'Atualização de Notas de Empenho Ano Atual', '2019-2-', 'anoatual-idx-orgao01;orgao02;orgaoN');

--rollback delete from scraper.parametro where par_id = 4;