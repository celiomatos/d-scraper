--liquibase formatted sql

--changeset celio:173

create table if not exists scraper.empenho_despesa
(
  des_id bigserial primary key,
  des_ano character varying(4) not null,
  des_valor numeric(20,2) not null,
  des_cla_id bigint references scraper.classificacao (cla_id),
  des_lancamento timestamp without time zone not null default now(),
  des_emp_id bigint not null references scraper.empenho (emp_id)
);

--rollback drop table scraper.empenho_despesa;

--changeset celio:174

create table if not exists scraper.empenho_despesa_2011
(
   check (des_ano = '2011')
)
inherits (scraper.empenho_despesa);

--changeset celio:175

create table if not exists scraper.empenho_despesa_2012
(
   check (des_ano = '2012')
)
inherits (scraper.empenho_despesa);

--changeset celio:176

create table if not exists scraper.empenho_despesa_2013
(
   check (des_ano = '2013')
)
inherits (scraper.empenho_despesa);

--changeset celio:177

create table if not exists scraper.empenho_despesa_2014
(
   check (des_ano = '2014')
)
inherits (scraper.empenho_despesa);

--changeset celio:178

create table if not exists scraper.empenho_despesa_2015
(
   check (des_ano = '2015')
)
inherits (scraper.empenho_despesa);

--changeset celio:179

create table if not exists scraper.empenho_despesa_2016
(
   check (des_ano = '2016')
)
inherits (scraper.empenho_despesa);

--changeset celio:180

create table if not exists scraper.empenho_despesa_2017
(
   check (des_ano = '2017')
)
inherits (scraper.empenho_despesa);

--changeset celio:181

create table if not exists scraper.empenho_despesa_2018
(
   check (des_ano = '2018')
)
inherits (scraper.empenho_despesa);

--changeset celio:182

create table if not exists scraper.empenho_despesa_2019
(
   check (des_ano = '2019')
)
inherits (scraper.empenho_despesa);

--changeset celio:183

create index if not exists empenho_despesa_2011_des_ano_idx on scraper.empenho_despesa_2011 (des_ano);

--changeset celio:184

create index if not exists empenho_despesa_2011_des_emp_id_idx on scraper.empenho_despesa_2011 (des_emp_id);

--changeset celio:185

create index if not exists empenho_despesa_2012_des_ano_idx on scraper.empenho_despesa_2012 (des_ano);

--changeset celio:186

create index if not exists empenho_despesa_2012_des_emp_id_idx on scraper.empenho_despesa_2012 (des_emp_id);

--changeset celio:187

create index if not exists empenho_despesa_2013_des_ano_idx on scraper.empenho_despesa_2013 (des_ano);

--changeset celio:188

create index if not exists empenho_despesa_2013_des_emp_id_idx on scraper.empenho_despesa_2013 (des_emp_id);

--changeset celio:189

create index if not exists empenho_despesa_2014_des_ano_idx on scraper.empenho_despesa_2014 (des_ano);

--changeset celio:190

create index if not exists empenho_despesa_2014_des_emp_id_idx on scraper.empenho_despesa_2014 (des_emp_id);

--changeset celio:191

create index if not exists empenho_despesa_2015_des_ano_idx on scraper.empenho_despesa_2015 (des_ano);

--changeset celio:192

create index if not exists empenho_despesa_2015_des_emp_id_idx on scraper.empenho_despesa_2015 (des_emp_id);

--changeset celio:193

create index if not exists empenho_despesa_2016_des_ano_idx on scraper.empenho_despesa_2016 (des_ano);

--changeset celio:194

create index if not exists empenho_despesa_2016_des_emp_id_idx on scraper.empenho_despesa_2016 (des_emp_id);

--changeset celio:195

create index if not exists empenho_despesa_2017_des_ano_idx on scraper.empenho_despesa_2017 (des_ano);

--changeset celio:196

create index if not exists empenho_despesa_2017_des_emp_id_idx on scraper.empenho_despesa_2017 (des_emp_id);

--changeset celio:197

create index if not exists empenho_despesa_2018_des_ano_idx on scraper.empenho_despesa_2018 (des_ano);

--changeset celio:198

create index if not exists empenho_despesa_2018_des_emp_id_idx on scraper.empenho_despesa_2018 (des_emp_id);

--changeset celio:199

create index if not exists empenho_despesa_2019_des_ano_idx on scraper.empenho_despesa_2019 (des_ano);

--changeset celio:200

create index if not exists empenho_despesa_2019_des_emp_id_idx on scraper.empenho_despesa_2019 (des_emp_id);

--changeset celio:201 splitStatements:false

create or replace function insercao_empenho_despesas() returns trigger language plpgsql as $$
  begin
  if (new.des_ano = '2011') then
	  insert into scraper.empenho_despesa_2011 values (new.*);
  elseif (new.des_ano = '2012') then
	  insert into scraper.empenho_despesa_2012 values (new.*);
  elseif (new.des_ano = '2013') then
	  insert into scraper.empenho_despesa_2013 values (new.*);
  elseif (new.des_ano = '2014') then
	  insert into scraper.empenho_despesa_2014 values (new.*);
  elseif (new.des_ano = '2015') then
	  insert into scraper.empenho_despesa_2015 values (new.*);
  elseif (new.des_ano = '2016') then
	  insert into scraper.empenho_despesa_2016 values (new.*);
  elseif (new.des_ano = '2017') then
	  insert into scraper.empenho_despesa_2017 values (new.*);
  elseif (new.des_ano = '2018') then
	  insert into scraper.empenho_despesa_2018 values (new.*);
  elseif (new.des_ano = '2019') then
	  insert into scraper.empenho_despesa_2019 values (new.*);
  else
	  raise exception 'a data não se encontra nos limites estabelecidos para a inserção...';
  end if;
  return null;
  end;
$$;

--changeset celio:202

create trigger empenho_despesa_anual_trigger
before insert on scraper.empenho_despesa
for each row
  execute procedure insercao_empenho_despesas();

--changeset celio:203

create table if not exists scraper.empenho_original
(
  ori_id bigserial primary key,
  ori_evento character varying(15) not null,
  ori_valor numeric(20,2),
  ori_descricao character varying(100000),
  ori_lancamento timestamp without time zone not null default now(),
  ori_emp_ref_id bigint not null,
  ori_emp_ori_id bigint not null,
  foreign key (ori_emp_ref_id) references scraper.empenho (emp_id),
  foreign key (ori_emp_ori_id) references scraper.empenho (emp_id)
);

--rollback drop table scraper.empenho_original;

--changeset celio:204

create index if not exists empenho_original_ori_emp_ref_id_idx on scraper.empenho_original (ori_emp_ref_id);

--changeset celio:205

create index if not exists empenho_original_ori_emp_ori_id_idx on scraper.empenho_original (ori_emp_ori_id);
