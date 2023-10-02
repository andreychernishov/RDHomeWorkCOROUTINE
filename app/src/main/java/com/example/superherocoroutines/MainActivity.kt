package com.example.superherocoroutines

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections

class MainActivity: AppCompatActivity(),RecyclerViewAdapter.Listener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        viewModel.getData()
        val rcView: RecyclerView = findViewById(R.id.main_rc_view)

//        val api = ApiClient.client.create(ApiInterface::class.java)
        val adapter = RecyclerViewAdapter(callBack = {}, listener = this)
        rcView.adapter = adapter
        rcView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                //dragFlag = 0 якщо елементи не рухаємо
                return makeMovementFlags(
                    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                    ItemTouchHelper.END
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromIndex = viewHolder.adapterPosition
                val toIndex = target.adapterPosition
                Collections.swap(adapter.items, fromIndex, toIndex)
                adapter.notifyItemMoved(fromIndex, toIndex)

                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    adapter.items.removeAt(viewHolder.adapterPosition)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                }
            }

        })

        itemTouchHelper.attachToRecyclerView(rcView)
        viewModel.uiState.observe(this) { uiState ->
            when (uiState) {
                is MyViewModel.UIState.Empty -> Unit
                is MyViewModel.UIState.Result -> {
                    adapter.items = uiState.mem
                    adapter.notifyDataSetChanged()
                }
                is MyViewModel.UIState.Processing -> Toast.makeText(
                    this,
                    "Processing",
                    Toast.LENGTH_SHORT
                ).show()

                is MyViewModel.UIState.Error -> Toast.makeText(
                    this,
                    uiState.description,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
//        lifecycleScope.launch {
//            withContext(Dispatchers.IO){
//                try{
//                    val memes = api.getMemes()
//                    withContext(Dispatchers.Main){
//                        if (memes.isNotEmpty()) {
//                            adapter.items = memes
//                            adapter.notifyDataSetChanged()
//                        }
//                    }
//                }catch (e:Throwable){
//
//                }
//            }
//        }
        }


        override fun onClick(itemClicked: JsData) {
            TODO("Not yet implemented")
        }
    }

