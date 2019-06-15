package nl.hu.bep.group4.bifi.exporter;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import nl.hu.bep.group4.bifi.exporter.factories.IEFExporterFactory;

public class IEFExporterFactoryTest {
	@Test
	public void testFactory() {
		assertNotNull(IEFExporterFactory.createIEFExporter());
	}
}
