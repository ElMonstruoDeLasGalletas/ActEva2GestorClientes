package com.example.crm.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.crm.data.ClientRepository
import com.example.crm.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager

class MainActivity : AppCompatActivity() {

    private lateinit var b: ActivityMainBinding
    private lateinit var repo: ClientRepository
    private lateinit var adapter: ClientAdapter

    private val refreshLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        loadAll()
        b.etSearch.setText("")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)

        repo = ClientRepository(this)

        adapter = ClientAdapter(mutableListOf(),
            onEdit = { client ->
                val i = Intent(this, EditClientActivity::class.java).apply {
                    putExtra("id", client.id)
                    putExtra("name", client.name)
                    putExtra("email", client.email)
                    putExtra("phone", client.phone)
                }
                refreshLauncher.launch(i)
            },
            onDelete = { client ->
                client.id?.let { id ->
                    repo.delete(id)
                    adapter.removeById(id)
                    updateCount()
                }
            }
        )
        b.rvClients.adapter = adapter
        b.rvClients.layoutManager = LinearLayoutManager(this)


        b.fabAdd.setOnClickListener {
            refreshLauncher.launch(Intent(this, EditClientActivity::class.java))
        }

        b.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val q = s?.toString().orEmpty()
                if (q.isBlank()) loadAll() else runSearch(q)
            }
        })

        loadAll()
    }

    private fun loadAll() {
        val all = repo.getAll()
        adapter.submitList(all)
        updateCount(all.size)
    }

    private fun runSearch(q: String) {
        val res = repo.search(q)
        adapter.submitList(res)
        updateCount(res.size)
    }

    private fun updateCount(n: Int = repo.count()) {
        b.tvCount.text = n.toString()
    }
}
