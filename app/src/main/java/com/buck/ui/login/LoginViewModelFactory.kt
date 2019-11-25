//package com.buck.ui.login
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.buck.ui.data.LoginDataSource
//import com.buck.ui.data.LoginRepository
//import com.buck.ui.login.LoginViewModel
//
///**
// * ViewModel provider factory to instantiate LoginViewModel.
// * Required given LoginViewModel has a non-empty constructor
// */
//class LoginViewModelFactory : ViewModelProvider.Factory {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            return LoginViewModel(
//                loginRepository = LoginRepository(
//                    dataSource = LoginDataSource()
//                )
//            ) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}
