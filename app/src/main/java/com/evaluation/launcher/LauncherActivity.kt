package com.evaluation.launcher

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.evaluation.R
import com.evaluation.language.LanguageActivity
import com.evaluation.main.MainActivity
import com.evaluation.utils.LauncherViewState
import com.evaluation.viewmodel.launcher.LaunchViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_launcher.*
import org.jetbrains.anko.intentFor

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private val viewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
        initLoader()
    }

    private fun initLoader() {
        viewModel.load.observe(this) { status ->
            val snackBar = Snackbar.make(layoutMain,
                when (status) {
                    LauncherViewState.CHECK_CONNECTION -> getString(R.string.launcher_connection)
                    LauncherViewState.CHECK_BOOT -> getString(R.string.launcher_boot)
                    LauncherViewState.LOAD_LANGUAGE -> getString(R.string.launcher_language)
                    LauncherViewState.LOAD_STATS -> getString(R.string.launcher_statistics)
                    LauncherViewState.LOAD_ABILITIES -> getString(R.string.launcher_abilities)
                    LauncherViewState.LOAD_TYPES -> getString(R.string.launcher_types)
                    LauncherViewState.FINISHED -> getString(R.string.launcher_finished)
                    else -> getString(R.string.launcher_error)
                }, Snackbar.LENGTH_INDEFINITE)

            val snackBarTextView = snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            snackBarTextView.isSingleLine = false
            snackBarTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackBar.show()

            if (status == LauncherViewState.FINISHED) {
                if (!viewModel.language.isNullOrEmpty()) {
                    startActivity(intentFor<MainActivity>())
                } else {
                    startActivity(intentFor<LanguageActivity>())
                }
            }
        }
    }
}