CREATE TABLE tb_user
(
    id UUID NOT NULL,
    email VARCHAR(200) NOT NULL,
    password VARCHAR(129) NOT NULL,
    name VARCHAR(120) NULL,
    deleted_at TIMESTAMP NULL,
    PRIMARY KEY (id)
);

CREATE UNIQUE INDEX "aa152fb1737749439313_ui" ON "tb_user" (email);

CREATE TABLE tb_external_project
(
    id UUID NOT NULL,
    name VARCHAR(120) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE tb_user_external_project
(
    external_project_id UUID NOT NULL,
    user_id UUID NOT NULL,
    PRIMARY KEY (external_project_id, user_id)
);

ALTER TABLE tb_user_external_project
    ADD CONSTRAINT "5097086b25e1443a9b44_fk" FOREIGN KEY (user_id) REFERENCES tb_user(id),
    ADD CONSTRAINT "d9e685729b0342b6a1cd_fk" FOREIGN KEY (external_project_id) REFERENCES tb_external_project(id);
