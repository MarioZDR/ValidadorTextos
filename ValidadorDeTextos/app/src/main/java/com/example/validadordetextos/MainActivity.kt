package com.example.validadordetextos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.example.validadordetextos.databinding.ActivityMainBinding;

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailFocusListener()
        passwordFocusListener()
        phoneFocusListener()

        binding.submitButton.setOnClickListener { submitForm() }
    }

    private fun submitForm()
    {
        binding.emailContainer.helperText = validEmail()
        binding.passwordContainer.helperText = validPassword()
        binding.phoneContainer.helperText = validPhone()

        val validEmail = binding.emailContainer.helperText == null
        val validPassword = binding.passwordContainer.helperText == null
        val validPhone = binding.phoneContainer.helperText == null

        if (validEmail && validPassword && validPhone)
            resetForm()
        else
            invalidForm()
    }

    private fun invalidForm()
    {
        var message = ""
        if(binding.emailContainer.helperText != null)
            message += "\n\nCorreo: " + binding.emailContainer.helperText
        if(binding.passwordContainer.helperText != null)
            message += "\n\nContraseña: " + binding.passwordContainer.helperText
        if(binding.phoneContainer.helperText != null)
            message += "\n\nTeléfono: " + binding.phoneContainer.helperText

        AlertDialog.Builder(this)
            .setTitle("Formulario Inválido")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->}
            .show()
    }

    private fun resetForm()
    {
        var message = "Correo: " + binding.emailEditText.text
        message += "\nContraseña: " + binding.passwordEditText.text
        message += "\nTeléfono: " + binding.phoneEditText.text
        AlertDialog.Builder(this)
            .setTitle("Formulario enviado")
            .setMessage(message)
            .setPositiveButton("Okay"){ _,_ ->
                binding.emailEditText.text = null
                binding.passwordEditText.text = null
                binding.phoneEditText.text = null

                binding.emailContainer.helperText = getString(R.string.required)
                binding.passwordContainer.helperText = getString(R.string.required)
                binding.phoneContainer.helperText = getString(R.string.required)
            }
            .show()
    }

    private fun emailFocusListener()
    {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.emailContainer.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String?
    {
        val emailText = binding.emailEditText.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Correo Inválido"
        }
        return null
    }

    private fun passwordFocusListener()
    {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.passwordContainer.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String?
    {
        val passwordText = binding.passwordEditText.text.toString()
        if(passwordText.length < 9)
        {
            return "Mínimo 9 caracteres"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Debe contener al menos una mayúscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "Debe contener al menos una minúscula"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Debe contener al menos un carácter especial (@#\$%^&+=)"
        }
        if(!passwordText.matches(".*\\d.*".toRegex()))
        {
            return "Debe contener al menos un número"
        }

        return null
    }

    private fun phoneFocusListener()
    {
        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.phoneContainer.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String?
    {
        val phoneText = binding.phoneEditText.text.toString()
        if(!phoneText.matches(".*[0-9].*".toRegex()))
        {
            return "Deben ser solo dígitos"
        }
        if(phoneText.length != 10)
        {
            return "Deben ser 10 dígitos"
        }
        return null
    }

}