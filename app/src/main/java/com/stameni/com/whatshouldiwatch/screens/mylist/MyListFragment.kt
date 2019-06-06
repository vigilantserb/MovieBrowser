package com.stameni.com.whatshouldiwatch.screens.mylist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.stameni.com.whatshouldiwatch.R

class MyListFragment : Fragment() {

    companion object {
        fun newInstance() = MyListFragment()
    }

    private lateinit var viewModel: MyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MyListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
