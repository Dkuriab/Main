package com.onlineshop.ui.productlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.onlineshop.R
import com.onlineshop.ShopApplication
import com.onlineshop.databinding.ProductsFragmentBinding
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Success
import com.onlineshop.viewmodel.ProductsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Draws product list sets observers and listeners for views
 */
class ProductsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ProductsViewModel
    private var binding: ProductsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding = ProductsFragmentBinding.inflate(inflater, container, false)
        binding?.productsRecyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            val productsListAdapter = ProductsListAdapter(
                onClick = { productId: Long ->
                    Log.d("TRANSACTION", "To details")
                    navigateToDetails(productId)
                },
                onAddToCartButtonClick = { productId ->
                    viewModel.addItemToCart(productId)
                    val snackBar = Snackbar.make(
                        binding!!.mainLayout,
                        getString(R.string.item_added_to_cart),
                        Snackbar.LENGTH_SHORT
                    )
                    snackBar.setAction(getString(R.string.ok), object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            snackBar.dismiss()
                        }
                    })
                    snackBar.show()
                }
            )
            adapter = productsListAdapter
        }

        setListeners()
        observeViewModel()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    /**
     * Sets listeners to view items
     */
    private fun setListeners() {
        binding?.searchBar?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText != null) {
                    viewModel.findByPartOfName(newText)
                    true
                } else {
                    false
                }
            }
        })
    }

    /**
     * Changes screen to Product Details with [com.onlineshop.data.model.Product] information by its is
     *
     * @param productId id of product to show details
     */
    private fun navigateToDetails(productId: Long) {
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
                productId
            )
        )
    }

    /**
     * Sets observes to viewModel fields, connecting it to view
     */
    private fun observeViewModel() {
        viewModel.products.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Success -> {
                        val adapter = binding?.productsRecyclerView?.adapter as ProductsListAdapter
                        adapter.submitList(it.data)
                    }
                    is Error -> {
                        Log.d("ERROR", it.message)
                    }
                    is Loading -> {
                        Log.d("LOADING", "...")
                    }
                }
            }
        )
    }
}
