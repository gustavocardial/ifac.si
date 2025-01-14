SET SESSION FOREIGN_KEY_CHECKS=0;

INSERT INTO usuarios (id, ativo, cargo, email, nome_usuario, senha)
VALUES
    (1, true, 'AUTOR', 'JS@gmail.com', 'joao.silva', 'senha123'),
    (2, true, 'EDITOR', 'MariaS@gmail.com', 'maria.santos', 'senha456'),
    (3, true, 'ADMIN', 'Pedrao@gmail.com', 'pedro.oliveira', 'senha789');

INSERT INTO tags (id, nome)
VALUES
    (1, 'Tecnologia'),
    (2, 'Esportes'),
    (3, 'Viagem');

INSERT INTO posts (id, data, legenda, texto, titulo, categoria_id, usuario_id)
VALUES
    (1, '2023-05-01', 'Introdução à Programação', 'Este é um post sobre programação...', 'Introdução à Programação', 1, 1),
    (2, '2023-05-02', 'Dicas para Correr Melhor', 'Aqui estão algumas dicas para melhorar sua corrida...', 'Dicas para Correr Melhor', 2, 2),
    (3, '2023-05-03', 'Destinos Incríveis para Viajar', 'Descubra os lugares mais incríveis para viajar...', 'Destinos Incríveis para Viajar', 3, 3);

INSERT INTO post_tags (posts_id, tags_id)
VALUES
    (1, 1), -- Post "Introdução à Programação" com Tag "Tecnologia"
    (2, 2), -- Post "Dicas para Correr Melhor" com Tag "Esportes"
    (3, 3); -- Post "Destinos Incríveis para Viajar" com Tag "Viagem"

INSERT INTO categorias (id, nome) VALUES
(1, 'Projeto'), (2, 'Vaga'), (3, 'Pesquisa');

SET SESSION FOREIGN_KEY_CHECKS=1;
