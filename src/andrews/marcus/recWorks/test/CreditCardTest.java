package andrews.marcus.recWorks.test;

import static org.junit.Assert.*;

import org.junit.Test;

import andrews.marcus.recWorks.CreditCard;

public class CreditCardTest {

	@Test
	public void testFieldConstruction() {
		CreditCard card = new CreditCard("Bank of Marcus", "1234-5678-9012-3456", "Oct-2019");
		
		assertEquals(card.getExpiry(), "Oct-2019");
		assertEquals(card.getIssuer(), "Bank of Marcus");
		assertEquals(card.getNumber(), "1234-5678-9012-3456");
	}
	
	@Test
	public void testComparison()
	{
		CreditCard card = new CreditCard("Bank of Marcus", "1234-5678-9012-3456", "Oct-2019");
		CreditCard card2 = new CreditCard("HSBC Canada", "5601-2345-3446-5678", "Nov-2017");
		
		assertEquals(card.compareTo(card2), -1);		//card2 has an earlier expiry date
	}
	
	@Test
	public void testMaskingLength()
	{
		CreditCard card = new CreditCard("Bank of Marcus", "1234-5678-9012-3456", "Oct-2019");
		CreditCard card2 = new CreditCard("HSBC Canada", "5601-2345-3446-5678", "Nov-2017");
		
		assertEquals(CreditCard.getMaskLength(card.getIssuer()), Integer.valueOf(0));  //should have full mask as this is unknown card issuer
		assertEquals(CreditCard.getMaskLength(card2.getIssuer()), Integer.valueOf(2));	//should have value of 2 for HSBC Canada
	}
	
	@Test
	public void testMaskingDirection()
	{
		CreditCard card = new CreditCard("Bank of Marcus", "1234-5678-9012-3456", "Oct-2019");
		CreditCard card2 = new CreditCard("American Express", "5601-2345-3446-567", "Nov-2017");
		CreditCard card3 = new CreditCard("HSBC Canada", "5601-2345-3446-5678", "Nov-2017");
		
		assertEquals(CreditCard.isMaskFromLeft(card.getIssuer()), true);					// should fall into default value of from the left
		assertEquals(CreditCard.isMaskFromLeft(card2.getIssuer()), false);
		assertEquals(CreditCard.isMaskFromLeft(card3.getIssuer()), true);
	}
	
	@Test
	public void testMask()
	{
		CreditCard card = new CreditCard("Bank of Marcus", "1234-5678-9012-3456", "Oct-2019");	//not in the switch statement, so should use default values 
		CreditCard card2 = new CreditCard("American Express", "5601-2345-3446-567", "Nov-2017");
		CreditCard card3 = new CreditCard("HSBC Canada", "5601-2345-3446-5678", "Nov-2017");
		
		assertEquals(card.getMaskedNumber(), "XXXX-XXXX-XXXX-XXXX");							// default is all masked
		assertEquals(card2.getMaskedNumber(), "XXXX-XXXX-XXXX-567");
		assertEquals(card3.getMaskedNumber(), "56XX-XXXX-XXXX-XXXX");
	}
}
