package ie.dit.britton.darren.shoppinglist;

import java.text.NumberFormat;

import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TableRow.LayoutParams;


public class CheckoutView extends ActionBarActivity 
{

	Basket basket;
	Button checkout;
	Button modifyCart;
	TableLayout purchasesTable;
	TableRow purchase;
	NumberFormat currency;
	Toast toast;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_view);
        
        modifyCart =(Button)findViewById(R.id.modifyCart);
        modifyCart.setOnClickListener(
	        new OnClickListener()
	        {
	        	@Override
	            public void onClick(View view)
	            {
	        		finish();
	            }
	        });
        
        checkout =(Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(
	        new OnClickListener()
	        {
	        	@Override
	            public void onClick(View view)
	            {
	        		MakeToast("Transaction Completed\nExiting in 3");
	        		SystemClock.sleep(1000);
	        		MakeToast("Transaction Completed\nExiting in 2");
	        		SystemClock.sleep(1000);
	        		MakeToast("Transaction Completed\nExiting in 1");
	        		SystemClock.sleep(1000);
	            }
	        });
        
        currency = NumberFormat.getCurrencyInstance(Locale.ITALY);
        
        basket = Basket.getInstance();
        purchasesTable = (TableLayout) findViewById(R.id.purchases);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        
        OrderLine[] purchases = basket.getPurchases();
        
        for(OrderLine p : purchases)
        {
        	if(p.getQuantity() > 0)
        	{
            	purchase = new TableRow(this);
            	purchase.setLayoutParams(layoutParams);
            	TextView name = new TextView(this);
            	TextView quantity = new TextView(this);
            	TextView VAT = new TextView(this);
            	TextView price = new TextView(this);
            	TextView total = new TextView(this);
            	name.setText(p.getName());
            	quantity.setText(String.valueOf(p.getQuantity()));
            	VAT.setText(String.valueOf(currency.format(p.getVat())));
            	price.setText(String.valueOf(currency.format(p.getPrice())));
            	total.setText(String.valueOf(currency.format(p.getTotal())));
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
        
      	purchase = new TableRow(this);
    	purchase.setLayoutParams(layoutParams);
    	TextView name = new TextView(this);
    	TextView quantity = new TextView(this);
    	TextView VAT = new TextView(this);
    	TextView price = new TextView(this);
    	TextView total = new TextView(this);
    	total.setText(String.valueOf(currency.format(basket.getTotal())));
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