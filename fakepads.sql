-- padsfake.sql
-- Script para inserir dados fake na tabela pads para testes.
-- Execute este script no DB Browser for SQLite após criar as tabelas.
-- Assume que os owners com os UIDs especificados já existem.

-- Inserir 10 pads fake, com owner escolhido aleatoriamente entre os disponíveis (via subquery)
-- Datas no passado variadas (baseado em 2025-11-12 como data atual)

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Receita de Bolo de Chocolate', 'Ingredientes: 2 xícaras de farinha, 1 xícara de açúcar, 3 ovos. Modo: Misture tudo e asse por 30 min. Delícia!', '2025-11-11 10:00:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Dicas para Viajar Barato', 'Compre passagens com antecedência, use hostels e coma street food. Aventura garantida!', '2025-11-10 14:30:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Como Treinar Seu Cachorro', 'Use petiscos como recompensa, treine comandos básicos como senta e fica. Paciência é chave!', '2025-11-09 09:15:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Receita de Pizza Caseira', 'Massa: farinha, água, fermento. Recheio: molho, queijo, pepperoni. Asse em forno quente.', '2025-11-08 16:45:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Melhores Filmes de Comédia', 'Recomendo "Se Beber Não Case" e "Superbad". Risadas non-stop!', '2025-11-07 11:20:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Dicas de Jardinagem para Iniciantes', 'Escolha plantas resistentes, regue moderadamente e use sol adequado. Verde na casa!', '2025-11-06 13:50:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Receita de Smoothie Energético', 'Banana, morango, iogurte e espinafre. Bata no liquidificador para um boost matinal.', '2025-11-05 08:30:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Histórias Engraçadas de Viagem', 'Uma vez, confundi o trem e acabei em outra cidade. Lição: leia as placas!', '2025-11-04 15:10:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Como Fazer Exercícios em Casa', 'Flexões, abdominais e corrida no lugar. Sem academia necessária!', '2025-11-03 17:40:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));

INSERT INTO pads (title, content, created_at, status, owner)
VALUES ('Receita de Cookies Perfeitos', 'Manteiga, açúcar, farinha e gotas de chocolate. Asse até dourar. Irresistíveis!', '2025-11-02 12:00:00', 'ON',
(SELECT owners.id FROM owners ORDER BY RANDOM() LIMIT 1));