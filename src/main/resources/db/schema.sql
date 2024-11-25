-- Supprimer la base de données si elle existe
DROP DATABASE IF EXISTS jee_projet;

-- Recréer la base de données
CREATE DATABASE jee_projet;

-- Utiliser la base de données recréée
USE jee_projet;

-- Créer la table `eleve`
CREATE TABLE IF NOT EXISTS eleve (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     nom VARCHAR(100) NOT NULL,
                                     prenom VARCHAR(100) NOT NULL,
                                     telephone VARCHAR(15) NOT NULL,
                                     email VARCHAR(255) UNIQUE NOT NULL,
                                     numero_adresse INT NOT NULL,
                                     adresse VARCHAR(255) NOT NULL,
                                     code_postal INT NOT NULL,
                                     date_inscription TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Supprimer les données existantes dans la table `eleve`
DELETE FROM eleve;

-- Insérer des données dans la table `eleve`
INSERT INTO eleve (nom, prenom, telephone, email, numero_adresse, adresse, code_postal)
VALUES ('Dupont', 'alif', '0602030405', 'jean.dupont@example.com', 10, 'Rue des Lilas', 75001);

