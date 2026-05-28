CREATE DATABASE IF NOT EXISTS OMNIWEAR_DB;

USE OMNIWEAR_DB;

CREATE TABLE Utente (
    id_utente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(256) NOT NULL,
    data_nascita DATE NOT NULL
);

CREATE TABLE Admin (
    id_admin INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(254) UNIQUE NOT NULL,
    password_hash VARCHAR(256) NOT NULL
);

CREATE TABLE Categoria (
    nome_categoria VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Misura (
    valore_misura VARCHAR(20) PRIMARY KEY
);

CREATE TABLE Ordine (
    id_ordine INT AUTO_INCREMENT PRIMARY KEY,
    data_ordine DATETIME DEFAULT CURRENT_TIMESTAMP,
    indirizzo_destinazione VARCHAR(255) NOT NULL,
    stato_ordine VARCHAR(50) NOT NULL DEFAULT 'In elaborazione',
    id_utente INT NOT NULL,
    FOREIGN KEY (id_utente) REFERENCES Utente(id_utente) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Prodotto (
    id_prodotto INT AUTO_INCREMENT PRIMARY KEY,
    nome_prodotto VARCHAR(100) NOT NULL,
    prezzo DECIMAL(10, 2) NOT NULL,
    id_admin INT NOT NULL,
    FOREIGN KEY (id_admin) REFERENCES Admin(id_admin) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Immagine (
    path_immagine VARCHAR(255) PRIMARY KEY,
    id_prodotto INT NOT NULL,
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ordine_Prodotto (
    id_ordine INT NOT NULL,
    id_prodotto INT NOT NULL,
    quantita INT NOT NULL,
    prezzo_vendita DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (id_ordine, id_prodotto),
    FOREIGN KEY (id_ordine) REFERENCES Ordine(id_ordine) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE RESTRICT
);

CREATE TABLE Prodotto_Categoria (
    id_prodotto INT NOT NULL,
    nome_categoria VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_prodotto, nome_categoria),
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (nome_categoria) REFERENCES Categoria(nome_categoria) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Prodotto_Misura (
    id_prodotto INT NOT NULL,
    valore_misura VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_prodotto, valore_misura),
    FOREIGN KEY (id_prodotto) REFERENCES Prodotto(id_prodotto) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (valore_misura) REFERENCES Misura(valore_misura) ON UPDATE CASCADE ON DELETE CASCADE
);

