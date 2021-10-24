/*
* Copyright (C) 2021 The Android Open Source Project.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.dogglers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dogglers.adapter.DogCardAdapter
import com.example.dogglers.const.Layout
import com.example.dogglers.databinding.ActivityVerticalListBinding

class VerticalListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerticalListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerticalListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.verticalRecyclerView.adapter = DogCardAdapter(
            applicationContext,
            Layout.VERTICAL
        )

        // Specify fixed size to improve performance
        binding.verticalRecyclerView.setHasFixedSize(true)

        // Enable up button for backward navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //Practising Map. It is not part of the Dogglers app
      val map = mutableMapOf(
          "Robert" to 23,
          "Patience" to 31
      )


        map.put("Narko",68)
        map.put("Narko",234)

map.forEach { "${it.key} is ${it.value} old" }

        val agesMap = mutableMapOf("Robert" to 32,
        "Patience" to 55,
        "Olivier" to 45
        )
        agesMap.forEach{
            println("${it.key} is ${it.value} year old")
        }

        agesMap["Oliver"] = 32


    }
}
