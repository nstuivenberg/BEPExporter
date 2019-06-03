package nl.hu.bep.group4.bifi.exporter.implementations;

import java.util.List;

import nl.hu.bep.group4.bifi.interfaces.IEFExporter;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.FactuurRegel.Unit;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

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
		String result = String.format("%"+beforeComma+"."+afterComma+"f", value).replaceAll(",", "").replaceAll("\\.", "").replaceAll("-", "");
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

	public String exportKlant(Klant klant, Persoon persoon) {
		return "K"
				+exportChar(klant.getBedrijfsnaam(), 40)
				+exportChar(getAanhef(persoon), 6)
				+exportChar(persoon.getVoornaam(), 20)
				+exportChar(persoon.getTussenvoegsel(), 7)
				+exportChar(persoon.getAchternaam(), 40)
				+exportChar(klant.getFactuurAdres().getStraat(), 60)
				+exportChar(klant.getFactuurAdres().getHuisnummer(), 10)
				+exportChar(klant.getFactuurAdres().getPostcode(), 6)
				+exportChar(klant.getFactuurAdres().getPlaats(), 20)
				+exportChar(klant.getVAT(), 13)
				+exportChar(klant.getBankrekeningNummer(), 64)
				+exportChar(klant.getBiC(), 10)
				;
	}
	
	private String getAanhef(Persoon persoon) {
		if(persoon.getGeslacht() == Persoon.Geslacht.MAN) {
			return "Dhr.";
		} else {
			return "Mvr.";
		}
	}

	public String exportFactuurRegel(FactuurRegel factuurRegel) {
		return "R"
				+exportChar(factuurRegel.getProductNaam(), 60)
				+exportDouble(factuurRegel.getAantal(), 3, 2)
				+exportDouble(factuurRegel.getTotaalprijsExBTW()/factuurRegel.getAantal(), 5, 2)
				+exportBtwType(factuurRegel.getBtwCode())
				//TODO: datum
				+exportChar(convertUnit(factuurRegel.getUnit()), 6)
				;
	}
	
	

	private String convertUnit(Unit unit) {
		if(unit == Unit.KILOGRAM) {
			return "kg";
		}
		return null;
	}

	private String exportBtwType(BTWcode btwCode) {
		switch(btwCode) {
			case GEEN:
				return "0";
			case LAAG:
				return "2";
			case HOOG:
				return "3";
		}
		return null;
	}

	public String invoiceInformatieRegel(Factuur factuur) {

		return null;
	}
}
