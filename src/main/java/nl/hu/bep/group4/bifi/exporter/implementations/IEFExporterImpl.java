package nl.hu.bep.group4.bifi.exporter.implementations;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nl.hu.bep.group4.bifi.interfaces.IEFExporter;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.FactuurRegel.Unit;
import nl.hu.bep.group4.bifi.model.Klant;
import nl.hu.bep.group4.bifi.model.Persoon;

public class IEFExporterImpl implements IEFExporter {

	@Override
	public String exportFacturen(List<Factuur> facturen){
		int i = 0;
		Map<Integer, String> factuurregels = new HashMap<>();
		
		StringBuilder iefRegel = new StringBuilder();
		iefRegel.append("B");
		iefRegel.append(exportChar("Factory Ansbach", 60) );
		iefRegel.append(exportChar("Eyber Strasse", 60));
		iefRegel.append(exportChar("81", 10));
		iefRegel.append("9152BI");
		iefRegel.append(exportChar("Ansbach", 20));
		iefRegel.append("DE81549090312");
		iefRegel.append(exportChar("DE23DEU000198761111", 64));
		iefRegel.append("1234567890");
		iefRegel.append("\n");

		for (Factuur factuur : facturen) {
			iefRegel.append(exportKlant(factuur.getKlant(), factuur.getContactPersoon()));
			iefRegel.append("\n");
			iefRegel.append(invoiceInformatieRegel(factuur));
			iefRegel.append("\n");
			for (FactuurRegel regel : factuur.getFactuurregels()) {
				iefRegel.append(exportFactuurRegel(regel, factuur));
				iefRegel.append("\n");
				iefRegel.append(exportTekstRegel(regel.getProductNaam()));
			}
			factuurregels.put(i, iefRegel.toString());
			i += 1;
		}
		
		StringBuilder sb = new StringBuilder();
		for (String regel : factuurregels.values()) {
			sb.append(regel);
		}
		
		String slash = File.separator;
		String property = System.getProperty("user.home");
		String fileName ="createdFile.txt";
		try(FileWriter fileWriter = new FileWriter(property+slash+fileName)) {
			fileWriter.write(sb.toString());
		} catch (IOException e) {
			throw new IllegalStateException("Could not create the file. \n" + e);
		}
		return sb.toString();
	}

	public String exportChar(String value, int length) {
		StringBuilder resultBuilder = new StringBuilder();
		resultBuilder.append(value);
		if(value.length() > length) {
			resultBuilder = new StringBuilder(value.substring(0, length));
		}
		for(int i=0;i<length-value.length();i++) {
			resultBuilder.append(" ");
		}
		return resultBuilder.toString();
	}
	
	public String exportDouble(double value, int beforeComma, int afterComma) {

		String result = String.format("%" + beforeComma + "." + afterComma + "f", value)
				.replaceAll(",", "")
				.replaceAll("\\.", "")
				.replaceAll("-", "");

		if (result.length() > beforeComma + afterComma) {
			result = result.substring(result.length() - (beforeComma + afterComma));
		}
		int zeros = beforeComma + afterComma - result.length();
		for (int i = 0; i < zeros; i++) {
			result = "0" + result;
		}
		if (value < 0) {
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
				+exportChar(klant.getBedrijfsnaam(), 40) + exportChar(getAanhef(persoon), 6)
				+exportChar(persoon.getVoornaam(), 20) + exportChar(persoon.getTussenvoegsel(), 7)
				+exportChar(persoon.getAchternaam(), 40) + exportChar(klant.getFactuurAdres().getStraat(), 60)
				+exportChar(klant.getFactuurAdres().getHuisnummer(), 10)
				+exportChar(klant.getFactuurAdres().getPostcode(), 6)
				+exportChar(klant.getFactuurAdres().getPlaats(), 20) + exportChar(klant.getVAT(), 13)
				+exportChar(klant.getBankrekeningNummer(), 64) + exportChar(klant.getBiC(), 10)
				;
	}

	private String getAanhef(Persoon persoon) {
		if (persoon.getGeslacht() == Persoon.Geslacht.MAN) {
			return "Dhr.";
		} else {
			return "Mvr.";
		}
	}
	
	public String exportDate(String datumTijd) {
		String[] dateParts = datumTijd.split("-");
		return dateParts[2].split("T")[0] + dateParts[1] + dateParts[0].substring(2,4);
	}
	
	public String exportTijd(String datumTijd) {
		String factuurTijd = datumTijd.split("T")[1];
		return factuurTijd.substring(0,2) + factuurTijd.substring(3,5);
	}

	public String exportFactuurRegel(FactuurRegel factuurRegel, Factuur factuur) {
		return "R"
				+exportChar(factuurRegel.getProductNaam(), 60)
				+exportDouble(factuurRegel.getAantal(), 3, 2)
				+exportDouble(factuurRegel.getTotaalprijsExBTW() / factuurRegel.getAantal(), 5, 2)
				+exportBtwType(factuurRegel.getBtwCode()) 
				+exportDate(factuur.getDatumtijd())
				+exportTijd(factuur.getDatumtijd())
				+exportChar(convertUnit(factuurRegel.getUnit()), 6)
				;
	}
	

	public String exportTekstRegel(String productOmschrijving) {
		if(productOmschrijving.length() >= 60) {
			productOmschrijving = "T" + exportChar(productOmschrijving.substring(60, productOmschrijving.length()), 120);
		}else {
			productOmschrijving = "";
		}
		return productOmschrijving;
	}
	
	public String invoiceInformatieRegel(Factuur factuur) {
		String factuurNummer = Integer.toString(factuur.getFactuurNummer());
		String date = exportDate(factuur.getDatumtijd());
		return "F"
				+date
				+exportChar(factuurNummer, 10);
	}

	private String convertUnit(Unit unit) {
		if (unit == Unit.KILOGRAM) {
			return "kg";
		}
		return "";
	}

	private String exportBtwType(BTWcode btwCode) {
		switch(btwCode) {
			case LAAG:
				return "2";
			case HOOG:
				return "3";
			case GEEN:
			default:
				return "0";
		}
	}

	public String exportBedrijfsInformatie(BedrijfsInformatie bedrijfsInformatie) {
		return "B"
				+exportChar(bedrijfsInformatie.getBedrijfsnaam(), 60)
				+exportChar(bedrijfsInformatie.getStraat(), 60)
				+exportChar(bedrijfsInformatie.getHuisnummer(), 10)
				+exportChar(bedrijfsInformatie.getPostcode(), 6)
				+exportChar(bedrijfsInformatie.getPlaats(), 20)
				+exportChar(bedrijfsInformatie.getBtw(), 13)
				+exportChar(bedrijfsInformatie.getIban(), 64)
				+exportChar(bedrijfsInformatie.getBic(), 60)
				;
	}
}
