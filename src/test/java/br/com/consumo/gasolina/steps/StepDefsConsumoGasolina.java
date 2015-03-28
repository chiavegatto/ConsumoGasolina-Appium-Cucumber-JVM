package br.com.consumo.gasolina.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.consumo.gasolina.sql.QuerysAbastecimentos;
import br.com.consumo.gasolina.utils.AppiumUtil;
import cucumber.api.DataTable;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class StepDefsConsumoGasolina extends StepDef{

	protected final String CAMINHO_APK = "src/test/resources/Consumo-Gasolina-0.0.2-SNAPSHOT.apk";
	
	@Before
	public void configuracoesIniciais() throws MalformedURLException{
		File appFile = new File(CAMINHO_APK);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
		capabilities.setCapability(MobileCapabilityType.APP, appFile);
		driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	}
	
	@After
	public void finalizarTestes(Scenario scenario){
		if(scenario.isFailed()){
			byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
		}
		driver.quit();
	}
	
	@Given("^estou acessando a aplicação Consumo Gasolina$")
	public void estou_acessando_a_aplicação_Consumo_Gasolina() throws Throwable {
		assertEquals("Desempenho Mensal", driver.findElement(By.id("android:id/action_bar_title")).getText());
	}

	@When("^acesso o menu \"(.*?)\"$")
	public void acesso_o_menu(String txMenu) throws Throwable {
		driver.findElement(By.id("br.com.consumogasolina:id/action_opcoes")).click();
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+txMenu+"\")").click();
	}

	@When("^preencho o abastecimento com os seguintes valores:$")
	public void preencho_o_abastecimento_com_os_seguintes_valores(DataTable dadosAbastecimento) throws Throwable {
		for(Map<String, String> abastecimento : dadosAbastecimento.asMaps(String.class, String.class)){
			driver.findElement(By.id("br.com.consumogasolina:id/editText_km_abastecimento")).sendKeys(abastecimento.get("KM Abastecimento"));
			driver.findElement(By.id("br.com.consumogasolina:id/editText_quantidade_litros")).sendKeys(abastecimento.get("Quantidade Litros"));
			driver.findElement(By.id("br.com.consumogasolina:id/editText_valor")).sendKeys(abastecimento.get("Valor"));
			if(abastecimento.get("Data Abastecimento")!=null){
				driver.findElement(By.id("br.com.consumogasolina:id/editText_data")).click();
				AppiumUtil.preencheDatePicker(abastecimento.get("Data Abastecimento"),driver);
			}
		}
	}

	@When("^pressiono o botão \"(.*?)\"$")
	public void pressiono_o_botão(String txBotao) throws Throwable {
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+txBotao+"\")").click();
	}

	@Then("^deve ser exibido as seguintes informações no resumo:$")
	public void deve_ser_exibido_as_seguintes_informações_no_resumo(DataTable dadosResumoAbastecimento) throws Throwable {
		for(Map<String, String> resumoAbastecimento : dadosResumoAbastecimento.asMaps(String.class, String.class)){
			assertEquals(resumoAbastecimento.get("KM Litro"), driver.findElement(By.id("br.com.consumogasolina:id/editText_km_litro")).getText());
			assertEquals(resumoAbastecimento.get("Valor Abastecimentos"), driver.findElement(By.id("br.com.consumogasolina:id/editText_valor_abastecimento")).getText());
			assertEquals(resumoAbastecimento.get("Quantidade Abastecimentos"), driver.findElement(By.id("br.com.consumogasolina:id/editText_quantidade_abastecimento")).getText());
			assertEquals(resumoAbastecimento.get("Quantidade Litros"), driver.findElement(By.id("br.com.consumogasolina:id/editText_quantidade_litros")).getText());
			assertEquals(resumoAbastecimento.get("Quantidade KM"), driver.findElement(By.id("br.com.consumogasolina:id/editText_quantidade_km")).getText());
		}
	}
	
	@Given("^possuo o abastecimento cadastrado com as seguintes informações:$")
	public void possuo_o_abastecimento_cadastrado_com_as_seguintes_informações(DataTable dadosAbastecimentos) throws Throwable {
		for(Map<String, String> abastecimentos : dadosAbastecimentos.asMaps(String.class, String.class)){
			QuerysAbastecimentos.inserirAbastecimentos(abastecimentos);
		}
	}
	
	@When("^pressiono no abastecimento com a data \"(.*?)\"$")
	public void pressiono_no_abastecimento_com_a_data(String txDataAbastecimento) throws Throwable {
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+txDataAbastecimento+"\")").click();
	}
	
	@Then("^deve ser exibido o abastecimento com as seguintes informações:$")
	public void deve_ser_exibido_o_abastecimento_com_as_seguintes_informações(DataTable dadosAbastecimentos) throws Throwable {
		for(Map<String, String> abastecimentos : dadosAbastecimentos.asMaps(String.class, String.class)){
			assertEquals(abastecimentos.get("Data Abastecimento"), driver.findElement(By.id("br.com.consumogasolina:id/textView_valor_data_do_abastecimento")).getText());
			assertEquals(abastecimentos.get("Quantidade Litros"), driver.findElement(By.id("br.com.consumogasolina:id/textView_valor_litros_do_abastecimento")).getText());
			assertEquals(abastecimentos.get("Valor"), driver.findElement(By.id("br.com.consumogasolina:id/textView_valor_abastecido")).getText());
			assertEquals(abastecimentos.get("KM Abastecimento"), driver.findElement(By.id("br.com.consumogasolina:id/textView_valor_km_no_abastecimento")).getText());
		}
	}
	
	@When("^pressiono longamente no abastecimento com a data \"(.*?)\"$")
	public void pressiono_longamente_no_abastecimento_com_a_data(String txDataAbastecimento) throws Throwable {
		MobileElement abastecimento = (MobileElement) driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+txDataAbastecimento+"\")");
		TouchAction touch = new TouchAction(driver);
		touch.longPress(abastecimento).perform();
	}
	
	@When("^confirmo a exclusão do abastecimento$")
	public void confirmo_a_exclusão_do_abastecimento() throws Throwable {
		driver.findElement(By.id("android:id/button1")).click();
	}


	@Then("^deve ser exibido a mensagem \"(.*?)\" na listagem de abastecimentos$")
	public void deve_ser_exibido_a_mensagem_na_listagem_de_abastecimentos(String txMensagem) throws Throwable {
		assertEquals(txMensagem, driver.findElement(By.id("android:id/empty")).getText());
	}
	
	@When("^Preencho a data inicial \"(.*?)\" e data final \"(.*?)\"$")
	public void preencho_a_data_inicial_e_data_final(String dataInicial, String dataFinal) throws Throwable {
		driver.findElement(By.id("br.com.consumogasolina:id/editText_data_inicio")).click();
		AppiumUtil.preencheDatePicker(dataInicial, driver);
		driver.findElement(By.id("br.com.consumogasolina:id/editText_data_final")).click();
		AppiumUtil.preencheDatePicker(dataFinal, driver);
	}

	@When("^seleciono o tipo de visualização \"(.*?)\"$")
	public void seleciono_o_tipo_de_visualização(String txTipoVisualizacao) throws Throwable {
		driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+txTipoVisualizacao+"\")").click();
	}
	
	@Then("^deve ser exibido os abastecimentos:$")
	public void deve_ser_exibido_os_abastecimentos(DataTable dadosAbastecimento) throws Throwable {
		for(Map<String, String> abastecimentos : dadosAbastecimento.asMaps(String.class, String.class)){
			assertTrue("KM do abastecimento não exibido.",driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+abastecimentos.get("KM Abastecimento")+"\")").isDisplayed());
			assertTrue("Quantidade do abastecimento não exibido.",driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+abastecimentos.get("Quantidade Litros")+"\")").isDisplayed());
			assertTrue("Valor do abastecimento não exibido.",driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+abastecimentos.get("Valor")+"\")").isDisplayed());
			assertTrue("Data do Abastecimento não exibido.",driver.findElementByAndroidUIAutomator("new UiSelector().text(\""+abastecimentos.get("Data Abastecimento")+"\")").isDisplayed());
		}
	}
}
