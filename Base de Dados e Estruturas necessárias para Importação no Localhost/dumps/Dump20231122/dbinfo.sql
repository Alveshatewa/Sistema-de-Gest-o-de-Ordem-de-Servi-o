-- phpMyAdmin SQL Dump
-- version 4.2.11
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: 24-Nov-2023 às 08:48
-- Versão do servidor: 5.6.21
-- PHP Version: 5.6.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `dbinfo`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbclientes`
--

CREATE TABLE IF NOT EXISTS `tbclientes` (
`idcliente` int(11) NOT NULL,
  `nomecliente` varchar(50) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `dataNascimento` date NOT NULL,
  `sexo` set('Masculino','Feminino') DEFAULT NULL,
  `naturalidade` varchar(50) DEFAULT NULL,
  `endereco` varchar(50) DEFAULT NULL
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbclientes`
--

INSERT INTO `tbclientes` (`idcliente`, `nomecliente`, `email`, `dataNascimento`, `sexo`, `naturalidade`, `endereco`) VALUES
(1, 'Alves Mazy', 'alvesmazy90@gmail.com', '2003-01-16', 'Masculino', 'Lubango', 'Tchioco-Rua do Estádio da Tundavala'),
(2, 'António João', 'antonioj@gmail.com', '0200-05-24', 'Masculino', 'Lubango', 'Bairro Tchioco');

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbordemservico`
--

CREATE TABLE IF NOT EXISTS `tbordemservico` (
  `os` int(11) NOT NULL,
  `dataos` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tipo` varchar(18) NOT NULL,
  `situacao` varchar(30) NOT NULL,
  `mercadoria` varchar(100) NOT NULL,
  `problema` varchar(50) NOT NULL,
  `servico` varchar(100) NOT NULL,
  `tecnico` varchar(50) NOT NULL,
  `valor` float(10,5) NOT NULL,
  `idcliente` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estrutura da tabela `tbusuario`
--

CREATE TABLE IF NOT EXISTS `tbusuario` (
`iduser` int(11) NOT NULL,
  `nomeuser` varchar(50) NOT NULL,
  `telefone` varchar(9) DEFAULT NULL,
  `login` varchar(50) NOT NULL,
  `senha` varchar(30) NOT NULL
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Extraindo dados da tabela `tbusuario`
--

INSERT INTO `tbusuario` (`iduser`, `nomeuser`, `telefone`, `login`, `senha`) VALUES
(1, 'Alves Hatewa', '946602461', 'almohawa', '1234567');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbclientes`
--
ALTER TABLE `tbclientes`
 ADD PRIMARY KEY (`idcliente`);

--
-- Indexes for table `tbordemservico`
--
ALTER TABLE `tbordemservico`
 ADD PRIMARY KEY (`os`), ADD KEY `idcliente` (`idcliente`);

--
-- Indexes for table `tbusuario`
--
ALTER TABLE `tbusuario`
 ADD PRIMARY KEY (`iduser`), ADD UNIQUE KEY `login` (`login`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbclientes`
--
ALTER TABLE `tbclientes`
MODIFY `idcliente` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tbusuario`
--
ALTER TABLE `tbusuario`
MODIFY `iduser` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- Constraints for dumped tables
--

--
-- Limitadores para a tabela `tbordemservico`
--
ALTER TABLE `tbordemservico`
ADD CONSTRAINT `tbordemservico_ibfk_1` FOREIGN KEY (`idcliente`) REFERENCES `tbclientes` (`idcliente`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
