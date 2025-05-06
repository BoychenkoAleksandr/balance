CREATE SEQUENCE IF NOT EXISTS public.users_seq START WITH 1;

CREATE TABLE IF NOT EXISTS public.users (
    id                  BIGINT DEFAULT nextval('public.users_seq') PRIMARY KEY,
    username            VARCHAR(40)     NOT NULL,
    password            VARCHAR(80)     NOT NULL,
    name                VARCHAR(20)     NOT NULL,
    surname             VARCHAR(20)     NOT NULL,
    parent_name         VARCHAR(20)     NOT NULL,
    creation_date       TIMESTAMP       NOT NULL,
    last_edit_date      TIMESTAMP       NOT NULL,
    role                VARCHAR(20)     NOT NULL
    );

CREATE SEQUENCE IF NOT EXISTS public.news_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.news (
    id                  BIGINT DEFAULT nextval('public.news_seq') PRIMARY KEY,
    title               VARCHAR(150)    NOT NULL,
    text                VARCHAR(2000)   NOT NULL,
    creation_date       TIMESTAMP       NOT NULL,
    last_edit_date      TIMESTAMP       NOT NULL,
    inserted_by_id      BIGINT          NOT NULL,
    updated_by_id       BIGINT          NOT NULL,
    FOREIGN KEY (inserted_by_id) REFERENCES public.users (id),
    FOREIGN KEY (updated_by_id) REFERENCES public.users (id)
    );

CREATE SEQUENCE IF NOT EXISTS public.comments_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.comments (
    id                  BIGINT DEFAULT nextval('public.comments_seq') PRIMARY KEY,
    text                VARCHAR(300)    NOT NULL,
    creation_date       TIMESTAMP       NOT NULL,
    last_edit_date      TIMESTAMP       NOT NULL,
    inserted_by_id      BIGINT          NOT NULL,
    updated_by_id       BIGINT          NOT NULL,
    news_id             BIGINT          NOT NULL,
    FOREIGN KEY (inserted_by_id) REFERENCES public.users (id),
    FOREIGN KEY (updated_by_id) REFERENCES public.users (id),
    FOREIGN KEY (news_id) REFERENCES public.news (id)
    );