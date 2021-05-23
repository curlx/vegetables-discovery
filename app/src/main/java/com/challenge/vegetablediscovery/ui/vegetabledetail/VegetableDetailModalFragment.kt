package com.challenge.vegetablediscovery.ui.vegetabledetail

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.challenge.vegetablediscovery.R
import com.challenge.vegetablediscovery.databinding.BottomSheetVegetableDetailBinding
import com.challenge.vegetablediscovery.glide.GlideApp
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VegetableDetailModalFragment: BottomSheetDialogFragment() {

    private val args: VegetableDetailModalFragmentArgs by navArgs()

    private var _binding: BottomSheetVegetableDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var vegetableDetailViewModel: VegetableDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = BottomSheetVegetableDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        vegetableDetailViewModel = ViewModelProvider(this).get(VegetableDetailViewModel::class.java)
        val vegetableDetail = vegetableDetailViewModel.getVegetableDetail(args.vegetableId)

        binding.name.text = vegetableDetail.name
        GlideApp.with(requireContext())
            .load(vegetableDetail.imageUrl)
            .transform(CenterCrop())
            .placeholder(R.drawable.vegetable_placeholder)
            .into(binding.image)
        binding.description.text = vegetableDetail.description

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme).apply {
            setOnShowListener {
                (it as? BottomSheetDialog)?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)?.let { parentLayout ->
                    setupFullHeight(parentLayout)
                    BottomSheetBehavior.from(parentLayout).apply {
                        state = BottomSheetBehavior.STATE_EXPANDED
                        skipCollapsed = true
                    }
                }
            }
        }
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}