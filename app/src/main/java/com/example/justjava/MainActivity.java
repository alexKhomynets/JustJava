/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        //For cream CheckBox
        CheckBox checkCream = findViewById(R.id.whipped_cream_check_box);
        boolean hasWhippedCream = checkCream.isChecked();
        //For chocolate CheckBox
        CheckBox checkChocolate = findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = checkChocolate.isChecked();

        //For entering your name
        EditText editName = findViewById(R.id.enter_name_edit_text);

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String summary =  createOrderSummary(price, hasWhippedCream, hasChocolate);

        nameChecker(editName, summary);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int quantityNumber) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantityNumber);
    }


//    /**
//     * This method displays text on the screen
//     */
//    private void displayMessage(String showMessage) {
//        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(showMessage);
//    }

    /**
     * This method increases the quantity
     */
    public void increment(View view) {
        if(quantity >= 100){
            //Creates toast that will show error on the screen
            Toast.makeText(getApplicationContext(), getString(R.string.increment), Toast.LENGTH_LONG).show();
            //Exits statement earlier
            return;
        }
        ++quantity;
        displayQuantity(quantity);
    }

    /**
     * This method decrease the quantity
     */
    public void decrement(View view) {
        if(quantity <= 1){
            //Creates toast that will show error on the screen
            Toast.makeText(getApplicationContext(), getString(R.string.decrement), Toast.LENGTH_LONG).show();
            //Exits statement earlier
            return;
        }
        --quantity;
        displayQuantity(quantity);
    }


    /**
     * Calculates the amount of ordered cups and price per one cup
     * @param choco topping
     * @param cream topping
     * @return total price of the order
     */
    private int calculatePrice(boolean cream , boolean choco) {
        //Price per one cup is $5
        int pricePerCup = 5;


        //Cream topping costs $2
        if(cream == true & pricePerCup > 0){
            pricePerCup += 1;
        }

        //Chocolate topping costs $3
        if(choco == true & pricePerCup > 0){
            pricePerCup += 2;
        }

        return quantity * pricePerCup;
    }

    /**
     * Makes the final concatenations
     *
     * @param price of the order
     * @param checkedCream to add or not to add whipped cream
     * @param checkedChocolate to add or not to add chocolate
     * @return allTotal
     */
    private String createOrderSummary(int price, boolean checkedCream, boolean checkedChocolate) {
        String allTotal = getString(R.string.any_cream) + checkedCream +
                "\n" + getString(R.string.any_chocolate) + checkedChocolate +
                "\n" + getString(R.string.quantity_submit) + quantity +
                "\n" + getString(R.string.total_submit)  + NumberFormat.getCurrencyInstance().format(price) +
                "\n" + getString(R.string.thank_you);

        return allTotal;
    }

    /**
     * Changes quantity to 1, if it`s needed
     * @param view when the RESET button is clicked
     */
    public void resetAll(View view){
        quantity = 1;
        displayQuantity(quantity);
    }


    /**
     * Method for checking, if user inputs his name
     * @param userName
     * @return
     */
    public void nameChecker(EditText userName, String totalSummary){
        if( TextUtils.isEmpty(userName.getText())){

            Toast.makeText(getApplicationContext(), getString(R.string.caffeine), Toast.LENGTH_LONG).show();

            userName.setError( getString(R.string.no_name_error) );

        }else{
            String nameSaver = userName.getText().toString();
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_EMAIL, "coffeeOrder@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.name_in_the_subject) + nameSaver);
            intent.putExtra(Intent.EXTRA_TEXT, totalSummary);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }
}
