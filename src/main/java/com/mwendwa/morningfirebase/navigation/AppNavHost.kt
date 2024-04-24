package com.mwendwa.morningfirebase.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mwendwa.morningfirebase.ui.theme.home.HomeScreen
import com.mwendwa.morningfirebase.ui.theme.login.LoginScreen
import com.mwendwa.morningfirebase.ui.theme.products.AddProductScreen
import com.mwendwa.morningfirebase.ui.theme.products.UpdateProductsScreen
import com.mwendwa.morningfirebase.ui.theme.products.ViewProductsScreen
import com.mwendwa.morningfirebase.ui.theme.products.ViewUploadsScreen
import com.mwendwa.morningfirebase.ui.theme.register.RegisterScreen

@Composable
fun AppNavHost(modifier: Modifier=Modifier, navController:NavHostController= rememberNavController(), startDestination:String= ROUTE_LOGIN) {

    NavHost(navController = navController, modifier=modifier, startDestination = startDestination ){
        composable(ROUTE_LOGIN){
            LoginScreen(navController)
        }
        composable(ROUTE_REGISTER){
            RegisterScreen(navController)
        }

        composable(ROUTE_HOME){
            HomeScreen(navController)
        }
        composable(ROUTE_ADD_PRODUCT) {
            AddProductScreen(navController)
        }
        composable(ROUTE_VIEW_PRODUCT){
            ViewProductsScreen(navController)
        }
        composable(ROUTE_UPDATE_PRODUCT+ "/{id}"){passedData ->
            UpdateProductsScreen(navController,passedData.arguments?.getString("id")!!)
        }
        composable(ROUTE_VIEW_UPLOAD){
            ViewUploadsScreen(navController)
        }
    }

}