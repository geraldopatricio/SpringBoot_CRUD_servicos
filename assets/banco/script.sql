-- Database: servicos

-- DROP DATABASE IF EXISTS servicos;

CREATE DATABASE servicos
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
-- Table: public.servicos

-- DROP TABLE IF EXISTS public.servicos;

CREATE TABLE IF NOT EXISTS public.servicos
(
    id integer NOT NULL DEFAULT nextval('servicos_id_seq'::regclass),
    data_final timestamp without time zone,
    data_inicial timestamp without time zone,
    nome character varying(255) COLLATE pg_catalog."default",
    observacoes character varying(255) COLLATE pg_catalog."default",
    laboratorio_id integer,
    propriedade_id integer,
    CONSTRAINT servicos_pkey PRIMARY KEY (id),
    CONSTRAINT fk67aqximt9f0synrehj2s0t3gw FOREIGN KEY (propriedade_id)
        REFERENCES public.propriedade (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fksry378mc4rwfuwrmflhh2mb30 FOREIGN KEY (laboratorio_id)
        REFERENCES public.laboratorio (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.servicos
    OWNER to postgres;
	
	
CREATE TABLE IF NOT EXISTS public.propriedade
(
    id integer NOT NULL DEFAULT nextval('propriedade_id_seq'::regclass),
    cnpj character varying(255) COLLATE pg_catalog."default",
    nome character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT propriedade_pkey PRIMARY KEY (id),
    CONSTRAINT uk_c78staohge8viewova3r3vcof UNIQUE (cnpj),
    CONSTRAINT uk_lm8kpewh79inq9a2l6re9jrwm UNIQUE (nome)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.propriedade
    OWNER to postgres;
	
	
	
CREATE TABLE IF NOT EXISTS public.laboratorio
(
    id integer NOT NULL DEFAULT nextval('laboratorio_id_seq'::regclass),
    nome character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT laboratorio_pkey PRIMARY KEY (id),
    CONSTRAINT uk_2vq3skin7dtuinocj6d1dw5uh UNIQUE (nome)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.laboratorio
    OWNER to postgres;