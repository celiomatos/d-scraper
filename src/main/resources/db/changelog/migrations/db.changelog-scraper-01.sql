--liquibase formatted sql

--changeset celio:16
create schema if not exists scraper;

--rollback drop schema scraper;

--changeset celio:17

create table if not exists scraper.orgao
(
  org_id bigserial primary key,
  org_codigo character varying(6),
  org_sigla character varying(30),
  org_orgao character varying(150) not null,
  org_nome character varying(150),
  org_esfera smallint not null
);

--rollback drop table scraper.orgao;

--changeset celio:18

create table if not exists scraper.fonte
(
  fon_id character varying(8) primary key,
  fon_nome character varying(100) not null
);

--rollback drop table scraper.fonte;

--changeset celio:19

create table if not exists scraper.credor
(
  cre_id bigserial primary key,
  cre_nome character varying(100) not null unique,
  cre_codigo character varying(20)
);

--rollback drop table scraper.credor;

--changeset celio:20

create table if not exists scraper.classificacao
(
  cla_id bigserial primary key,
  cla_codigo character varying(10),
  cla_nome character varying(100)
);

--rollback drop table scraper.classificacao;

--changeset celio:21

create table if not exists scraper.pagamento
(
  pag_id bigserial primary key,
  pag_date date not null,
  pag_nr_ob character varying(15),
  pag_nr_nl character varying(15),
  pag_nr_ne character varying(15),
  pag_valor numeric(20,2) not null,
  pag_org_id bigint not null,
  pag_cre_id bigint not null,
  pag_fon_id character varying(8) not null,
  pag_cla_id bigint,
  pag_lancamento timestamp without time zone not null default now(),
  pag_removido boolean not null default false,
  foreign key (pag_cla_id) references scraper.classificacao (cla_id),
  foreign key (pag_cre_id) references scraper.credor (cre_id),
  foreign key (pag_fon_id) references scraper.fonte (fon_id),
  foreign key (pag_org_id) references scraper.orgao (org_id)
);

--rollback drop table scraper.pagamento;

--changeset celio:22

create table if not exists scraper.parametro
(
  par_id integer primary key,
  par_descricao character varying(100) not null,
  par_atual character varying(2048),
  par_padrao character varying(2048)
);

--rollback drop table scraper.parametro;

--changeset celio:23

create index if not exists classificacao_cla_nome_idx on scraper.classificacao (cla_nome);

--changeset celio:24

create index if not exists classificacao_cla_codigo_idx on scraper.classificacao (cla_codigo);

--changeset celio:25

create index if not exists fonte_fon_nome_idx on scraper.fonte (fon_nome);

--changeset celio:26

create index if not exists orgao_org_codigo_idx on scraper.orgao (org_codigo);

--changeset celio:27

create index if not exists orgao_org_sigla_idx on scraper.orgao (org_sigla);

--changeset celio:28

create index if not exists orgao_org_orgao_idx on scraper.orgao (org_orgao);

--changeset celio:29

create table if not exists scraper.pagamento_2011
(
   check (pag_date <= date '2011-12-31')
)
inherits (scraper.pagamento);

--changeset celio:30

create table if not exists scraper.pagamento_2012
(
   check (pag_date >= date '2012-01-01' and pag_date <= date '2012-12-31')
)
inherits (scraper.pagamento);

--changeset celio:31

create table if not exists scraper.pagamento_2013
(
   check (pag_date >= date '2013-01-01' and pag_date <= date '2013-12-31')
)
inherits (scraper.pagamento);

--changeset celio:32

create table if not exists scraper.pagamento_2014
(
   check (pag_date >= date '2014-01-01' and pag_date <= date '2014-12-31')
)
inherits (scraper.pagamento);

--changeset celio:33

create table if not exists scraper.pagamento_2015
(
   check (pag_date >= date '2015-01-01' and pag_date <= date '2015-12-31')
)
inherits (scraper.pagamento);

--changeset celio:34

create table if not exists scraper.pagamento_2016
(
   check (pag_date >= date '2016-01-01' and pag_date <= date '2016-12-31')
)
inherits (scraper.pagamento);

--changeset celio:35

create table if not exists scraper.pagamento_2017
(
   check (pag_date >= date '2017-01-01' and pag_date <= date '2017-12-31')
)
inherits (scraper.pagamento);

--changeset celio:36

create table if not exists scraper.pagamento_2018
(
   check (pag_date >= date '2018-01-01' and pag_date <= date '2018-12-31')
)
inherits (scraper.pagamento);

--changeset celio:37

create table if not exists scraper.pagamento_2019
(
   check (pag_date >= date '2019-01-01' and pag_date <= date '2019-12-31')
)
inherits (scraper.pagamento);

--changeset celio:38

create index if not exists pagamento_2011_pag_date_idx on scraper.pagamento_2011 (pag_date);

--changeset celio:39

create index if not exists pagamento_2011_pag_org_id_idx on scraper.pagamento_2011 (pag_org_id);

--changeset celio:40

create index if not exists pagamento_2011_pag_cre_id_idx on scraper.pagamento_2011 (pag_cre_id);

--changeset celio:41

create index if not exists pagamento_2011_pag_fon_id_idx on scraper.pagamento_2011 (pag_fon_id);

--changeset celio:42

create index if not exists pagamento_2011_pag_cla_id_idx on scraper.pagamento_2011 (pag_cla_id);

--changeset celio:43

create index if not exists pagamento_2012_pag_date_idx on scraper.pagamento_2012 (pag_date);

--changeset celio:44

create index if not exists pagamento_2012_pag_org_id_idx on scraper.pagamento_2012 (pag_org_id);

--changeset celio:45

create index if not exists pagamento_2012_pag_cre_id_idx on scraper.pagamento_2012 (pag_cre_id);

--changeset celio:46

create index if not exists pagamento_2012_pag_fon_id_idx on scraper.pagamento_2012 (pag_fon_id);

--changeset celio:47

create index if not exists pagamento_2012_pag_cla_id_idx on scraper.pagamento_2012 (pag_cla_id);

--changeset celio:48

create index if not exists pagamento_2013_pag_date_idx on scraper.pagamento_2013 (pag_date);

--changeset celio:49

create index if not exists pagamento_2013_pag_org_id_idx on scraper.pagamento_2013 (pag_org_id);

--changeset celio:50

create index if not exists pagamento_2013_pag_cre_id_idx on scraper.pagamento_2013 (pag_cre_id);

--changeset celio:51

create index if not exists pagamento_2013_pag_fon_id_idx on scraper.pagamento_2013 (pag_fon_id);

--changeset celio:52

create index if not exists pagamento_2013_pag_cla_id_idx on scraper.pagamento_2013 (pag_cla_id);

--changeset celio:53

create index if not exists pagamento_2014_pag_date_idx on scraper.pagamento_2014 (pag_date);

--changeset celio:54

create index if not exists pagamento_2014_pag_org_id_idx on scraper.pagamento_2014 (pag_org_id);

--changeset celio:55

create index if not exists pagamento_2014_pag_cre_id_idx on scraper.pagamento_2014 (pag_cre_id);

--changeset celio:56

create index if not exists pagamento_2014_pag_fon_id_idx on scraper.pagamento_2014 (pag_fon_id);

--changeset celio:57

create index if not exists pagamento_2014_pag_cla_id_idx on scraper.pagamento_2014 (pag_cla_id);

--changeset celio:58

create index if not exists pagamento_2015_pag_date_idx on scraper.pagamento_2015 (pag_date);

--changeset celio:59

create index if not exists pagamento_2015_pag_org_id_idx on scraper.pagamento_2015 (pag_org_id);

--changeset celio:60

create index if not exists pagamento_2015_pag_cre_id_idx on scraper.pagamento_2015 (pag_cre_id);

--changeset celio:61

create index if not exists pagamento_2015_pag_fon_id_idx on scraper.pagamento_2015 (pag_fon_id);

--changeset celio:62

create index if not exists pagamento_2015_pag_cla_id_idx on scraper.pagamento_2015 (pag_cla_id);

--changeset celio:63

create index if not exists pagamento_2016_pag_date_idx on scraper.pagamento_2016 (pag_date);

--changeset celio:64

create index if not exists pagamento_2016_pag_org_id_idx on scraper.pagamento_2016 (pag_org_id);

--changeset celio:65

create index if not exists pagamento_2016_pag_cre_id_idx on scraper.pagamento_2016 (pag_cre_id);

--changeset celio:66

create index if not exists pagamento_2016_pag_fon_id_idx on scraper.pagamento_2016 (pag_fon_id);

--changeset celio:67

create index if not exists pagamento_2016_pag_cla_id_idx on scraper.pagamento_2016 (pag_cla_id);

--changeset celio:68

create index if not exists pagamento_2017_pag_date_idx on scraper.pagamento_2017 (pag_date);

--changeset celio:69

create index if not exists pagamento_2017_pag_org_id_idx on scraper.pagamento_2017 (pag_org_id);

--changeset celio:70

create index if not exists pagamento_2017_pag_cre_id_idx on scraper.pagamento_2017 (pag_cre_id);

--changeset celio:71

create index if not exists pagamento_2017_pag_fon_id_idx on scraper.pagamento_2017 (pag_fon_id);

--changeset celio:72

create index if not exists pagamento_2017_pag_cla_id_idx on scraper.pagamento_2017 (pag_cla_id);

--changeset celio:73

create index if not exists pagamento_2018_pag_date_idx on scraper.pagamento_2018 (pag_date);

--changeset celio:74

create index if not exists pagamento_2018_pag_org_id_idx on scraper.pagamento_2018 (pag_org_id);

--changeset celio:75

create index if not exists pagamento_2018_pag_cre_id_idx on scraper.pagamento_2018 (pag_cre_id);

--changeset celio:76

create index if not exists pagamento_2018_pag_fon_id_idx on scraper.pagamento_2018 (pag_fon_id);

--changeset celio:77

create index if not exists pagamento_2018_pag_cla_id_idx on scraper.pagamento_2018 (pag_cla_id);

--changeset celio:78

create index if not exists pagamento_2019_pag_date_idx on scraper.pagamento_2019 (pag_date);

--changeset celio:79

create index if not exists pagamento_2019_pag_org_id_idx on scraper.pagamento_2019 (pag_org_id);

--changeset celio:80

create index if not exists pagamento_2019_pag_cre_id_idx on scraper.pagamento_2019 (pag_cre_id);

--changeset celio:81

create index if not exists pagamento_2019_pag_fon_id_idx on scraper.pagamento_2019 (pag_fon_id);

--changeset celio:82

create index if not exists pagamento_2019_pag_cla_id_idx on scraper.pagamento_2019 (pag_cla_id);
