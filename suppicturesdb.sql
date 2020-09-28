-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 07 fév. 2019 à 15:32
-- Version du serveur :  5.7.19
-- Version de PHP :  5.6.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `suppicturesdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `category`
--

DROP TABLE IF EXISTS `category`;
CREATE TABLE IF NOT EXISTS `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `categoryName` (`categoryName`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `category`
--

INSERT INTO `category` (`id`, `categoryName`) VALUES
(1, 'Cuisine'),
(2, 'Livre'),
(3, 'Voyage');

-- --------------------------------------------------------

--
-- Structure de la table `members`
--

DROP TABLE IF EXISTS `members`;
CREATE TABLE IF NOT EXISTS `members` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` varchar(150) NOT NULL,
  `firstname` varchar(32) NOT NULL,
  `lastname` varchar(32) NOT NULL,
  `phoneNumber` varchar(20) NOT NULL,
  `adresse` varchar(60) NOT NULL,
  `email` varchar(80) NOT NULL,
  `isAdmin` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `members`
--

INSERT INTO `members` (`id`, `username`, `password`, `firstname`, `lastname`, `phoneNumber`, `adresse`, `email`, `isAdmin`) VALUES
(1, 'Admin', 'abbdc3cff5486427953c3ac1ed2a421e01a430d0ff56894bea9040bda65003da3e34234ea57ef88e2566971bcaa0bfbe255da9ddccc0cc5282fe32b0409749f7', 'Admin', 'Admin', '', '', '', 1),
(34, 'JohnDoe', 'b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86', 'John', 'Doe', '0658899663', '16 Rue Jean Desparmet, 69008 Lyon', 'jdoe@gmail.com', 0),
(35, 'SelinaKyle', '72905e7b32d847468edcdbf99f7d218e466cd828300306f1d9f8c3e0512e44fe4394644b581ed52656a2870c9a67c592bc40ca322099aa52bf528c54f9cabde0', 'Selina', 'Kyle', '0745521363', '105 Avenue Jean Mermoz, 69008 Lyon', 'skyle@gmail.com', 0);

-- --------------------------------------------------------

--
-- Structure de la table `post`
--

DROP TABLE IF EXISTS `post`;
CREATE TABLE IF NOT EXISTS `post` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(90) NOT NULL,
  `description` text NOT NULL,
  `path` varchar(90) NOT NULL,
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `locality` varchar(32) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `memberId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `title` (`title`),
  KEY `memberId` (`memberId`) USING BTREE,
  KEY `categoryId` (`categoryId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `post`
--

INSERT INTO `post` (`id`, `title`, `description`, `path`, `createdAt`, `updatedAt`, `locality`, `categoryId`, `memberId`) VALUES
(1, 'Voyage en transsibérien: le guide complet', 'Un voyage en transsibérien, c’est un rêve pour de nombreux voyageurs. Un mythe qui fascine, car il s’agit de la plus longue ligne de train du monde, avec 9.300 km de Moscou jusqu’à Vladivostok.', '', '2019-02-07 15:51:30', '2019-02-07 15:51:30', 'Russie', 3, 34),
(2, 'Les accessoires indispensables pour partir en voyage', 'Partir en voyage, que ce soit pour une courte ou une longue durée, implique un minimum de préparatifs. Il faut notamment veiller à bien faire son sac-à-dos ou sa valise, en emportant avec soi.', '', '2019-02-07 15:51:30', '2019-02-07 15:51:30', 'Monde', 3, 34),
(3, 'Entremets framboise et chocolat blanc', 'Après la chandeleur et le nouvel an chinois, il est temps de passer aux recettes pour la Saint-Valentin', '', '2019-02-07 15:51:30', '2019-02-07 15:51:30', 'Paris', 1, 35),
(4, 'Burger aux galettes de pommes de terre', 'Une recette de fast-food homemade bien meilleure et plus saine qu\'\'un vrai fast-food', '', '2019-02-07 16:13:32', '2019-02-07 16:13:32', 'Paris', 1, 34),
(5, 'Le poids du monde de David Joy', 'Et revoici, avec le poids du monde, de David Joy, notre maître du polar, Philippe Manche, l\'\'un des pionniers du Blog des livres.', '', '2019-02-07 16:13:32', '2019-02-07 16:13:32', 'Nice', 2, 35),
(6, 'Le reste de leur vie - Jean-Paul Didierlaurent', 'Suite à vos retours positifs, c\'\'est avec un immense plaisir que nous invitons à nouveau une libraire à venir partager son coup de coeur sur le Blog des livres : Le reste de leur vie, de Jean-Paul Didierlaurent.', '', '2019-02-07 16:13:32', '2019-02-07 16:13:32', 'Nice', 2, 35);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `post`
--
ALTER TABLE `post`
  ADD CONSTRAINT `post_ibfk_1` FOREIGN KEY (`memberId`) REFERENCES `members` (`id`),
  ADD CONSTRAINT `post_ibfk_2` FOREIGN KEY (`categoryId`) REFERENCES `category` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
