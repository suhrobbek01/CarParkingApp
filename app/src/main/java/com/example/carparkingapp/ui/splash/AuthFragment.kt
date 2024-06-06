package com.example.carparkingapp.ui.splash

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.carparkingapp.MainActivity
import com.example.carparkingapp.R
import com.example.carparkingapp.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_SIGN_IN
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private var firebaseUserId: String = ""
    private lateinit var refUsers: DatabaseReference
    private lateinit var refSpaces: DatabaseReference
    private var progressDialog: ProgressDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()


        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        binding.apply {
            signInWithGoogle.setOnClickListener {
                googleSignIn()
            }
        }
        return binding.root
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
        progressDialog = ProgressDialog(requireContext())
        progressDialog?.setMessage("Please wait, the process is in progress")
        progressDialog?.show()
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9001) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account.idToken?.let { firebaseAuthWithGoogle(it) }
            } catch (e: Exception) {
                progressDialog?.dismiss()
                Toast.makeText(requireContext(), "89 " + e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                firebaseUserId = auth.currentUser?.uid.toString()
                refUsers =
                    FirebaseDatabase.getInstance().reference.child("users").child(firebaseUserId)

                val userHashMap = HashMap<String, Any>()
                userHashMap["uid"] = firebaseUserId
                userHashMap["username"] = user?.displayName.toString()
                userHashMap["profile"] = user?.photoUrl.toString()

                refUsers.updateChildren(userHashMap).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        requireActivity().finish()
                        progressDialog?.dismiss()
                    } else {
                        progressDialog?.dismiss()
                        Toast.makeText(
                            requireContext(), task.exception?.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                refSpaces =
                    FirebaseDatabase.getInstance().reference.child("spaces")
            } else {
                progressDialog?.dismiss()
                Toast.makeText(
                    requireContext(), "133" + task.exception?.message, Toast.LENGTH_SHORT
                ).show()
                Log.w("MainActivity", "signInWithCredential:failure", task.exception)
            }
        }
    }
}