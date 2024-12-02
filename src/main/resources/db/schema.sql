-- Create the database only if it does not already exist
CREATE DATABASE IF NOT EXISTS jee_project CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Use the database
USE jee_project;

-- Use the database (assumes it already exists)
USE jee_project;

-- Table des rôles
CREATE TABLE IF NOT EXISTS roles (
                                     id_role INT AUTO_INCREMENT PRIMARY KEY,
                                     nom_role VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des utilisateurs
CREATE TABLE IF NOT EXISTS utilisateurs (
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
CREATE TABLE IF NOT EXISTS etudiants (
                                         id_etudiant INT AUTO_INCREMENT PRIMARY KEY,
                                         date_naissance DATE NOT NULL,
                                         contact VARCHAR(15),
                                         classe ENUM('ING1', 'ING2', 'ING3') NOT NULL,
                                         filiere ENUM('GI', 'GM', 'GC', 'BTC') NOT NULL,
                                         id_utilisateur INT NOT NULL,
                                         FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des enseignants
CREATE TABLE IF NOT EXISTS enseignants (
                                           id_enseignant INT AUTO_INCREMENT PRIMARY KEY,
                                           date_naissance DATE NOT NULL,
                                           contact VARCHAR(15),
                                           specialite VARCHAR(100),
                                           id_utilisateur INT NOT NULL,
                                           FOREIGN KEY (id_utilisateur) REFERENCES utilisateurs(id_utilisateur) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des cours
CREATE TABLE IF NOT EXISTS cours (
                                     id_cours INT AUTO_INCREMENT PRIMARY KEY,
                                     nom_cours VARCHAR(100) NOT NULL,
                                     description TEXT,
                                     id_enseignant INT,
                                     FOREIGN KEY (id_enseignant) REFERENCES enseignants(id_enseignant) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des inscriptions à l'année
CREATE TABLE IF NOT EXISTS inscriptions_annee (
                                                  id_inscription INT AUTO_INCREMENT PRIMARY KEY,
                                                  id_etudiant INT NOT NULL,
                                                  date_inscription DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                  FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des inscriptions à des cours
CREATE TABLE IF NOT EXISTS inscriptions_cours (
                                                  id_inscription INT AUTO_INCREMENT PRIMARY KEY,
                                                  id_etudiant INT NOT NULL,
                                                  id_cours INT NOT NULL,
                                                  debut_cours DATETIME NOT NULL,
                                                  fin_cours DATETIME NOT NULL,
                                                  FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE,
                                                  FOREIGN KEY (id_cours) REFERENCES cours(id_cours) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table des résultats
CREATE TABLE IF NOT EXISTS resultats (
                                         id_resultat INT AUTO_INCREMENT PRIMARY KEY,
                                         id_etudiant INT NOT NULL,
                                         id_cours INT NOT NULL,
                                         note DECIMAL(5, 2),
                                         FOREIGN KEY (id_etudiant) REFERENCES etudiants(id_etudiant) ON DELETE CASCADE,
                                         FOREIGN KEY (id_cours) REFERENCES cours(id_cours) ON DELETE CASCADE,
                                         UNIQUE KEY unique_etudiant_cours (id_etudiant, id_cours)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert default roles if they do not exist
INSERT IGNORE INTO roles (id_role, nom_role)
VALUES
    (1, 'Administrateur'),
    (2, 'Enseignant'),
    (3, 'Étudiant');

-- Insert default users if they do not exist
INSERT IGNORE INTO utilisateurs (id_utilisateur, nom, prenom, adresse, email, mot_de_passe, id_role)
VALUES
    (1, 'admin', 'User', '1 Rue de l\'Admin, 75001, Paris', 'admin@domain.com', 'password123', 1),
    (2, 'John', 'Doe', '5 Rue de l\'Enseignant, 75002, Paris', 'john.doe@domain.com', 'password123', 2),
    (3, 'Jane', 'Smith', '10 Rue de l\'Étudiant, 75003, Paris', 'jane.smith@domain.com', 'password123', 3);

-- Insert default students if they do not exist
INSERT IGNORE INTO etudiants (id_etudiant, date_naissance, contact, classe, filiere, id_utilisateur)
VALUES
    (1, '2000-05-15', '0601010203', 'ING1', 'GI', 3);

-- Insert default teachers if they do not exist
INSERT IGNORE INTO enseignants (id_enseignant, date_naissance, contact, specialite, id_utilisateur)
VALUES
    (1, '1985-09-12', '0610101010', 'Mathématiques', 2);

-- Insert default courses if they do not exist
INSERT IGNORE INTO cours (id_cours, nom_cours, description, id_enseignant)
VALUES
    (1, 'Programmation Java', 'Introduction à la programmation en Java', 1),
    (2, 'Bases de Données', 'Conception et manipulation des bases de données', 1);

-- Insert default year registrations if they do not exist
INSERT IGNORE INTO inscriptions_annee (id_inscription, id_etudiant)
VALUES
    (1, 1);

-- Insert default course registrations if they do not exist
INSERT IGNORE INTO inscriptions_cours (id_inscription, id_etudiant, id_cours, debut_cours, fin_cours)
VALUES
    (1, 1, 1, '2024-01-15 09:00:00', '2024-06-15 12:00:00'),
    (2, 1, 2, '2024-02-01 14:00:00', '2024-07-01 17:00:00');

-- Insert default results if they do not exist
INSERT INTO resultats (id_resultat, id_etudiant, id_cours, note)
VALUES
    (1, 1, 1, 18.5),
    (2, 1, 2, 16.0)
ON DUPLICATE KEY UPDATE
    note = VALUES(note);

-- Insert a super admin if not exists
INSERT IGNORE INTO utilisateurs (id_utilisateur, nom, prenom, adresse, email, mot_de_passe, id_role)
VALUES
    (4, 'Super', 'Admin', '99 Avenue de l\'Admin, 75000, Paris', 'admin@jee.com', 'admin_password', 1);
