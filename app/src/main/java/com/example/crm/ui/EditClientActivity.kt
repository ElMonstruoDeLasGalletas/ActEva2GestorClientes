package com.example.crm.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.crm.data.Client
import com.example.crm.data.ClientRepository
import com.example.crm.databinding.ActivityEditClientBinding
import com.example.crm.util.Validators

class EditClientActivity : AppCompatActivity() {

    private lateinit var b: ActivityEditClientBinding
    private lateinit var repo: ClientRepository
    private var editingId: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityEditClientBinding.inflate(layoutInflater)
        setContentView(b.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Cliente"

        repo = ClientRepository(this)

        editingId = intent.getLongExtra("id", -1L).takeIf { it != -1L }
        editingId?.let {
            b.etName.setText(intent.getStringExtra("name") ?: "")
            b.etEmail.setText(intent.getStringExtra("email") ?: "")
            b.etPhone.setText(intent.getStringExtra("phone") ?: "")
        }

        b.btnSave.setOnClickListener { save() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun save() {
        val name = b.etName.text?.toString()?.trim().orEmpty()
        val email = b.etEmail.text?.toString()?.trim().orEmpty()
        val phone = b.etPhone.text?.toString()?.trim().orEmpty()

        if (!Validators.isNotBlank(name, email, phone)) {
            toast("Rellena todos los campos")
            return
        }
        if (!Validators.isValidEmail(email)) {
            toast("Email inválido")
            return
        }
        if (!Validators.isValidPhone(phone)) {
            toast("Teléfono inválido (mínimo 9 dígitos)")
            return
        }

        if (editingId == null) {
            val id = repo.insert(Client(null, name, email, phone))
            if (id > 0) {
                toast("Insertado")
                setResult(RESULT_OK)
                finish()
            } else toast("Error al insertar")
        } else {
            val rows = repo.update(Client(editingId, name, email, phone))
            if (rows > 0) {
                toast("Actualizado")
                setResult(RESULT_OK)
                finish()
            } else toast("Error al actualizar")
        }
    }

    private fun toast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
