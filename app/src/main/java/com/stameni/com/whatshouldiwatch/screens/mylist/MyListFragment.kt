package com.stameni.com.whatshouldiwatch.screens.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.my_list_fragment.*
import javax.inject.Inject

class MyListFragment : BaseFragment() {

    @Inject
    lateinit var movieRoomDatabase: MovieDatabase

    @Inject
    lateinit var imageLoader: ImageLoader

    private lateinit var viewModel: MyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.my_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this).get(MyListViewModel::class.java)

        var layoutManager = GridLayoutManager(context, 1, RecyclerView.VERTICAL, false)
        var adapter = LocalMovieListAdapter(ArrayList(), imageLoader)

        movies_rv.adapter = adapter
        movies_rv.layoutManager = layoutManager

        val xx = movieRoomDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                adapter.addAll(it)
            }, { println("Failure") })
    }
}
