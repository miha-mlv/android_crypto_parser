package com.example.cryptoapp.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.cryptoapp.databinding.FragmentCoinDetailBinding
import com.squareup.picasso.Picasso
import javax.inject.Inject

class CoinDetailFragment : Fragment() {

    private var _binding: FragmentCoinDetailBinding? = null
    val binding: FragmentCoinDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentCoinDetailBinding is null")

    private lateinit var viewModel: CoinViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CoinApp).component
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fromSymbol = getSymbol()
        viewModel = ViewModelProvider(this, viewModelFactory)[CoinViewModel::class.java]

        viewModel.getDetailInfo(fromSymbol).observe(viewLifecycleOwner) {
            with(binding) {
                tvPrice.text = it.price
                tvMinPrice.text = it.lowDay
                tvMaxPrice.text = it.highDay
                tvLastMarket.text = it.lastMarket
                tvLastUpdate.text = it.lastUpdate
                tvFromSymbol.text = it.fromSymbol
                tvToSymbol.text = it.toSymbol
                Picasso.get().load(it.imageUrl).into(ivLogoCoin)
            }
        }
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    private fun getSymbol(): String {
        return requireArguments().getString(KEY_FSyms, "")
    }

    companion object {
        private const val EXTRA_FROM_SYMBOL = "fSym"
        private const val KEY_FSyms = "fromSymbol"

        fun newInstance(fromSymbol: String): Fragment {
            return CoinDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FSyms, fromSymbol)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
