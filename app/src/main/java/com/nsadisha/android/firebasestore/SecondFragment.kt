package com.nsadisha.android.firebasestore

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.nsadisha.android.firebasestore.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()

        binding.saveBtn.setOnClickListener {
            //toast message
            val toast = Toast.makeText(context, "Saved successfully.", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.BOTTOM, 0, 0)
            //fields
            val firstName = binding.firstName.text
            val lastName = binding.lastName.text
            val age = binding.age.text

            //creating a user object
            val user = mapOf(
                "first_name" to firstName.toString(),
                "last_name" to lastName.toString(),
                "age" to age.toString()
            )
            //disable the save btn
            binding.saveBtn.isEnabled = false

            //add users to firestore
            db.collection("users")
                .add(user)
                .addOnSuccessListener {
                    toast.show()
                    //clear the fields
                    binding.firstName.setText("")
                    binding.lastName.setText("")
                    binding.age.setText("")
                    //enable the save btn
                    binding.saveBtn.isEnabled = true
                }
                .addOnFailureListener {
                    toast.setText("Unable to save the user!")
                    toast.show()
                    //enable the save btn
                    binding.saveBtn.isEnabled = true
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}