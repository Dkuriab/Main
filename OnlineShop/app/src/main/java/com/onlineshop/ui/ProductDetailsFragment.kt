package com.onlineshop.ui

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.onlineshop.R
import com.onlineshop.data.model.Product
import com.onlineshop.databinding.ProductDetailsFragmentBinding
import com.onlineshop.networking.Error
import com.onlineshop.networking.Loading
import com.onlineshop.networking.Success
import com.onlineshop.utils.ImageDownloader
import com.onlineshop.viewmodel.ProductDetailsViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Draws product details view sets observers and listeners for views
 */
class ProductDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModel: ProductDetailsViewModel
    private var binding: ProductDetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        viewModel.initialize(ProductDetailsFragmentArgs.fromBundle(requireArguments()).productId)
        binding = ProductDetailsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.productDescription?.movementMethod = ScrollingMovementMethod()
        observeViewModel()
        setListeners()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    /**
     * Sets listeners to view items
     */
    private fun setListeners() {
        binding?.buttonBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    /**
     * Sets observes to viewModel fields, connecting it to view
     */
    private fun observeViewModel() {
        viewModel.product?.observe(
            viewLifecycleOwner,
            {
                when (it) {
                    is Success -> {
                        fillView(it.data)
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
     * Sets information about passed [Product] to view
     *
     * @param product [Product] which information will be used
     */
    private fun fillView(product: Product?) {
        binding?.apply {
            productDescription.text = product?.description
            ImageDownloader().downloadImageToViewByURL(
                product?.imagePath.toString(),
                productImage
            )
            productName.text = product?.name ?: getString(R.string.product_name)
            productRating.rating = product?.rating?.toFloat() ?: 0f
            productPrice.text = product?.formattedPrice
        }
    }
}
