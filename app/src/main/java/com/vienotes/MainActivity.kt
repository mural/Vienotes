package com.vienotes

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smartledge.vienotes.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            findNavController(R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        // Remaining:
        // - crear tasks
        // - control de errores
        // - test basico
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_update -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}


//    private fun createDefaultTask() {
//        val apolloClient = ApolloClient.builder()
//            .serverUrl("https://380odjc5vi.execute-api.us-east-1.amazonaws.com/dev/graphql/")
//            .okHttpClient(
//                OkHttpClient.Builder()
//                    .addInterceptor(AuthorizationInterceptor(this))
//                    .build()
//            )
//            .build()
//
//// in your coroutine scope, call `ApolloClient.query(...).toDeferred().await()`
//        coroutineScope.launch {
//            val response = try {
////                    apolloClient.query(LaunchDetailsQuery(id = "83")).toDeferred().await()
//                apolloClient.mutate(CreateTaskMutation()).await()
//            } catch (e: ApolloException) {
//                // handle protocol errors
//                e.printStackTrace()
//                return@launch
//            }
//
//            val launch = response.data?.createTask
//            if (launch == null || response.hasErrors()) {
//                // handle application errors
//                Log.d(this.toString(), "create task response error")
//                return@launch
//            }
//
//            // launch now contains a typesafe model of your data
//            Log.d(this.toString(), "Tasks id: ${launch.id}")
//        }
//    }