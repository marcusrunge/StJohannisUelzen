package com.marcusrunge.stjohannisuelzen.ui.privacy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.PrivacyFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] to display the privacy policy of the app.
 * It uses data binding to connect the layout with the [PrivacyViewModel].
 */
@AndroidEntryPoint
class PrivacyFragment : Fragment() {

    /**
     * The backing property for the view binding.
     * This is nullable and is set to null in [onDestroyView] to avoid memory leaks.
     */
    private var _binding: PrivacyFragmentBinding? = null

    /**
     * The non-nullable view binding property.
     * This should only be used between [onCreateView] and [onDestroyView].
     */
    private val binding get() = _binding!!

    /**
     * The [PrivacyViewModel] for this fragment, scoped to the fragment's lifecycle.
     * It is used to provide data to the UI and handle user interactions.
     */
    private val viewModel by viewModels<PrivacyViewModel>()

    /**
     * Called to have the fragment instantiate its user interface view.
     * This is where the layout is inflated and view binding is initialized.
     *
     * @param inflater The [LayoutInflater] object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The [View] for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.privacy_fragment, container, false)
        return binding.root
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored in to the view.
     * This is where the [viewModel] and [viewLifecycleOwner] are set on the binding object
     * to enable data binding.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    /**
     * Called when the view previously created by [onCreateView] has been detached from the fragment.
     * This is where the binding is cleaned up to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
