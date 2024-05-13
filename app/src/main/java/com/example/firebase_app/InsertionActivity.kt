package com.example.firebase_app


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etGoodsName: EditText
    private lateinit var etGoodsNo: EditText
    private lateinit var etStoreNumber: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etGoodsName = findViewById(R.id.etGoodsName)
        etGoodsNo = findViewById(R.id.etGoodsNo)
        etStoreNumber = findViewById(R.id.etStore)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Goods")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }
    }

    private fun saveEmployeeData() {

        //getting values
        val GoodName = etGoodsName.text.toString()
        val GoodsNo = etGoodsNo.text.toString()
        val StoreNo = etStoreNumber.text.toString()


        if (GoodsNo.isEmpty()) {
            etGoodsNo.error = "Please enter Goods Number"
        }


        val empId = dbRef.push().key!!

        val employee = GoodsModel(empId, GoodName, GoodsNo, StoreNo)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etGoodsName.text.clear()
                etGoodsNo.text.clear()
                etStoreNumber.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }



    }
}