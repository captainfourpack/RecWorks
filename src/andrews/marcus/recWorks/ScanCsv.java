package andrews.marcus.recWorks;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
 
/**
 * @author Marcus Andrews (marcus.k.s.andrews@gmail.com)
 *
 * ScanCsv - A class for scanning in csv files and reading the CreditCard info contained therein.  Note that error handling is minimal to say the least
 */
public class ScanCsv
{
	public ArrayList<CreditCard> cards = new ArrayList<CreditCard>();
	String delimiter = ",";
	String fileToParse = "C://temp/mid-test.csv";   // default location. TODO file chooser interface
        
	public ScanCsv()
	{}
	
	public ScanCsv(String fileToParse)
	{
		this.fileToParse = fileToParse;		
	}
    
	public ScanCsv(String fileToParse, String delimiter)
	{
		this.delimiter = delimiter;	
		this.fileToParse = fileToParse;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException
    {
    	ScanCsv reader = new ScanCsv();
    	if(args.length>0)
    	{
    		reader.setFileToParse(args[0]);    		
    	}
    	reader.scanForCreditCards();
    }
    
    
    /**
     * go - makes the program do what it says
     * 
     */
    private void scanForCreditCards()
    {
    	readCSV(fileToParse, delimiter);
        sortCards();
        outputDeets();
    }
    
    
    /**
     * sortCards - Sort the cards by their Date field  
     */
    public void sortCards()
    {
    	Collections.sort(cards);
    }
    
    
    /**
     * outputDeets - Loops through all CreditCards stored and outputs the Issuer, card number and masked card number
     */
    private void outputDeets()
    {
    	Iterator<CreditCard> iterator = cards.iterator();
		while (iterator.hasNext()) 
		{
			CreditCard card = iterator.next();
			System.out.println(card.getIssuer() + "|" + card.getNumber() + "|" + card.getMaskedNumber());    			
		}
    }
    
    
    /**
     * readCSV - Parse a CSV file into memory, creating CreditCard Objects for each enter read
     * 
     * @param fileToParse - The filepath
     * @param delimiter - The delimiter symbol used in the file
     */
    public void readCSV(String fileToParse, String delimiter)
    {
    	BufferedReader fileReader = null;
        
    	try
        {
            String line = "";
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            while ((line = fileReader.readLine()) != null)
            {
                String[] tokens = line.split(delimiter);
                processCreditCardTokens(tokens);				// we are reading a credit card CSV, so call the appropriate (and only) set of processing instructions.  Args can be added to the method sig later to decide hot to process from among alternative data other than credit cards.
            }
        }
        catch (Exception e) 
    	{
            e.printStackTrace();
        }
        finally
        {
            try 
            {
                fileReader.close();
            } catch (IOException e) 
            {
                e.printStackTrace();
            }
        }    	
    }
    
    /**
     * processCreditCardTokens - An encapsulated method to create CreditCard objects from the parsed tokens of one line of the csv file.  Stripped from the readCSV() method to 
     * 		keep it generic to read other files in future. 
     * 
     * @param tokens - The line of a csv file, tokenised
     */
    private void processCreditCardTokens(String[] tokens) 
    {
	    	
	    if(tokens.length ==3)				// check the line has the right number of data cells, else just skip it (TODO: warning message/log)
	    {
	    	CreditCard card = new CreditCard(tokens[0], tokens[1], tokens[2]);
	    	cards.add(card);
	    }
    }
    
    
    //Accessors
    public ArrayList<CreditCard> getCards() {
		return cards;
	}

    public String getDelimiter() {
		return delimiter;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}


	public String getFileToParse() {
		return fileToParse;
	}


	public void setFileToParse(String fileToParse) {
		this.fileToParse = fileToParse;
	}
}

