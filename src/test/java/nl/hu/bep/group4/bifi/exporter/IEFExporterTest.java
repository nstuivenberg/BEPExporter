package nl.hu.bep.group4.bifi.exporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.hu.bep.group4.bifi.model.Adres;
import nl.hu.bep.group4.bifi.model.FactuurRegel;
import nl.hu.bep.group4.bifi.model.FactuurRegel.BTWcode;
import nl.hu.bep.group4.bifi.model.FactuurRegel.Unit;
import nl.hu.bep.group4.bifi.model.Persoon;
import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exporter.implementations.IEFExporterImpl;
import nl.hu.bep.group4.bifi.model.Klant;

import java.text.SimpleDateFormat;

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
	public void testExportKlant() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		Adres adres = new Adres("nepstraat", "666", "3582XN", "Hell", "1234");
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, adres);

		Persoon persoon = new Persoon(2, "Matthias", "Judas", "tussen", "0609090906", "nee", Persoon.Geslacht.MAN);
		assertEquals("KTestbedrijf                             Dhr.  Matthias            tussen Judas                                   nepstraat                                                   666       3582XNHell                testVat      testRekening                                                    testBic   ", exporter.exportKlant(klant, persoon));
	}
	
	@Test
	public void testExportFactuurRegel() {
		IEFExporterImpl exporter = new IEFExporterImpl();
		FactuurRegel factuurRegel = new FactuurRegel(5,"Bifi betstelauto van worst", 60, 3200, BTWcode.LAAG, Unit.KILOGRAM);
		
		assertEquals("RBifi betstelauto van worst.                                 06000032002" + new SimpleDateFormat("ddMMyyHHmm") + "kg", exporter.exportFactuurRegel(factuurRegel));
	}
}
