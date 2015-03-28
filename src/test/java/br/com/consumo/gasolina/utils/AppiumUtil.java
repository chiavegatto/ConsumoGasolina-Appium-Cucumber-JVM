package br.com.consumo.gasolina.utils;

import io.appium.java_client.android.AndroidDriver;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AppiumUtil {

	public static void preencheDatePicker(String data, AndroidDriver driver) throws ParseException {
		List<WebElement> datePicker = driver.findElements(By.className("android.widget.NumberPicker"));
		Calendar dataParaPreenchimento = Util.converteStringEmCalendar(data);
		datePicker.get(0).sendKeys(Util.retornaMesEmExtensoPassandoUmInteiro(dataParaPreenchimento.get(Calendar.MONTH)));
		datePicker.get(1).sendKeys(String.valueOf(dataParaPreenchimento.get(Calendar.DATE)));
		datePicker.get(2).sendKeys(String.valueOf(dataParaPreenchimento.get(Calendar.YEAR)));
		driver.findElement(By.id("android:id/button1")).click();
	}
}
