-- MySQL dump 10.13  Distrib 5.5.16, for Win64 (x86)
--
-- Host: localhost    Database: gladioscev
-- ------------------------------------------------------
-- Server version	5.5.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `movimentosestoque`
--

DROP TABLE IF EXISTS `movimentosestoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movimentosestoque` (
  `ID_Movimento` int(11) NOT NULL DEFAULT '0',
  `DataMovimento` datetime NOT NULL,
  `ID_TipoMovimento` int(11) NOT NULL,
  `ID_Variante` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_Movimento`),
  KEY `fk_MovimentosEstoque_TiposMovimentos_idx` (`ID_TipoMovimento`),
  KEY `ID_Variante` (`ID_Variante`),
  CONSTRAINT `ID_Variante` FOREIGN KEY (`ID_Variante`) REFERENCES `variantesestoque` (`ID_Variante`),
  CONSTRAINT `fk_MovimentosEstoque_TiposMovimentos` FOREIGN KEY (`ID_TipoMovimento`) REFERENCES `tiposmovimentos` (`ID_TipoMovimento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movimentosestoque`
--

LOCK TABLES `movimentosestoque` WRITE;
/*!40000 ALTER TABLE `movimentosestoque` DISABLE KEYS */;
INSERT INTO `movimentosestoque` VALUES (0,'2003-12-23 00:00:00',8,NULL),(1,'2003-12-23 12:12:12',8,NULL),(2,'2018-07-09 03:00:39',8,NULL),(3,'2018-07-09 03:01:46',8,NULL),(4,'2018-07-09 03:03:19',8,NULL),(5,'2018-07-09 03:04:47',8,NULL),(6,'2018-07-09 03:07:03',8,NULL),(7,'2018-07-09 03:08:33',8,NULL),(8,'2018-07-09 03:08:50',8,NULL),(9,'2018-07-09 09:59:05',8,NULL),(10,'2018-07-10 15:25:28',8,NULL),(11,'2018-07-10 15:28:02',8,NULL);
/*!40000 ALTER TABLE `movimentosestoque` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtos`
--

DROP TABLE IF EXISTS `produtos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produtos` (
  `ID_Produto` int(11) NOT NULL AUTO_INCREMENT,
  `NomeProduto` varchar(45) NOT NULL,
  `DataAquisicao` datetime NOT NULL,
  `Descricao` mediumtext,
  `Quantidade` decimal(10,2) NOT NULL,
  `Unidade` char(2) NOT NULL,
  `PrecoAquisicao` decimal(10,2) NOT NULL,
  `PrecoVenda` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ID_Produto`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtos`
--

LOCK TABLES `produtos` WRITE;
/*!40000 ALTER TABLE `produtos` DISABLE KEYS */;
INSERT INTO `produtos` VALUES (1,'Banana','2018-11-23 00:00:00','Bananananana',5.00,'U',10.00,11.00);
/*!40000 ALTER TABLE `produtos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `produtosmovimentos`
--

DROP TABLE IF EXISTS `produtosmovimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `produtosmovimentos` (
  `ID_ProdutoMovimento` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Movimento` int(11) NOT NULL,
  `ID_Produto` int(11) NOT NULL,
  `Quantidade` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ID_ProdutoMovimento`),
  KEY `fk_Movimento_idx` (`ID_Movimento`),
  KEY `fk_Produto_idx` (`ID_Produto`),
  CONSTRAINT `fk_Movimento` FOREIGN KEY (`ID_Movimento`) REFERENCES `movimentosestoque` (`ID_Movimento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Produto` FOREIGN KEY (`ID_Produto`) REFERENCES `produtos` (`ID_Produto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `produtosmovimentos`
--

LOCK TABLES `produtosmovimentos` WRITE;
/*!40000 ALTER TABLE `produtosmovimentos` DISABLE KEYS */;
INSERT INTO `produtosmovimentos` VALUES (3,4,1,2.00),(4,4,1,2.00),(6,8,1,2.00),(7,9,1,2.00),(8,10,1,2.00),(9,11,1,2.00),(10,11,1,2.00),(11,11,1,2.00),(12,11,1,2.00),(13,1,1,5.00),(14,1,1,5.00),(15,1,1,5.00),(16,1,1,5.00);
/*!40000 ALTER TABLE `produtosmovimentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiposmovimentos`
--

DROP TABLE IF EXISTS `tiposmovimentos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tiposmovimentos` (
  `ID_TipoMovimento` int(11) NOT NULL AUTO_INCREMENT,
  `NomeMovimento` varchar(45) NOT NULL,
  `DebitoCredito` char(1) NOT NULL,
  PRIMARY KEY (`ID_TipoMovimento`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiposmovimentos`
--

LOCK TABLES `tiposmovimentos` WRITE;
/*!40000 ALTER TABLE `tiposmovimentos` DISABLE KEYS */;
INSERT INTO `tiposmovimentos` VALUES (8,'VENDA','C');
/*!40000 ALTER TABLE `tiposmovimentos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `variantesestoque`
--

DROP TABLE IF EXISTS `variantesestoque`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `variantesestoque` (
  `ID_Variante` int(11) NOT NULL,
  `Nome` varchar(200) NOT NULL,
  `CPF` varchar(10) DEFAULT NULL,
  `CNPJ` varchar(14) DEFAULT NULL,
  `TelefoneCelular` varchar(20) DEFAULT NULL,
  `TelefoneFixo` varchar(20) DEFAULT NULL,
  `CEP` varchar(9) DEFAULT NULL,
  `Rua` varchar(80) DEFAULT NULL,
  `Complemento` varchar(50) DEFAULT NULL,
  `Bairro` varchar(100) DEFAULT NULL,
  `DataCadastro` datetime DEFAULT NULL,
  `NomeFantasia` varchar(200) DEFAULT NULL,
  `Tipo` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`ID_Variante`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `variantesestoque`
--

LOCK TABLES `variantesestoque` WRITE;
/*!40000 ALTER TABLE `variantesestoque` DISABLE KEYS */;
/*!40000 ALTER TABLE `variantesestoque` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-12 23:53:59
