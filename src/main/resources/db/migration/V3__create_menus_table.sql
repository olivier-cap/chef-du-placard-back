CREATE TABLE menu (
    id BIGSERIAL PRIMARY KEY,
    nom VARCHAR NOT NULL UNIQUE
);


CREATE TABLE menu_line (
    id BIGSERIAL PRIMARY KEY,
    menu_id BIGINT NOT NULL REFERENCES menu(id) ON DELETE CASCADE,
    recette_id BIGINT NOT NULL REFERENCES recette(id),
    nombre_personnes NUMERIC(10,2) NOT NULL CHECK ( nombre_personnes > 0 )
);