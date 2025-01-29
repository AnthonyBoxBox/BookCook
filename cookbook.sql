-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sty 29, 2025 at 07:29 AM
-- Wersja serwera: 10.4.28-MariaDB
-- Wersja PHP: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cookbook`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `przepisy`
--

CREATE TABLE `przepisy` (
  `id_przepisu` int(11) NOT NULL,
  `nazwa` varchar(255) DEFAULT NULL,
  `typ` varchar(30) NOT NULL,
  `instrukcje` text DEFAULT NULL,
  `ilosc_skladnikow` int(11) DEFAULT NULL,
  `vege` tinyint(1) DEFAULT NULL,
  `poziom_ostr` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `przepisy`
--

INSERT INTO `przepisy` (`id_przepisu`, `nazwa`, `typ`, `instrukcje`, `ilosc_skladnikow`, `vege`, `poziom_ostr`) VALUES
(1, 'Zupa pomidorowa', 'Obiad', 'Obierz pomidory, cebulę. Wszystko razem ugotuj w wodzie. Dodaj liście laurowe, sól i pieprz.', 6, 1, 1),
(2, 'Rosół', 'Obiad', 'Ugotuj mięso z warzywami (marchew, pietruszka, seler). Dodaj makaron i przyprawy.', 9, 0, 1),
(3, 'Tiramisu', 'Deser', '...', 9, 1, 0);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `przepisy_skladniki`
--

CREATE TABLE `przepisy_skladniki` (
  `id_przepisu` int(11) NOT NULL,
  `id_skladnika` int(11) NOT NULL,
  `ilosc` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `przepisy_skladniki`
--

INSERT INTO `przepisy_skladniki` (`id_przepisu`, `id_skladnika`, `ilosc`) VALUES
(1, 1, 500.00),
(1, 2, 1.00),
(1, 3, 2.00),
(1, 4, 2.00),
(1, 5, 5.00),
(1, 6, 5.00),
(2, 3, 2.00),
(2, 5, 5.00),
(2, 6, 5.00),
(2, 7, 1000.00),
(2, 8, 2.00),
(2, 9, 1.00),
(2, 10, 1.00),
(2, 11, 300.00),
(2, 12, 2.00);

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `skladniki`
--

CREATE TABLE `skladniki` (
  `id_skladnika` int(11) NOT NULL,
  `nazwa_skladnika` varchar(255) DEFAULT NULL,
  `jednostka_miary` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `skladniki`
--

INSERT INTO `skladniki` (`id_skladnika`, `nazwa_skladnika`, `jednostka_miary`) VALUES
(1, 'pomidory', 'g'),
(2, 'cebula', 'sztuka'),
(3, 'woda', 'litr'),
(4, 'liście laurowe', 'sztuka'),
(5, 'sól', 'szczypta'),
(6, 'pieprz', 'szczypta'),
(7, 'kurczak', 'g'),
(8, 'marchew', 'sztuka'),
(9, 'pietruszka', 'sztuka'),
(10, 'seler', 'korzeń'),
(11, 'makaron', 'g'),
(12, 'liść laurowy', 'sztuka');

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `przepisy`
--
ALTER TABLE `przepisy`
  ADD PRIMARY KEY (`id_przepisu`);

--
-- Indeksy dla tabeli `przepisy_skladniki`
--
ALTER TABLE `przepisy_skladniki`
  ADD PRIMARY KEY (`id_przepisu`,`id_skladnika`),
  ADD KEY `id_skladnika` (`id_skladnika`);

--
-- Indeksy dla tabeli `skladniki`
--
ALTER TABLE `skladniki`
  ADD PRIMARY KEY (`id_skladnika`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `przepisy`
--
ALTER TABLE `przepisy`
  MODIFY `id_przepisu` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `skladniki`
--
ALTER TABLE `skladniki`
  MODIFY `id_skladnika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `przepisy_skladniki`
--
ALTER TABLE `przepisy_skladniki`
  ADD CONSTRAINT `przepisy_skladniki_ibfk_1` FOREIGN KEY (`id_przepisu`) REFERENCES `przepisy` (`id_przepisu`),
  ADD CONSTRAINT `przepisy_skladniki_ibfk_2` FOREIGN KEY (`id_skladnika`) REFERENCES `skladniki` (`id_skladnika`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
