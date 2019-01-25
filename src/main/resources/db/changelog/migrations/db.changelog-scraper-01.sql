--liquibase formatted sql

--changeset celio:12
CREATE SCHEMA scraper;

--rollback drop schema scraper;

--changeset celio:13

CREATE TABLE scraper.orgao
(
  org_id bigserial PRIMARY KEY,
  org_codigo character varying(6),
  org_sigla character varying(30),
  org_orgao character varying(150) NOT NULL,
  org_nome character varying(150),
  org_esfera smallint NOT NULL
);

--rollback drop table scraper.orgao;

--changeset celio:14

CREATE TABLE scraper.fonte
(
  fon_id character varying(8) PRIMARY KEY,
  fon_nome character varying(100) NOT NULL
);

--rollback drop table scraper.fonte;

--changeset celio:15

CREATE TABLE scraper.credor
(
  cre_id bigserial PRIMARY KEY,
  cre_nome character varying(100) NOT NULL UNIQUE,
  cre_codigo character varying(20)
);

--rollback drop table scraper.credor;

--changeset celio:16

CREATE TABLE scraper.classificacao
(
  cla_id bigserial PRIMARY KEY,
  cla_codigo character varying(10),
  cla_nome character varying(100)
);

--rollback drop table scraper.classificacao;

--changeset celio:17

CREATE TABLE scraper.pagamento
(
  pag_id bigserial PRIMARY KEY,
  pag_date date NOT NULL,
  pag_nr_ob character varying(15),
  pag_nr_nl character varying(15),
  pag_nr_ne character varying(15),
  pag_valor numeric(20,2) NOT NULL,
  pag_org_id bigint NOT NULL,
  pag_cre_id bigint NOT NULL,
  pag_fon_id character varying(8) NOT NULL,
  pag_cla_id bigint,
  pag_lancamento timestamp without time zone NOT NULL DEFAULT now(),
  pag_removido boolean NOT NULL DEFAULT false,
  FOREIGN KEY (pag_cla_id) REFERENCES scraper.classificacao (cla_id),
  FOREIGN KEY (pag_cre_id) REFERENCES scraper.credor (cre_id),
  FOREIGN KEY (pag_fon_id) REFERENCES scraper.fonte (fon_id),
  FOREIGN KEY (pag_org_id) REFERENCES scraper.orgao (org_id)
);

--rollback drop table scraper.pagamento;

--changeset celio:18

CREATE TABLE scraper.parametro
(
  par_id integer PRIMARY KEY,
  par_descricao character varying(100) NOT NULL,
  par_atual character varying(2048),
  par_padrao character varying(2048)
);

--rollback drop table scraper.parametro;
