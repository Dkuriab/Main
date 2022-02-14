package com.onlineshop.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.onlineshop.databinding.CartFragmentBinding
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Success
import com.onlineshop.viewmodel.ProductsCartViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Draws cart view, sets observers and listeners for views
 */
class CartFragment : Fragment() {

    @Inject
    lateinit var viewModel: ProductsCartViewModel
    private var binding: CartFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        viewModel.initialize(1)

        binding = CartFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            val listAdapter = CartListAdapter { productId ->
                Log.d("TRANSACTION", "To details")
                navigateToDetails(productId)
            }
            adapter = listAdapter
        }

        setListeners()
        observeViewModel()
        attachSwipeHandler()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun attachSwipeHandler() {
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = binding?.recyclerView?.adapter as CartListAdapter
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
//                viewModel.remove(adapter.currentList(viewHolder.adapterPosition))
//                            AsyncTaskDelete(viewHolder.adapterPosition).execute()
            }
        }
        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding?.recyclerView)
    }

    private fun setListeners() {
        binding?.buyButton?.setOnClickListener {
            viewModel.buy()
        }
    }

    private fun observeViewModel() {
        viewModel.products?.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Success -> {
                        val adapter = binding?.recyclerView?.adapter as CartListAdapter
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
        viewModel.totalCost.observe(
            viewLifecycleOwner,
            {
                binding?.totalCost?.text = it.toString()
            }
        )
    }

    /**
     * Changes screen to Product Details with [com.onlineshop.data.model.Product] information by its is
     *
     * @param productId id of product to show details
     */
    private fun navigateToDetails(productId: Long) {
        findNavController().navigate(
            CartFragmentDirections.actionCartFragmentToProductDetailsFragment(
                productId
            )
        )
    }
}
