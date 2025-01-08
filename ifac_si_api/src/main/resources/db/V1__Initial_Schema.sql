-- Criação da tabela de Usuários
CREATE TABLE usuarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    nome_usuario VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cargo ENUM('AUTOR', 'EDITOR', 'ADMIN') NOT NULL,
    ativo BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabela de Categorias
CREATE TABLE categorias (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
);

-- Tabela de Tags
CREATE TABLE tags (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(255) NOT NULL
);

-- Tabela de Posts
CREATE TABLE posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255),
    usuario_id BIGINT NOT NULL,
    categoria_id BIGINT,
    texto TEXT NOT NULL,
    data DATE NOT NULL,
    legenda TEXT,
    status ENUM('RASCUNHO', 'PENDENTE', 'PUBLICADO', 'ARQUIVADO'),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB;

-- Tabela de relacionamento entre Posts e Tags (Many-to-Many)
CREATE TABLE posts_tags (
    post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (post_id, tag_id),
    FOREIGN KEY (post_id) REFERENCES posts(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
) ENGINE=InnoDB;

-- Tabela de Imagens
CREATE TABLE imagens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255) NOT NULL,
    nome_arquivo VARCHAR(255) NOT NULL,
    tamanho BIGINT NOT NULL,
    data_upload DATE NOT NULL,
    post_id BIGINT NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
) ENGINE=InnoDB;