-- phpMyAdmin SQL Dump
-- version 5.0.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2022 at 05:59 PM
-- Server version: 10.4.17-MariaDB
-- PHP Version: 7.3.27

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kafic`
--

-- --------------------------------------------------------

--
-- Table structure for table `evidencija_dolaska`
--

CREATE TABLE `evidencija_dolaska` (
  `id` int(11) NOT NULL,
  `jmbg` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vreme_dolaska` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `evidencija_dolaska`
--

INSERT INTO `evidencija_dolaska` (`id`, `jmbg`, `vreme_dolaska`) VALUES
(1, '0406999710162', '2022-03-21 02:04:20'),
(2, '0406999710162', '2022-03-22 05:54:42'),
(3, '2112996123456', '2022-03-23 05:34:34'),
(4, '1234567890123', '2022-03-24 05:58:39'),
(5, '0406999710162', '2022-03-29 14:03:14'),
(6, '2112996123456', '2022-03-25 06:00:56'),
(7, '1594567890964', '2022-03-26 06:00:51'),
(8, '1594567890964', '2022-03-27 05:05:51'),
(9, '2112996123456', '2022-03-28 04:53:51'),
(10, '1234567890123', '2022-03-29 05:01:31'),
(11, '1594567890964', '2022-03-30 04:52:05'),
(12, '2112996123456', '2022-03-31 14:59:24'),
(13, '1594567890964', '2022-03-31 15:01:42');

-- --------------------------------------------------------

--
-- Table structure for table `evidencija_odlaska`
--

CREATE TABLE `evidencija_odlaska` (
  `id` int(11) NOT NULL,
  `jmbg` varchar(13) COLLATE utf8_unicode_ci DEFAULT NULL,
  `vreme_odlaska` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `pazar` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `evidencija_odlaska`
--

INSERT INTO `evidencija_odlaska` (`id`, `jmbg`, `vreme_odlaska`, `pazar`) VALUES
(1, '0406999710162', '2022-03-21 14:03:00', 14567),
(2, '0406999710162', '2022-03-22 14:00:00', 23456),
(5, '2112996123456', '2022-03-23 13:53:43', 20802),
(6, '1234567890123', '2022-03-24 14:02:51', 35612),
(7, '2112996123456', '2022-03-25 14:21:15', 14678),
(8, '1594567890964', '2022-03-26 14:58:43', 45623),
(9, '1594567890964', '2022-03-27 13:03:43', 24623),
(10, '2112996123456', '2022-03-28 13:03:43', 34612),
(11, '1234567890123', '2022-03-29 13:53:43', 32456),
(12, '1594567890964', '2022-03-30 13:33:43', 23562),
(13, '2112996123456', '2022-03-31 14:32:09', 4055),
(14, '1594567890964', '2022-03-31 14:48:27', 5910);

-- --------------------------------------------------------

--
-- Table structure for table `korisnici`
--

CREATE TABLE `korisnici` (
  `id` int(11) NOT NULL,
  `username` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `password` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `advanced_privilages` char(1) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pincode` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sektor` char(1) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `korisnici`
--

INSERT INTO `korisnici` (`id`, `username`, `password`, `advanced_privilages`, `pincode`, `sektor`) VALUES
(1, 'marko', 'marko123', '1', '1234', NULL),
(2, 'nikola', 'nikola223', '0', '', 'A'),
(3, 'marija', 'marija334', '0', ' ', 'B'),
(4, 'ana', 'stamen', '0', '', 'B');

-- --------------------------------------------------------

--
-- Table structure for table `narudzbine`
--

CREATE TABLE `narudzbine` (
  `id` int(11) NOT NULL,
  `id_stavke_narudzbenice` int(11) NOT NULL,
  `id_zaposlenog` int(11) NOT NULL,
  `ukupna_cena` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `narudzbine`
--

INSERT INTO `narudzbine` (`id`, `id_stavke_narudzbenice`, `id_zaposlenog`, `ukupna_cena`) VALUES
(42, 56, 2, 1080),
(43, 55, 2, 1000),
(44, 3, 2, 170),
(45, 1, 2, 2080),
(46, 2, 2, 490),
(47, 7, 2, 995),
(48, 6, 2, 340),
(49, 4, 2, 1160),
(50, 8, 3, 1050),
(51, 9, 3, 340),
(52, 10, 3, 3520),
(53, 11, 3, 860),
(54, 12, 3, 720),
(55, 13, 4, 1540),
(56, 14, 4, 1720),
(57, 15, 4, 400),
(58, 16, 4, 2110),
(59, 19, 2, 1025),
(60, 17, 2, 1380),
(61, 18, 2, 420),
(62, 21, 3, 880),
(63, 20, 3, 2330),
(64, 22, 2, 1625),
(65, 24, 2, 900),
(66, 23, 2, 2140),
(67, 25, 2, 1280),
(68, 27, 2, 695),
(69, 28, 2, 800),
(70, 26, 2, 1280),
(71, 30, 2, 310),
(72, 31, 2, 1160),
(73, 29, 2, 1450),
(74, 32, 2, 1420),
(75, 33, 3, 3020),
(76, 34, 3, 240),
(77, 35, 3, 300),
(78, 37, 3, 880),
(79, 36, 3, 1470),
(80, 38, 2, 1100),
(81, 40, 3, 600),
(82, 41, 3, 3485),
(83, 39, 3, 880);

-- --------------------------------------------------------

--
-- Table structure for table `pica`
--

CREATE TABLE `pica` (
  `id` int(11) NOT NULL,
  `ime` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `cena` int(11) DEFAULT NULL,
  `raspolozivost` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `pica`
--

INSERT INTO `pica` (`id`, `ime`, `cena`, `raspolozivost`) VALUES
(1, 'Espresso kafa', 125, 37),
(2, 'Cappuccino', 145, 28),
(3, 'Nescafe', 140, 38),
(4, 'Nescafe sa slagom', 170, 34),
(5, 'Instant kafa', 155, 50),
(6, 'Black n easy', 80, 53),
(7, 'Irska kafa', 170, 12),
(8, 'Topla cokolada bela', 180, 50),
(10, 'Topla cokolada crna', 180, 23),
(11, 'Topla cokolada', 150, 33),
(12, 'Mleko', 120, 45);

-- --------------------------------------------------------

--
-- Table structure for table `stavke_narudzbenice`
--

CREATE TABLE `stavke_narudzbenice` (
  `id` int(11) NOT NULL,
  `id_pica` int(11) NOT NULL,
  `vreme` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `kolicina` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `stavke_narudzbenice`
--

INSERT INTO `stavke_narudzbenice` (`id`, `id_pica`, `vreme`, `kolicina`) VALUES
(1, 7, '2022-03-31 13:12:13', 3),
(1, 10, '2022-03-31 13:12:13', 4),
(2, 7, '2022-03-31 13:12:22', 2),
(2, 11, '2022-03-31 13:12:22', 1),
(3, 7, '2022-03-31 13:12:31', 1),
(4, 7, '2022-03-31 13:12:49', 2),
(4, 10, '2022-03-31 13:12:49', 4),
(4, 12, '2022-03-31 13:12:49', 1),
(5, 10, '2022-03-31 13:12:58', 3),
(5, 12, '2022-03-31 13:12:58', 1),
(6, 4, '2022-03-31 13:13:06', 2),
(7, 2, '2022-03-31 13:13:15', 1),
(7, 4, '2022-03-31 13:13:15', 5),
(8, 3, '2022-03-31 13:13:57', 1),
(8, 5, '2022-03-31 13:13:57', 2),
(8, 11, '2022-03-31 13:13:57', 4),
(9, 7, '2022-03-31 13:14:05', 2),
(10, 3, '2022-03-31 13:14:23', 3),
(10, 5, '2022-03-31 13:14:23', 20),
(11, 4, '2022-03-31 13:14:43', 3),
(11, 11, '2022-03-31 13:14:43', 1),
(11, 12, '2022-03-31 13:14:43', 2),
(12, 10, '2022-03-31 13:14:57', 4),
(13, 8, '2022-03-31 13:15:14', 3),
(13, 11, '2022-03-31 13:15:14', 4),
(13, 12, '2022-03-31 13:15:14', 4),
(14, 1, '2022-03-31 13:15:22', 1),
(14, 2, '2022-03-31 13:15:22', 11),
(15, 6, '2022-03-31 13:15:30', 5),
(16, 4, '2022-03-31 13:15:43', 5),
(16, 8, '2022-03-31 13:15:43', 3),
(16, 10, '2022-03-31 13:15:43', 4),
(17, 4, '2022-03-31 13:43:22', 1),
(17, 7, '2022-03-31 13:43:22', 3),
(17, 10, '2022-03-31 13:43:22', 2),
(18, 6, '2022-03-31 13:44:00', 3),
(18, 12, '2022-03-31 13:44:00', 1),
(19, 2, '2022-03-31 13:44:23', 1),
(19, 4, '2022-03-31 13:44:23', 2),
(19, 10, '2022-03-31 13:44:23', 3),
(20, 4, '2022-03-31 13:46:34', 5),
(20, 7, '2022-03-31 13:46:34', 2),
(20, 8, '2022-03-31 13:46:34', 3),
(20, 11, '2022-03-31 13:46:34', 4),
(21, 4, '2022-03-31 13:46:45', 2),
(21, 10, '2022-03-31 13:46:45', 3),
(22, 5, '2022-03-31 14:03:18', 2),
(22, 8, '2022-03-31 14:03:18', 3),
(23, 7, '2022-03-31 14:04:10', 2),
(23, 8, '2022-03-31 14:04:10', 10),
(24, 8, '2022-03-31 14:04:23', 2),
(24, 10, '2022-03-31 14:04:23', 3),
(25, 4, '2022-03-31 14:30:30', 4),
(25, 6, '2022-03-31 14:30:30', 3),
(25, 8, '2022-03-31 14:30:30', 2),
(26, 6, '2022-03-31 14:30:59', 5),
(26, 7, '2022-03-31 14:30:59', 2),
(26, 10, '2022-03-31 14:30:59', 3),
(27, 5, '2022-03-31 14:31:13', 1),
(27, 10, '2022-03-31 14:31:13', 3),
(28, 6, '2022-03-31 14:31:36', 10),
(29, 2, '2022-03-31 14:38:33', 4),
(29, 4, '2022-03-31 14:38:33', 3),
(29, 8, '2022-03-31 14:38:33', 2),
(30, 6, '2022-03-31 14:38:58', 2),
(30, 12, '2022-03-31 14:38:58', 3),
(31, 5, '2022-03-31 14:39:20', 1),
(31, 7, '2022-03-31 14:39:20', 2),
(31, 12, '2022-03-31 14:39:20', 4),
(32, 5, '2022-03-31 14:45:28', 2),
(32, 7, '2022-03-31 14:45:28', 3),
(32, 11, '2022-03-31 14:45:28', 4),
(33, 5, '2022-03-31 14:47:21', 10),
(33, 7, '2022-03-31 14:47:21', 2),
(33, 11, '2022-03-31 14:47:21', 3),
(34, 6, '2022-03-31 14:47:30', 3),
(35, 11, '2022-03-31 14:47:35', 2),
(36, 8, '2022-03-31 14:48:02', 4),
(36, 11, '2022-03-31 14:48:02', 5),
(37, 7, '2022-03-31 14:48:10', 2),
(37, 10, '2022-03-31 14:48:10', 3),
(38, 4, '2022-03-31 15:00:31', 4),
(38, 6, '2022-03-31 15:00:31', 3),
(38, 10, '2022-03-31 15:00:31', 1),
(39, 7, '2022-03-31 15:01:57', 2),
(39, 8, '2022-03-31 15:01:57', 3),
(40, 6, '2022-03-31 15:02:05', 3),
(40, 8, '2022-03-31 15:02:05', 2),
(41, 5, '2022-03-31 15:02:21', 19),
(41, 8, '2022-03-31 15:02:21', 2),
(41, 10, '2022-03-31 15:02:21', 1);

-- --------------------------------------------------------

--
-- Table structure for table `zaposleni`
--

CREATE TABLE `zaposleni` (
  `id_korisnika` int(11) DEFAULT NULL,
  `ime` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `prezime` text COLLATE utf8_unicode_ci DEFAULT NULL,
  `jmbg` varchar(13) COLLATE utf8_unicode_ci NOT NULL,
  `telefon` text COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `zaposleni`
--

INSERT INTO `zaposleni` (`id_korisnika`, `ime`, `prezime`, `jmbg`, `telefon`) VALUES
(1, 'Marko', 'Markovic', '0406999710162', '0645678234'),
(4, 'Ana', 'Stamenkovic', '1234567890123', '064/923-8456'),
(3, 'Marija', 'Nikolic', '1594567890964', '066/987-7623'),
(2, 'Nikola', 'Jovanovic', '2112996123456', '063/213-1234');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `evidencija_dolaska`
--
ALTER TABLE `evidencija_dolaska`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Id_zaposlenog` (`jmbg`);

--
-- Indexes for table `evidencija_odlaska`
--
ALTER TABLE `evidencija_odlaska`
  ADD PRIMARY KEY (`id`),
  ADD KEY `jmbg` (`jmbg`);

--
-- Indexes for table `korisnici`
--
ALTER TABLE `korisnici`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `narudzbine`
--
ALTER TABLE `narudzbine`
  ADD PRIMARY KEY (`id`,`id_stavke_narudzbenice`,`id_zaposlenog`),
  ADD KEY `id_stavke_narudzbenice` (`id_stavke_narudzbenice`),
  ADD KEY `id_zaposlenog` (`id_zaposlenog`);

--
-- Indexes for table `pica`
--
ALTER TABLE `pica`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `stavke_narudzbenice`
--
ALTER TABLE `stavke_narudzbenice`
  ADD PRIMARY KEY (`id`,`id_pica`,`vreme`),
  ADD KEY `id_pica` (`id_pica`);

--
-- Indexes for table `zaposleni`
--
ALTER TABLE `zaposleni`
  ADD PRIMARY KEY (`jmbg`),
  ADD KEY `FK_Id_Korisnika` (`id_korisnika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `evidencija_dolaska`
--
ALTER TABLE `evidencija_dolaska`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=45;

--
-- AUTO_INCREMENT for table `evidencija_odlaska`
--
ALTER TABLE `evidencija_odlaska`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT for table `korisnici`
--
ALTER TABLE `korisnici`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `narudzbine`
--
ALTER TABLE `narudzbine`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=84;

--
-- AUTO_INCREMENT for table `stavke_narudzbenice`
--
ALTER TABLE `stavke_narudzbenice`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `evidencija_dolaska`
--
ALTER TABLE `evidencija_dolaska`
  ADD CONSTRAINT `FK_Id_zaposlenog` FOREIGN KEY (`jmbg`) REFERENCES `zaposleni` (`jmbg`);

--
-- Constraints for table `evidencija_odlaska`
--
ALTER TABLE `evidencija_odlaska`
  ADD CONSTRAINT `evidencija_odlaska_ibfk_1` FOREIGN KEY (`jmbg`) REFERENCES `zaposleni` (`jmbg`);

--
-- Constraints for table `narudzbine`
--
ALTER TABLE `narudzbine`
  ADD CONSTRAINT `narudzbine_ibfk_1` FOREIGN KEY (`id_stavke_narudzbenice`) REFERENCES `stavke_narudzbenice` (`id`),
  ADD CONSTRAINT `narudzbine_ibfk_2` FOREIGN KEY (`id_zaposlenog`) REFERENCES `zaposleni` (`id_korisnika`);

--
-- Constraints for table `stavke_narudzbenice`
--
ALTER TABLE `stavke_narudzbenice`
  ADD CONSTRAINT `stavke_narudzbenice_ibfk_1` FOREIGN KEY (`id_pica`) REFERENCES `pica` (`id`);

--
-- Constraints for table `zaposleni`
--
ALTER TABLE `zaposleni`
  ADD CONSTRAINT `FK_Id_Korisnika` FOREIGN KEY (`id_korisnika`) REFERENCES `korisnici` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
