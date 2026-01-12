package com.marcusrunge.stjohannisuelzen.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.marcusrunge.stjohannisuelzen.R
import com.marcusrunge.stjohannisuelzen.databinding.CalendarFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A [Fragment] for displaying the church's calendar.
 *
 * This fragment uses a [WebView] to display the calendar, which is loaded from a remote URL.
 * It follows the MVVM pattern, using [CalendarViewModel] to manage its data and state.
 * Data binding is used to connect the layout with the ViewModel.
 */
@AndroidEntryPoint
class CalendarFragment : Fragment() {

    private var _binding: CalendarFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CalendarViewModel>()

    // region Lifecycle Methods

    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * This is where the layout is inflated and data binding is initialized.
     *
     * @param inflater The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI, or null.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.calendar_fragment, container, false)
        return binding.root
    }

    /**
     * Called immediately after [onCreateView] has returned, but before any saved state has been restored in to the view.
     *
     * This is where the ViewModel and LifecycleOwner are set for data binding, and any other
     * view-related setup is performed.
     *
     * @param view The View returned by [onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the ViewModel for data binding. This allows the layout to access data
        // from the ViewModel.
        binding.viewmodel = viewModel

        // Set the LifecycleOwner for data binding. This allows LiveData to automatically
        // update the UI when the data changes, while respecting the fragment's lifecycle.
        binding.lifecycleOwner = this.viewLifecycleOwner
    }

    /**
     * Called when the view previously created by [onCreateView] has been detached from the fragment.
     *
     * This is where we clean up the binding to avoid memory leaks.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // endregion
}
