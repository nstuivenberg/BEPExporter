package nl.hu.bep.group4.bifi.exporter.implementations;

import java.util.List;

import nl.hu.bep.group4.bifi.interfaces.IEFExporter;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.Klant;

public class IEFExporterImpl implements IEFExporter {

	@Override
	public void exportFacturen(List<Factuur> facturen) {
		// TODO Auto-generated method stub
		
	}
	
	public String exportChar(String value, int length) {
		String result = value;
		if(result.length() > length) {
			result = result.substring(0, length);
		}
		for(int i=0;i<length-value.length();i++) {
			result += " ";
		}
		return result;
	}

	public String exportDouble(double value, int beforeComma, int afterComma) {
		String result = String.format("%"+beforeComma+"."+afterComma+"f", value).replaceAll(",", "").replaceAll("-", "");
		if(result.length() > beforeComma+afterComma) {
			result = result.substring(result.length()-(beforeComma+afterComma));
		}
		int zeros = beforeComma+afterComma-result.length();
		for(int i=0;i<zeros;i++) {
			result = "0"+result;
		}
		if(value < 0) {
			result = result.replaceAll("0", " ");
			result = result.replaceAll("1", "!");
			result = result.replaceAll("2", "\"");
			result = result.replaceAll("3", "#");
			result = result.replaceAll("4", "\\$");
			result = result.replaceAll("5", "%");
			result = result.replaceAll("6", "&");
			result = result.replaceAll("7", "\\\\");
			result = result.replaceAll("8", "(");
			result = result.replaceAll("9", ")");
		}
		return result;
	}

	public String exportKlant(Klant klant) {
		return "";
	}

}
