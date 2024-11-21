package com.example.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.example.jettipapp.util.calculateTotalTip
import com.example.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetTipApp { paddingValues ->
                Column(
                    modifier = Modifier.padding(
                        horizontal = 16.dp,
                        vertical = paddingValues.calculateTopPadding()
                    )
                ) {
                    TopHeader()
                    Spacer(modifier = Modifier.height(16.dp))
                    MainContent()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetTipApp(content: @Composable (PaddingValues) -> Unit) {
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
            content(paddingValues)
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double = 33.00) {
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
fun MainContent() {
    BillForm(Modifier) {

    }
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier, onValChange: (String) -> Unit = {}
) {
    val totalBillState = remember {
        mutableStateOf("")
    }
    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }
    val sliderPositionState = remember {
        mutableFloatStateOf(0f)
    }
    val splitByState = remember {
        mutableIntStateOf(1)
    }
    val tipAmountState = remember {
        mutableDoubleStateOf(0.0)
    }

    val range = IntRange(start = 1, endInclusive = 100)
    val keyboardController = LocalSoftwareKeyboardController.current


    Surface(
        modifier = modifier
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
                    onValChange(totalBillState.value.trim())
                    keyboardController?.hide()
                })

            if (validState) {
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
                                splitByState.intValue = if (splitByState.intValue > 1) {
                                    splitByState.intValue - 1
                                } else {
                                    1
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
                                }
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tip",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = "$${tipAmountState.doubleValue}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${tipAmountState.doubleValue}%",
                    )
                    Slider(
                        value = sliderPositionState.floatValue,
                        onValueChange = { newValue ->
                            sliderPositionState.floatValue = newValue
                            val tipPercentage = (newValue * 100).toInt()
                            tipAmountState.doubleValue =
                                calculateTotalTip(
                                    totalBill = totalBillState.value.toDouble(),
                                    tipPercentage = tipPercentage
                                )
                        },
                        valueRange = 0f..1f,
                        steps = 5
                    )
                }
            } else {
                Box {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetTipAppPreview() {
    JetTipApp { paddingValues ->
        Column(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = paddingValues.calculateTopPadding()
            )
        ) {
            TopHeader()
            Spacer(modifier = Modifier.height(16.dp))
            MainContent()
        }
    }
}