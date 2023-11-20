package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings({"rawtypes","unchecked"})
public class ReportUtil implements Serializable {
	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
				
		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrBeanCollectionDataSource);
		
		return JasperExportManager.exportReportToPdf(jasperPrint);
		
	}
	
//public byte[] geraRelatorioXLS(List listaDados, String nomeRelatorio, HashMap<String, Object> params, ServletContext servletContext) throws Exception {
	public byte[] geraRelatorioXLS(List listaDados, String nomeRelatorio, Map params, ServletContext servletContext) throws Exception {
		
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(listaDados);
		
		String caminhoJasper = servletContext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
				
		JasperPrint jasperPrint = JasperFillManager.fillReport(caminhoJasper, params, jrBeanCollectionDataSource);

		JRExporter jrExporter = new JRXlsExporter();
		
		jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		
		jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
		
		jrExporter.exportReport();
		
		return byteArrayOutputStream.toByteArray();
		
	}
	
}
