package ie.dit.britton.darren.shoppinglist;

import java.io.File;

import java.io.OutputStreamWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


@SuppressLint("WorldReadableFiles")
public class CheckoutView extends ActionBarActivity 
{

	Basket basket;
	Button checkout;
	Button modifyCart;
	TableLayout purchasesTable;
	TableRow purchase;
	NumberFormat currency;
	Toast toast;
	String mailBody;
	
    @SuppressWarnings("deprecation")
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        
        modifyCart =(Button)findViewById(R.id.modifyCart);
        modifyCart.setOnClickListener(
	        new OnClickListener()
	        {
	            public void onClick(View view)
	            {
	        		finish();
	            }
	        });
    
        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy|HH:mm:ss", Locale.UK);
                
        checkout =(Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(
	        new OnClickListener()
	        {
	            public void onClick(View view)
	            {
	        		
        		   class SendEmail extends AsyncTask<Void, Void, Void>
        		   {
						@Override
	    		        protected Void doInBackground(Void... params) {
	    		        		
				        	OutputStreamWriter outputStream;
				        	String currTime = dateFormat.format(calendar.getTime());
				        	String fileName = "shopping-list-for-" + currTime + ".html";

							try
							{
							  outputStream = new OutputStreamWriter(openFileOutput(fileName, Context.MODE_WORLD_READABLE));
							  outputStream.write(mailBody);
							  outputStream.close();
							} 
							catch (Exception e) 
							{
							  e.printStackTrace();
							}
							
							String to = User.getInstance().getEmail();
							String subject = "Personalised Shopping List Created on " + currTime;
							
							Intent email = new Intent(Intent.ACTION_SEND);
							email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
							email.putExtra(Intent.EXTRA_SUBJECT, subject);
							email.putExtra(Intent.EXTRA_TEXT, "Please open the attached file for your shopping list");
							File file;
							try
							{
								file = new File(getBaseContext().getFilesDir() + "/" + fileName);
							}
							catch(Exception e)
							{
								e.printStackTrace();
								MakeToast("File not Found");
								return null;
							}
							  
							Uri uri = Uri.fromFile(file);
							email.putExtra(Intent.EXTRA_STREAM, uri);
							
							email.setType("message/rfc822");
							 
							startActivity(Intent.createChooser(email, "Choose an Email client :"));
							
							return null;
	    		        }
        		   }
        		  
        		   new SendEmail().execute();
        		   MakeToast("Transaction Completed");
	                	        	
	            }
	        });
        
        currency = NumberFormat.getCurrencyInstance(Locale.ITALY);
        
        
        mailBody = getResources().getString(R.string.htmlStart);
        
        basket = Basket.getInstance();
        purchasesTable = (TableLayout) findViewById(R.id.purchases);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        OrderLine[] purchases = basket.getPurchases();
        
        for(OrderLine p : purchases)
        {
        	if(p.getQuantity() > 0)
        	{
        		mailBody += "<tr>";
            	purchase = new TableRow(this);
            	purchase.setLayoutParams(layoutParams);
            	TextView name = new TextView(this);
            	TextView quantity = new TextView(this);
            	TextView VAT = new TextView(this);
            	TextView price = new TextView(this);
            	TextView total = new TextView(this);
            	name.setText(p.getName());
            	mailBody += "<td>" + p.getName() + "</td>";
            	quantity.setText(String.valueOf(p.getQuantity()));
            	mailBody += "<td>" + String.valueOf(p.getQuantity()) + "</td>";
            	VAT.setText(String.valueOf(currency.format(p.getVat())));
            	mailBody += "<td>" + String.valueOf(currency.format(p.getVat())) + "</td>";
            	price.setText(String.valueOf(currency.format(p.getPrice())));
            	mailBody += "<td>" + String.valueOf(currency.format(p.getPrice())) + "</td>";
            	total.setText(String.valueOf(currency.format(p.getTotal())));
            	mailBody += "<td>" + String.valueOf(currency.format(p.getTotal())) + "</td>";
            	mailBody += "</tr>";
            	name.setLayoutParams(layoutParams);
            	quantity.setLayoutParams(layoutParams);
            	VAT.setLayoutParams(layoutParams);
            	price.setLayoutParams(layoutParams);
            	total.setLayoutParams(layoutParams);
            	purchase.addView(name);
            	purchase.addView(quantity);
            	purchase.addView(VAT);
            	purchase.addView(price);
            	purchase.addView(total);
            	//tr.setBackgroundResource(R.drawable.sf_gradient_03);
            	purchasesTable.addView(purchase);
        	}
        }
        mailBody += "<tfoot> <td></td><td></td><td></td><td></td>";
      	purchase = new TableRow(this);
    	purchase.setLayoutParams(layoutParams);
    	TextView name = new TextView(this);
    	TextView quantity = new TextView(this);
    	TextView VAT = new TextView(this);
    	TextView price = new TextView(this);
    	TextView total = new TextView(this);
    	total.setText(String.valueOf(currency.format(basket.getTotal())));
    	mailBody += "<td><b>" + String.valueOf(currency.format(basket.getTotal())) + "</td></b>";
    	mailBody += getResources().getString(R.string.htmlEnd);
    	layoutParams.setMargins(0, 5, 0, 0);
    	name.setLayoutParams(layoutParams);
    	quantity.setLayoutParams(layoutParams);
    	VAT.setLayoutParams(layoutParams);
    	price.setLayoutParams(layoutParams);
    	total.setLayoutParams(layoutParams);
    	purchase.addView(name);
    	purchase.addView(quantity);
    	purchase.addView(VAT);
    	purchase.addView(price);
    	purchase.addView(total);
    	//tr.setBackgroundResource(R.drawable.sf_gradient_03);
    	purchasesTable.addView(purchase);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_detail_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	  switch (item.getItemId()) {
    	    case R.id.action_settings:
    	        // Settings option clicked.
    	        return true;
    	    default:
    	        return super.onOptionsItemSelected(item);
    	    }
    }
    
    public void MakeToast(String message)
    {
    	if (toast != null)
    	{
    		toast.cancel();
    	}
    	
    	toast =Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    	toast.show();
    }
}
