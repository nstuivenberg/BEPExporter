package nl.hu.bep.group4.bifi.exporter.factories;

import nl.hu.bep.group4.bifi.exporter.implementations.IEFExporterImpl;
import nl.hu.bep.group4.bifi.interfaces.IEFExporter;

public class IEFExporterFactory {

	public static IEFExporter createFactuurLader() {
		return new IEFExporterImpl();
	}
	private IEFExporterFactory() {}
}
