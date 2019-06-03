package nl.hu.bep.group4.bifi.exporter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exporter.implementations.IEFExporterImpl;
import nl.hu.bep.group4.bifi.model.Klant;

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
		Klant klant = new Klant(5, "Testbedrijf", "bv", "testVat", "testRekening", "testGiroNummer", "testBic", null, null, null);
		//Sjard, schrijf deze testdata
		assertEquals("KTestbedrijf                             ", exporter.exportKlant(klant));
	}
}
