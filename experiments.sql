-- Alguns experimentos com SQL e H2 Database

-- Apaga as tabelas
-- Isso permite executar esse script inteiro de forma mais prática
-- Comente em DEVTIME
DROP TABLE PADS IF EXISTS;
DROP TABLE OWNERS IF EXISTS;

-- Cria a tabela "OWNERS", equivalente a um "cadastro de usuários"
CREATE TABLE OWNERS (
    -- Chave primária (PK)
    ID INT PRIMARY KEY AUTO_INCREMENT,
    -- Data de criação do registro
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Data do último login
    LAST_LOGIN_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    -- Nome do registro obrigatório
    NAME VARCHAR(127) NOT NULL,
    -- E-mail do registro deve ser único
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    -- Senha do registro obrigatório
    PASSWORD VARCHAR(63) NOT NULL,
    -- Data de nascimento do registro
    BIRTH_DATE DATE NOT NULL,
    -- Situação do registro no sistema com limite bem alto
    STATUS VARCHAR(3) DEFAULT 'ON' CHECK (STATUS IN ('ON', 'OFF', 'DEL')),
    -- 32767 bytes reservados para uso futuro, por exemplo um JSON
    METADATA VARCHAR(32767)
);

-- Insere alguns registros em "OWNERS"
INSERT INTO OWNERS (
    NAME,
    EMAIL,
    PASSWORD,
    BIRTH_DATE
) VALUES (
    'Joca da Silva',
    'joca@silva.com',
    'Senha123',
    -- Data de nascimento no formato ISO
    '2000-08-02'
), (
    'Maricleuza Siriliano',
    'maria@cleuza.com',
    'Senha123',
    '1998-10-14'
);

-- Lista todos os registro existentes
SELECT * FROM OWNERS;

-- Lista o ID, NAME e EMAIL dos registros
SELECT ID, NAME, EMAIL FROM OWNERS;

-- Lista o ID, NAME e EMAIL dos registros ordenando pelo NAME e pelo BIRTH_DATE (ASC* ou DESC)
SELECT ID, NAME, EMAIL FROM OWNERS ORDER BY NAME, BIRTH_DATE DESC;

-- Lista os dados de um registro único
SELECT * FROM OWNERS WHERE ID = 2;

-- Lista os registos cujo NAME começa com a letra "M"
SELECT * FROM OWNERS WHERE NAME LIKE 'J%';

-- Lista os registros cujo NAME tenha a letra "c"
SELECT * FROM OWNERS WHERE NAME LIKE '%ca%';

-- Lista os registros com STATUS válido (ON)
SELECT * FROM OWNERS WHERE STATUS='ON';

-- Marca o registro como "apagado" (DEL)
UPDATE OWNERS SET STATUS = 'DEL' WHERE ID = '1';

-- Altera o NAME do registro
UPDATE OWNERS SET NAME = 'Maricleuza Siriliano de Souza' WHERE ID = '2';

-- Apaga o registro completamente. CUIDADO!!!
DELETE FROM OWNERS WHERE ID = '1';

-- Insere alguns registros em "OWNERS"
INSERT INTO OWNERS (
    NAME,
    EMAIL,
    PASSWORD,
    BIRTH_DATE
) VALUES (
    'Setembrino Trocatapas',
    'set@tapas.com',
    'Senha123',
    -- Data de nascimento no formato ISO
    '1982-05-01'
);

-- Cria a tabela PADS usando o prefixo PAD_ nos campos, referente ao "produto" da aplicação
CREATE TABLE PADS (
    PAD_ID INT PRIMARY KEY AUTO_INCREMENT,
    PAD_CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PAD_TITLE VARCHAR(127) NOT NULL,
    -- Mantém a capacidade máxima de 10^9 caracteres
    PAD_CONTENT VARCHAR,
    -- Coluna que referencia o dono (OWNER) do registro → Chave estrangeira
    PAD_OWNER INT NOT NULL,
    PAD_STATUS VARCHAR(3) DEFAULT 'ON' CHECK (PAD_STATUS IN ('ON', 'OFF', 'DEL')),
    -- VARCHAR sem tamanho definido (até 10^9 bytes)
    -- CLOB também pode ser apropriado para PAD_METADATA
    PAD_METADATA VARCHAR,

    -- Cria a chave de relacionamento entre PADS e OWNERS → Chave estrangeira (FK)
    CONSTRAINT FK_PADS_OWNER FOREIGN KEY (PAD_OWNER) REFERENCES OWNERS (ID)
);

-- Cadastra alguns PADS
INSERT INTO PADS (
    PAD_TITLE,
    PAD_CONTENT,
    PAD_OWNER
) VALUES (
    'Receita de Bolo de Fubá',
    'lorem ipsum',
    '2'
), (
    'Receita de frango',
    'Lorem ipsum',
    '3'
);

-- Lista todos os PADS
SELECT * FROM PADS;

-- Obtém um PAD pelo PAD_TITLE
SELECT PAD_ID, PAD_TITLE FROM PADS WHERE PAD_TITLE = 'Receita de frango';

-- Obtém os PADS pelo PAD_TITLE contendo um texto (Pesquisa)
SELECT PAD_ID, PAD_TITLE FROM PADS WHERE PAD_TITLE LIKE '%Receita%' LIMIT 10;

-- Lista um PAD e os dados do OWNER
SELECT * FROM PADS
    INNER JOIN OWNERS ON PAD_OWNER = ID;

-- Lista um PAD e os dados do OWNER
SELECT PAD_ID, PAD_TITLE, ID, NAME, EMAIL FROM PADS
    INNER JOIN OWNERS ON PAD_OWNER = ID;


-- ATIVIDADE DESAFIO
-- Crie uma tabela para armazenar os dados do formulário de contatos
-- Campos: nome, e-mail, assunto e mensagem
-- Use prefixo no nome dos campos
-- Faça alguns cadastros e consultas