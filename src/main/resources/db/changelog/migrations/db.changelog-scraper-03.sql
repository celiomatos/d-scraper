--liquibase formatted sql

--changeset celio:124

create table if not exists scraper.empenho
(
  emp_id bigserial primary key,
  emp_nota character varying(11),
  emp_date date not null,
  emp_tipo character varying(255),
  emp_programa character varying(255),
  emp_funcao character varying(255),
  emp_sub_funcao character varying(255),
  emp_licitacao character varying(255),
  emp_referencia character varying(255),
  emp_processo character varying(255),
  emp_descricao character varying(100000),
  emp_lancamento timestamp without time zone not null default now(),
  emp_cre_id bigint not null,
  emp_org_id bigint not null,
  emp_fon_id character varying(8) not null,
  foreign key (emp_cre_id) references scraper.credor (cre_id),
  foreign key (emp_fon_id) references scraper.fonte (fon_id),
  foreign key (emp_org_id) references scraper.orgao (org_id)
);

--rollback drop table scraper.empenho;

--changeset celio:125

create table if not exists scraper.empenho_2011
(
   check (emp_date <= date '2011-12-31')
)
inherits (scraper.empenho);

--changeset celio:126

create table if not exists scraper.empenho_2012
(
   check (emp_date >= date '2012-01-01' and emp_date <= date '2012-12-31')
)
inherits (scraper.empenho);

--changeset celio:127

create table if not exists scraper.empenho_2013
(
   check (emp_date >= date '2013-01-01' and emp_date <= date '2013-12-31')
)
inherits (scraper.empenho);

--changeset celio:128

create table if not exists scraper.empenho_2014
(
   check (emp_date >= date '2014-01-01' and emp_date <= date '2014-12-31')
)
inherits (scraper.empenho);

--changeset celio:129

create table if not exists scraper.empenho_2015
(
   check (emp_date >= date '2015-01-01' and emp_date <= date '2015-12-31')
)
inherits (scraper.empenho);

--changeset celio:130

create table if not exists scraper.empenho_2016
(
   check (emp_date >= date '2016-01-01' and emp_date <= date '2016-12-31')
)
inherits (scraper.empenho);

--changeset celio:131

create table if not exists scraper.empenho_2017
(
   check (emp_date >= date '2017-01-01' and emp_date <= date '2017-12-31')
)
inherits (scraper.empenho);

--changeset celio:132

create table if not exists scraper.empenho_2018
(
   check (emp_date >= date '2018-01-01' and emp_date <= date '2018-12-31')
)
inherits (scraper.empenho);

--changeset celio:133

create table if not exists scraper.empenho_2019
(
   check (emp_date >= date '2019-01-01' and emp_date <= date '2019-12-31')
)
inherits (scraper.empenho);

--changeset celio:134

create index if not exists empenho_2011_emp_date_idx on scraper.empenho_2011 (emp_date);

--changeset celio:135

create index if not exists empenho_2011_emp_org_id_idx on scraper.empenho_2011 (emp_org_id);

--changeset celio:136

create index if not exists empenho_2011_emp_cre_id_idx on scraper.empenho_2011 (emp_cre_id);

--changeset celio:137

create index if not exists empenho_2011_emp_fon_id_idx on scraper.empenho_2011 (emp_fon_id);

--changeset celio:138

create index if not exists empenho_2012_emp_date_idx on scraper.empenho_2012 (emp_date);

--changeset celio:139

create index if not exists empenho_2012_emp_org_id_idx on scraper.empenho_2012 (emp_org_id);

--changeset celio:140

create index if not exists empenho_2012_emp_cre_id_idx on scraper.empenho_2012 (emp_cre_id);

--changeset celio:141

create index if not exists empenho_2012_emp_fon_id_idx on scraper.empenho_2012 (emp_fon_id);

--changeset celio:142

create index if not exists empenho_2013_emp_date_idx on scraper.empenho_2013 (emp_date);

--changeset celio:143

create index if not exists empenho_2013_emp_org_id_idx on scraper.empenho_2013 (emp_org_id);

--changeset celio:144

create index if not exists empenho_2013_emp_cre_id_idx on scraper.empenho_2013 (emp_cre_id);

--changeset celio:145

create index if not exists empenho_2013_emp_fon_id_idx on scraper.empenho_2013 (emp_fon_id);

--changeset celio:146

create index if not exists empenho_2014_emp_date_idx on scraper.empenho_2014 (emp_date);

--changeset celio:147

create index if not exists empenho_2014_emp_org_id_idx on scraper.empenho_2014 (emp_org_id);

--changeset celio:148

create index if not exists empenho_2014_emp_cre_id_idx on scraper.empenho_2014 (emp_cre_id);

--changeset celio:149

create index if not exists empenho_2014_emp_fon_id_idx on scraper.empenho_2014 (emp_fon_id);

--changeset celio:150

create index if not exists empenho_2015_emp_date_idx on scraper.empenho_2015 (emp_date);

--changeset celio:151

create index if not exists empenho_2015_emp_org_id_idx on scraper.empenho_2015 (emp_org_id);

--changeset celio:152

create index if not exists empenho_2015_emp_cre_id_idx on scraper.empenho_2015 (emp_cre_id);

--changeset celio:153

create index if not exists empenho_2015_emp_fon_id_idx on scraper.empenho_2015 (emp_fon_id);

--changeset celio:154

create index if not exists empenho_2016_emp_date_idx on scraper.empenho_2016 (emp_date);

--changeset celio:155

create index if not exists empenho_2016_emp_org_id_idx on scraper.empenho_2016 (emp_org_id);

--changeset celio:156

create index if not exists empenho_2016_emp_cre_id_idx on scraper.empenho_2016 (emp_cre_id);

--changeset celio:157

create index if not exists empenho_2016_emp_fon_id_idx on scraper.empenho_2016 (emp_fon_id);

--changeset celio:158

create index if not exists empenho_2017_emp_date_idx on scraper.empenho_2017 (emp_date);

--changeset celio:159

create index if not exists empenho_2017_emp_org_id_idx on scraper.empenho_2017 (emp_org_id);

--changeset celio:160

create index if not exists empenho_2017_emp_cre_id_idx on scraper.empenho_2017 (emp_cre_id);

--changeset celio:161

create index if not exists empenho_2017_emp_fon_id_idx on scraper.empenho_2017 (emp_fon_id);

--changeset celio:163

create index if not exists empenho_2018_emp_date_idx on scraper.empenho_2018 (emp_date);

--changeset celio:164

create index if not exists empenho_2018_emp_org_id_idx on scraper.empenho_2018 (emp_org_id);

--changeset celio:165

create index if not exists empenho_2018_emp_cre_id_idx on scraper.empenho_2018 (emp_cre_id);

--changeset celio:166

create index if not exists empenho_2018_emp_fon_id_idx on scraper.empenho_2018 (emp_fon_id);

--changeset celio:167

create index if not exists empenho_2019_emp_date_idx on scraper.empenho_2019 (emp_date);

--changeset celio:168

create index if not exists empenho_2019_emp_org_id_idx on scraper.empenho_2019 (emp_org_id);

--changeset celio:169

create index if not exists empenho_2019_emp_cre_id_idx on scraper.empenho_2019 (emp_cre_id);

--changeset celio:170

create index if not exists empenho_2019_emp_fon_id_idx on scraper.empenho_2019 (emp_fon_id);

--changeset celio:171 splitStatements:false

create or replace function insercao_empenhos() returns trigger language plpgsql as $$
  begin
  if (new.emp_date <= date '2011-12-31') then
	  insert into scraper.empenho_2011 values (new.*);
  elseif (new.emp_date >= date '2012-01-01' and new.emp_date <= date '2012-12-31') then
	  insert into scraper.empenho_2012 values (new.*);
  elseif (new.emp_date >= date '2013-01-01' and new.emp_date <= date '2013-12-31') then
	  insert into scraper.empenho_2013 values (new.*);
  elseif (new.emp_date >= date '2014-01-01' and new.emp_date <= date '2014-12-31') then
	  insert into scraper.empenho_2014 values (new.*);
  elseif (new.emp_date >= date '2015-01-01' and new.emp_date <= date '2015-12-31') then
	  insert into scraper.empenho_2015 values (new.*);
  elseif (new.emp_date >= date '2016-01-01' and new.emp_date <= date '2016-12-31') then
	  insert into scraper.empenho_2016 values (new.*);
  elseif (new.emp_date >= date '2016-01-01' and new.emp_date <= date '2017-12-31') then
	  insert into scraper.empenho_2017 values (new.*);
  elseif (new.emp_date >= date '2018-01-01' and new.emp_date <= date '2018-12-31') then
	  insert into scraper.empenho_2018 values (new.*);
  elseif (new.emp_date >= date '2019-01-01' and new.emp_date <= date '2019-12-31') then
	  insert into scraper.empenho_2019 values (new.*);
  else
	  raise exception 'a data não se encontra nos limites estabelecidos para a inserção...';
  end if;
  return null;
  end;
$$;

--changeset celio:172

create trigger empenho_anual_trigger
before insert on scraper.empenho
for each row
  execute procedure insercao_empenhos();