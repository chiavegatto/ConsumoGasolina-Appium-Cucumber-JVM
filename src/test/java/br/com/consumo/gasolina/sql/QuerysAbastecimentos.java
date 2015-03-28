package br.com.consumo.gasolina.sql;

import java.util.Map;

import br.com.consumo.gasolina.utils.Util;
import androidtools.db.sqlite.SQLiteAgent;

public class QuerysAbastecimentos {
	private static String PCKG_NAME = "br.com.consumogasolina";
	private static String DB_NAME = "abastecimentos.db";
	private static SQLiteAgent sqLiteAgent;
	
	
	public static void inserirAbastecimentos(Map<String, String> abastecimentos) throws Exception{
		sqLiteAgent = new SQLiteAgent(PCKG_NAME, DB_NAME);
		sqLiteAgent.startSQLSequence();
		sqLiteAgent.execSQL("INSERT INTO abastecimento (kmAbastecimento, quantidade, valor, data) VALUES ("+abastecimentos.get("KM Abastecimento")
		+","+abastecimentos.get("Quantidade Litros")+","+abastecimentos.get("Valor")+",'"+ Util.converteStringParaFormatoDoBancoDeDados(abastecimentos.get("Data Abastecimento"))+"')");
		sqLiteAgent.finishSQLSequence();
	}
}
