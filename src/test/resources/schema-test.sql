
CREATE SEQUENCE IF NOT EXISTS public.product_article_mapping_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 2147483647
    CACHE 1;

CREATE SEQUENCE IF NOT EXISTS public.product_id_seq
        INCREMENT 1
        START 1
        MINVALUE 1
        MAXVALUE 2147483647
        CACHE 1;

CREATE TABLE IF NOT EXISTS public.article
(
    art_id character varying(128) COLLATE pg_catalog."default" NOT NULL,
    name character varying(128) COLLATE pg_catalog."default" NOT NULL,
    stock integer DEFAULT 0,
    CONSTRAINT article_pkey PRIMARY KEY (art_id),
    CONSTRAINT st_ck CHECK (stock >= 0)
);

CREATE TABLE IF NOT EXISTS public.product
(
    id integer NOT NULL DEFAULT nextval('product_id_seq'::regclass),
    name character varying(128) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT product_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.product_article_mapping
(
    art_id character varying COLLATE pg_catalog."default",
    amount_of integer,
    id integer NOT NULL DEFAULT nextval('product_article_mapping_id_seq'::regclass),
    prod_id integer,
    CONSTRAINT product_article_mapping_pkey PRIMARY KEY (id),
    CONSTRAINT article_fk FOREIGN KEY (art_id)
        REFERENCES public.article (art_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT prod_fk FOREIGN KEY (prod_id)
        REFERENCES public.product (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);
INSERT INTO public.article(art_id, name, stock) VALUES ('10', 'article_10', 10);
INSERT INTO public.product(id, name) VALUES (100, 'productName_100');