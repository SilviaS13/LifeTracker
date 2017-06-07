-- phpMyAdmin SQL Dump
-- version 4.0.10.10
-- http://www.phpmyadmin.net
--
-- Хост: 127.0.0.1:3306
-- Час створення: Чрв 07 2017 р., 11:11
-- Версія сервера: 5.5.45
-- Версія PHP: 5.3.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- База даних: `LifeTracker`
--

-- --------------------------------------------------------

--
-- Структура таблиці `Activity`
--

CREATE TABLE IF NOT EXISTS `Activity` (
  `id` bigint(20) NOT NULL,
  `Private` tinyint(1) NOT NULL,
  `Name` varchar(200) NOT NULL,
  `Type` tinyint(1) NOT NULL,
  `Min` int(11) NOT NULL,
  `Max` int(11) NOT NULL,
  `id_User` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_User` (`id_User`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `Badge`
--

CREATE TABLE IF NOT EXISTS `Badge` (
  `id` bigint(20) NOT NULL,
  `Name` varchar(128) DEFAULT NULL,
  `Points` int(11) DEFAULT NULL,
  `Description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `Check`
--

CREATE TABLE IF NOT EXISTS `Check` (
  `id` bigint(20) NOT NULL,
  `id_Activity` bigint(11) NOT NULL,
  `Date` date DEFAULT NULL,
  `EndDate` date DEFAULT NULL,
  `Value` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_Activity` (`id_Activity`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `Followers`
--

CREATE TABLE IF NOT EXISTS `Followers` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_UserWatcher` bigint(20) NOT NULL,
  `id_UserWatching` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Структура таблиці `User`
--

CREATE TABLE IF NOT EXISTS `User` (
  `id` int(11) NOT NULL,
  `Name` varchar(128) NOT NULL,
  `Pass` varchar(128) NOT NULL,
  `Lvl` int(11) DEFAULT NULL,
  `Xp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблиці `UserBadges`
--

CREATE TABLE IF NOT EXISTS `UserBadges` (
  `id` bigint(20) NOT NULL,
  `id_User` bigint(20) NOT NULL,
  `id_Badge` bigint(20) NOT NULL,
  `Date` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_Badge` (`id_Badge`),
  UNIQUE KEY `id_User` (`id_User`),
  KEY `id` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Обмеження зовнішнього ключа збережених таблиць
--

--
-- Обмеження зовнішнього ключа таблиці `Activity`
--
ALTER TABLE `Activity`
  ADD CONSTRAINT `activity_ibfk_1` FOREIGN KEY (`id_User`) REFERENCES `user` (`id`);

--
-- Обмеження зовнішнього ключа таблиці `Check`
--
ALTER TABLE `Check`
  ADD CONSTRAINT `check_ibfk_1` FOREIGN KEY (`id_Activity`) REFERENCES `Activity` (`id`);

--
-- Обмеження зовнішнього ключа таблиці `UserBadges`
--
ALTER TABLE `UserBadges`
  ADD CONSTRAINT `userbadges_ibfk_1` FOREIGN KEY (`id_Badge`) REFERENCES `badge` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
