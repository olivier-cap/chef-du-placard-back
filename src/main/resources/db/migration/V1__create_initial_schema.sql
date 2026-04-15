CREATE TABLE unite (
                       id BIGSERIAL PRIMARY KEY,
                       code VARCHAR(20) NOT NULL UNIQUE,
                       nom VARCHAR(100) NOT NULL
);

CREATE TABLE aliment (
                         id BIGSERIAL PRIMARY KEY,
                         nom VARCHAR(150) NOT NULL UNIQUE,
                         description TEXT,
                         actif BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE recette (
                         id BIGSERIAL PRIMARY KEY,
                         nom VARCHAR(200) NOT NULL UNIQUE,
                         instructions TEXT NOT NULL,
                         duree_minutes INTEGER,
                         difficulte VARCHAR(50)
);

CREATE TABLE ingredient (
                            id BIGSERIAL PRIMARY KEY,
                            recette_id BIGINT NOT NULL REFERENCES recette(id) ON DELETE CASCADE,
                            aliment_id BIGINT NOT NULL REFERENCES aliment(id),
                            unite_id BIGINT NOT NULL REFERENCES unite(id),
                            quantite_par_personne NUMERIC(10,2) NOT NULL CHECK (quantite_par_personne > 0),
                            CONSTRAINT uk_ingredient_recette_aliment UNIQUE (recette_id, aliment_id)
);

CREATE TABLE stock (
                       id BIGSERIAL PRIMARY KEY,
                       nom VARCHAR(100) NOT NULL
);

CREATE TABLE stock_line (
                            id BIGSERIAL PRIMARY KEY,
                            stock_id BIGINT NOT NULL REFERENCES stock(id) ON DELETE CASCADE,
                            aliment_id BIGINT NOT NULL REFERENCES aliment(id),
                            unite_id BIGINT NOT NULL REFERENCES unite(id),
                            quantite NUMERIC(10,2) NOT NULL CHECK (quantite >= 0),
                            CONSTRAINT uk_stock_line UNIQUE (stock_id, aliment_id, unite_id)
);

INSERT INTO stock (nom) VALUES ('stock principal');