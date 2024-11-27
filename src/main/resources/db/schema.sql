-- Supprimer la base de données si elle existe
DROP DATABASE IF EXISTS jee_projet;

-- Créer la base de données et utiliser la nouvelle base
CREATE DATABASE jee_projet CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE jee_projet;

-- Table des rôles
CREATE TABLE roles (
                       id_role INT AUTO_INCREMENT PRIMARY KEY,
                       nom_role VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des utilisateurs
CREATE TABLE utilisateurs (
                              id_utilisateur INT AUTO_INCREMENT PRIMARY KEY,
                              nom VARCHAR(100) NOT NULL,
                              prenom VARCHAR(100) NOT NULL,
                              adresse VARCHAR(200) NOT NULL,
                              email VARCHAR(100) UNIQUE NOT NULL,
                              mot_de_passe VARCHAR(255) NOT NULL,
                              id_role INT NOT NULL,
                              FOREIGN KEY (id_role) REFERENCES roles(id_role) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des étudiants
CREATE TABLE etudiants (
                           id_etudiant INT AUTO_INCREMENT PRIMARY KEY,
                           date_naissance DATE NOT NULL,
                           contact VARCHAR(15),
                           classe ENUM('ING1', 'ING2', 'ING3') NOT NULL,
                           filiere ENUM('GI', 'GM', 'GC', 'BTC') NOT NULL,
                           id_utilisateur INT NOT NULL,
                           FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des enseignants
CREATE TABLE enseignants (
                             id_enseignant INT AUTO_INCREMENT PRIMARY KEY,
                             date_naissance DATE NOT NULL,
                             contact VARCHAR(15),
                             specialite VARCHAR(100),
                             id_utilisateur INT NOT NULL,
                             FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des cours
CREATE TABLE cours (
                       id_cours INT AUTO_INCREMENT PRIMARY KEY,
                       nom_cours VARCHAR(100) NOT NULL,
                       description TEXT,
                       id_enseignant INT,
                       FOREIGN KEY (id_enseignant) REFERENCES enseignants(id_enseignant) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des inscriptions
CREATE TABLE inscriptions_annee (
                                    id_inscription INT AUTO_INCREMENT PRIMARY KEY,
                                    id_etudiant INT NOT NULL,
                                    date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE inscriptions_cours (
                              id_inscription INT AUTO_INCREMENT PRIMARY KEY,
                              id_etudiant INT NOT NULL,
                              id_cours INT NOT NULL,
                              date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
                              FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE,
                              FOREIGN KEY (id_cours) REFERENCES cours(id_cours) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des résultats
CREATE TABLE resultats (
                           id_resultat INT AUTO_INCREMENT PRIMARY KEY,
                           id_etudiant INT NOT NULL,
                           id_cours INT NOT NULL,
                           note DECIMAL(5, 2),
                           FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE,
                           FOREIGN KEY (id_cours) REFERENCES cours(id_cours) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insertion des rôles par défaut
INSERT INTO roles (nom_role) VALUES ('Administrateur'), ('Enseignant'), ('Étudiant');
-- Insérer des rôles par défaut
INSERT INTO roles (nom_role) VALUES ('Administrateur'), ('Enseignant'), ('Étudiant');

-- Insérer des utilisateurs
INSERT INTO utilisateurs (nom, prenom, adresse, email, mot_de_passe, id_role)
VALUES
    ('Admin', 'User', '1 Rue de l\'Admin, 75001, Paris', 'admin@domain.com', 'password123', 1),
    ('John', 'Doe', '5 Rue de l\'Enseignant, 75002, Paris', 'john.doe@domain.com', 'password123', 2),
    ('Jane', 'Smith', '10 Rue de l\'Étudiant, 75003, Paris', 'jane.smith@domain.com', 'password123', 3);

-- Insérer des étudiants
INSERT INTO etudiants (date_naissance, contact, classe, filiere, id_utilisateur)
VALUES
    ('2000-05-15', '0601010203', 'ING1', 'GI', 3);

-- Insérer des enseignants
INSERT INTO enseignants (date_naissance, contact, specialite, id_utilisateur)
VALUES
    ('1985-09-12', '0610101010', 'Mathématiques', 2);

-- Insérer des cours
INSERT INTO cours (nom_cours, description, id_enseignant)
VALUES
    ('Programmation Java', 'Introduction à la programmation en Java', 1),
    ('Bases de Données', 'Conception et manipulation des bases de données', 1);

-- Insérer des inscriptions à l'année
INSERT INTO inscriptions_annee (id_etudiant)
VALUES
    (1);

-- Insérer des inscriptions à des cours
INSERT INTO inscriptions_cours (id_etudiant, id_cours)
VALUES
    (1, 1),
    (1, 2);

-- Insérer des résultats
INSERT INTO resultats (id_etudiant, id_cours, note)
VALUES
    (1, 1, 18.5),
    (1, 2, 16.0);
INSERT INTO resultats (id_etudiant, id_cours, note)
VALUES
    (1, 1, 18.5),
    (1, 2, 16.0);

-- Ajouter un administrateur avec un mot de passe sécurisé
INSERT INTO utilisateurs (nom, prenom, adresse, email, mot_de_passe, id_role)
VALUES
    ('Super', 'Admin', '99 Avenue de l\'Admin, 75000, Paris', 'admin@jee.com', 'admin_password', 1);
