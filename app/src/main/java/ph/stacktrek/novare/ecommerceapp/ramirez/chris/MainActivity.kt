package ph.stacktrek.novare.ecommerceapp.ramirez.chris

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.adapter.ProductAdapter
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.adapter.SwipeCallback
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.dao.ProductDAO
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.dao.ProductDAOSQLLiteImplementation
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.dao.ProductDAOStubImplementation
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.databinding.ActivityMainBinding
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.databinding.DialogueAddProductBinding
import ph.stacktrek.novare.ecommerceapp.ramirez.chris.model.Product

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var productDAO:ProductDAO
    private lateinit var itemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        Log.d("MAIN ACTIVITY","USERNAME BUNDLE: ${bundle!!.getString("bundle_username")}")

        val extra = intent.getStringExtra("username")
        Log.d("MAIN ACTIVITY","USERNAME EXTRA : $extra")

        loadProducts()

        binding.fabAddProductButton.setOnClickListener {
            showAddProductDialogue().show()
        }

    }

    fun loadProducts(){
        productDAO=ProductDAOSQLLiteImplementation(applicationContext)
        productAdapter = ProductAdapter(applicationContext,
            productDAO.getProducts())

        with(binding.productsList){
            layoutManager=LinearLayoutManager(applicationContext,
                LinearLayoutManager.VERTICAL,
                false)
            adapter=productAdapter
        }

        var swipeCallback = SwipeCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        swipeCallback.productAdapter = productAdapter
        itemTouchHelper = ItemTouchHelper(swipeCallback).apply {
            attachToRecyclerView(binding.productsList)
        }


    }

    fun showAddProductDialogue(): Dialog {
        return this.let {
            val builder = AlertDialog.Builder(it)
            val dialogueAddProductBinding: DialogueAddProductBinding =
                DialogueAddProductBinding.inflate(it.layoutInflater)

            with(builder) {
                setPositiveButton("ADD", DialogInterface.OnClickListener { dialog, id ->
                    val product = Product("")
                    product.name = dialogueAddProductBinding.productName.text.toString()
                    product.description= dialogueAddProductBinding.productDescription.text.toString()
                    product.price=dialogueAddProductBinding.productPrice.text.toString().toInt().toBigDecimal()



                    val productDAO = ProductDAOSQLLiteImplementation(applicationContext)
                    productDAO.addProduct(product)
                    productAdapter.addProduct(product)
                })
                setNegativeButton("CANCEL",DialogInterface.OnClickListener { dialog, id ->

                })
                setView(dialogueAddProductBinding.root)
                create()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val goToMain = Intent(applicationContext,
            LoginActivity::class.java)
        startActivity(goToMain)
        finish()
    }

}