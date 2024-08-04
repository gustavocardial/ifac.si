SET SESSION FOREIGN_KEY_CHECKS=0;

INSERT INTO Usuario (id, ativo, cargo, nome_completo, nome_usuario, senha)
VALUES
    (1, true, 'AUTOR', 'João Silva', 'joao.silva', 'senha123'),
    (2, true, 'EDITOR', 'Maria Santos', 'maria.santos', 'senha456'),
    (3, true, 'ADMIN', 'Pedro Oliveira', 'pedro.oliveira', 'senha789');

INSERT INTO Tag (id, nome)
VALUES
    (1, 'Tecnologia'),
    (2, 'Esportes'),
    (3, 'Viagem');

INSERT INTO Post (id, data, legenda, texto, titulo, categoria_id, usuario_id)
VALUES
    (1, '2023-05-01', 'Introdução à Programação', 'Este é um post sobre programação...', 'Introdução à Programação', 1, 1),
    (2, '2023-05-02', 'Dicas para Correr Melhor', 'Aqui estão algumas dicas para melhorar sua corrida...', 'Dicas para Correr Melhor', 2, 2),
    (3, '2023-05-03', 'Destinos Incríveis para Viajar', 'Descubra os lugares mais incríveis para viajar...', 'Destinos Incríveis para Viajar', 3, 3);

INSERT INTO post_tags (posts_id, tags_id)
VALUES
    (1, 1), -- Post "Introdução à Programação" com Tag "Tecnologia"
    (2, 2), -- Post "Dicas para Correr Melhor" com Tag "Esportes"
    (3, 3); -- Post "Destinos Incríveis para Viajar" com Tag "Viagem"

INSERT INTO Categoria (id, nome) VALUES 
(1, 'Projeto'), (2, 'Vaga'), (3, 'Pesquisa');

SET SESSION FOREIGN_KEY_CHECKS=1;
