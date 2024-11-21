package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jettipapp.components.InputField
import com.example.jettipapp.ui.theme.JetTipAppTheme
import com.example.jettipapp.util.calculateTotalPerPerson
import com.example.jettipapp.util.calculateTotalTip
import com.example.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val totalBillState = remember { mutableStateOf("") }
            val sliderPositionState = remember { mutableFloatStateOf(0f) }
            val splitByState = remember { mutableIntStateOf(1) }
            val tipAmountState = remember { mutableDoubleStateOf(0.0) }
            val totalPerPersonState = remember { mutableDoubleStateOf(0.0) }

            JetTipApp(
                totalBillState = totalBillState,
                sliderPositionState = sliderPositionState,
                splitByState = splitByState,
                tipAmountState = tipAmountState,
                totalPerPersonState = totalPerPersonState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetTipApp(
    totalBillState: MutableState<String>,
    sliderPositionState: MutableFloatState,
    splitByState: MutableIntState,
    tipAmountState: MutableDoubleState,
    totalPerPersonState: MutableDoubleState
) {
    JetTipAppTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Jet Tip",
                            textAlign = TextAlign.Center,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        )
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = paddingValues.calculateTopPadding()
                )
            ) {
                TopHeader(totalPerPerson = totalPerPersonState.value)
                Spacer(modifier = Modifier.height(16.dp))
                MainContent(
                    totalBillState = totalBillState,
                    sliderPositionState = sliderPositionState,
                    splitByState = splitByState,
                    tipAmountState = tipAmountState,
                    totalPerPersonState = totalPerPersonState
                )
            }
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double) {
    val total = "%.2f".format(totalPerPerson)
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(12.dp))),
        color = Color(0xFFE9D7F7),
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Total Per Person",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 24.sp
            )
            Text(
                text = "$${total}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun MainContent(
    totalBillState: MutableState<String>,
    sliderPositionState: MutableFloatState,
    splitByState: MutableIntState,
    tipAmountState: MutableDoubleState,
    totalPerPersonState: MutableDoubleState
) {
    BillForm(
        totalBillState = totalBillState,
        sliderPositionState = sliderPositionState,
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState
    )
}

@Composable
fun BillForm(
    totalBillState: MutableState<String>,
    sliderPositionState: MutableFloatState,
    splitByState: MutableIntState,
    tipAmountState: MutableDoubleState,
    totalPerPersonState: MutableDoubleState
) {
    val validState = totalBillState.value.trim().isNotEmpty()
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        border = BorderStroke(width = 1.dp, color = Color.LightGray),
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(all = 6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                modifier = Modifier.fillMaxWidth(),
                valueState = totalBillState,
                labelId = "Enter Bill",
                enabled = true,
                isSingleLine = true,
                onAction = KeyboardActions {
                    if (!validState) return@KeyboardActions
                    keyboardController?.hide()
                })

            if (validState) {
                val tipPercentage = (sliderPositionState.floatValue * 100).toInt()
                SplitSection(
                    splitByState = splitByState,
                    range = IntRange(start = 1, endInclusive = 100),
                    totalBill = totalBillState.value,
                    tipPercentage = tipPercentage,
                    totalPerPersonState = totalPerPersonState
                )
                TipSection(
                    sliderPositionState = sliderPositionState,
                    tipAmountState = tipAmountState,
                    totalBill = totalBillState.value,
                    splitByState = splitByState,
                    totalPerPersonState = totalPerPersonState
                )
            }
        }
    }
}

@Composable
fun SplitSection(
    splitByState: MutableIntState,
    range: IntRange,
    totalBill: String,
    tipPercentage: Int,
    totalPerPersonState: MutableDoubleState
) {
    val totalBillValue = totalBill.toDoubleOrNull() ?: 0.0

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Split",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Row(
            modifier = Modifier.padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RoundIconButton(
                imageVector = Icons.Default.Remove,
                onClick = {
                    if (splitByState.intValue > 1) {
                        splitByState.intValue -= 1
                        totalPerPersonState.value = calculateTotalPerPerson(
                            totalBill = totalBillValue,
                            splitBy = splitByState.value,
                            tipPercentage = tipPercentage
                        )
                    }
                }
            )
            Text(
                text = "${splitByState.intValue}",
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            )
            RoundIconButton(
                imageVector = Icons.Default.Add,
                onClick = {
                    if (splitByState.intValue < range.last) {
                        splitByState.intValue += 1
                        totalPerPersonState.value = calculateTotalPerPerson(
                            totalBill = totalBillValue,
                            splitBy = splitByState.value,
                            tipPercentage = tipPercentage
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun TipSection(
    sliderPositionState: MutableFloatState,
    tipAmountState: MutableDoubleState,
    totalBill: String,
    splitByState: MutableIntState,
    totalPerPersonState: MutableDoubleState
) {
    val totalBillValue = totalBill.toDoubleOrNull() ?: 0.0
    val tipPercentageState = remember {
        mutableIntStateOf(0)
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "${tipPercentageState.intValue}%",
        )
        Slider(
            value = sliderPositionState.floatValue,
            onValueChange = { newValue ->
                sliderPositionState.floatValue = newValue
                tipPercentageState.value = (newValue * 100).toInt()
                tipAmountState.doubleValue = calculateTotalTip(totalBillValue, tipPercentageState.value)
                totalPerPersonState.value = calculateTotalPerPerson(
                    totalBill = totalBillValue,
                    splitBy = splitByState.value,
                    tipPercentage = tipPercentageState.value
                )
            },
            valueRange = 0f..1f,
            steps = 5
        )
    }
}


@Preview(showBackground = true)
@Composable
fun JetTipAppPreview() {
    val totalBillState = remember { mutableStateOf("100.00") }
    val sliderPositionState = remember { mutableFloatStateOf(0.2f) } // 20%
    val splitByState = remember { mutableIntStateOf(2) }
    val tipAmountState = remember { mutableDoubleStateOf(20.0) } // $20 tip
    val totalPerPersonState = remember { mutableDoubleStateOf(60.0) } // $60 per person

    JetTipApp(
        totalBillState = totalBillState,
        sliderPositionState = sliderPositionState,
        splitByState = splitByState,
        tipAmountState = tipAmountState,
        totalPerPersonState = totalPerPersonState
    )
}