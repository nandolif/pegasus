package com.example.agenda.ui.system

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PermissionInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.agenda.app.App
import com.example.agenda.ui.Theme
import com.example.agenda.ui.component.BTN
import com.example.agenda.ui.component.BTNType
import com.example.agenda.ui.component.TXT

object Permission {


    val list = listOf(
        ManageExternalStoragePermission(),
    )

    interface Permission {
        val data: String
        val name: String
        fun intent()
        fun isValid(): Boolean
        fun isGranted(): Boolean

        fun exists(permission: String, pm: PackageManager): Boolean {
            return try {
                val info = pm.getPermissionInfo(permission, 0)
                return true
            } catch (_: PackageManager.NameNotFoundException) {
                false
            }
        }

        fun isDangerous(permission: String, pm: PackageManager): Boolean {
            return try {
                val info = pm.getPermissionInfo(permission, 0)
                val base = info.protectionLevel and PermissionInfo.PROTECTION_MASK_BASE
                base == PermissionInfo.PROTECTION_DANGEROUS
            } catch (_: PackageManager.NameNotFoundException) {
                false
            }
        }
    }

    class AccessLocationHardwarePermission : Permission {
        override val data: String = Manifest.permission.LOCATION_HARDWARE
        override val name: String = "Localização"
        override fun intent() {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", App.UI.context.packageName, null)
            )
            App.UI.context.startActivity(intent)
        }

        override fun isValid(): Boolean {
            return exists(data, App.UI.context.packageManager) && isDangerous(
                data,
                App.UI.context.packageManager
            )
        }

        override fun isGranted(): Boolean {
            return ContextCompat.checkSelfPermission(
                App.UI.context,
                data
            ) == PackageManager.PERMISSION_GRANTED
        }

    }

    class ManageExternalStoragePermission : Permission {
        override val data: String = Manifest.permission.MANAGE_EXTERNAL_STORAGE
        override val name: String = "Armazenamento Externo"
        override fun intent() {
            val intent = Intent(
                Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                Uri.fromParts("package", App.UI.context.packageName, null)
            )
            App.UI.context.startActivity(intent)
        }

        override fun isValid(): Boolean {
            return exists(data, App.UI.context.packageManager)
        }

        override fun isGranted(): Boolean {
            return Environment.isExternalStorageManager()
        }
    }

    class Ask : ComponentActivity() {
        @Composable
        fun Component(content: @Composable () -> Unit) {
            fun getAvailablePermissions(): List<Permission> {
                return list.filter { it.isValid() && !it.isGranted() }
            }

            val availablePermissions = remember {
                mutableStateOf(
                    getAvailablePermissions()
                )
            }

            fun setAvailablePermissions() {
                availablePermissions.value = getAvailablePermissions()
            }

            val hasAllNow by remember {
                derivedStateOf {
                    availablePermissions.value.all { it.isGranted() }
                }
            }
            if (hasAllNow) {
                content()
            } else {
                PermissionsRequiredScreen(availablePermissions, ::setAvailablePermissions)
            }
        }


        @Composable
        private fun PermissionsRequiredScreen(
            availablePermissions: State<List<Permission>>,
            callback: () -> Unit,
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.Colors.A.color)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TXT("Conceda todas as permissões, por favor.")
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(availablePermissions.value) {
                            BTN(it.name, onClick = {
                                it.intent()
                            }, type = BTNType.SECONDARY)
                        }
                    }
                    BTN("Concedi todas as permissões", onClick = { callback() })

                }
            }
        }
    }
}



