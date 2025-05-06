CREATE SEQUENCE IF NOT EXISTS public.users_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.users (
    id                  BIGINT DEFAULT nextval('public.users_seq') PRIMARY KEY,
    username            VARCHAR(500)    NOT NULL,
    password            VARCHAR(500)    NOT NULL,
    date_of_birth       DATE            NOT NULL
    );

CREATE SEQUENCE IF NOT EXISTS public.account_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.account (
    id                  BIGINT DEFAULT nextval('public.account_seq') PRIMARY KEY,
    user_id             BIGINT          NOT NULL    UNIQUE,
    start_balance       DECIMAL         NOT NULL,
    balance             DECIMAL         NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.users (id)
    );

CREATE SEQUENCE IF NOT EXISTS public.email_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.email (
    id                  BIGINT DEFAULT nextval('public.email_seq') PRIMARY KEY,
    user_id             BIGINT          NOT NULL,
    email               VARCHAR(200)    NOT NULL    UNIQUE,
    FOREIGN KEY (user_id) REFERENCES public.users (id)
    );

CREATE SEQUENCE IF NOT EXISTS public.phone_seq START WITH 1;
CREATE TABLE IF NOT EXISTS public.phone (
    id                  BIGINT DEFAULT nextval('public.phone_seq') PRIMARY KEY,
    user_id             BIGINT          NOT NULL,
    phone               VARCHAR(13)     NOT NULL    UNIQUE,
    FOREIGN KEY (user_id) REFERENCES public.users (id)
    );