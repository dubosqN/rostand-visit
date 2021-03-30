-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mar. 30 mars 2021 à 09:56
-- Version du serveur :  5.7.31-log
-- Version de PHP : 7.4.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `rostand-visit`
--

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

DROP TABLE IF EXISTS `doctrine_migration_versions`;
CREATE TABLE IF NOT EXISTS `doctrine_migration_versions` (
  `version` varchar(191) COLLATE utf8_unicode_ci NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL,
  PRIMARY KEY (`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20210316190607', '2021-03-16 19:06:15', 184),
('DoctrineMigrations\\Version20210316190955', '2021-03-16 19:10:03', 322),
('DoctrineMigrations\\Version20210316191141', '2021-03-16 19:11:46', 232);

-- --------------------------------------------------------

--
-- Structure de la table `etablissement`
--

DROP TABLE IF EXISTS `etablissement`;
CREATE TABLE IF NOT EXISTS `etablissement` (
  `id` int(11) NOT NULL,
  `libelle` varchar(100) DEFAULT NULL,
  `cp` varchar(5) DEFAULT NULL,
  `ville` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etablissement`
--

INSERT INTO `etablissement` (`id`, `libelle`, `cp`, `ville`) VALUES
(1, 'Rostand', '14000', 'Caen'),
(2, 'Fresnel', '14000', 'Caen'),
(3, 'Autre', NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `prenom` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `section_id` int(11) DEFAULT NULL,
  `visite_id` int(11) DEFAULT '1',
  `option_specialite_id` int(11) DEFAULT NULL,
  `etablissement_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_717E22E3D823E37A` (`section_id`),
  KEY `IDX_717E22E3C1C5DC59` (`visite_id`),
  KEY `FK_etudiant_option_specialite` (`option_specialite_id`),
  KEY `FK_etab_etudiant` (`etablissement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id`, `nom`, `prenom`, `mail`, `section_id`, `visite_id`, `option_specialite_id`, `etablissement_id`) VALUES
(51, 'DUBOSQ', 'Noe', 'noe.dubosq.allende@gmail.com', 1, 1, 8, 2),
(52, 'ADALA', 'Yanis', 'adala.yanis@gmail.com', 1, 1, 10, 3),
(53, 'AUBRIS', 'Leo', 'aubris.leo@gmail.com', 1, 1, 8, 1),
(54, 'CHERUEL', 'Quantin', 'quantin.cheruel@gmail.com', 3, 1, 10, 3),
(55, 'MANGION', 'Palbo', 'mangion.pablo@gmail.com', 1, 1, 23, 1),
(56, 'CLEMENT', 'HUGOOOOO', 'HUGGOOOOOOO.clement@gmail.com', 1, 1, 24, 1),
(57, 'PROVOST', 'Romain', 'romain.provost@gmail.com', 1, 1, 24, 1),
(58, 'd', 'd', 'd', 1, 1, 10, 1);

-- --------------------------------------------------------

--
-- Structure de la table `option_specialite`
--

DROP TABLE IF EXISTS `option_specialite`;
CREATE TABLE IF NOT EXISTS `option_specialite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `specialite_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `specialite_id` (`specialite_id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `option_specialite`
--

INSERT INTO `option_specialite` (`id`, `libelle`, `specialite_id`) VALUES
(8, 'Maths', 5),
(9, 'Physique-Chimie', 5),
(10, 'Informatique', 4),
(11, 'Economie', 4),
(12, 'Arts', 5),
(13, 'Langues', 5),
(14, 'Anglais', 5),
(15, 'Allemand', 5),
(16, 'Espagnol', 5),
(17, 'Economie', 5),
(18, 'Sociologie', 5),
(19, 'Philosophie', 5),
(20, 'Histoire', 5),
(22, 'Geographie', 5),
(23, 'Electronique', 6),
(24, 'Informatique', 6);

-- --------------------------------------------------------

--
-- Structure de la table `section`
--

DROP TABLE IF EXISTS `section`;
CREATE TABLE IF NOT EXISTS `section` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `section`
--

INSERT INTO `section` (`id`, `libelle`) VALUES
(1, 'SLAM'),
(2, 'SISR'),
(3, 'Incertain');

-- --------------------------------------------------------

--
-- Structure de la table `specialite`
--

DROP TABLE IF EXISTS `specialite`;
CREATE TABLE IF NOT EXISTS `specialite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `libelle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `specialite`
--

INSERT INTO `specialite` (`id`, `libelle`) VALUES
(3, 'BAC'),
(4, 'Licence'),
(5, 'BAC GENERAL'),
(6, 'BAC PRO');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `mail`, `password`, `username`) VALUES
(1, 'noe.dubosq.allende@gmail.com', '$2y$10$iNU5mCI30POpSFrOFsbYt.ePCEydK3SjfFNCYEUOoa6mIRNQVF5Sa', 'test');

-- --------------------------------------------------------

--
-- Structure de la table `visite`
--

DROP TABLE IF EXISTS `visite`;
CREATE TABLE IF NOT EXISTS `visite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `lieu` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `libelle` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_visite` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `visite`
--

INSERT INTO `visite` (`id`, `lieu`, `libelle`, `date_visite`) VALUES
(1, 'Caen', 'Porte ouvertes Rostand 2021', '2021-03-21 15:01:35');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `FK_717E22E3C1C5DC59` FOREIGN KEY (`visite_id`) REFERENCES `visite` (`id`),
  ADD CONSTRAINT `FK_717E22E3D823E37A` FOREIGN KEY (`section_id`) REFERENCES `section` (`id`),
  ADD CONSTRAINT `FK_etab_etudiant` FOREIGN KEY (`etablissement_id`) REFERENCES `etablissement` (`id`),
  ADD CONSTRAINT `FK_etudiant_option_specialite` FOREIGN KEY (`option_specialite_id`) REFERENCES `option_specialite` (`id`);

--
-- Contraintes pour la table `option_specialite`
--
ALTER TABLE `option_specialite`
  ADD CONSTRAINT `option_specialite_ibfk_1` FOREIGN KEY (`specialite_id`) REFERENCES `specialite` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
