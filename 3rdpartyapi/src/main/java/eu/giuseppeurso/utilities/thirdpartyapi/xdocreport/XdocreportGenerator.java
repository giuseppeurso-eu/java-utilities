package eu.giuseppeurso.utilities.thirdpartyapi.xdocreport;

import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.odfdom.converter.pdf.PdfOptions;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

public class XdocreportGenerator {
	
	/**
	 * Merges the given data with a ODT template and converts the generated outputStream into a PDF document.
	 * @param templateOdtInputStream
	 * @param pdfOutputStream
	 * @param json
	 *
	 * https://github.com/opensagres/xdocreport/wiki/ODTReportingJavaMain
	 * https://groups.google.com/forum/#!topic/xdocreport/6F8nJcqLXas
	 * https://github.com/opensagres/xdocreport/issues/228
	 * https://github.com/opensagres/xdocreport/issues/4
	 * 
	 */
	public static void createPdf (InputStream templateOdtInputStream, OutputStream pdfOutputStream, CustomerPojo customer) throws Exception{
		try {
			System.out.println("Load ODT file and set the template engine to: " + TemplateEngineKind.Freemarker.name());
			IXDocReport xdocGenerator = XDocReportRegistry.getRegistry().loadReport(templateOdtInputStream,TemplateEngineKind.Freemarker);
			
            IContext context = xdocGenerator.createContext();
			
			System.out.println("Configuring the XDOCReport Context, put object: "+customer.getClass().getSimpleName());
            context.put("customer", customer);
            
            //System.out.println("Including the Fonts provider (prevents missing default xdocreport FontProvider).");
			//PdfOptions subOptions = PdfOptions.create();
			//subOptions.fontProvider(new FontsProvder());
			
			System.out.println("Set format converter: " + DocumentKind.ODT + " to " + ConverterTypeTo.PDF);
			//Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF).subOptions(subOptions);
			Options options = Options.getFrom(DocumentKind.ODT).to(ConverterTypeTo.PDF);

			System.out.println("Merge Java model with the ODT and convert it to PDF...");
			xdocGenerator.convert(context, options, pdfOutputStream);

			System.out.println("PDF conversion process has finished, closing the input/output streams...");
			templateOdtInputStream.close();
			pdfOutputStream.close();
		} catch (Exception e) {
			System.out.println("Error while trying to generate the final PDF from the ODT template. Exception: " + e);
			e.printStackTrace();
		}
	}
	

}
