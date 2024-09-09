CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE hierarquia (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Nivel VARCHAR(30) NOT NULL
);

INSERT INTO hierarquia(Nivel) VALUES
('bibliotecario'),
('leitor');

CREATE TABLE usuario (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Nome VARCHAR(100) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Senha VARCHAR(15) NOT NULL,
    Id_Nivel INT,
    FOREIGN KEY (Id_Nivel) REFERENCES hierarquia(Id)
);

INSERT INTO usuario (Nome, Email, Senha, Id_Nivel) VALUES 
('Gustavo', 'gustavo@gmail.com', 1234567, 1);

CREATE TABLE livro (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Titulo VARCHAR(100) NOT NULL,
    Descricao VARCHAR(250) NOT NULL,
    AnoPublicacao INT,
    Disponivel BOOLEAN NOT NULL
);

INSERT INTO livro (Titulo, Descricao, AnoPublicacao, Disponivel) VALUES
('Orgulho e Preconceito', 'Um romance clássico de Jane Austen sobre amor e sociedade.', 1813, 1),
('1984', 'Uma distopia de George Orwell sobre um futuro totalitário.', 1949, 1),
('Moby Dick', 'A história épica de Herman Melville sobre a caça à baleia.', 1851, 1),
('Dom Quixote', 'A famosa obra de Miguel de Cervantes sobre um cavaleiro errante.', 1605, 1),
('Guerra e Paz', 'Um romance histórico de Liev Tolstói sobre a invasão napoleônica na Rússia.', 1869, 1),
('O Grande Gatsby', 'Um romance de F. Scott Fitzgerald sobre o sonho americano.', 1925, 1),
('Jane Eyre', 'A história de uma jovem órfã escrita por Charlotte Brontë.', 1847, 1),
('Os Miseráveis', 'Um romance de Victor Hugo sobre a injustiça social na França.', 1862, 1),
('Odisseia', 'O épico grego de Homero sobre a jornada de Odisseu.', -800, 1),
('Frankenstein', 'O romance de Mary Shelley sobre a criação de um monstro.', 1818, 1),
('O Sol é para Todos', 'Um romance de Harper Lee sobre racismo e injustiça no sul dos EUA.', 1960, 1),
('Crime e Castigo', 'Um romance de Fiódor Dostoiévski sobre culpa e redenção.', 1866, 1),
('O Retrato de Dorian Gray', 'Um romance de Oscar Wilde sobre beleza e moralidade.', 1890, 1),
('O Conde de Monte Cristo', 'A história de vingança de Alexandre Dumas.', 1844, 1),
('O Senhor dos Anéis', 'A épica fantasia de J.R.R. Tolkien sobre a luta contra o mal.', 1954, 1),
('O Hobbit', 'A aventura de Bilbo Bolseiro escrita por J.R.R. Tolkien.', 1937, 1),
('Cem Anos de Solidão', 'O romance de Gabriel García Márquez sobre a família Buendía.', 1967, 1),
('O Nome da Rosa', 'Um romance de Umberto Eco sobre mistério e investigação na Idade Média.', 1980, 1),
('Os Lusíadas', 'O poema épico de Luís de Camões sobre as navegações portuguesas.', 1572, 1),
('Decamerão', 'Uma coleção de 100 histórias de Giovanni Boccaccio.', 1353, 1);


CREATE TABLE livroEmprestado (
	Id INT PRIMARY KEY AUTO_INCREMENT,
    Id_Livro INT NOT NULL,
    Id_Usuario INT NOT NULL,
    FOREIGN KEY (Id_Livro) REFERENCES livro (Id),
    FOREIGN KEY (Id_usuario) REFERENCES usuario (Id)
);

/*
DROP TABLE usuario;
DROP TABLE livro;
DROP TABLE livroEmprestado;
*/

SELECT * FROM hierarquia;
SELECT * FROM biblioteca.usuario;
SELECT * FROM biblioteca.livro;
SELECT * FROM livroEmprestado;