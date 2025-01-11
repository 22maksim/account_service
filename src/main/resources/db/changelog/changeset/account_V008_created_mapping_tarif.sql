-- Создание таблицы для типов операций
CREATE TABLE type_operation
(
    id         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name       VARCHAR(255) NOT NULL UNIQUE,
    created_at timestamp with time zone
);

CREATE TABLE percents
(
    id      BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    percent NUMERIC(5, 2),
    name    VARCHAR(150)
);

-- Создание таблицы для мапингов типов операций на процент
CREATE TABLE operation_type_percents
(
    id                BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    operation_type_id BIGINT NOT NULL,
    percents_id       BIGINT NOT NULL,
    CONSTRAINT fk_operation_type FOREIGN KEY (operation_type_id) REFERENCES operation_type (id) ON DELETE CASCADE,
    CONSTRAINT fk_percents FOREIGN KEY (percents_id) REFERENCES percents (id) ON DELETE CASCADE
);

-- связь списка в тарифе с промежуточной таблицей которая связывает с operation_type_percents. Чтобы избавиться от json
-- это если бизнес не требует избавиться от лишних таблиц, которых можно избежать в логике
CREATE TABLE tariff_operation_type_percents
(
    id                         BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tariff_cashback_id         BIGINT NOT NULL,
    operation_type_percents_id BIGINT NOT NULL,
    CONSTRAINT fk_tariff_operation_type FOREIGN KEY (tariff_cashback_id) REFERENCES tariff_cashback (id) ON DELETE CASCADE,
    CONSTRAINT fk_operation_type_percents FOREIGN KEY (operation_type_percents_id) REFERENCES operation_type_percents ON DELETE CASCADE
);

-- Создание таблицы для продавцов
CREATE TABLE merchant
(
    id   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Создание таблицы для маппингов продавцов на процент
CREATE TABLE merchant_percents
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    merchant_id BIGINT NOT NULL,
    percents_id BIGINT NOT NULL,
    CONSTRAINT fk_merchant_percent FOREIGN KEY (merchant_id) REFERENCES merchant (id) ON DELETE CASCADE,
    CONSTRAINT fk_percent_merchant FOREIGN KEY (percents_id) REFERENCES percents (id) ON DELETE CASCADE
);

CREATE TABLE tariff_merchant_percents
(
    id                   BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    tariff_cashback_id   BIGINT NOT NULL,
    merchant_percents_id BIGINT NOT NULL,
    CONSTRAINT fk_tariff_merchant FOREIGN KEY (tariff_cashback_id) REFERENCES tariff_cashback (id) ON DELETE CASCADE
);

--Таблица tariff_cashback
CREATE TABLE tariff_cashback
(
    id          BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    description VARCHAR(150),
    created_at  TIMESTAMP WITH TIME ZONE
)




