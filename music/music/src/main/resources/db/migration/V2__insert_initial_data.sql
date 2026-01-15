-- Inserir dados iniciais de Regionais
INSERT INTO regionais (nome, pais, continente, idioma_principal) VALUES
('América do Norte', 'Estados Unidos', 'Américas', 'Inglês'),
('América Latina', 'Brasil', 'Américas', 'Português'),
('Europa', 'Reino Unido', 'Europa', 'Inglês'),
('Ásia', 'Japão', 'Ásia', 'Japonês');

-- Inserir dados iniciais de Gêneros
INSERT INTO generos (nome, descricao, origem, decada_popularizacao) VALUES
('Rock', 'Gênero musical popular que se originou no rock and roll', 'Estados Unidos', '1950'),
('Metal', 'Gênero do rock que se desenvolveu no final dos anos 1960', 'Reino Unido', '1970'),
('Sertanejo', 'Gênero musical brasileiro com raízes na música caipira', 'Brasil', '1990'),
('Pop', 'Música popular caracterizada por elementos atraentes e comercializáveis', 'Estados Unidos', '1960'),
('Alternative Rock', 'Subgênero do rock que surgiu na década de 1980', 'Estados Unidos', '1990'),
('Rap Rock', 'Fusão de hip hop e rock', 'Estados Unidos', '1990'),
('MPB', 'Música Popular Brasileira', 'Brasil', '1960');

-- Inserir dados iniciais de Artistas
INSERT INTO artistas (nome, descricao, tipo, pais_origem, website, regional_id) VALUES
('Serj Tankian', 'Vocalista, compositor e multi-instrumentista armênio-americano', 'SOLO', 'Estados Unidos', 'serjtankian.com', 1),
('Mike Shinoda', 'Músico, rapper, cantor, compositor e produtor musical americano', 'SOLO', 'Estados Unidos', 'mikeshinoda.com', 1),
('Michel Teló', 'Cantor, compositor e instrumentista brasileiro', 'SOLO', 'Brasil', 'micheltelo.com.br', 2),
('Guns N Roses', 'Banda de hard rock americana formada em Los Angeles em 1985', 'BANDA', 'Estados Unidos', 'gunsnroses.com', 1),
('Linkin Park', 'Banda americana de rock formada em Agoura Hills, Califórnia', 'BANDA', 'Estados Unidos', 'linkinpark.com', 1);

-- Inserir dados iniciais de Álbuns (com base nos exemplos fornecidos)
INSERT INTO albuns (titulo, ano_lancamento, tipo, gravadora, descricao, artista_id) VALUES
-- Álbuns do Serj Tankian
('Harakiri', 2012, 'STUDIO', 'Reprise Records', 'Quarto álbum de estúdio solo', 1),
('Black Blooms', 2020, 'STUDIO', 'Serjical Strike Records', 'Coleção de demos e músicas inéditas', 1),
('The Rough Dog', 2018, 'STUDIO', 'Axis of Justice', 'Álbum de jazz e rock experimental', 1),

-- Álbuns do Mike Shinoda
('The Rising Tied', 2005, 'STUDIO', 'Machine Shop', 'Álbum solo de estreia', 2),
('Post Traumatic', 2018, 'STUDIO', 'Warner Bros.', 'Álbum sobre luto e superação', 2),
('Post Traumatic EP', 2018, 'EP', 'Warner Bros.', 'EP precursor do álbum Post Traumatic', 2),

-- Álbuns do Michel Teló
('Bem Sertanejo', 2015, 'STUDIO', 'Som Livre', 'Álbum de estreia solo', 3),
('Bem Sertanejo - O Show (Ao Vivo)', 2016, 'AO_VIVO', 'Som Livre', 'Álbum ao vivo gravado em show', 3),
('Bem Sertanejo - (1ª Temporada) - EP', 2015, 'EP', 'Som Livre', 'EP com músicas do álbum', 3),

-- Álbuns do Guns N Roses
('Use Your Illusion I', 1991, 'STUDIO', 'Geffen Records', 'Terceiro álbum de estúdio da banda', 4),
('Use Your Illusion II', 1991, 'STUDIO', 'Geffen Records', 'Quarto álbum de estúdio da banda', 4),
('Greatest Hits', 2004, 'COMPILACAO', 'Geffen Records', 'Compilação dos maiores sucessos', 4),

-- Álbuns do Linkin Park (como exemplo adicional)
('Hybrid Theory', 2000, 'STUDIO', 'Warner Bros.', 'Álbum de estreia da banda', 5),
('Meteora', 2003, 'STUDIO', 'Warner Bros.', 'Segundo álbum de estúdio', 5);

-- Inserir dados iniciais de Músicas
INSERT INTO musicas (titulo, duracao_segundos, numero_faixa, formato, album_id) VALUES
-- Músicas do álbum "Harakiri" de Serj Tankian
('Figure It Out', 210, 1, 'MP3', 1),
('Cornucopia', 195, 2, 'MP3', 1),
('Harakiri', 180, 3, 'MP3', 1),

-- Músicas do álbum "The Rising Tied" de Mike Shinoda
('Remember the Name', 195, 1, 'MP3', 4),
('Where''d You Go', 210, 2, 'MP3', 4),
('Believe Me', 185, 3, 'MP3', 4),

-- Músicas do álbum "Bem Sertanejo" de Michel Teló
('Ai Se Eu Te Pego', 180, 1, 'MP3', 7),
('Fugidinha', 195, 2, 'MP3', 7),
('Humilde Residência', 210, 3, 'MP3', 7),

-- Músicas do álbum "Use Your Illusion I" de Guns N Roses
('November Rain', 537, 1, 'MP3', 10),
('Don''t Cry', 284, 2, 'MP3', 10),
('Live and Let Die', 199, 3, 'MP3', 10);

-- Relacionar Artistas com Gêneros
INSERT INTO artista_generos (artista_id, genero_id) VALUES
(1, 1), (1, 2),  -- Serj Tankian: Rock, Metal
(2, 1), (2, 5), (2, 6),  -- Mike Shinoda: Rock, Alternative Rock, Rap Rock
(3, 3), (3, 7),  -- Michel Teló: Sertanejo, MPB
(4, 1), (4, 2),  -- Guns N Roses: Rock, Metal
(5, 1), (5, 5), (5, 6);  -- Linkin Park: Rock, Alternative Rock, Rap Rock

-- Relacionar Álbuns com Gêneros
INSERT INTO album_generos (album_id, genero_id) VALUES
(1, 1), (1, 2),   -- Harakiri: Rock, Metal
(4, 1), (4, 5), (4, 6),   -- The Rising Tied: Rock, Alternative Rock, Rap Rock
(7, 3), (7, 7),   -- Bem Sertanejo: Sertanejo, MPB
(10, 1), (10, 2); -- Use Your Illusion I: Rock, Metal