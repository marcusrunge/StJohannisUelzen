package com.marcusrunge.stjohannisuelzen.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.MapsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] for displaying a Google Map with a marker for the church's location.
 *
 * This fragment integrates with the Google Maps API to show the location of St. Johannis Uelzen.
 * It follows the MVVM pattern, using [MapsViewModel] to handle its data and state, and uses
 * data binding to connect the layout with the ViewModel.
 *
 * The map's functionality, such as adding markers and camera positioning, is managed within
 * the `maps_fragment.xml` layout via a custom binding adapter for the `MapView`.
 */
@AndroidEntryPoint
class MapsFragment : Fragment() {
    private var _binding: MapsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MapsViewModel>()

    // region Lifecycle Methods

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * This is where the layout is inflated using [DataBindingUtil] to create the binding object.
     *
     * @param inflater The LayoutInflater object that can be used to inflate views.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return The View for the fragment's UI.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.maps_fragment, container, false)
        return binding.root
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored.
     *
     * This is where the ViewModel and LifecycleOwner are set for data binding, enabling the layout
     * to observe [LiveData] and respond to data changes.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the ViewModel for data binding. This allows the layout to access data
        // from the ViewModel.
        binding.viewmodel = viewModel

        // Set the LifecycleOwner for data binding. This allows LiveData in the ViewModel
        // to automatically update the UI while respecting the fragment's lifecycle.
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    /**
     * Called when the view previously created by [onCreateView] has been detached from the fragment.
     *
     * It's crucial to clear the reference to the binding object here to prevent memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Clear the binding reference to avoid memory leaks. The binding holds a reference to the
        // view hierarchy, which should be garbage collected when the view is destroyed.
        _binding = null
    }

    // endregion
}
