SET SESSION FOREIGN_KEY_CHECKS=0;

INSERT INTO Usuario (nomeCompleto, nomeUsuario, senha, cargo, ativo)
VALUES
    ('João Silva', 'joao.silva', 'senha123', 'AUTOR', true),
    ('Maria Santos', 'maria.santos', 'senha456', 'EDITOR', true),
    ('Pedro Oliveira', 'pedro.oliveira', 'senha789', 'ADMIN', true);

INSERT INTO Tag (nome)
VALUES
    ('Tecnologia'),
    ('Esportes'),
    ('Viagem');

INSERT INTO Post (titulo, usuario_id, categoria_id, texto, data, legenda)
VALUES
    ('Introdução à Programação', 1, 1, 'Este é um post sobre programação...', '2023-05-01', 'Programação'),
    ('Dicas para Correr Melhor', 2, 2, 'Aqui estão algumas dicas para melhorar sua corrida...', '2023-05-02', 'Esportes'),
    ('Destinos Incríveis para Viajar', 3, 3, 'Descubra os lugares mais incríveis para viajar...', '2023-05-03', 'Viagem');

SET SESSION FOREIGN_KEY_CHECKS=1;
