package com.example.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Orange

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel) {
    val onAction = viewModel::onAction
    CalculatorTheme {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Box(
                modifier = Modifier
                    .weight(.2f)
                    .fillMaxWidth()
                    .padding(40.dp)
            ) {
                InputArea(
                    text = viewModel.state.number1 +
                            (viewModel.state.operation?.symbol ?: "") +
                            viewModel.state.number2
                )
            }
                Box(
                    modifier = Modifier
                        .weight(.2f)
                        .fillMaxSize()
                        .padding(40.dp)


                    ,
                    contentAlignment = Alignment.BottomEnd
                )
                {
                    Text(
                        text = viewModel.result,
                        fontSize = 32.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            HorizontalDivider(
                color = Color.Black,
                thickness = 1.dp
            )
            Box(
                modifier = Modifier
                    .weight(.6f) // Takes up all available space
                    .fillMaxWidth()
            ) {
                ButtonsGrid(
                    onNumberClick = {
                        onAction(it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun InputArea(text: String = "") {
        Text(
            text = text,
            fontSize = 32.sp,
            color = Color.Black,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth()
        )

}

@Preview(showBackground = true)
@Composable
fun ButtonsGrid(
    onNumberClick: (CalculatorAction) -> Unit = {},
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp,20.dp)
        ,
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        items(
            listOf(
                "C", "()", "%", "÷",
                "7", "8", "9", "×",
                "4", "5", "6", "-",
                "1", "2", "3", "+",
                "+/-", "0", ".", "=",
            )
        ) { index ->
            Btn(index) {
                when (index) {
                    "C" -> onNumberClick(CalculatorAction.Clear)
                    "÷" -> onNumberClick(CalculatorAction.Operation(CalculatorOperation.Divide))
                    "×" -> onNumberClick(CalculatorAction.Operation(CalculatorOperation.Times))
                    "-" -> onNumberClick(CalculatorAction.Operation(CalculatorOperation.Subtraction))
                    "+" -> onNumberClick(CalculatorAction.Operation(CalculatorOperation.Addition))
                    "=" -> onNumberClick(CalculatorAction.Calculate)
                    "." -> onNumberClick(CalculatorAction.Decimal)
                    else -> onNumberClick(CalculatorAction.Number(index.toInt()))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Btn(
    index: String = "",
    onNumberPressed: (String) -> Unit = {}

) {
    val backgroundColor: Color =
        if (!(index >= "0" && index <= "9")) {
        Orange
    } else {
        Color.LightGray
    }
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                onNumberPressed(index)
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            index,
            color = Color.Black,
            fontSize = 28.sp
        )
    }
}

