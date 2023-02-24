package com.example.triplekaisse.presentation.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.triplekaisse.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}