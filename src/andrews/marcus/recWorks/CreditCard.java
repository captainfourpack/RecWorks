package andrews.marcus.recWorks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Marcus Andrews (marcus.k.s.andrews@gmail.com)
 *
 * CreditCard - A class capturing the important maethods and data in storing credit card details
 */
/**
 * @author Marcus
 *
 */
public class CreditCard implements Comparable<CreditCard>
{
	private String issuer;
	private String number;
	private String expiry;
	private Integer maskLength;
	private Boolean maskFromLeft;
	private Date expiryDate;
	
	

	public CreditCard(String issuer, String number, String expiry)
	{
		this.issuer = issuer;
		this.number = number;
		this.expiry = expiry;
		
		maskLength = CreditCard.getMaskLength(issuer);
		maskFromLeft = CreditCard.isMaskFromLeft(issuer);
		parseDate();
	}
	
	
	/**
	 * getMaskLength - A static method to lookup the mask length for different card issuers
	 * 
	 * @param issuer - The issuer of the card
	 * @return - The length of the mask that should be applied to cards from this issuer
	 */
	public static Integer getMaskLength(String issuer)
	{
		switch(issuer)
		{
			case "HSBC Canada": return 2;
			case "Royal Bank of  Canada": return 4;
			case "American Express": return 3;
			default: return 0;  // mask the whole thing for most security if we dont recognise it		
		}	
	}
	
	
	
	/**
	 * isMaskFromLeft - A static method to lookup the direction the mask should be applied from
	 * 
	 * @param issuer - The issuer of the card
	 * @return - True if the mask should be applied as visible from the left, False otherwise
	 */
	public static boolean isMaskFromLeft(String issuer)
	{
		switch(issuer)
		{
			case "HSBC Canada": return true;
			case "Royal Bank of  Canada": return true;
			case "American Express": return false;
			default: return true;  		
		}	
	}
	
	
	/**
	 * parseDate - Transforms the given expiry field into a Date Object
	 * 
	 */
	private void parseDate()
	{
		String dateString = getExpiry();
		
		//I would really use Joda-Time libraries here
		SimpleDateFormat formatter = new SimpleDateFormat("MMM-yyyy");
		try 
		{
			expiryDate = formatter.parse(dateString);

		} catch (ParseException e) 
		{
			e.printStackTrace();
		} 		
	}
	
	@Override
	public int compareTo(CreditCard o) 
	{
	    return o.getExpiryDate().compareTo(getExpiryDate());
	}
	
	//Accessors
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public String getMaskedNumber()
	{
		 StringBuilder sb = new StringBuilder(number);
		 
		 if(maskFromLeft)
		 {
			 for(int i=maskLength; i<number.length(); i++)
			 {
				 if((i+1)%5!=0)	sb.setCharAt(i, 'X');	//mod 5 on the count to not replace the "-" 				 
			 }
		 }
		 else
		 {
			 for(int i=number.length()-maskLength-1; i>=0; i--)
			 {
				 if((i+1)%5!=0) sb.setCharAt(i, 'X');	//mod 5 on the count to not replace the "-" 			 
			 }		 
		 }
		 return sb.toString();
	}
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getExpiry() {
		return expiry;
	}

	public void setExpiry(String expiry) {
		this.expiry = expiry;
	}
}
