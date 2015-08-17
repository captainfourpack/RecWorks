package andrews.marcus.recWorks.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import andrews.marcus.recWorks.ScanCsv;
import junit.framework.TestCase;


public class ScanCsvTest extends TestCase 
{
	String testFilePath = "C://temp/mid-test.csv";
	String tokenErrorFilePath = "C://temp/mid-test-tokenError.csv";
	String delimiterFilePath = "C://temp/mid-test-delimiter.csv";

	@Before
	protected void setUp() throws Exception {
		super.setUp();
	}

	@After
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testReadCSVStandard() 
	{
		ScanCsv scanner = new ScanCsv();
		scanner.readCSV(testFilePath, ",");
		assertEquals(scanner.getCards().size(), 3);			//all three cards should be read in
		assertEquals(scanner.getCards().get(0).getIssuer(), "HSBC Canada");		//test a selection of the fields
		assertEquals(scanner.getCards().get(1).getNumber(), "4519-4532-4524-2456");
		assertEquals(scanner.getCards().get(2).getMaskedNumber(), "XXXX-XXXX-XXXX-345");
	}
	
	@Test
	public void testReadCSVWrongTokens() 
	{
		ScanCsv scanner = new ScanCsv();
		scanner.readCSV(tokenErrorFilePath, ",");
		assertEquals(scanner.getCards().size(), 2);			// one of the lines in the test file has only two tokens so should be rejected and only the other two read in
	}
	
	@Test
	public void testReadCSVDelimeter() 
	{
		ScanCsv scanner = new ScanCsv();
		scanner.readCSV(delimiterFilePath, "	");						//The file is tab-delimited
		assertEquals(scanner.getCards().size(), 3);						//All three cards should have been read in
		assertEquals(scanner.getCards().get(0).getIssuer(), "HSBC Canada");		//test a selection of the fields
		assertEquals(scanner.getCards().get(1).getNumber(), "4519-4532-4524-2456");
		assertEquals(scanner.getCards().get(2).getMaskedNumber(), "XXXX-XXXX-XXXX-345");
	}
	
	
	@Test 
	public void testSort()
	{
		ScanCsv scanner = new ScanCsv();
		scanner.readCSV(testFilePath, ",");
		scanner.sortCards();
		assertEquals(scanner.getCards().get(0).getIssuer(), "American Express");
		assertEquals(scanner.getCards().get(1).getIssuer(), "HSBC Canada");
		assertEquals(scanner.getCards().get(2).getIssuer(), "Royal Bank of  Canada");
	}	
}
