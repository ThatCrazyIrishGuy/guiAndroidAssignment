package ie.dit.britton.darren.shoppinglist;

import android.annotation.SuppressLint;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.*;
import java.util.Locale;

public class ShoppingListView extends ListActivity
{
	
	private Basket basket = Basket.getInstance();
	private NumberFormat currency;
	private Toast toast;
	
	@SuppressLint("ViewHolder")
	public class ShoppingListAdapter  extends ArrayAdapter<String>
	{
		Context context;
		Store store;
		String[] values;
		int textViewResourceId;
		int priceResourceId;
		int plusResourceId;
		int minusResourceId;
		int quantityResourceId;
		
		public ShoppingListAdapter(Context context,int resource,int textViewResourceId,
				int priceResourceId, int plusResourceId, int minusResourceId, int quantityResourceId,String[] values)
		{
			super(context,resource,textViewResourceId,values);
			
			store = Store.getInstance();
			
			this.textViewResourceId = textViewResourceId;
			this.priceResourceId = priceResourceId;
			this.plusResourceId = plusResourceId;
			this.minusResourceId = minusResourceId;
			this.quantityResourceId = quantityResourceId;
			this.context = context;
			this.values = values;
		}

		@SuppressLint({ "ViewHolder", "InflateParams" })
		@Override
		public View getView(final int position,  View view, ViewGroup parent)
		{
		    if (view == null)
		    {
				LayoutInflater inflater=getLayoutInflater();
				inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        view = inflater.inflate(R.layout.row, parent, false);
		        
		    }
		    
	        final Item item = store.getItem(position);
	        TextView name = (TextView) view.findViewById(textViewResourceId);
	        name.setText(item.getName());
	        TextView price = (TextView) view.findViewById(priceResourceId);
	        price.setText(currency.format(item.getPrice()));
	        Button plus = (Button) view.findViewById(plusResourceId);
	        Button minus = (Button) view.findViewById(minusResourceId);
	        final TextView quantity = (TextView) view.findViewById(quantityResourceId);
	        if (basket.contains(item))
	        {
	        	quantity.setText(String.valueOf(basket.findItem(item).getQuantity()));
	        }
	        else
	        {
	        	quantity.setText("0");
	        }
	        
			plus.setOnClickListener(
               new OnClickListener()
               {
               	@Override
                   public void onClick(View view)
                   {
						OrderLine orderline = basket.findItem(item);
						
						if(basket.getRemainingBudget() <= orderline.getPrice())
						{
							makeToast("Cannot Excede Budget \n" + currency.format(basket.getRemainingBudget())
								+ " Remaining");
						}
						else
						{
							if (orderline.getQuantity() == -1.0)
							{
						   		orderline.setQuantity(orderline.getQuantity() + 2);
						   		basket.addItem(orderline);
							}
							else
							{
						   		orderline.setQuantity(orderline.getQuantity() + 1);
							}
							
					   		makeToast(currency.format(basket.getRemainingBudget()));
							quantity.setText(String.valueOf(orderline.getQuantity()));
						}
                   }
               });
			
			minus.setOnClickListener(
               new OnClickListener()
               {
               	   @Override
                   public void onClick(View view)
                   {
               		OrderLine orderline = basket.findItem(item);
	               		
               		if (orderline.getQuantity() > 0)
               		{
	               		orderline.setQuantity(orderline.getQuantity() - 1);
	               		quantity.setText(String.valueOf(orderline.getQuantity()));
	               		makeToast(currency.format(basket.getRemainingBudget()));
               		}	               		
                   }
               }
			);
		        
	        return view;
		}

	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_view);
        
        Button checkout = (Button)findViewById(R.id.checkout);
        checkout.setOnClickListener(
                new OnClickListener()
                {
                	@Override
                    public void onClick(View view)
                    {
                		checkout();
                    }
                });
        
        
        Store store = Store.getInstance();
        currency = NumberFormat.getCurrencyInstance(Locale.ITALY);
        setListAdapter(new ShoppingListAdapter(this,R.layout.row,R.id.item,R.id.price,R.id.plus,R.id.minus,R.id.quantity,store.getItemNames()));
    }
    
    private void checkout()
    {
		Intent intent = new Intent(ShoppingListView.this,CheckoutView.class);
		startActivity(intent);
    }
    
    protected void makeToast(String message)
    {  	
    	if (toast != null)
    	{
    		toast.cancel();
    	}
    	toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
    	toast.show();
    }
}
