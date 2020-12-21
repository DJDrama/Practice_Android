package com.introducemyselfsample.www.ui.masterpiece

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.introducemyselfsample.www.data.Idol
import com.introducemyselfsample.www.databinding.ActivityMyMasterpeieceBinding
import com.introducemyselfsample.www.db.AppDatabase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyMasterpieceActivity : AppCompatActivity(), IdolRecyclerViewAdapter.IdolClickListener {
    private lateinit var mAdapter: IdolRecyclerViewAdapter

    private lateinit var binding: ActivityMyMasterpeieceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyMasterpeieceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.apply {
            mAdapter = IdolRecyclerViewAdapter(this@MyMasterpieceActivity)
            adapter = mAdapter
        }

        binding.button6.setOnClickListener {
            // add
            val name = binding.editTextTextPersonName.text.toString()
            val number = binding.editTextNumber.text.toString()
            if(name.isEmpty() || name.isBlank()) {
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(number.isEmpty() || number.isBlank()) {
                Toast.makeText(this, "인원을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch(IO){
                AppDatabase.invoke(this@MyMasterpieceActivity).getIdolDao().insertIdol(Idol(name, number.toInt()))
                withContext(Main){
                    Toast.makeText(this@MyMasterpieceActivity, "추가 되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.button9.setOnClickListener {
            lifecycleScope.launch(IO){
                getAllIdols()
            }
        }
        binding.button5.setOnClickListener {
            finish()
        }
    }

    suspend fun getAllIdols(){
        val list = AppDatabase.invoke(this@MyMasterpieceActivity).getIdolDao().getAllIdols()
        withContext(Main){
            mAdapter.submitList(list)
        }
    }

    override fun onIdolClicked(idol: Idol) {
        lifecycleScope.launch(IO){
            AppDatabase.invoke(this@MyMasterpieceActivity).getIdolDao().deleteIdol(idol)
            withContext(Main){
                Toast.makeText(this@MyMasterpieceActivity, "삭제 되었습니다.", Toast.LENGTH_SHORT).show()
            }
            getAllIdols()
        }
    }
}
