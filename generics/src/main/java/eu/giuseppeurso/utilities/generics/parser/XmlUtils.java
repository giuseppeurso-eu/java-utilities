package eu.giuseppeurso.utilities.generics.parser;

import org.mozilla.universalchardet.UniversalDetector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * SAX Xml Parser
 * @author Giuseppe Urso - www.giuseppeurso.eu
 *
 */
public class XmlUtils {
	
    public static void main(String[] args) {
        SAXParserFactory spf = SAXParserFactory.newInstance();
        SchoolsHandler handler = new SchoolsHandler();
        String filePath = "src/test/resources/parser/test-01.xml";
        try {
            SAXParser sp = spf.newSAXParser();
            sp.parse(filePath, handler);
            System.out.println("Number of read schools: " + handler.getSchools().size());
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }
    
    /**
     * 
     */
    public static void stripXmlBOM(String xmlToCheck, String encodingToCheck) {
		String[] encodings = { "UTF-8", "UTF-16", "ISO-8859-1" };
		for (String actual : encodings) {
			System.out.println("Checking source with encoding '"+actual+"'...");
//			for (String declared : encodings) {
				if (actual != encodingToCheck) {
//					String xml = "<?xml version='1.0' encoding='" + declaredEncoding + "'?><x/>";
					byte[] encoded = xmlToCheck.getBytes(Charset.forName(actual));
					try {
						InputStream stream = new ByteArrayInputStream(encoded);
						SAXParserFactory.newInstance().newSAXParser().parse(stream, new DefaultHandler());
						System.out.println("BOM HIDDEN ERROR! Actual encoding is '" + actual + "' Desired encoding is '"+encodingToCheck+"'. Source xml is: " + xmlToCheck);
					} catch (Exception e) {
						System.out.println(e.getMessage() + " Error detected with encoding '" + actual + "'. Source xml: " + xmlToCheck);
					}
				}else {
					System.out.println("Source encoding OK '"+encodingToCheck+"'");
				}
				
//			}
		}

	}
    
    
    public static void detectEncoding(File file) {
    	byte[] buf = new byte[4096];
		try {
			FileInputStream fis = new FileInputStream(file);
			UniversalDetector detector = new UniversalDetector(null);
	    	
	        int nread;
	        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) detector.handleData(buf, 0, nread);
	        fis.close();
	        
	        detector.dataEnd();
	        String encoding = detector.getDetectedCharset();
	        if (encoding!=null) {
	        	System.out.println("Encoding detected: "+encoding);
	        }else {
				System.out.println("Unable to detect Encoding.");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public static InputStream checkForUtf8BOMAndDiscardIfAny(InputStream inputStream) throws IOException {
        PushbackInputStream pushbackInputStream = new PushbackInputStream(new BufferedInputStream(inputStream), 3);
        byte[] bom = new byte[3];
        if (pushbackInputStream.read(bom) != -1) {
            if (!(bom[0] == (byte) 0xEF && bom[1] == (byte) 0xBB && bom[2] == (byte) 0xBF)) {
                pushbackInputStream.unread(bom);
            }
        }
        return pushbackInputStream;
    }
     
    
}

class SchoolsHandler extends DefaultHandler {
    private static final String TAG_SCHOOLS = "Schools";
    private static final String TAG_SCHOOL = "School";
    private static final String TAG_STUDENT = "Student";
    private static final String TAG_ID = "ID";
    private static final String TAG_NAME = "Name";

    private final Stack<String> tagsStack = new Stack<String>();
    private final StringBuilder tempVal = new StringBuilder();

    private List<School> schools;
    private School school;
    private Student student;

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
    	System.out.println("START - Current tag: "+qName);
        pushTag(qName);
        tempVal.setLength(0);
        if (TAG_SCHOOLS.equalsIgnoreCase(qName)) {
            schools = new ArrayList<School>();
        } else if (TAG_SCHOOL.equalsIgnoreCase(qName)) {
            school = new School();
        } else if (TAG_STUDENT.equalsIgnoreCase(qName)) {
            student = new Student();
        }
    }

    public void characters(char ch[], int start, int length) {
        tempVal.append(ch, start, length);
    }

    public void endElement(String uri, String localName, String qName) {
        String tag = peekTag();
        System.out.println("END - Current tag: "+tag);
        if (!qName.equals(tag)) {
            throw new InternalError();
        }

        popTag();
        String parentTag = peekTag();
        System.out.println("END - Current parent: "+parentTag);
        
        if (TAG_ID.equalsIgnoreCase(tag)) {
            int id = Integer.valueOf(tempVal.toString().trim());
            if (TAG_STUDENT.equalsIgnoreCase(parentTag)) {
                student.setId(id);
            } else if (TAG_SCHOOL.equalsIgnoreCase(parentTag)) {
                school.setId(id);
            }
        } else if (TAG_NAME.equalsIgnoreCase(tag)) {
            String name = tempVal.toString().trim();
            if (TAG_STUDENT.equalsIgnoreCase(parentTag)) {
                student.setName(name);
            } else if (TAG_SCHOOL.equalsIgnoreCase(parentTag)) {
                school.setName(name);
            }
        } else if (TAG_STUDENT.equalsIgnoreCase(tag)) {
            school.addStudent(student);
        } else if (TAG_SCHOOL.equalsIgnoreCase(tag)) {
            schools.add(school);
        }
    }

    public void startDocument() {
        pushTag("");
    }

    public List<School> getSchools() {
        return schools;
    }

    private void pushTag(String tag) {
        tagsStack.push(tag);
    }

    private String popTag() {
        return tagsStack.pop();
    }

    private String peekTag() {
        return tagsStack.peek();
    }
}

class School {
    private int id;
    private String name;
    private List<Student> students = new ArrayList<Student>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public List<Student> getStudents() {
        return students;
    }
}

class Student {
    private int id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}