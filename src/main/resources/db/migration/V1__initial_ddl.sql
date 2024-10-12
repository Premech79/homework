CREATE TABLE tb_user
(
    uuid UUID NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(129) NOT NULL,
    name VARCHAR(120) NULL,
    PRIMARY KEY (uuid)
);

CREATE UNIQUE INDEX "aa152fb1737749439313_ui" ON "tb_user" (email);

CREATE TABLE tb_user_external_project
(
    id UUID NOT NULL,
    user_id UUID NOT NULL,
    name VARCHAR(120) NOT NULL,
    PRIMARY KEY (id, user_id)
);

ALTER TABLE tb_user_external_project
    ADD CONSTRAINT "5097086b25e1443a9b44_fk" FOREIGN KEY (user_id) REFERENCES tb_user(uuid);
