package nl.hu.bep.group4.bifi.exporter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.Factuur;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.FactuurRegel.Unit;
import nl.hu.bep.group4.bifi.model.Persoon;
import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exporter.implementations.IEFExporterImpl;
import nl.hu.bep.group4.bifi.interfaces.IEFExporter;
import nl.hu.bep.group4.bifi.model.Klant;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class IEFExporterTest {
	@Test
	public void testExportChar() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("Hoi  ", exporter.exportChar("Hoi", 5));
	}
	@Test
	public void testExportChar2() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("test ", exporter.exportChar("test", 5));
	}
	@Test
	public void testExportChar3() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("test  ", exporter.exportChar("test", 6));
	}
	@Test
	public void testExportCharTooLong() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("Hoi D", exporter.exportChar("Hoi Daar", 5));
	}
	
	@Test
	public void testExportCharTooLong2() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("Hoi Da", exporter.exportChar("Hoi Da", 6));
	}
	
	@Test
	public void testExportDouble() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("0001004", exporter.exportDouble(10.04, 5, 2));
	}
	
	@Test
	public void testExportDouble2() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("0004356", exporter.exportDouble(43.56, 5, 2));
	}
	
	@Test
	public void testExportDouble3() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("00043560", exporter.exportDouble(43.56, 5, 3));
	}
	
	@Test
	public void testExportDouble4() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("3560", exporter.exportDouble(43.56, 1, 3));
	}
	
	@Test
	public void testExportNegativeDouble() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("   !  $", exporter.exportDouble(-10.04, 5, 2));
	}
	
	@Test
	public void testExportNegativeDouble2() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("\"#%&\\()", exporter.exportDouble(-23567.89, 5, 2));
	}
	
	@Test
	public void testExportDate() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("031214", exporter.exportDate("2014-12-03T10:15:30.00Z"));
	}
	
	@Test
	public void testExportTijd() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("1015", exporter.exportTijd("2014-12-03T10:15:30.00Z"));
	}
	
	@Test
	public void testExportKlantMan() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);

		Persoon persoon = new Persoon(2, "Matthias", "tussen", "Judas", "0609090906", "nee", Persoon.Geslacht.MAN);
		assertEquals("KTestbedrijf                             Dhr.  Matthias            tussen Judas                                   nepstraat                                                   666       3582XNHell                testVat      testRekening                                                    testBic   ", exporter.exportKlant(klant, persoon));
	}
	
	@Test
	public void testExportKlantVrouw() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);

		Persoon persoon = new Persoon(2, "Joanna", "tussen", "Judas", "0609090906", "nee", Persoon.Geslacht.VROUW);
		assertEquals("KTestbedrijf                             Mvr.  Joanna              tussen Judas                                   nepstraat                                                   666       3582XNHell                testVat      testRekening                                                    testBic   ", exporter.exportKlant(klant, persoon));
	}
	
	@Test
	public void testExportFactuurRegel() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi auto van worst", 30, 96000, BTWcode.LAAG, Unit.KILOGRAM);
		Factuur factuur = new Factuur(null, "2018-08-11T10:15:30.00Z", 0, null, null, null); 

		assertEquals("RBifi auto van worst                                         03000032000021108181015kg    ", exporter.exportFactuurRegel(factuurRegel, factuur));
	}
	
	@Test
	public void testExportFactuurRegelBtwHoog() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi auto van worst", 30, 96000, BTWcode.HOOG, Unit.KILOGRAM);
		Factuur factuur = new Factuur(null, "2018-08-11T10:15:30.00Z", 0, null, null, null); 

		assertEquals("RBifi auto van worst                                         03000032000031108181015kg    ", exporter.exportFactuurRegel(factuurRegel, factuur));
	}
	
	@Test
	public void testExportFactuurRegelBtwGeenNoUnit() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi auto van worst", 30, 96000, BTWcode.GEEN, null);
		Factuur factuur = new Factuur(null, "2018-08-11T10:15:30.00Z", 0, null, null, null); 

		assertEquals("RBifi auto van worst                                         03000032000001108181015      ", exporter.exportFactuurRegel(factuurRegel, factuur));
	}
	
	@Test
	public void testExportTekstRegel() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		String productOmschrijving = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras sit amet vulputate quam, eu viverra nisi. Quisque posuere eu urna vel posuere cras amet.";
		
		assertEquals("Ts sit amet vulputate quam, eu viverra nisi. Quisque posuere eu urna vel posuere cras amet.                              ", exporter.exportTekstRegel(productOmschrijving));
	}
	
	@Test
	public void testExportTekstRegelKleinerDanZestig() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		assertEquals("", exporter.exportTekstRegel("Dit is een omschrijving"));
	}

	@Test
	public void testInvoiceInformatieRegel() {
		IEFExporterImpl exporter = new IEFExporterImpl();

		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);
		Persoon persoon = new Persoon(2, "Matthias", "Judas", "tussen", "0609090906", "nee", Persoon.Geslacht.MAN);

		FactuurRegel factuurregel = new FactuurRegel();

		List<FactuurRegel> factuurregels = new ArrayList<>();
		factuurregels.add(factuurregel);


		Factuur factuur = new Factuur(klant, "2014-12-03T10:15:30.00Z", 1, factuurregels, "Opmerking", persoon);
		assertEquals("F0312141         ", exporter.invoiceInformatieRegel(factuur));
	}
	
	@Test
	public void testExportFacturen() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		List<Factuur> facturen = new ArrayList<>();
		
		List<FactuurRegel> factuurregels = new ArrayList<>();
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi bestelauto van worst", 30, 96000, BTWcode.LAAG, Unit.KILOGRAM);
		Persoon persoon = new Persoon(2, "Matthias", "tussen", "Judas", "0609090906", "nee", Persoon.Geslacht.MAN);
		factuurregels.add(factuurRegel);
		Factuur factuur = new Factuur(klant, "2014-12-03T10:15:30.00Z", 1, factuurregels, "String", persoon);
		facturen.add(factuur);
		
		exporter.exportFacturen(facturen);
		
		assertEquals("BFactory Ansbach                                             Eyber Strasse                                               81        9152BIAnsbach             DE81549090312DE23DEU000198761111                                             1234567890\nKTestbedrijf                             Dhr.  Matthias            tussen Judas                                   nepstraat                                                   666       3582XNHell                testVat      testRekening                                                    testBic   \nF0312141         \nRBifi bestelauto van worst                                   03000032000020312141015kg    \n", exporter.exportFacturen(facturen));

	}
	
	@Test
	public void testExportTweeFacturen() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		List<Factuur> facturen = new ArrayList<>();
		List<FactuurRegel> factuurregels = new ArrayList<>();
		
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi bestelauto van worst", 30, 96000, BTWcode.LAAG, Unit.KILOGRAM);
		FactuurRegel factuurRegel2 = new FactuurRegel(6,"Bifi worst", 300, 15011, BTWcode.LAAG, Unit.KILOGRAM);
		factuurregels.add(factuurRegel);
		factuurregels.add(factuurRegel2);
		Persoon persoon = new Persoon(2, "Matthias", "tussen", "Judas", "0609090906", "nee", Persoon.Geslacht.MAN);
		Factuur factuur = new Factuur(klant, "2014-12-03T10:15:30.00Z", 1, factuurregels, "String", persoon);
		facturen.add(factuur);
		
		exporter.exportFacturen(facturen);
		
		assertEquals("BFactory Ansbach                                             Eyber Strasse                                               81        9152BIAnsbach             DE81549090312DE23DEU000198761111                                             1234567890\n" + 
				"KTestbedrijf                             Dhr.  Matthias            tussen Judas                                   nepstraat                                                   666       3582XNHell                testVat      testRekening                                                    testBic   \n" + 
				"F0312141         \n" + 
				"RBifi bestelauto van worst                                   03000032000020312141015kg    \n" + 
				"RBifi worst                                                  30000000500420312141015kg    \n", exporter.exportFacturen(facturen));
	}
	
	@Test
	public void testFileBestaatNaFileExport() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		List<Factuur> facturen = new ArrayList<>();
		List<FactuurRegel> factuurregels = new ArrayList<>();
		
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi bestelauto van worst", 30, 96000, BTWcode.LAAG, Unit.KILOGRAM);
		Persoon persoon = new Persoon(2, "Matthias", "Judas", "tussen", "0609090906", "nee", Persoon.Geslacht.MAN);
		factuurregels.add(factuurRegel);
		Factuur factuur = new Factuur(klant, "2014-12-03T10:15:30.00Z", 1, factuurregels, "String", persoon);
		facturen.add(factuur);
		exporter.exportFacturen(facturen);
		String property = System.getProperty("user.home");
		File file = new File(property + "/createdFile.txt");
		
		assertTrue(file.exists());
		
		file.delete();
	}
	
	@Test
	public void testExportBedrijfsInformatie() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		BedrijfsInformatie bedrijfsInformatie = new BedrijfsInformatie();
		bedrijfsInformatie.setBedrijfsnaam("Testnaam");
		bedrijfsInformatie.setStraat("teststraat");
		bedrijfsInformatie.setHuisnummer("123");
		bedrijfsInformatie.setPostcode("1234AB");
		bedrijfsInformatie.setPlaats("testplaats");
		bedrijfsInformatie.setBtw("BTW");
		bedrijfsInformatie.setIban("INGB03475");
		bedrijfsInformatie.setBic("BIC");
		assertEquals("BTestnaam                                                    teststraat                                                  123       1234ABtestplaats          BTW          INGB03475                                                       BIC                                                         ", exporter.exportBedrijfsInformatie(bedrijfsInformatie));
	}
}
