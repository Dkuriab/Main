package com.onlineshop.ui.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.onlineshop.databinding.HistoryFragmentBinding
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Success
import com.onlineshop.viewmodel.HistoryViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Draws history view, sets observers and listeners for views
 */
class HistoryFragment : Fragment() {

    @Inject
    lateinit var viewModel: HistoryViewModel
    private var binding: HistoryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        viewModel.initialize()

        binding = HistoryFragmentBinding.inflate(inflater, container, false)
        binding?.recyclerView?.apply {
            layoutManager = GridLayoutManager(requireContext(), 1)
            val listAdapter = HistoryListAdapter { productId ->
                Log.d("TRANSACTION", "To details")
                navigateToDetails(productId)
            }
            adapter = listAdapter
        }

        observeViewModel()
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    private fun observeViewModel() {
        viewModel.products?.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Success -> {
                        val adapter = binding?.recyclerView?.adapter as HistoryListAdapter
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

    /**
     * Changes screen to Product Details with [com.onlineshop.data.model.Product] information by its id
     *
     * @param productId id of product to show details
     */
    private fun navigateToDetails(productId: Long) {
        findNavController().navigate(
            HistoryFragmentDirections.actionHistoryFragmentToProductDetailsFragment(
                productId
            )
        )
    }
}
