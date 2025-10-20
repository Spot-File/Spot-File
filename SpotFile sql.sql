CREATE DATABASE bd_spot_file;
USE bd_spot_file;

CREATE TABLE artista ( 
id_artista BIGINT AUTO_INCREMENT,
PRIMARY KEY (id_artista),
email VARCHAR(50) UNIQUE NOT NULL, 
senha VARCHAR(40) NOT NULL, 
nome VARCHAR(40) NOT NULL, 
about TEXT NOT NULL, 
foto_de_perfil_url TEXT NOT NULL
);

CREATE TABLE ouvinte(
id_ouvinte BIGINT AUTO_INCREMENT,
PRIMARY KEY (id_ouvinte),
email VARCHAR(50) UNIQUE NOT NULL, 
senha VARCHAR(40) NOT NULL, 
nome VARCHAR(40) NOT NULL, 
foto_de_perfil_url TEXT
);

CREATE TABLE album ( 
id_album BIGINT AUTO_INCREMENT, 
PRIMARY KEY (id_album),
ano_lancamento SMALLINT NOT NULL, 
nome VARCHAR(60) NOT NULL, 
foto_da_capa_url TEXT NOT NULL, 
tempo_de_streaming MEDIUMINT NOT NULL, 
id_artista BIGINT NOT NULL, 
FOREIGN KEY (id_artista) REFERENCES artista(id_artista)
);

CREATE TABLE musica ( 
id_musica BIGINT AUTO_INCREMENT, 
PRIMARY KEY (id_musica), 
nome VARCHAR(50) NOT NULL, 
duração SMALLINT NOT NULL, -- em segundos 
id_album BIGINT NOT NULL,
FOREIGN KEY (id_album) REFERENCES album(id_album)
);

CREATE TABLE playlist (
id_playlist BIGINT AUTO_INCREMENT, 
PRIMARY KEY (id_playlist), 
nome VARCHAR(60) DEFAULT "MyPlaylist#000", -- DEFAULT 'PLAYLIST#001'
foto_da_capa_url TEXT, 
bio TINYTEXT, 
tempo_de_streaming MEDIUMINT NOT NULL, 
id_ouvinte BIGINT NOT NULL, 
FOREIGN KEY (id_ouvinte) references ouvinte(id_ouvinte)
);

-- relacionamentos

CREATE TABLE fans( 
id_artista BIGINT NOT NULL, 
id_ouvinte BIGINT NOT NULL,
PRIMARY KEY (id_artista), 
FOREIGN KEY (id_ouvinte) REFERENCES ouvinte(id_ouvinte),
FOREIGN KEY (id_artista) REFERENCES artista(id_artista) 
); 

CREATE TABLE seguidores ( 
id_seguido BIGINT NOT NULL,
PRIMARY KEY (id_seguido), 
id_seguidores BIGINT NOT NULL, 
FOREIGN KEY (id_seguido) REFERENCES ouvinte(id_ouvinte),
FOREIGN KEY (id_seguidores) REFERENCES ouvinte(id_ouvinte)
);

CREATE TABLE salvo ( 
id_playlist BIGINT NOT NULL,
PRIMARY KEY (id_playlist), 
id_musica BIGINT NOT NULL, 
FOREIGN KEY (id_playlist) REFERENCES playlist(id_playlist),
FOREIGN KEY (id_musica) REFERENCES musica(id_musica)
);

ALTER TABLE album CHANGE tempo_de_streaming tempo_de_streaming MEDIUMINT DEFAULT(0); -- tinha uns erros de digitação, eu só mexi neles aqui, nao mudei o prompt antigo
ALTER TABLE musica CHANGE duração duracao SMALLINT NOT NULL;
ALTER TABLE playlist CHANGE tempo_de_streaming tempo_de_streaming MEDIUMINT DEFAULT(0);
ALTER TABLE playlist CHANGE nome nome VARCHAR(60) DEFAULT "MyPlaylist#0";
ALTER TABLE seguidores CHANGE id_seguidores id_seguidor BIGINT NOT NULL;
ALTER TABLE ouvinte CHANGE foto_de_perfil_url foto_de_perfil TEXT;
ALTER TABLE artista CHANGE foto_de_perfil_url foto_de_perfil TEXT;
ALTER TABLE seguidores CHANGE id_seguido id_seguido BIGINT NOT NULL;

SELECT id_musica FROM musica WHERE id_album = ?;
SELECT * FROM artista;
SELECT * FROM ouvinte;
SELECT * FROM seguidores;
SELECT * FROM fans;
SELECT * FROM album;
USE bd_spot_file;
SET GLOBAL max_connections = 300;



-- formato de INSERTS
-- INSERT INTO artista (email,senha,nome,about,foto_de_perfil_url) VALUES(?,?,?,?,?); 
-- INSERT INTO ouvinte (email,senha,nome,foto_de_perfil_url) VALUES(?,?,?,?); 
-- INSERT INTO album (ano_lancamento,nome,foto_da_capa_url,id_artista) VALUES(?,?,?,?); 
-- INSERT INTO musica (nome,duracao,id_album) VALUES(?,?,?); 
-- INSERT INTO playlist (nome,foto_da_capa_url,bio,tempo_de_streaming,id_ouvinte) VALUES(?,?,?,?,?); 
-- INSERT INTO fans (id_artista,id_ouvinte) VALUES(?,?); 
-- INSERT INTO seguidores (id_seguido,id_seguidor) VALUES(?,?); 
-- INSERT INTO salvo (id_playlist,id_musica) VALUES(?,?);

-- formato de EXCLUSÕES
-- DELETE FROM artista WHERE id_artista = ?;
-- DELETE FROM ouvinte WHERE id_ouvinte = ?;
-- DELETE FROM album WHERE id_album = ?;
-- DELETE FROM musica WHERE id_musica = ?;
-- DELETE FROM playlist WHERE id_playlist = ?;
-- DELETE FROM fans WHERE id_ouvinte = ? AND id_artista = ?;
-- DELETE FROM seguidores WHERE id_seguidor = ? AND id_seguido = ?;
-- DELETE FROM salvo WHERE id_musica = ? AND id_playlist = ?; 

-- formato de ATUALIZAÇÕES
-- ARTISTA
-- UPDATE artista SET nome = ?, email = ?, senha = ?, about = ?, foto_de_perfil_url = ? WHERE id_artista =  ?;

-- OUVINTE
-- UPDATE ouvinte SET nome = ?,email = ?, senha = ?, foto_de_perfil = ? WHERE id_ouvinte = ?;

-- ALBUM
-- UPDATE album SET nome = ?, ano_lancamento = ?, foto_da_capa_url = ?, tempo_de_streaming = ? WHERE id_album = ?;

-- MUSICA 
-- UPDATE musica SET nome = ?, duracao = ?, id_album = ? WHERE id_musica = ?;

-- PLAYLIST
-- UPDATE playlist SET nome = ?,foto_da_capa_url = ?, bio = ?, tempo_de_streaming = ? WHERE id_playlist = ?;
-- -- -- no tempoStreaming --> .calcStreaming();

-- RELACIONAMENTOS
-- Não acho que tenha como fazer um update além de deletar e adicionar.

-- formato de BUSCAS
-- não sei como funciona ainda
-- não fiz
