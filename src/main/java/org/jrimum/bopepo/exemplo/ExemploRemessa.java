package org.jrimum.bopepo.exemplo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jrimum.bopepo.pdf.Files;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.jrimum.texgit.Texgit;
import org.jrimum.utilix.ClassLoaders;

public class ExemploRemessa {
	
	private static int convenio = 2866935 ;
	
	private static int sequencial ; // Variável usada para seta a sequencia dos arquivos
	private static String nDocumento = "0000000";	
	/**
	 * Classe geradora de arquivos de REMESSA para BANCO DO BRASIL - CNAB 400 
	 * @author Rayan
	 */
	
	/*
	 * NOTA : as linhas comentadas são setadas por padrão no layout do arquivo
	 */
	public static void main(String[] args) throws IOException {

		// Identificando Layout
		File layoutTemporario = new File("LayoutRemessa_DaycovalCNAB400.txg.xml");
		File layout = Files.bytesToFile(layoutTemporario, IOUtils.toByteArray(ClassLoaders.getResourceAsStream("/layoutBanco/LayoutRemessa_DaycovalCNAB400.txg.xml")));

		// Criando o arquivo de remessa
		FlatFile<Record> ff = Texgit.createFlatFile(layout);
		
		// Registrando Header
		ff.addRecord(createHeader(ff));
		
		/* Laço para representar mais de 1 boleto registrado no arquivo
		*  Registrando Títulos(boletos)
		**/
		for( sequencial = 1; sequencial <= 5; sequencial++){ 
			ff.addRecord(createTransacaoTitulos(ff, sequencial));
		}
		
		// Registrando Trailer
		ff.addRecord(createTrailer(ff, sequencial));

		// Salvando arquivo de remessa.
		File arquivoDeRemessa = new File("resource/arquivoRemessa/REMESSADaycoval"+new SimpleDateFormat("ddMMyyyy").format(new Date())+".REM");
		FileUtils.writeLines(arquivoDeRemessa, ff.write(), "\r\n");
		System.out.println("Arquivo gerado com sucesso em: "+ arquivoDeRemessa);

		if (layoutTemporario != null) {
			layoutTemporario.delete();
		}
	}

	private static Record createHeader(FlatFile<Record> ff) {
				
		/*
		 * NOTA : as linhas comentadas são setadas por padrão no layout do arquivo
		 */
		Record header = ff.createRecord("Header");

		/* 
		header.setValue("IdentificacaoRegistro", 0);
		header.setValue("TipoOperacao", 1);
		header.setValue("IdentificacaoExtensoTipoOperacao", "REMESSA");
		header.setValue("IdentificacaoTipoServico", 1);
		header.setValue("IdentificacaoExtensoTipoServico", "COBRANCA");
		*/
		/*header.setValue("PrefixoAgencia", 1623);
		header.setValue("DigitoVerificadorAgencia", 2);
		header.setValue("NumeroContaCorrente", 101916);
		header.setValue("DigitoVerificadorContaCorrente", 0);
		header.setValue("NomeCedente", "FADESP");
		//header.setValue("BB", "001BANCO DO BRASIL");
		header.setValue("DataGravacao", 170118);
		header.setValue("SequencialRemessa", "0000000");
		header.setValue("NumeroConvenioLider", convenio);
		header.setValue("SequencialRegistro", 1);*/
                
                /*Exemplo Daycoval*/
                header.setValue("CodigoEmpresa", "cleancoat"); 
                header.setValue("NomeEmpresa","Cleancoat Chemical Ltda");
                header.setValue("DataGravacao","150921");                       
                //header.setValue("Filler", " ");
                header.setValue("SequencialRegistro", 1) ; 

		return header;
	}

	private static Record createTransacaoTitulos(FlatFile<Record> ff, int sequencial) {
			
		/*
		 * NOTA : as linhas comentadas são setadas por padrão no layout do arquivo
		 */
		Record transacaoTitulos = ff.createRecord("TransacaoTitulo");

		/*
		transacaoTitulos.setValue("IdentiFicacaoRegistroDetalhe", 7);
		transacaoTitulos.setValue("TipoInscricaoCedente", 02);
		transacaoTitulos.setValue("NumeroCpfCnpjCedente", "06313870000441");
		transacaoTitulos.setValue("PrefixoAgencia", 1623);
		transacaoTitulos.setValue("DigitoVerificadorAgencia", 2);
		transacaoTitulos.setValue("NumeroContaCorrenteCedente", 101916);
		transacaoTitulos.setValue("DigitoVerificadorConta", 0);
		transacaoTitulos.setValue("NumeroCovenioCobrancaCedente", convenio);
		//transacaoTitulos.setValue("CodigoControleEmpresa", "");
		transacaoTitulos.setValue("NossoNumero", nDocumento+sequencial);
		transacaoTitulos.setValue("NumeroPrestacao", 00);
		transacaoTitulos.setValue("GrupoValor", 00);
		transacaoTitulos.setValue("IndicativoMensagemSacador", "");
		transacaoTitulos.setValue("PrefixoTitulo", "   ");
		transacaoTitulos.setValue("VariacaoCarteira", "019");
		transacaoTitulos.setValue("ContaCaucao", 0);
		transacaoTitulos.setValue("NumeroBordero", 000000);
		transacaoTitulos.setValue("TipoCobranca", "     ");
		transacaoTitulos.setValue("CarteiraCobranca", 17);
		transacaoTitulos.setValue("Comando", 01);
		//transacaoTitulos.setValue("NumeroTituloAtribuidoCedente", "          ");
		transacaoTitulos.setValue("DataVencimento", "190118");
		transacaoTitulos.setValue("ValorTitulo", 1);
		/*transacaoTitulos.setValue("NumeroBanco", 001);
		transacaoTitulos.setValue("PrefixoAgenciaCobradora", 0000);
		transacaoTitulos.setValue("DigitoVerificadorPrefixoAgenciaCobradora", " ");
		transacaoTitulos.setValue("EspecieTitulo", 12);
		transacaoTitulos.setValue("AceiteTitulo", "N");
		transacaoTitulos.setValue("DataEmissao", 170118);
		transacaoTitulos.setValue("InstrucaoCodificada", "  ");
		transacaoTitulos.setValue("InstrucaoCodificada", "  ");
		transacaoTitulos.setValue("JurosMoraDiaAtraso", "00000000003");
                transacaoTitulos.setValue("DataLimite", "      ");
 		transacaoTitulos.setValue("ValorDesconto", "           "); 
		transacaoTitulos.setValue("ValorIof", "           ");
		transacaoTitulos.setValue("ValorAbatimento", "           ");
		transacaoTitulos.setValue("TipoInscricaoSacado", 01);
		transacaoTitulos.setValue("NumeroCnpjCpfSacado", "12345678900");
		transacaoTitulos.setValue("NomeSacado", "FULADO DA SILVA SAURO");
		//transacaoTitulos.setValue("ComplementoRegistro", "");
		transacaoTitulos.setValue("EnderecoSacado", "Rua Augusto Corrêa, LABES");
		transacaoTitulos.setValue("BairroSacado", "Guamá");
		transacaoTitulos.setValue("CepEnderecoSacado", "66075110");
		transacaoTitulos.setValue("CidadeSacado", "BELÉM");
		transacaoTitulos.setValue("UfCidadeSacado", "PA");
		transacaoTitulos.setValue("Observacao", "                                        ");
		transacaoTitulos.setValue("NumeroDiasProtesto", "  ");
		transacaoTitulos.setValue("ComplementoRegistro", " ");
		transacaoTitulos.setValue("SequencialRegistro", sequencial);*/
                        
                        
                /*Exemplo Daycoval*/
                transacaoTitulos.setValue("NumeroCpfCnpjCedente", "17430900000171");   
                transacaoTitulos.setValue("CodigoEmpresa", "cleancoat"); 
                transacaoTitulos.setValue("CodigoControleEmpresa", "123");
                transacaoTitulos.setValue("NossoNumero", nDocumento+sequencial);
                transacaoTitulos.setValue("NumeroPrestacao", 01); 
                transacaoTitulos.setValue("DataVencimento", "200921");   
                transacaoTitulos.setValue("ValorTitulo",100.50);
                transacaoTitulos.setValue("PrefixoAgencia",0000);   
                transacaoTitulos.setValue("DigitoVerificadorAgencia", 0); 
                transacaoTitulos.setValue("EspecieTitulo",12);  
                transacaoTitulos.setValue("AceiteTitulo","N");
                transacaoTitulos.setValue("DataEmissao", "160921");  
                transacaoTitulos.setValue("JurosMoraDiaAtraso", "00000000003");
                transacaoTitulos.setValue("DescontoAte", "");
                transacaoTitulos.setValue("ValorDesconto", 0);
                transacaoTitulos.setValue("ValorAbatimento", 0);
                transacaoTitulos.setValue("TipoInscricaoSacado",01) ;
                transacaoTitulos.setValue("NumeroCnpjCpfSacado", "12345678900");  
                transacaoTitulos.setValue("NomeSacado", "FULADO DA SILVA SAURO");  
                transacaoTitulos.setValue("ComplementoRegistro",""); 
                transacaoTitulos.setValue("EnderecoSacado", "Rua Augusto Corrêa, LABES");
		transacaoTitulos.setValue("BairroSacado", "Guamá");
		transacaoTitulos.setValue("CepEnderecoSacado", "66075110");
		transacaoTitulos.setValue("CidadeSacado", "BELÉM");
		transacaoTitulos.setValue("UfCidadeSacado", "PA");
                transacaoTitulos.setValue("NomeAvalista", "");  
                transacaoTitulos.setValue("ComplementoRegistro","");  
                transacaoTitulos.setValue("DiasProtesto",0);
                transacaoTitulos.setValue("Moeda",1);  
                transacaoTitulos.setValue("SequencialRegistro", sequencial);
		
		return transacaoTitulos;
	}

	private static Record createTrailer(FlatFile<Record> ff, int sequencial) {
		
		Record trailer = ff.createRecord("Trailler");
		
		trailer.setValue("NumeroSequencialRegistro", sequencial);
		
		return trailer;
	}
}