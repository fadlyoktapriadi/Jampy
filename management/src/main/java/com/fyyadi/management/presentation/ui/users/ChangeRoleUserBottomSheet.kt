package com.fyyadi.management.presentation.ui.users

import androidx.browser.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.theme.Green600
import com.fyyadi.theme.PrimaryGreen
import com.fyyadi.theme.RethinkSans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeRoleUserBottomSheet(
    sheetState: SheetState,
    user: UserProfile,
    onDismiss: () -> Unit,
    onSave: (Int, String) -> Unit
) {
    val roles = listOf("Pengguna", "Petugas")
    var selectedRole by remember { mutableStateOf(user.role ?: "") }
    var expanded by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 24.dp)
        ) {
            Text(
                text = stringResource(com.fyyadi.management.R.string.change_role_user),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryGreen,
                fontFamily = RethinkSans
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = user.userFullName ?: "Unknown",
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedRole,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text(
                            text = stringResource(com.fyyadi.management.R.string.select_role),
                            color = PrimaryGreen,
                            fontFamily = RethinkSans
                        )
                    },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PrimaryGreen,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roles.forEach { role ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = role,
                                    fontFamily = RethinkSans
                                )
                            },
                            onClick = {
                                selectedRole = role
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    user.userId?.let { onSave(it, selectedRole) }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Green600
                )
            ) {
                Text(
                    text = stringResource(com.fyyadi.management.R.string.save_button),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontFamily = RethinkSans,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
